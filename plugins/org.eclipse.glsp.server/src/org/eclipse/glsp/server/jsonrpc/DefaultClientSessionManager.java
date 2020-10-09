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
package org.eclipse.glsp.server.jsonrpc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.glsp.server.protocol.ClientSessionListener;
import org.eclipse.glsp.server.protocol.ClientSessionManager;
import org.eclipse.glsp.server.protocol.GLSPClient;

public final class DefaultClientSessionManager implements ClientSessionManager {

   private final Set<ClientSessionListener> listeners = new LinkedHashSet<>();
   private final Map<GLSPClient, Set<String>> clientSessions = new HashMap<>();

   public static final DefaultClientSessionManager INSTANCE = new DefaultClientSessionManager();

   private DefaultClientSessionManager() {}

   @Override
   public synchronized boolean connectClient(final GLSPClient client) {
      boolean success = clientSessions.putIfAbsent(client, new HashSet<>()) == null;
      if (success) {
         new ArrayList<>(this.listeners).forEach(listener -> listener.clientConnected(client));
      }
      return success;
   }

   @Override
   public synchronized boolean createClientSession(final GLSPClient client, final String clientId) {
      connectClient(client);
      boolean success = clientSessions.get(client).add(clientId);
      if (success) {
         new ArrayList<>(this.listeners).forEach(listener -> listener.sessionCreated(clientId, client));
      }
      return success;
   }

   @Override
   public synchronized boolean disposeClientSession(final GLSPClient client, final String clientId) {
      boolean success = clientSessions.getOrDefault(client, Collections.emptySet()).remove(clientId);
      if (success) {
         new ArrayList<>(this.listeners).forEach(listener -> listener.sessionClosed(clientId, client));
         return true;
      }
      return false;
   }

   @Override
   public synchronized boolean disconnectClient(final GLSPClient client) {
      if (clientSessions.containsKey(client)) {
         Collection<String> sessionsToDisconnect = new ArrayList<>(
            clientSessions.getOrDefault(client, Collections.emptySet()));
         sessionsToDisconnect.forEach(clientId -> this.disposeClientSession(client, clientId));
         this.clientSessions.remove(client);
         new ArrayList<>(this.listeners).forEach(listener -> listener.clientDisconnected(client));
         return true;
      }
      return false;
   }

   @Override
   public boolean addListener(final ClientSessionListener listener) {
      return listeners.add(listener);
   }

   @Override
   public boolean removeListener(final ClientSessionListener listener) {
      return listeners.remove(listener);
   }

}
