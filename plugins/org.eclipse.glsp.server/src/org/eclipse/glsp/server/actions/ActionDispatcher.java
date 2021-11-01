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

import org.eclipse.glsp.server.disposable.IDisposable;

public interface ActionDispatcher extends IDisposable {

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
}
