/********************************************************************************
 * Copyright (c) 2020 EclipseSource and others.
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
package org.eclipse.glsp.server.features.toolpalette;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.glsp.server.features.contextactions.ContextActionsProvider;
import org.eclipse.glsp.server.features.directediting.LabeledAction;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.types.EditorContext;

public interface ToolPaletteItemProvider extends ContextActionsProvider {
   String CONTEXT_ID = "tool-palette";

   @Override
   default String getContextId() {
      return ToolPaletteItemProvider.CONTEXT_ID;
   }

   @Override
   default List<? extends LabeledAction> getActions(final EditorContext editorContext,
      final GModelState modelState) {
      return getItems(editorContext.getArgs(), modelState);
   }

   List<PaletteItem> getItems(Map<String, String> args, GModelState modelState);

   final class NullImpl implements ToolPaletteItemProvider {

      @Override
      public List<PaletteItem> getItems(final Map<String, String> args, final GModelState modelState) {
         return Collections.emptyList();
      }

   }
}
