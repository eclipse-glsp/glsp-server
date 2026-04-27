/********************************************************************************
 * Copyright (c) 2019-2026 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ********************************************************************************/
package org.eclipse.glsp.server.internal.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.glsp.server.actions.AbstractActionHandler;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.actions.ActionHandlerRegistry;
import org.eclipse.glsp.server.actions.ClientActionForwarder;
import org.eclipse.glsp.server.actions.RejectAction;
import org.eclipse.glsp.server.actions.RequestAction;
import org.eclipse.glsp.server.actions.ResponseAction;
import org.eclipse.glsp.server.di.ClientId;
import org.eclipse.glsp.server.disposable.Disposable;
import org.eclipse.glsp.server.features.core.model.SetModelAction;
import org.eclipse.glsp.server.features.core.model.UpdateModelAction;
import org.eclipse.glsp.server.types.GLSPServerException;
import org.eclipse.glsp.server.utils.FutureUtil;

import com.google.inject.Inject;

/**
 * <p>
 * An ActionDispatcher that executes all handlers in the same thread. The dispatcher's
 * public methods can be invoked from any thread, e.g. from background jobs running on
 * the server.
 * </p>
 */
public class DefaultActionDispatcher extends Disposable implements ActionDispatcher {

   protected static final Logger LOGGER = LogManager.getLogger(DefaultActionDispatcher.class);

   protected static final long STALE_TIMEOUT_GRACE_MS = 30_000L;

   private static final AtomicInteger COUNT = new AtomicInteger(0);

   @Inject
   protected ActionHandlerRegistry actionHandlerRegistry;

   @Inject
   @ClientId
   protected String clientId;

   @Inject
   protected ClientActionForwarder clientActionForwarder;

   protected String name;

   protected Thread thread;

   protected final BlockingQueue<Action> actionsQueue = new ArrayBlockingQueue<>(100, true);

   // Guarded by postUpdateLock: written from any thread that drains a response.
   protected final List<Action> postUpdateQueue = new ArrayList<>();
   protected final Object postUpdateLock = new Object();

   // Results will be placed in the map when the action dispatcher receives a new action (From arbitrary threads),
   // and will be removed from the dispatcher's thread.
   protected final Map<Action, CompletableFuture<Void>> results = Collections.synchronizedMap(new HashMap<>());

   // Pending server-initiated requests awaiting a matching response. Keyed by requestId.
   // Accessed from multiple threads (dispatcher thread, JSON-RPC thread, scheduler thread).
   protected final Map<String, CompletableFuture<? extends ResponseAction>> pendingRequests
      = new ConcurrentHashMap<>();

   // Active timeout markers per requestId. After the timeout fires the entry is kept briefly as
   // a stale marker to filter late responses, then cleaned up after STALE_TIMEOUT_GRACE_MS.
   protected final Map<String, ScheduledFuture<?>> requestTimeouts = new ConcurrentHashMap<>();

   protected final AtomicInteger nextRequestId = new AtomicInteger(1);

   protected ScheduledExecutorService scheduler;

   public DefaultActionDispatcher() {
      this.initialize();
   }

   protected void initialize() {
      this.name = getClass().getSimpleName() + " " + COUNT.incrementAndGet();
      this.scheduler = this.createScheduler();
      this.thread = this.createThread();
      this.initializeThread(thread, name);
      thread.start();
   }

   protected String generateRequestId() {
      return "server_" + clientId + "_" + nextRequestId.getAndIncrement();
   }

   /**
    * Grace period in milliseconds during which a stale-timeout marker is retained after a
    * request times out, so that a slightly late response can still be filtered. Override to
    * tune; subclasses (notably tests) should keep this short.
    */
   protected long getStaleTimeoutGraceMs() {
      return STALE_TIMEOUT_GRACE_MS;
   }

   protected Thread createThread() {
      return new Thread(this::runThread);
   }

   protected ScheduledExecutorService createScheduler() {
      return Executors.newSingleThreadScheduledExecutor(runnable -> {
         Thread schedulerThread = new Thread(runnable);
         initializeThread(schedulerThread, name + "-scheduler");
         return schedulerThread;
      });
   }

   protected void initializeThread(final Thread thread, final String name) {
      thread.setName(name);
      thread.setDaemon(true);
   }

