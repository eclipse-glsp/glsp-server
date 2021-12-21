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

import org.eclipse.glsp.server.registry.MultiRegistry;

/**
 * An action handler registry keeps track of registered action handlers for a certain action.
 */
public interface ActionHandlerRegistry extends MultiRegistry<Class<? extends Action>, ActionHandler> {
   /**
    * Returns the registered {@link ActionHandler} for a given {@link Action}.
    *
    * @param action The action for which the handler should be returned.
    * @return The registered ActionHandler for the given action.
    */
   default List<ActionHandler> get(final Action action) {
      return get(action.getClass());
   }
}
