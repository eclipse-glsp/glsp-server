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
package org.eclipse.glsp.server.features.commandpalette;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.glsp.server.features.contextactions.ContextActionsProvider;
import org.eclipse.glsp.server.features.directediting.LabeledAction;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.types.EditorContext;

@FunctionalInterface
public interface CommandPaletteActionProvider extends ContextActionsProvider {

   String CONTEXT_ID = "command-palette";
   String TEXT = "text";
   String INDEX = "index";

   @Override
   default String getContextId() {
      return CommandPaletteActionProvider.CONTEXT_ID;
   }

   default String getText(final Map<String, String> args) {
      return args.getOrDefault(TEXT, "");
   }

   default int getIndex(final Map<String, String> args) {
      return (int) Double.parseDouble(args.getOrDefault(INDEX, "0.0"));
   }

   final class NullImpl implements CommandPaletteActionProvider {

      @Override
      public List<? extends LabeledAction> getActions(final EditorContext editorContext,
         final GModelState modelState) {
         return Collections.emptyList();
      }

   }
}