   @Override
   public CompletableFuture<Void> dispatch(final Action action) {
      // Intercept first to avoid deadlock: a handler may be awaiting this response.
      if (interceptPendingResponse(action)) {
         return CompletableFuture.completedFuture(null);
      }
      CompletableFuture<Void> result = new CompletableFuture<>();
      results.put(action, result);
      if (thread == Thread.currentThread()) {
         // Actions dispatched from the ActionDispatcher thread don't have to go back
         // to the queue, as they are just fragments of the current action from the queue.
         // Process them immediately.
         handleAction(action);
      } else {
         addToQueue(action);
      }
      return result;
   }

   @Override
   public <RES extends ResponseAction> CompletableFuture<RES> request(final RequestAction<RES> action) {
      return doRequest(action, null, true);
   }

   @Override
   public <RES extends ResponseAction> CompletableFuture<RES> requestUntil(final RequestAction<RES> action,
      final long timeoutMs, final boolean rejectOnTimeout) {
      return doRequest(action, timeoutMs >= 0 ? timeoutMs : null, rejectOnTimeout);
   }

   /**
    * A handler that calls {@code .get()} or {@code .join()} on the returned future from the
    * dispatcher thread blocks that thread until the response arrives, holding the action queue
    * during the wait. This is intentional: the queue's purpose is to serialize handler
    * execution. Use {@link #requestUntil(RequestAction, long, boolean) requestUntil} with a
    * positive timeout to bound the wait when the responding side may not reply.
    *
    * @param timeoutMs Maximum wait time in milliseconds, or {@code null} to wait indefinitely.
    */
   protected <RES extends ResponseAction> CompletableFuture<RES> doRequest(final RequestAction<RES> action,
      final Long timeoutMs, final boolean rejectOnTimeout) {
      if (action.getRequestId() == null || action.getRequestId().isEmpty()) {
         action.setRequestId(generateRequestId());
      }
      action.setTimeout(timeoutMs);

      final String requestId = action.getRequestId();
      final CompletableFuture<RES> deferred = new CompletableFuture<>();
      pendingRequests.put(requestId, deferred);

      if (timeoutMs != null) {
         scheduleRequestTimeout(requestId, action.getKind(), timeoutMs, rejectOnTimeout, deferred);
      }

      // dispatch() routes correctly: external callers queue, dispatcher-thread callers run inline.
      // The matching response resolves the deferred via interceptPendingResponse().
      dispatch(action).exceptionally(error -> {
         failPendingRequest(requestId, error, deferred);
         return null;
      });

      return deferred;
   }

   protected <RES extends ResponseAction> void scheduleRequestTimeout(final String requestId, final String kind,
      final long timeoutMs, final boolean rejectOnTimeout, final CompletableFuture<RES> deferred) {
      ScheduledFuture<?> timeout = scheduler.schedule(() -> {
         if (pendingRequests.remove(requestId) == null) {
            return;
         }
         // Keep the requestTimeouts entry briefly as a stale marker so a late response can be
         // filtered, then drop it after a grace period to avoid leaking markers for requests
         // whose late responses never arrive.
         scheduler.schedule(() -> requestTimeouts.remove(requestId),
            getStaleTimeoutGraceMs(), TimeUnit.MILLISECONDS);
         String message = String.format("Request '%s' (%s) timed out after %dms", requestId, kind, timeoutMs);
         if (rejectOnTimeout) {
            deferred.completeExceptionally(new TimeoutException(message));
         } else {
            LOGGER.info(message);
            deferred.complete(null);
         }
      }, timeoutMs, TimeUnit.MILLISECONDS);
      requestTimeouts.put(requestId, timeout);
   }

   protected void failPendingRequest(final String requestId, final Throwable error,
      final CompletableFuture<?> deferred) {
      if (pendingRequests.remove(requestId) == null) {
         return;
      }
      ScheduledFuture<?> pendingTimeout = requestTimeouts.remove(requestId);
      if (pendingTimeout != null) {
         pendingTimeout.cancel(false);
      }
      deferred.completeExceptionally(error);
   }

   @Override
   public void dispatchAfterNextUpdate(final Action... actions) {
      synchronized (postUpdateLock) {
         postUpdateQueue.addAll(Arrays.asList(actions));
      }
   }

