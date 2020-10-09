/********************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
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

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public interface ActionDispatcher {

   /**
    * @see ActionDispatcher#dispatch(String, Action)
    *
    * @param message ActionMessage received from the client
    * @return
    *         A {@link CompletableFuture} indicating when the action processing is complete
    */
   default CompletableFuture<Void> dispatch(final ActionMessage message) {
      return dispatch(message.getClientId(), message.getAction());
   }

   /**
    * <p>
    * Processes the given action, received from the specified clientId, by dispatching it to all registered handlers.
    * </p>
    *
    * @param clientId The client from which the action was received
    * @param action   The action to dispatch
    * @return
    *         A {@link CompletableFuture} indicating when the action processing is complete
    */
   CompletableFuture<Void> dispatch(String clientId, Action action);

   /**
    * <p>
    * Processes all given actions, received from the specified clientId, by dispatching to the corresponding handlers.
    * </p>
    *
    * @param clientId
    * @param actions
    * @return A list of {@link CompletableFuture CompletableFutures}; one for each dispatched action
    */
   default List<CompletableFuture<Void>> dispatchAll(final String clientId, final List<Action> actions) {
      return actions.stream().map(action -> dispatch(clientId, action)).collect(Collectors.toList());
   }

   class NullImpl implements ActionDispatcher {

      @Override
      public CompletableFuture<Void> dispatch(final String clientId, final Action action) {
         return CompletableFuture.completedFuture(null);
      }
   }
}
