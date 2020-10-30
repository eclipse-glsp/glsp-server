/********************************************************************************
 * Copyright (c) 2019-2020 EclipseSource and others.
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
package org.eclipse.glsp.server.model;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.glsp.server.protocol.ClientSessionListener;
import org.eclipse.glsp.server.protocol.ClientSessionManager;
import org.eclipse.glsp.server.protocol.GLSPClient;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DefaultModelStateProvider implements ModelStateProvider, ClientSessionListener {
   @Inject()
   protected ClientSessionManager sessionManager;

   private final Map<String, GModelState> clientModelStates;

   @Inject()
   public DefaultModelStateProvider() {
      clientModelStates = new ConcurrentHashMap<>();
   }

   @Inject()
   public void postConstruct() {
      sessionManager.addListener(this);
   }

   @Override
   public Optional<GModelState> getModelState(final String clientId) {
      return Optional.ofNullable(clientModelStates.get(clientId));
   }

   @Override
   public GModelState create(final String clientId) {
      GModelState modelState = createModelState();
      modelState.setClientId(clientId);
      clientModelStates.put(clientId, modelState);
      return modelState;
   }

   protected GModelState createModelState() {
      return new GModelStateImpl();
   }

   @Override
   public void remove(final String clientId) {
      clientModelStates.remove(clientId);
   }

   @Override
   public void sessionClosed(final String clientId, final GLSPClient client) {
      this.clientModelStates.remove(clientId);
   }

   @Override
   public void clientDisconnected(final GLSPClient client) {
      sessionManager.removeListener(this);
   }
}
