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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.actions.ActionExecutor;
import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.actions.ActionHandlerRegistry;
import org.eclipse.glsp.server.actions.ResponseAction;
import org.eclipse.glsp.server.di.ClientId;
import org.eclipse.glsp.server.features.core.model.UpdateModelAction;
import org.eclipse.glsp.server.utils.FutureUtil;

import com.google.inject.Inject;

/**
 * <p>
 * An ActionDispatcher that executes all handlers with the {@link ActionExecutor}. The dispatcher's
 * public methods can be invoked from any thread, e.g. from background jobs running on
 * the server.
 * </p>
 */
public class DefaultActionDispatcher implements ActionDispatcher {

   private static final Logger LOG = Logger.getLogger(DefaultActionDispatcher.class);

   @Inject
   protected ActionHandlerRegistry actionHandlerRegistry;

   @Inject
   protected ActionExecutor actionExecutor;

   @Inject
   @ClientId
   protected String clientId;

   protected List<Action> postUpdateQueue = new ArrayList<>();

   // Results will be placed in the map when the action dispatcher receives a new action (From arbitrary threads),
   // and will be removed from the dispatcher's thread.
   protected final Map<Action, CompletableFuture<Void>> results = Collections.synchronizedMap(new HashMap<>());

   @Override
   public CompletableFuture<Void> dispatch(final Action action) {
      CompletableFuture<Void> result = new CompletableFuture<>();
      results.put(action, result);
      if (actionExecutor.isExecutorThread(Thread.currentThread())) {
         // Actions dispatched from the ActionExecutor don't have to go back
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
      try {
         actionExecutor.execute(() -> handleAction(action));
      } catch (RejectedExecutionException ex) {
         if (actionExecutor.isShutdown()) {
            LOG.warn(String.format(
               "Received an action after the ActionExecutor was stopped. Ignoring action: %s", action));
            return;
         }
         throw new IllegalStateException("Action was rejected: " + action, ex);
      }
   }

   @SuppressWarnings("checkstyle:IllegalCatch")
   protected void handleAction(final Action action) {
      if (action == null) {
         LOG.warn(String.format("Received a null action for client %s", clientId));
         return;
      }

      try {
         List<CompletableFuture<Void>> actionResults = runAction(action);
         CompletableFuture<Void> result = FutureUtil.aggregateResults(actionResults);
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

      List<CompletableFuture<Void>> actionResults = new ArrayList<>();
      for (final ActionHandler actionHandler : actionHandlers) {
         final List<Action> responses = actionHandler.execute(action).stream()
            .map(response -> ResponseAction.respond(action, response))
            .collect(Collectors.toList());
         actionResults.addAll(dispatchAll(responses));
         if (action instanceof UpdateModelAction) {
            actionResults.add(dispatchPostUpdateQueue());
         }
      }
      return actionResults;
   }

   protected CompletableFuture<Void> dispatchPostUpdateQueue() {
      ArrayList<Action> toDispatch = new ArrayList<>(postUpdateQueue);
      postUpdateQueue.clear();
      dispatchAll(toDispatch);
      return CompletableFuture.completedFuture(null);
   }
}
