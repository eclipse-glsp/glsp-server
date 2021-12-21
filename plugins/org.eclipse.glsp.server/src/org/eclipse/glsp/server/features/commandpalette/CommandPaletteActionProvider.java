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
package org.eclipse.glsp.server.features.commandpalette;

import java.util.Map;

import org.eclipse.glsp.server.features.contextactions.ContextActionsProvider;

/**
 * A {@link ContextActionsProvider} for CommandPaletteActions.
 */
@FunctionalInterface
public interface CommandPaletteActionProvider extends ContextActionsProvider {

   String CONTEXT_ID = "command-palette";
   String TEXT = "text";
   String INDEX = "index";

   /**
    * Returns the context id of the {@link CommandPaletteActionProvider}.
    */
   @Override
   default String getContextId() { return CommandPaletteActionProvider.CONTEXT_ID; }

   /**
    * Returns the content of the "text" argument for a given Map of arguments.
    *
    * @param args The given map of string arguments.
    * @return The content of the "text" argument.
    */
   default String getText(final Map<String, String> args) {
      return args.getOrDefault(TEXT, "");
   }

   /**
    * Returns the content of the "index" argument for a given Map of arguments.
    *
    * @param args The given map of string arguments.
    * @return The content of the "index" argument.
    */
   default int getIndex(final Map<String, String> args) {
      return (int) Double.parseDouble(args.getOrDefault(INDEX, "0.0"));
   }
}
