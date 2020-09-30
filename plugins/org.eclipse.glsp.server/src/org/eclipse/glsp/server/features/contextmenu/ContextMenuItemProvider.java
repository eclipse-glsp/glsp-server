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
package org.eclipse.glsp.server.features.contextmenu;

import static org.eclipse.glsp.graph.util.GraphUtil.point;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.server.features.contextactions.ContextActionsProvider;
import org.eclipse.glsp.server.features.directediting.LabeledAction;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.types.EditorContext;

@FunctionalInterface
public interface ContextMenuItemProvider extends ContextActionsProvider {

   String KEY = "context-menu";

   @Override
   default String getContextId() {
      return ContextMenuItemProvider.KEY;
   }

   List<MenuItem> getItems(List<String> selectedElementIds, GPoint position, Map<String, String> args,
      GModelState modelState);

   @Override
   default List<? extends LabeledAction> getActions(final EditorContext editorContext,
      final GModelState modelState) {
      return getItems(editorContext.getSelectedElementIds(), editorContext.getLastMousePosition().orElse(point(0, 0)),
         editorContext.getArgs(),
         modelState);
   }

   final class NullImpl implements ContextMenuItemProvider {

      @Override
      public List<MenuItem> getItems(final List<String> selectedElementIds, final GPoint position,
         final Map<String, String> args,
         final GModelState modelState) {
         return Collections.emptyList();
      }

   }

}
