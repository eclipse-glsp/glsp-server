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
package org.eclipse.glsp.server.jsonrpc;

import static org.eclipse.glsp.server.di.DefaultGLSPModule.CLIENT_ACTIONS;
import static org.eclipse.glsp.server.utils.ServerMessageUtil.error;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.actions.ActionMessage;
import org.eclipse.glsp.server.actions.ActionRegistry;
import org.eclipse.glsp.server.diagram.DiagramConfigurationRegistry;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.model.ModelStateProvider;
import org.eclipse.glsp.server.protocol.ClientSessionManager;
import org.eclipse.glsp.server.protocol.DisposeClientSessionParameters;
import org.eclipse.glsp.server.protocol.GLSPClient;
import org.eclipse.glsp.server.protocol.GLSPServer;
import org.eclipse.glsp.server.protocol.GLSPServerException;
import org.eclipse.glsp.server.protocol.InitializeClientSessionParameters;
import org.eclipse.glsp.server.protocol.InitializeParameters;
import org.eclipse.glsp.server.protocol.InitializeResult;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class DefaultGLSPServer implements GLSPServer {
   private static Logger LOG = Logger.getLogger(DefaultGLSPServer.class);
   public static final String PROTOCOL_VERSION = "0.9.0";

   @Inject
   protected ModelStateProvider modelStateProvider;

   @Inject
   protected ClientSessionManager sessionManager;

   @Inject
   protected ActionDispatcher actionDispatcher;

   @Inject
   protected ActionRegistry actionRegistry;

   @Inject
   protected DiagramConfigurationRegistry diagramConfigurationRegistry;

   @Inject
   @Named(CLIENT_ACTIONS)
   protected Set<Action> clientActions;

   protected GLSPClient clientProxy;
   protected CompletableFuture<InitializeResult> initialized;

   protected String applicationId;

   // TODO: Temporary needed before DI rework, remove in actual PR
   private final Set<String> clientSessions = new HashSet<>();

   public DefaultGLSPServer() {
      this.initialized = new CompletableFuture<>();
   }

   @Override
   @SuppressWarnings("checkstyle:IllegalCatch")
   public CompletableFuture<InitializeResult> initialize(final InitializeParameters params) {
      LOG.debug("Initializing server with the following params:\n" + params);
      if (isInitialized()) {
         throw new GLSPServerException("This GLSP server has already been initialized.");
      }

      if (!params.getProtocolVersion().equals(PROTOCOL_VERSION)) {
         throw new GLSPServerException(String.format(
            "Protocol version mismatch! The client protocol version '%s' is not compatible with the server protocol version '%s'!",
            params.getProtocolVersion(), PROTOCOL_VERSION));
      }
      this.applicationId = params.getApplicationId();

      InitializeResult result = new InitializeResult(PROTOCOL_VERSION);
      Set<String> serverActions = actionRegistry.keys();
      serverActions.removeAll(clientActions.stream().map(Action::getKind).collect(Collectors.toList()));
      diagramConfigurationRegistry.getAll().forEach(configuration -> {
         result.addServerActions(configuration.getDiagramType(), serverActions);
      });

      initialized = handleIntializeArgs(result, params.getArgs());
      return initialized;
   }

   protected CompletableFuture<InitializeResult> handleIntializeArgs(final InitializeResult result,
      final Map<String, String> args) {
      return CompletableFuture.completedFuture(result);
   }

   @Override
   public CompletableFuture<Void> initializeClientSession(final InitializeClientSessionParameters params) {
      LOG.debug("Initializing client session with the following params:\n" + params);

      if (!isInitialized()) {
         throw new GLSPServerException("This GLSP server has not been initialized.");
      }

      GModelState modelState = modelStateProvider.create(params.getClientSessionId());
      if (sessionManager.createClientSession(clientProxy, params.getClientSessionId())) {
         modelState.setClientId(params.getClientSessionId());
         clientSessions.add(params.getClientSessionId());
         return handleInitializeClientSessionArgs(params.getArgs());
      }
      throw new GLSPServerException(String.format("Could not initialize new session for client id '%s'. "
         + "Another session with the same id already exists", params.getClientSessionId()));
   }

   protected CompletableFuture<Void> handleInitializeClientSessionArgs(final Map<String, String> args) {
      return CompletableFuture.completedFuture(null);
   }

   @Override
   public CompletableFuture<Void> disposeClientSession(final DisposeClientSessionParameters params) {
      LOG.debug("Dispose client session with the following params:\n" + params);
      if (!isInitialized()) {
         throw new GLSPServerException("This GLSP server has not been initialized.");
      }
      if (clientSessions.contains(params.getClientSessionId())) {
         sessionManager.disposeClientSession(clientProxy, params.getClientSessionId());
         clientSessions.remove(params.getClientSessionId());
         return handleDisposeClientSessionArgs(params.getArgs());
      }
      return CompletableFuture.completedFuture(null);
   }

   protected CompletableFuture<Void> handleDisposeClientSessionArgs(final Map<String, String> args) {
      return CompletableFuture.completedFuture(null);
   }

   @Override
   public void connect(final GLSPClient clientProxy) {
      this.clientProxy = clientProxy;
      if (clientProxy != null) {
         this.sessionManager.connectClient(clientProxy);
      }
   }

   @Override
   @SuppressWarnings("checkstyle:IllegalCatch")
   public void process(final ActionMessage message) {
      if (!isInitialized()) {
         throw new GLSPServerException("This GLSP server has not been initialized.");
      }
      LOG.debug("process " + message);
      String clientId = message.getClientId();
      if (!clientSessions.contains(clientId)) {
         throw new GLSPServerException("No client session has beend initialized for client id: " + clientId);
      }

      Function<Throwable, Void> errorHandler = ex -> {
         String errorMsg = "Could not process message:" + message;
         LOG.error("[ERROR] " + errorMsg, ex);
         getClient().process(new ActionMessage(clientId, error("[GLSP-Server] " + errorMsg, ex)));
         return null;
      };

      try {
         actionDispatcher.dispatch(message).exceptionally(errorHandler);
      } catch (RuntimeException e) {
         errorHandler.apply(e);
      }
   }

   public boolean isInitialized() { return initialized.isDone(); }

   @Override
   public void shutdown() {
      if (this.clientProxy != null) {
         sessionManager.disconnectClient(this.clientProxy);
         this.clientProxy = null;
      }
   }

   public String getApplicationId() { return applicationId; }

   @Override
   public GLSPClient getClient() { return this.clientProxy; }
}
