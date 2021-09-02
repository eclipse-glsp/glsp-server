/*******************************************************************************
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
 ******************************************************************************/
package org.eclipse.glsp.server.protocol;

import java.util.concurrent.CompletableFuture;

import org.eclipse.glsp.server.actions.ActionMessage;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;

/**
 * Interface for implementations of a server component using json-rpc for client-server communication.
 * Based on the specification of the Graphical Language Server Protocol:
 * https://github.com/eclipse-glsp/glsp/blob/master/PROTOCOL.md
 */
public interface GLSPServer {
   /**
    *
    * The `initialize` request has to be the first request from the client to the server. Until the server has responded
    * with an {@link InitializeResult} no other request or notification can be handled and is expected to throw an
    * error. A client is uniquely identified by an `applicationId` and has to specify on which `protocolVersion` it is
    * based on. In addition, custom arguments can be provided in the `args` map to allow for custom initialization
    * behavior on the server.
    *
    * @param params the {@link InitializeParameters}.
    * @return A @{@link CompletableFuture} of the {@link InitializeResult} .
    */
   @JsonRequest
   CompletableFuture<InitializeResult> initialize(InitializeParameters params);

   /**
    * The InitializeClientSession` request is sent to the server whenever a new graphical representation (diagram) is
    * created. Each individual diagram on the client side counts as one session and has to provide a unique
    * `clientSessionId` and its `diagramType`. In addition, custom arguments can be provided in the `args` map to allow
    * for custom initialization behavior on the server.
    *
    * @param params the {@link InitializeClientSessionParameters}.
    * @return A `void` {@link CompletableFuture} that completes if the initialization was successful.
    */
   @JsonRequest
   CompletableFuture<Void> initializeClientSession(InitializeClientSessionParameters params);

   /**
    * The 'DisposeClientSession' request is sent to the server when a graphical representation (diagram) is no longer
    * needed, e.g. the tab containing the diagram widget has been closed. The session is identified by its unique
    * `clientSessionId`. In addition, custom arguments can be provided in the `args` map to allow for custom dispose
    * behavior on the server.
    *
    * @param params the {@link DisposeClientSessionParameters}.
    * @return A `void` {@link CompletableFuture} that completes if the disposal was successful.
    *
    */
   @JsonRequest
   CompletableFuture<Void> disposeClientSession(DisposeClientSessionParameters params);

   /**
    * A `process` notification is sent from the client to server when the server should handle i.e. process a specific
    * {@link ActionMessage}. Any communication that is performed between initialization and shutdown is handled by
    * sending action messages, either from the client to the server or from the server to the client. This is the core
    * part of the Graphical Language Server Protocol.
    *
    * @param message The {@link ActionMessage} that should be processed.
    */
   @JsonNotification
   void process(ActionMessage message);

   /**
    * The 'shutdown' notification is sent from the client to the server if the client disconnects from the server (e.g.
    * the client application has been closed).
    * This gives the server a chance to clean up and dispose any resources dedicated to the client and its sessions.
    *
    */
   @JsonNotification
   void shutdown();

   /**
    * Connects a {@link GLSPClient} json-rpc proxy interface to this server. The server can use this proxy to sent
    * {@link ActionMessage}s to
    * the client using the {@link GLSPClient#process(ActionMessage)} notification.
    *
    * @param client The {@link GLSPClient} proxy
    */
   void connect(GLSPClient client);

   /**
    * Returns the connected {@link GLSPClient} proxy.
    *
    * @return the connected {@link GLSPClient} proxy.
    */
   GLSPClient getClient();

   /**
    * Register a new {@link ServerConnectionListener}.
    *
    * @param listener The listener that should be registered.
    * @return 'true' if the listener was registered successfully, `false` otherwise (e.g. listener is already
    *         registered).
    */
   boolean addListener(ServerConnectionListener listener);

   /**
    * Unregister a {@link ServerConnectionListener}.
    *
    * @param listener The listener that should be removed
    * @return 'true' if the listener was unregistered successfully, `false` otherwise (e.g. listener is was not
    *         registered in the first place).
    */
   boolean remove(ServerConnectionListener listener);

}
