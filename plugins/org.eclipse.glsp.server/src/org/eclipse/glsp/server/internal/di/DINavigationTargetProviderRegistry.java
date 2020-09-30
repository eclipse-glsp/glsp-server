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
package org.eclipse.glsp.server.internal.di;

import java.util.Set;

import org.eclipse.glsp.server.features.navigation.NavigationTargetProvider;
import org.eclipse.glsp.server.features.navigation.NavigationTargetProviderRegistry;

import com.google.inject.Inject;

public class DINavigationTargetProviderRegistry extends MapRegistry<String, NavigationTargetProvider>
   implements NavigationTargetProviderRegistry {

   @Inject
   public DINavigationTargetProviderRegistry(final Set<NavigationTargetProvider> navigationTargetProviders) {
      navigationTargetProviders.forEach(provider -> register(provider.getTargetTypeId(), provider));
   }
}