   protected void addToQueue(final Action action) {
      if (Thread.currentThread() == this.thread) {
         LOGGER.error("Actions shouldn't be added to the actions queue from the dispatcher thread!");
         // Handle the action immediately, to avoid deadlocks when the queue if full
         handleAction(action);
         return;
      }
      boolean success = actionsQueue.offer(action);
      while (!success) {
         if (!thread.isAlive() || thread.isInterrupted()) {
            // This may happen if e.g. some background tasks were still running when the client disconnected.
            // This (probably) isn't critical and can be safely ignored.
            LOGGER.warn(String.format(
               "Received an action after the ActionDispatcher was stopped. Ignoring action: %s", action));
            return;
         }
         try {
            // The queue may be temporarily full because we receive a lot of actions (e.g. during initialization),
            // but if this keeps failing for a long time, it might indicate a deadlock
            success = actionsQueue.offer(action, 1, TimeUnit.SECONDS);
            if (!success) {
               LOGGER.warn(String.format("Actions queue is currently full for dispatcher %s ; retrying...", name));
            }
         } catch (final InterruptedException ex) {
            break;
         }
      }
   }

   protected void runThread() {
      while (true) {
         try {
            handleNextAction();
         } catch (final InterruptedException e) {
            LOGGER
               .info(String.format("Terminating DefaultActionDispatcher thread %s", Thread.currentThread().getName()));
            break;
         }
      }
      LOGGER.info("Terminating DefaultActionDispatcher");
   }

   protected void handleNextAction()
      throws InterruptedException {
      final Action action = actionsQueue.take();
      if (action != null) {
         handleAction(action);
      }
   }

   @SuppressWarnings("checkstyle:IllegalCatch")
   protected void handleAction(final Action action) {
      checkThread();
      if (action == null) {
         LOGGER.warn(String.format("Received a null action for client %s", clientId));
         return;
      }

      try {
         List<CompletableFuture<Void>> results = runAction(action);
         CompletableFuture<Void> result = FutureUtil.aggregateResults(results);
         result.thenAccept(any -> {
            this.results.remove(action).complete(null);
         }).exceptionally(t -> {
            this.results.remove(action).completeExceptionally(t);
            return null;
         });
      } catch (Throwable t) {
         results.remove(action).completeExceptionally(t);
      }
   }

   protected List<CompletableFuture<Void>> runAction(final Action action) {
      boolean handledOnClient = clientActionForwarder.handle(action);
      final List<ActionHandler> actionHandlers = actionHandlerRegistry.get(action);
      if (!handledOnClient && actionHandlers.isEmpty()) {
         throw new IllegalArgumentException("No handler registered for action: " + action);
      }

      List<CompletableFuture<Void>> results = new ArrayList<>();
      for (final ActionHandler actionHandler : actionHandlers) {
         final List<Action> responses = actionHandler.execute(action).stream()
            .map(response -> ResponseAction.respond(action, response))
            .collect(Collectors.toList());
         results.addAll(dispatchAll(responses));
      }
      if (action instanceof UpdateModelAction || action instanceof SetModelAction) {
         results.add(dispatchPostUpdateQueue());
      }
      return results;
   }

   protected CompletableFuture<Void> dispatchPostUpdateQueue() {
      List<Action> toDispatch = drainPostUpdateQueue();
      dispatchAll(toDispatch);
      return CompletableFuture.completedFuture(null);
   }

   protected List<Action> drainPostUpdateQueue() {
      synchronized (postUpdateLock) {
         if (postUpdateQueue.isEmpty()) {
            return Collections.emptyList();
         }
         List<Action> drained = new ArrayList<>(postUpdateQueue);
         postUpdateQueue.clear();
         return drained;
      }
   }

   protected final void checkThread() {
      if (Thread.currentThread() != thread) {
         throw new IllegalStateException(
            "This method should only be invoked from the ActionDispatcher's thread: " + name);
      }
   }

   /**
    * Checks whether the given action is a response matching a pending {@link #request} or
    * {@link #requestUntil} call. If matched, completes (or fails) the corresponding future and
    * returns {@code true} so the caller can short-circuit normal dispatch.
    *
    * <p>
    * For responses with a populated {@code responseId} but no matching pending request, checks
    * for a stale timeout marker (timed-out request) and clears the {@code responseId} so the
    * action is not forwarded by {@link ClientActionForwarder}. If no stale marker exists the
    * {@code responseId} is left intact for normal forwarding.
    * </p>
    */
   protected boolean interceptPendingResponse(final Action action) {
      if (!(action instanceof ResponseAction response)) {
         return false;
      }
      String responseId = response.getResponseId();
      if (responseId == null || responseId.isEmpty()) {
         return false;
      }
      CompletableFuture<? extends ResponseAction> deferred = pendingRequests.remove(responseId);
      if (deferred != null) {
         return resolvePendingResponse(deferred, response);
      }
      clearStaleMarker(responseId, response);
      return false;
   }

