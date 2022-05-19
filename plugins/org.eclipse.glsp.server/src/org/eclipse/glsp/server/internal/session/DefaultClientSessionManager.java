/********************************************************************************
 * Copyright (c) 2020-2022 EclipseSource and others.
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
package org.eclipse.glsp.server.internal.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.glsp.server.disposable.Disposable;
import org.eclipse.glsp.server.protocol.GLSPServer;
import org.eclipse.glsp.server.protocol.GLSPServerListener;
import org.eclipse.glsp.server.session.ClientSession;
import org.eclipse.glsp.server.session.ClientSessionFactory;
import org.eclipse.glsp.server.session.ClientSessionListener;
import org.eclipse.glsp.server.session.ClientSessionManager;
import org.eclipse.glsp.server.types.GLSPServerException;

import com.google.inject.Inject;

public class DefaultClientSessionManager extends Disposable implements ClientSessionManager, GLSPServerListener {
   private static final String ALL_CLIENT_IDS_KEY = "*";

   @Inject
   protected ClientSessionFactory sessionFactory;

   protected final Map<String, ClientSession> clientSessions = new HashMap<>();
   protected final Map<String, List<ClientSessionListener>> listeners = new HashMap<>();

   @Inject
   public DefaultClientSessionManager(final GLSPServer server) {
      server.addListener(this);
   }

   @Override
   public synchronized ClientSession getOrCreateClientSession(final String clientSessionId,
      final String diagramType) {
      ClientSession session = clientSessions.get(clientSessionId);
      if (session != null) {
         if (!session.getDiagramType().equals(diagramType)) {
            String errorMsg = String.format("Could not initialize new session for diagram type '%s'"
               + "Another session with the same id for the diagram type '%s' already exists",
               diagramType, session.getDiagramType());
            throw new GLSPServerException(errorMsg);
         }
         return session;
      }

      ClientSession newSession = sessionFactory.create(clientSessionId, diagramType);
      clientSessions.put(clientSessionId, newSession);
      getListenersToNotifiy(newSession).forEach(listener -> listener.sessionCreated(newSession));
      return newSession;
   }

   protected List<ClientSessionListener> getListenersToNotifiy(final ClientSession clientSession) {
      List<ClientSessionListener> toNotify = new ArrayList<>();
      // Retrieve all listeners that should be notified. During this step all listeners that are null (i.e have been
      // disposed since the last notification) will be removed as well
      List<ClientSessionListener> globalListeners = listeners.getOrDefault(ALL_CLIENT_IDS_KEY, new ArrayList<>())
         .stream().filter(listener -> listener != null).collect(Collectors.toList());
      List<ClientSessionListener> sessionListeners = listeners.getOrDefault(clientSession.getId(), new ArrayList<>())
         .stream().filter(listener -> listener != null).collect(Collectors.toList());

      listeners.put(ALL_CLIENT_IDS_KEY, globalListeners);
      listeners.put(clientSession.getId(), sessionListeners);

      toNotify.addAll(globalListeners);
      toNotify.addAll(sessionListeners);
      return toNotify;
   }

   @Override
   public synchronized boolean disposeClientSession(final String clientSessionId) {
      ClientSession session = clientSessions.remove(clientSessionId);
      if (session == null) {
         return false;
      }
      session.dispose();
      getListenersToNotifiy(session).forEach(listener -> listener.sessionDisposed(session));
      listeners.remove(clientSessionId);
      return true;
   }

   @Override
   public Optional<ClientSession> getSession(final String clientSessionId) {
      return Optional.ofNullable(clientSessions.get(clientSessionId));
   }

   @Override
   public boolean addListener(final ClientSessionListener listener, final String... clientSessionIds) {
      if (clientSessionIds.length == 0) {
         return addListener(ALL_CLIENT_IDS_KEY, listener);
      }
      return Stream.of(clientSessionIds).map(clientId -> addListener(clientId, listener)).allMatch(added -> added);
   }

   protected boolean addListener(final String clientSessionId, final ClientSessionListener listener) {
      return listeners.computeIfAbsent(clientSessionId, k -> new ArrayList<>())
         .add(listener);
   }

   @Override
   public boolean removeListener(final ClientSessionListener listener) {
      return new ArrayList<>(listeners.values()).stream()
         .map(listeners -> listeners.remove(listener))
         .anyMatch(removed -> removed);
   }

   @Override
   public void removeListeners(final String... clientSessionIds) {
      if (clientSessionIds.length == 0) {
         listeners.clear();
      } else {
         Stream.of(clientSessionIds).forEach(id -> listeners.remove(id));
      }
   }

   @Override
   public List<ClientSession> getSessionsByType(final String diagramType) {
      return clientSessions.values().stream()
         .filter(session -> session.getDiagramType().equals(diagramType))
         .collect(Collectors.toList());
   }

   @Override
   protected synchronized void doDispose() {
      new ArrayList<>(clientSessions.keySet())
         .forEach(clientSessionId -> disposeClientSession(clientSessionId));
      listeners.clear();
   }

   @Override
   public void serverShutDown(final GLSPServer glspServer) {
      this.dispose();
   }

}
