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
package org.eclipse.glsp.server.session;

import java.util.List;
import java.util.Optional;

import org.eclipse.glsp.server.disposable.IDisposable;
import org.eclipse.glsp.server.types.GLSPServerException;

/**
 * The central component that manages the lifecycle of client sessions.
 */
public interface ClientSessionManager extends IDisposable {

   /**
    * Retries an existing (or created a new) {@link ClientSession} for the given id and diagram type.
    * If a new session has been created all {@link ClientSessionListener}s are notified via the
    * {@link ClientSessionListener#sessionCreated(ClientSession)} method.
    *
    * @param clientSessionId The client session id (i.e. clientId).
    * @param diagramType     The diagram type.
    * @return The existing or newly constructed {@link ClientSession}.
    *
    * @throws GLSPServerException if another session with matching client id but different diagram type already exists.
    */
   ClientSession getOrCreateClientSession(String clientSessionId, String diagramType);

   /**
    * Retrieve an existing (i.e. currently active) {@link ClientSession} for the given client session id.
    *
    * @param clientSessionId The client session id.
    * @return The client session for the given id or an empty optional if no session is present with the given id.
    */
   Optional<ClientSession> getSession(String clientSessionId);

   /**
    * Return all currently active {@link ClientSession}s for the given diagram type.
    *
    * @param diagramType The diagram type.
    * @return A list of all currently active {@link ClientSession}s.
    */
   List<ClientSession> getSessionsByType(String diagramType);

   /**
    * Dispose the active client session with the given id. This marks the end of the lifecylce of a client session.
    * After successfully disposal all {@link ClientSessionListener}s are notified via the
    * {@link ClientSessionListener#sessionDisposed(ClientSession)} method.
    *
    * @param clientSessionId The id of the client session which should be disposed
    * @return `true` if a session with the given id was active and successfully disposed, `false` otherwise
    */
   boolean disposeClientSession(String clientSessionId);

   /**
    * Register a new {@link ClientSessionListener}. Optionally the scope of the listener can be restricted to a set
    * of client session ids. If no client session ids are passed, the listener will be registered globally and trigger
    * for all client sessions.
    *
    * @param listener         The listener that should be registered.
    * @param clientSessionIds Scope of client ids
    * @return `true` if the listener was registered successfully, `false` otherwise
    */
   boolean addListener(ClientSessionListener listener, String... clientSessionIds);

   /**
    * Unregister a given {@link ClientSessionListener} from this client session manager.
    *
    * @param listener The listener that should be removed
    * @return `false` if the listener is not registered, `true` if it was successfully unregistered.
    */
   boolean removeListener(ClientSessionListener listener);

   /**
    * Unregister all {@link ClientSessionListener} that haven been registered for the given scope of client session ids.
    * If not client session ids are passed all listeners will be removed.
    *
    * @param clientSessionIds Scope (i.e. set) of client session ids.
    */
   void removeListeners(String... clientSessionIds);
}
