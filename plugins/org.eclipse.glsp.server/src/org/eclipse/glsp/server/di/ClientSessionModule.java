/********************************************************************************
 * Copyright (c) 2021-2023 EclipseSource and others.
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
package org.eclipse.glsp.server.di;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

/**
 * Helper module that is typically combined a{@link DiagramModule} to make the
 * client session specific configuration available in the injector.
 */
public class ClientSessionModule extends AbstractModule {
   public static final String CLIENT_ACTIONS = "ClientActions";

   protected final String clientId;
   protected final Set<String> clientActionKinds;

   public ClientSessionModule(final String clientId, final Collection<String> clientActionKinds) {
      this.clientId = clientId;
      this.clientActionKinds = new HashSet<>(clientActionKinds);
   }

   @Override
   protected void configure() {
      bind(String.class).annotatedWith(ClientId.class).toInstance(clientId);
      bind(new TypeLiteral<Set<String>>() {}).annotatedWith(Names.named(CLIENT_ACTIONS)).toInstance(clientActionKinds);
   }

}
