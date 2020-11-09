/********************************************************************************
 * Copyright (c) 2019-2020 EclipseSource and others.
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
package org.eclipse.glsp.server.internal.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import org.eclipse.glsp.server.actions.ActionMessage;
import org.eclipse.glsp.server.actions.InitializeClientSessionAction;
import org.eclipse.glsp.server.actions.ResponseAction;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.model.ModelStateProvider;
import org.eclipse.glsp.server.protocol.ClientSessionListener;
import org.eclipse.glsp.server.protocol.ClientSessionManager;
import org.eclipse.glsp.server.protocol.GLSPClient;
import org.eclipse.glsp.server.protocol.GLSPServerException;
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
public class DefaultActionDispatcher implements ActionDispatcher, ClientSessionListener {

   private static final Logger LOG = Logger.getLogger(DefaultActionDispatcher.class);

   private static final AtomicInteger COUNT = new AtomicInteger(0);

   @Inject
   protected ActionHandlerRegistry actionHandlerRegistry;

   @Inject
   protected ModelStateProvider modelStateProvider;

   private final ClientSessionManager clientSessionManager;

   protected final String name;

   protected final Thread thread;

   protected final BlockingQueue<ActionMessage> actionsQueue = new ArrayBlockingQueue<>(100, true);

   // Results will be placed in the map when the action dispatcher receives a new message (From arbitrary threads),
   // and will be removed from the dispatcher's thread.
   protected final Map<ActionMessage, CompletableFuture<Void>> results = Collections.synchronizedMap(new HashMap<>());

   // Use a provider, as the GLSPClient is probably not created yet. We won't receive
   // any message until it's ready anyway.
   @Inject
   protected Provider<GLSPClient> client;

   @Inject
   public DefaultActionDispatcher(final ClientSessionManager clientSessionManager) {
      this.name = getClass().getSimpleName() + " " + COUNT.incrementAndGet();
      this.clientSessionManager = clientSessionManager;
      this.clientSessionManager.addListener(this);
      this.thread = new Thread(this::runThread);
      this.thread.setName(this.name);
      this.thread.setDaemon(true);
      this.thread.start();
   }

   @Override
   public CompletableFuture<Void> dispatch(final String clientId, final Action action) {
      return dispatch(new ActionMessage(clientId, action));
   }

   @Override
   public CompletableFuture<Void> dispatch(final ActionMessage message) {
      if (message == null) {
         String errorMsg = String.format("Received a null message in DefaultActionDispatcher: %s", name);
         throw new IllegalArgumentException(errorMsg);
      }
      CompletableFuture<Void> result = new CompletableFuture<>();
      results.put(message, result);
      if (thread == Thread.currentThread()) {
         // Actions dispatched from the ActionDispatcher thread don't have to go back
         // to the queue, as they are just fragments of the current action from the queue.
         // Process them immediately.
         handleMessage(message);
      } else {
         addToQueue(message);
      }
      return result;
   }

   protected void addToQueue(final ActionMessage message) {
      if (Thread.currentThread() == this.thread) {
         LOG.error("ActionMessages shouldn't be added to the actions queue from the dispatcher thread!");
         // Handle the message immediately, to avoid deadlocks when the queue if full
         handleMessage(message);
         return;
      }
      boolean success = actionsQueue.offer(message);
      while (!success) {
         if (!thread.isAlive() || thread.isInterrupted()) {
            // This may happen if e.g. some background tasks were still running when the client disconnected.
            // This (probably) isn't critical and can be safely ignored.
            LOG.warn(String.format(
               "Received an action message after the ActionDispatcher was stopped. Ignoring message: %s", message));
            return;
         }
         try {
            // The queue may be temporarily full because we receive a lot of messages (e.g. during initialization),
            // but if this keeps failing for a long time, it might indicate a deadlock
            success = actionsQueue.offer(message, 1, TimeUnit.SECONDS);
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
            handleNextMessage();
         } catch (final InterruptedException e) {
            LOG.info(String.format("Terminating DefaultActionDispatcher thread %s", Thread.currentThread().getName()));
            break;
         }
      }
      LOG.info("Terminating DefaultActionDispatcher");
   }

   private void handleNextMessage()
      throws InterruptedException {
      final ActionMessage message = actionsQueue.take();
      if (message != null) {
         handleMessage(message);
      }
   }

   @SuppressWarnings("checkstyle:IllegalCatch")
   protected void handleMessage(final ActionMessage message) {
      checkThread();
      final Action action = message.getAction();
      final String clientId = message.getClientId();
      if (action == null) {
         LOG.warn(String.format("Received an action message without an action for client %s", clientId));
         return;
      }

      try {
         List<CompletableFuture<Void>> results = runAction(action, clientId);
         CompletableFuture<Void> result = FutureUtil.aggregateResults(results);
         result.thenAccept(any -> {
            this.results.remove(message).complete(null);
         }).exceptionally(t -> {
            this.results.remove(message).completeExceptionally(t);
            return null;
         });
      } catch (Throwable t) {
         results.remove(message).completeExceptionally(t);
      }
   }

   protected List<CompletableFuture<Void>> runAction(final Action action, final String clientId) {
      final List<ActionHandler> actionHandlers = actionHandlerRegistry.get(action);
      if (actionHandlers.isEmpty()) {
         throw new IllegalArgumentException("No handler registered for action: " + action);
      }

      Optional<GModelState> modelState = modelStateProvider.getModelState(clientId);

      if (!modelState.isPresent()) {
         if (action instanceof InitializeClientSessionAction) {
            modelState = Optional.of(modelStateProvider.create(clientId));
         } else {
            String errorMsg = String.format(
               "The session for client '%s' has not been initialized yet. Could not process action: %s", clientId,
               action);
            throw new GLSPServerException(errorMsg);
         }
      }

      List<CompletableFuture<Void>> results = new ArrayList<>();
      for (final ActionHandler actionHandler : actionHandlers) {
         final List<Action> responses = actionHandler.execute(action, modelState.get()).stream()
            .map(response -> ResponseAction.respond(action, response))
            .collect(Collectors.toList());
         results.addAll(dispatchAll(clientId, responses));
      }
      return results;
   }

   protected final void checkThread() {
      if (Thread.currentThread() != thread) {
         throw new IllegalStateException(
            "This method should only be invoked from the ActionDispatcher's thread: " + name);
      }
   }

   @Override
   public void clientDisconnected(final GLSPClient client) {
      if (client == this.client.get()) {
         this.thread.interrupt();
         this.clientSessionManager.removeListener(this);
      }
   }

}
