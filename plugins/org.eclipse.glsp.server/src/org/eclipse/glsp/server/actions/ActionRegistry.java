/********************************************************************************
 * Copyright (c) 2019-2023 EclipseSource and others.
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
import java.util.Map;

import org.eclipse.glsp.server.registry.Registry;

/**
 * An action registry keeps track of registered actions for a certain diagramType.
 */
public interface ActionRegistry extends Registry<String, Map<String, Class<? extends Action>>> {

   /**
    * Registers a server handled action for a diagramType.
    *
    * @param diagramType The diagramType for which the action is registered.
    * @param actionKind  The actionKind of the action to be registered.
    * @param action      The action to be registered.
    * @return A boolean flag that indicates if the registration was successful.
    */
   boolean register(String diagramType, String actionKind, Class<? extends Action> action);

   /**
    * Registers a set of server handled actions for a diagramType.
    *
    * @param diagramType The diagramType for which the action is registered.
    * @param actionMap   A map of actionKind and actions to be registered.
    * @return A boolean flag that indicates if the registration was successful.
    */
   @Override
   boolean register(String diagramType, Map<String, Class<? extends Action>> actionMap);

   /**
    * Returns a list of actions kinds that are handled by the server for the given diagramType.
    *
    * @return A list of actions that are handled on the server for the given diagramType.
    */
   List<String> getHandledActionKinds(String diagramType);

   /**
    * Returns a map of all registered diagramTypes and their server-handled actions kinds.
    *
    * @return A map of all registered diagramTypes and their server-handled actions.
    */
   Map<String, List<String>> getHandledActionKinds();

   /**
    * Returns a map of all currently registered actionKinds and their corresponding {@link Action} class.
    *
    * @return a map of all registered actionKinds and their corresponding {@link Action} class.
    */
   Map<String, Class<? extends Action>> getAllAsMap();

}
