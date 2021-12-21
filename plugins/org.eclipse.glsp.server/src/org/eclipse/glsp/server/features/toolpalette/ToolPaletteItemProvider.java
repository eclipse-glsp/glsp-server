/********************************************************************************
 * Copyright (c) 2020-2021 EclipseSource and others.
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

import java.util.List;
import java.util.Map;

import org.eclipse.glsp.server.features.contextactions.ContextActionsProvider;
import org.eclipse.glsp.server.features.directediting.LabeledAction;
import org.eclipse.glsp.server.types.EditorContext;

/**
 * A {@link ContextActionsProvider} for {@link PaletteItem}s.
 */
public interface ToolPaletteItemProvider extends ContextActionsProvider {
   String CONTEXT_ID = "tool-palette";

   /**
    * Returns the context id of the provider.
    */
   @Override
   default String getContextId() { return ToolPaletteItemProvider.CONTEXT_ID; }

   /**
    * Returns a list of {@link LabeledAction}s for a given {@link EditorContext}.
    *
    * @param editorContext The editorContext for which the actions are returned.
    * @return A list of {@link LabeledAction}s for a given {@link EditorContext}.
    */
   @Override
   default List<? extends LabeledAction> getActions(final EditorContext editorContext) {
      return getItems(editorContext.getArgs());
   }

   /**
    * Constructs a list of {@link PaletteItem}s from a given map of string arguments.
    *
    * @param args A map of string arguments.
    * @return A list of {@link PaletteItem}s for a given map of string arguments.
    */
   List<PaletteItem> getItems(Map<String, String> args);
}
