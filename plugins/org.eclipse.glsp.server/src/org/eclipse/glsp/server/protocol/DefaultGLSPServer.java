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
package org.eclipse.glsp.server.protocol;

import static org.eclipse.glsp.server.utils.ServerMessageUtil.error;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.apache.log4j.Logger;
import org.eclipse.glsp.server.actions.ActionExecutor;
import org.eclipse.glsp.server.actions.ActionMessage;
import org.eclipse.glsp.server.actions.ActionRegistry;
import org.eclipse.glsp.server.session.ClientSession;
import org.eclipse.glsp.server.session.ClientSessionManager;
import org.eclipse.glsp.server.types.GLSPServerException;
import org.eclipse.lsp4j.jsonrpc.ResponseErrorException;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode;

import com.google.inject.Inject;

public class DefaultGLSPServer implements GLSPServer {
   private static Logger LOG = Logger.getLogger(DefaultGLSPServer.class);
   public static final String PROTOCOL_VERSION = "0.9.0";

   @Inject
   protected ClientSessionManager sessionManager;

   @Inject
   protected ActionRegistry actionRegistry;

   @Inject
   protected ActionExecutor actionExecutor;

   protected GLSPClient clientProxy;
   protected CompletableFuture<InitializeResult> initialized;

   protected String applicationId;

   protected Map<String, ClientSession> clientSessions;
   protected Set<GLSPServerListener> serverConnectionListeners;

   public DefaultGLSPServer() {
      initialized = new CompletableFuture<>();
      serverConnectionListeners = new LinkedHashSet<>();
      clientSessions = new HashMap<>();
   }

   @Override
   @SuppressWarnings("checkstyle:IllegalCatch")
   public CompletableFuture<InitializeResult> initialize(final InitializeParameters params) {
      LOG.debug("Initializing server with the following params:\n" + params);

      validateProtocolVersion(params);

      if (isInitialized()) {
         if (!params.getApplicationId().equals(applicationId)) {
            String errorMsg = String
               .format("Could not initialize GLSP server for application '%s'. Server has already been initialized"
                  + "for a different appliction with id '%s'", params.getApplicationId(), applicationId);
            throw new ResponseErrorException(new ResponseError(ResponseErrorCode.InvalidParams, errorMsg, params));
         }
         return initialized;
      }

      this.applicationId = params.getApplicationId();

      InitializeResult result = new InitializeResult(PROTOCOL_VERSION);
      actionRegistry.getServerHandledActions()
         .forEach((diagramType, serverHandledActions) -> result.addServerActions(diagramType, serverHandledActions));

      initialized = handleIntializeArgs(result, params.getArgs());
      serverConnectionListeners.forEach(listener -> listener.serverInitialized(this));
      return initialized;
   }

   protected void validateProtocolVersion(final InitializeParameters params) {
      if (!params.getProtocolVersion().equals(PROTOCOL_VERSION)) {
         String errorMsg = String.format(
            "Protocol version mismatch! The client protocol version '%s' is not compatible with the server protocol version '%s'!",
            params.getProtocolVersion(), PROTOCOL_VERSION);
         throw new ResponseErrorException(new ResponseError(ResponseErrorCode.InvalidParams, errorMsg, params));
      }
   }

   protected CompletableFuture<InitializeResult> handleIntializeArgs(final InitializeResult result,
      final Map<String, String> args) {
      return CompletableFuture.completedFuture(result);
   }

   protected void validateServerInitialized() {
      if (!isInitialized()) {
         throw new ResponseErrorException(new ResponseError(ResponseErrorCode.serverNotInitialized,
            "The GLSP server has not been initialized.", null));
      }
   }

   @Override
   public CompletableFuture<Void> initializeClientSession(final InitializeClientSessionParameters params) {
      LOG.debug("Initializing client session with the following params:\n" + params);

      validateServerInitialized();

      try {
         ClientSession session = sessionManager.getOrCreateClientSession(params.getClientSessionId(),
            params.getDiagramType());
         clientSessions.put(params.getClientSessionId(), session);
         return handleInitializeClientSessionArgs(params.getArgs());
      } catch (GLSPServerException exception) {
         throw new ResponseErrorException(
            new ResponseError(ResponseErrorCode.InternalError, exception.getMessage(), exception));
      }

   }

   protected CompletableFuture<Void> handleInitializeClientSessionArgs(final Map<String, String> args) {
      return CompletableFuture.completedFuture(null);
   }

   @Override
   public CompletableFuture<Void> disposeClientSession(final DisposeClientSessionParameters params) {
      LOG.debug("Dispose client session with the following params:\n" + params);
      validateServerInitialized();
      if (sessionManager.disposeClientSession(params.getClientSessionId())) {
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
         serverConnectionListeners.forEach(listener -> listener.clientConnected(clientProxy));
      }
   }

   @Override
   @SuppressWarnings("checkstyle:IllegalCatch")
   public void process(final ActionMessage message) {
      validateServerInitialized();
      LOG.debug("process " + message);
      String clientSessionId = message.getClientId();
      if (!clientSessions.containsKey(clientSessionId)) {
         throw new ResponseErrorException(new ResponseError(ResponseErrorCode.InvalidParams,
            "No client session has beend initialized for client id: " + clientSessionId, message));
      }

      Function<Throwable, Void> errorHandler = ex -> {
         String errorMsg = "Could not process message:" + message;
         LOG.error("[ERROR] " + errorMsg, ex);
         getClient().process(new ActionMessage(clientSessionId, error("[GLSP-Server] " + errorMsg, ex)));
         return null;
      };

      try {
         clientSessions.get(clientSessionId).getActionDispatcher().dispatch(message.getAction())
            .exceptionally(errorHandler);
      } catch (RuntimeException e) {
         errorHandler.apply(e);
      }
   }

   public boolean isInitialized() { return initialized.isDone(); }

   @Override
   public void shutdown() {
      LOG.info("Shutdown GLSP Server " + this);
      serverConnectionListeners.forEach(listener -> listener.serverShutDown(this));
      clientSessions.clear();
      initialized = new CompletableFuture<>();
      this.clientProxy = null;
      if (actionExecutor != null) {
         actionExecutor.dispose();
      }
   }

   public String getApplicationId() { return applicationId; }

   @Override
   public GLSPClient getClient() { return this.clientProxy; }

   @Override
   public boolean addListener(final GLSPServerListener listener) {
      return serverConnectionListeners.add(listener);
   }

   @Override
   public boolean remove(final GLSPServerListener listener) {
      return serverConnectionListeners.remove(listener);
   }
}
