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
package org.eclipse.glsp.server.supplier;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.glsp.api.provider.CommandPaletteActionProvider;
import org.eclipse.glsp.api.provider.ContextActionsProvider;
import org.eclipse.glsp.api.provider.ContextMenuItemProvider;
import org.eclipse.glsp.api.provider.ToolPaletteItemProvider;
import org.eclipse.glsp.api.supplier.ContextActionsProviderSupplier;

import com.google.inject.Inject;

public class DIContextActionsProviderSupplier implements ContextActionsProviderSupplier {

   protected final Set<ContextActionsProvider> contextActionsProviders;

   @Inject
   public DIContextActionsProviderSupplier(final Set<ContextActionsProvider> contextActionsProviders,
      final ContextMenuItemProvider contextMenuItemProvider,
      final CommandPaletteActionProvider commandPaletteActionProvider,
      final ToolPaletteItemProvider toolPaletteItemProvider) {
      this.contextActionsProviders = new HashSet<>();
      this.contextActionsProviders.addAll(contextActionsProviders);
      this.contextActionsProviders.add(contextMenuItemProvider);
      this.contextActionsProviders.add(commandPaletteActionProvider);
      this.contextActionsProviders.add(toolPaletteItemProvider);
   }

   @Override
   public Set<ContextActionsProvider> get() {
      return contextActionsProviders;
   }

}
