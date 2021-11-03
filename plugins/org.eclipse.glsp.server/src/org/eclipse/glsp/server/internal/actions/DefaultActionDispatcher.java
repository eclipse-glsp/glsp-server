/********************************************************************************
 * Copyright (c) 2019-2021 EclipseSource and others.
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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.actions.ActionHandlerRegistry;
import org.eclipse.glsp.server.actions.ResponseAction;
import org.eclipse.glsp.server.di.ClientId;
import org.eclipse.glsp.server.disposable.Disposable;
import org.eclipse.glsp.server.features.core.model.UpdateModelAction;
import org.eclipse.glsp.server.protocol.GLSPClient;
import org.eclipse.glsp.server.utils.FutureUtil;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * <p>
 * An ActionDispatcher that executes all handlers in the same thread. The dispatcher's
 * public methods can be invoked from any thread, e.g. from background jobs running on
 * the server.
 * </p>
 */
public class DefaultActionDispatcher extends Disposable implements ActionDispatcher {

   private static final Logger LOG = Logger.getLogger(DefaultActionDispatcher.class);

   private static final AtomicInteger COUNT = new AtomicInteger(0);

   @Inject
   protected ActionHandlerRegistry actionHandlerRegistry;

   @Inject
   @ClientId
   protected String clientId;

   protected final String name;

   protected final Thread thread;

   protected final BlockingQueue<Action> actionsQueue = new ArrayBlockingQueue<>(100, true);

   protected List<Action> postUpdateQueue = new ArrayList<>();

   // Results will be placed in the map when the action dispatcher receives a new action (From arbitrary threads),
   // and will be removed from the dispatcher's thread.
   protected final Map<Action, CompletableFuture<Void>> results = Collections.synchronizedMap(new HashMap<>());

   // Use a provider, as the GLSPClient is probably not created yet. We won't receive
   // any action until it's ready anyway.
   @Inject
   protected Provider<GLSPClient> client;

   public DefaultActionDispatcher() {
      this.name = getClass().getSimpleName() + " " + COUNT.incrementAndGet();
      this.thread = new Thread(this::runThread);
      this.thread.setName(this.name);
      this.thread.setDaemon(true);
      this.thread.start();
   }

   @Override
   public CompletableFuture<Void> dispatch(final Action action) {

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
   public void dispatchAfterNextUpdate(final Action... actions) {
      postUpdateQueue.addAll(Arrays.asList(actions));
   }

   protected void addToQueue(final Action action) {
      if (Thread.currentThread() == this.thread) {
         LOG.error("Actions shouldn't be added to the actions queue from the dispatcher thread!");
         // Handle the action immediately, to avoid deadlocks when the queue if full
         handleAction(action);
         return;
      }
      boolean success = actionsQueue.offer(action);
      while (!success) {
         if (!thread.isAlive() || thread.isInterrupted()) {
            // This may happen if e.g. some background tasks were still running when the client disconnected.
            // This (probably) isn't critical and can be safely ignored.
            LOG.warn(String.format(
               "Received an action after the ActionDispatcher was stopped. Ignoring action: %s", action));
            return;
         }
         try {
            // The queue may be temporarily full because we receive a lot of actions (e.g. during initialization),
            // but if this keeps failing for a long time, it might indicate a deadlock
            success = actionsQueue.offer(action, 1, TimeUnit.SECONDS);
            if (!success) {
               LOG.warn(String.format("Actions queue is currently full for dispatcher %s ; retrying...", name));
            }
         } catch (final InterruptedException ex) {
            break;
         }
      }
   }

   private void runThread() {
      while (true) {
         try {
            handleNextAction();
         } catch (final InterruptedException e) {
            LOG.info(String.format("Terminating DefaultActionDispatcher thread %s", Thread.currentThread().getName()));
            break;
         }
      }
      LOG.info("Terminating DefaultActionDispatcher");
   }

   private void handleNextAction()
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
         LOG.warn(String.format("Received a null action for client %s", clientId));
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
      final List<ActionHandler> actionHandlers = actionHandlerRegistry.get(action);
      if (actionHandlers.isEmpty()) {
         throw new IllegalArgumentException("No handler registered for action: " + action);
      }

      List<CompletableFuture<Void>> results = new ArrayList<>();
      for (final ActionHandler actionHandler : actionHandlers) {
         final List<Action> responses = actionHandler.execute(action).stream()
            .map(response -> ResponseAction.respond(action, response))
            .collect(Collectors.toList());
         results.addAll(dispatchAll(responses));
         if (action instanceof UpdateModelAction) {
            results.add(dispatchPostUpdateQueue());
         }
      }
      return results;
   }

   protected CompletableFuture<Void> dispatchPostUpdateQueue() {
      ArrayList<Action> toDispatch = new ArrayList<>(postUpdateQueue);
      postUpdateQueue.clear();
      dispatchAll(toDispatch);
      return CompletableFuture.completedFuture(null);
   }

   protected final void checkThread() {
      if (Thread.currentThread() != thread) {
         throw new IllegalStateException(
            "This method should only be invoked from the ActionDispatcher's thread: " + name);
      }
   }

   @Override
   public void doDispose() {
      if (thread.isAlive()) {
         thread.interrupt();
      }
   }
}
