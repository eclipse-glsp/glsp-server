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
import java.util.Map;

import org.eclipse.glsp.server.registry.Registry;

public interface ActionRegistry extends Registry<String, Map<String, Class<? extends Action>>> {

   boolean register(String diagramType, String actionKind, Class<? extends Action> action, boolean isServerAction);

   default boolean register(final String diagramType, final String actionKind, final Class<? extends Action> action) {
      return register(diagramType, actionKind, action, true);
   }

   @Override
   default boolean register(final String diagramType, final Map<String, Class<? extends Action>> actionMap) {
      return register(diagramType, actionMap, true);
   }

   boolean register(String diagramType, Map<String, Class<? extends Action>> actionMap,
      boolean isServerAction);

   List<String> getServerHandledAction(String diagramType);

   Map<String, List<String>> getServerHandledActions();

   /**
    * Returns a map of all currently registered actionKinds and their corresponding {@link Action} class.
    *
    * @return a map of all registered actionKinds and their corresponding {@link Action} class.
    */
   Map<String, Class<? extends Action>> getAllAsMap();

}