   @SuppressWarnings({ "unchecked", "rawtypes" })
   protected boolean resolvePendingResponse(final CompletableFuture<? extends ResponseAction> deferred,
      final ResponseAction response) {
      ScheduledFuture<?> timeout = requestTimeouts.remove(response.getResponseId());
      if (timeout != null) {
         timeout.cancel(false);
      }
      // Intercepted responses skip handleAction, so drain post-update actions here when the
      // response is a SetModel. RejectAction does not trigger a drain; pending post-update
      // actions stay queued until the next successful update.
      List<Action> postUpdateActions = drainPostUpdateForResponse(response);
      if (response instanceof RejectAction reject) {
         String message = reject.getMessage()
            + reject.getDetail().map(detail -> ": " + detail).orElse("");
         deferred.completeExceptionally(new GLSPServerException(message));
      } else {
         ((CompletableFuture) deferred).complete(response);
      }
      if (!postUpdateActions.isEmpty()) {
         dispatchPostUpdateAfterResponse(postUpdateActions);
      }
      return true;
   }

   protected List<Action> drainPostUpdateForResponse(final ResponseAction response) {
      if (!(response instanceof SetModelAction)) {
         return Collections.emptyList();
      }
      return drainPostUpdateQueue();
   }

   /** Fire-and-forget: failures here should not block the request resolution. */
   protected void dispatchPostUpdateAfterResponse(final List<Action> actions) {
      List<CompletableFuture<Void>> futures = dispatchAll(actions);
      if (futures.isEmpty()) {
         return;
      }
      CompletableFuture.allOf(futures.toArray(new CompletableFuture<?>[0]))
         .exceptionally(ex -> {
            LOGGER.error("Failed to dispatch post-update actions", ex);
            return null;
         });
   }

   /**
    * Late response for a timed-out request: clear {@code responseId} so
    * {@link ClientActionForwarder} does not re-emit it to the client.
    */
   protected void clearStaleMarker(final String responseId, final ResponseAction response) {
      ScheduledFuture<?> staleTimeout = requestTimeouts.remove(responseId);
      if (staleTimeout == null) {
         return;
      }
      staleTimeout.cancel(false);
      LOGGER.debug(String.format("Late response for timed-out request '%s', dispatching as normal action", responseId));
      response.setResponseId("");
   }

   protected void executeAllPendingActions() {
      // block until all pending actions have been executed
      dispatch(new JoinAction()).join();
   }

   @Override
   public void doDispose() {
      // Cancel pending requests first so any awaiting handler unblocks.
      pendingRequests.forEach((id, deferred) -> deferred.completeExceptionally(
         new IllegalStateException("Request '" + id + "' cancelled: dispatcher disposed")));
      pendingRequests.clear();
      requestTimeouts.values().forEach(timeout -> timeout.cancel(false));
      requestTimeouts.clear();
      if (scheduler != null) {
         scheduler.shutdownNow();
      }
      // Reject queued actions: no further processing should happen after dispose.
      List<Action> remaining = new ArrayList<>();
      actionsQueue.drainTo(remaining);
      remaining.forEach(action -> {
         CompletableFuture<Void> result = results.remove(action);
         if (result != null) {
            result.completeExceptionally(new IllegalStateException("ActionDispatcher disposed"));
         }
      });
      synchronized (postUpdateLock) {
         postUpdateQueue.clear();
      }
      if (thread.isAlive()) {
         thread.interrupt();
      }
   }

   /**
    * An internal action class that is used to define a join-point within the queue of all pending actions.
    */
   public static class JoinAction extends Action {
      public JoinAction() {
         super("internal.join");
      }
   }

   public static class JoinActionHandler extends AbstractActionHandler<JoinAction> {
      @Override
      protected List<Action> executeAction(final JoinAction actualAction) {
         return none();
      }
   }
}
