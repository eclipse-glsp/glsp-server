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
package org.eclipse.glsp.api.provider;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.types.MenuItem;
import org.eclipse.glsp.graph.GPoint;

@FunctionalInterface
public interface ContextMenuItemProvider {

   String KEY = "context-menu";

   List<MenuItem> getItems(GraphicalModelState modelState, List<String> selectedElementIds,
      Optional<GPoint> lastMousePosition, Map<String, String> args);

   default List<MenuItem> getItems(final GraphicalModelState modelState, final List<String> selectedElementIds,
      final GPoint lastMousePosition, final Map<String, String> args) {
      return getItems(modelState, selectedElementIds, Optional.ofNullable(lastMousePosition), args);
   }

   class NullImpl implements ContextMenuItemProvider {
      @Override
      public List<MenuItem> getItems(final GraphicalModelState modelState, final List<String> selectedElementIds,
         final Optional<GPoint> lastMousePosition, final Map<String, String> args) {
         return Collections.emptyList();
      }
   }
}
