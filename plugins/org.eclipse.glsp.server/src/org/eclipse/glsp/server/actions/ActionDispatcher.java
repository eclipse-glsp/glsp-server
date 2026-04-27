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
package org.eclipse.glsp.server.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.glsp.server.disposable.IDisposable;
import org.eclipse.glsp.server.features.core.model.UpdateModelAction;

/**
 * The central component that process all {@link Action}s by dispatching them to their designated
 * handlers.
 */
public interface ActionDispatcher extends IDisposable {

   /**
    * Default timeout in milliseconds applied by {@link #requestUntil(RequestAction)} when neither
    * a timeout argument nor {@link RequestAction#getTimeout()} provides one.
    */
   long DEFAULT_REQUEST_TIMEOUT_MS = 2_000L;

   /**
    * <p>
    * Processes the given action by dispatching it to all registered handlers.
    * </p>
    *
    * @param action The action that should be dispatched.
    * @return
    *         A {@link CompletableFuture} indicating when the action processing is complete
    */
   CompletableFuture<Void> dispatch(Action action);

   /**
    * Dispatches the given actions sequentially. A failure short-circuits the batch; remaining
    * actions are not dispatched and their futures complete with the upstream failure.
    *
    * @param actions Actions to dispatch
    * @return A list of {@link CompletableFuture CompletableFutures}; one for each dispatched action
    */
   default List<CompletableFuture<Void>> dispatchAll(final List<Action> actions) {
      List<CompletableFuture<Void>> results = new ArrayList<>(actions.size());
      CompletableFuture<Void> previous = CompletableFuture.completedFuture(null);
      for (Action action : actions) {
         CompletableFuture<Void> current = previous.thenCompose(v -> dispatch(action));
         results.add(current);
         previous = current;
      }
      return results;
   }

   /**
    * Processes all given actions, by dispatching them to the corresponding handlers, after the next model update.
    * The given actions are queued until the next model update cycle has been completed i.e. an
    * {@link UpdateModelAction} has been dispatched and processed by this action dispatcher.
    *
    * @param actions The actions that should be dispatched after the next model update
    */
   void dispatchAfterNextUpdate(Action... actions);

   /**
    * Dispatches a request action and returns a future that completes when a matching response
    * action is dispatched, or completes exceptionally if the response is a {@link RejectAction}.
    * The matching response is <em>not</em> passed to registered action handlers; it is the
    * caller's responsibility to handle the response.
    *
    * <p>
    * If the request's {@code kind} is registered as a client action, it is forwarded to the
    * client. If server-side handlers are registered for the kind, they are also executed. Only
    * the first matching response resolves the request; additional or late responses are
    * dispatched as normal actions.
    * </p>
    *
    * <p>
    * The returned future never completes by timeout. Use {@link #requestUntil} if a timeout is
    * needed.
    * </p>
    *
    * <p>
    * Note: mutates {@code action.requestId} (if unset) and {@code action.timeout}.
    * </p>
    *
    * @param action The request action to dispatch.
    * @return A future that completes with the matching response action.
    */
   default <RES extends ResponseAction> CompletableFuture<RES> request(final RequestAction<RES> action) {
      throw new UnsupportedOperationException("request() is not implemented by this ActionDispatcher");
   }

   /**
    * Dispatches a request using {@link RequestAction#getTimeout} or
    * {@link #DEFAULT_REQUEST_TIMEOUT_MS} when unset. Soft timeout (no rejection).
    *
    * @param action The request action to dispatch.
    * @return The matching response, or {@code null} on soft timeout.
    */
   default <RES extends ResponseAction> CompletableFuture<RES> requestUntil(final RequestAction<RES> action) {
      Long configured = action.getTimeout();
      long effective = configured != null ? configured : DEFAULT_REQUEST_TIMEOUT_MS;
      return requestUntil(action, effective, false);
   }

   /**
    * Dispatches a request with the given timeout, completing with {@code null} on timeout
    * (no exception). Equivalent to
    * {@link #requestUntil(RequestAction, long, boolean) requestUntil(action, timeoutMs, false)}.
    *
    * @param action    The request action to dispatch.
    * @param timeoutMs Maximum wait time in milliseconds. {@code 0} fires immediately;
    *                  a negative value disables the timeout (wait indefinitely).
    * @return The matching response, or {@code null} on soft timeout.
    */
   default <RES extends ResponseAction> CompletableFuture<RES> requestUntil(final RequestAction<RES> action,
      final long timeoutMs) {
      return requestUntil(action, timeoutMs, false);
   }

   /**
    * Dispatches a request and waits for a response until the given timeout is reached. The
    * returned future completes when a matching response is dispatched, or when the timeout
    * elapses. The matching response is <em>not</em> passed to registered action handlers.
    *
    * <p>
    * If {@code rejectOnTimeout} is {@code false} the future completes with {@code null} on
    * timeout; otherwise it completes exceptionally.
    * </p>
    *
    * <p>
    * Note: mutates {@code action.requestId} (if unset) and {@code action.timeout}.
    * </p>
    *
    * @param action          The request action to dispatch.
    * @param timeoutMs       Maximum wait time in milliseconds. {@code 0} fires immediately;
    *                        a negative value disables the timeout (wait indefinitely).
    * @param rejectOnTimeout Whether to reject the future on timeout.
    * @return The matching response, or {@code null} on soft timeout.
    */
   default <RES extends ResponseAction> CompletableFuture<RES> requestUntil(final RequestAction<RES> action,
      final long timeoutMs, final boolean rejectOnTimeout) {
      throw new UnsupportedOperationException("requestUntil() is not implemented by this ActionDispatcher");
   }
}
