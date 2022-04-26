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
package org.eclipse.glsp.server.actions;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.eclipse.glsp.server.features.core.model.UpdateModelAction;

/**
 * The central component that process all {@link Action}s by dispatching them to their designated
 * handlers.
 */
public interface ActionDispatcher {

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
    * <p>
    * Processes all given actions by dispatching to the corresponding handlers.
    * </p>
    *
    * @param actions Actions to dispatch
    * @return A list of {@link CompletableFuture CompletableFutures}; one for each dispatched action
    */
   default List<CompletableFuture<Void>> dispatchAll(final List<Action> actions) {
      return actions.stream().map(action -> dispatch(action)).collect(Collectors.toList());
   }

   /**
    * Processes all given actions, by dispatching them to the corresponding handlers, after the next model update.
    * The given actions are queued until the next model update cycle has been completed i.e. an
    * {@link UpdateModelAction} has been dispatched and processed by this action dispatcher.
    *
    * @param actions The actions that should be dispatched after the next model update
    */
   void dispatchAfterNextUpdate(Action... actions);
}
