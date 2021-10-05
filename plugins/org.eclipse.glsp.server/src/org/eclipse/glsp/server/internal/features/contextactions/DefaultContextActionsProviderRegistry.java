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
package org.eclipse.glsp.server.internal.features.contextactions;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.glsp.server.features.commandpalette.CommandPaletteActionProvider;
import org.eclipse.glsp.server.features.contextactions.ContextActionsProvider;
import org.eclipse.glsp.server.features.contextactions.ContextActionsProviderRegistry;
import org.eclipse.glsp.server.features.contextmenu.ContextMenuItemProvider;
import org.eclipse.glsp.server.features.toolpalette.ToolPaletteItemProvider;
import org.eclipse.glsp.server.internal.registry.MapRegistry;

import com.google.inject.Inject;

public class DefaultContextActionsProviderRegistry extends MapRegistry<String, ContextActionsProvider>
   implements ContextActionsProviderRegistry {

   @Inject
   public DefaultContextActionsProviderRegistry(final Set<ContextActionsProvider> contextActionsProviders,
      final Optional<ContextMenuItemProvider> contextMenuItemProvider,
      final Optional<CommandPaletteActionProvider> commandPaletteActionProvider,
      final Optional<ToolPaletteItemProvider> toolPaletteItemProvider) {
      contextActionsProviders.forEach(provider -> register(provider.getContextId(), provider));

      Stream.of(contextMenuItemProvider, commandPaletteActionProvider, toolPaletteItemProvider)
         .flatMap(Optional::stream)
         .forEach(provider -> register(provider.getContextId(), provider));
   }
}
