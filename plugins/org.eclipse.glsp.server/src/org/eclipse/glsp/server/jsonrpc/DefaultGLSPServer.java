/********************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
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

import static org.eclipse.glsp.api.utils.ServerMessageUtil.error;

import java.util.concurrent.CompletableFuture;

import org.apache.log4j.Logger;
import org.eclipse.glsp.api.action.ActionDispatcher;
import org.eclipse.glsp.api.action.ActionMessage;
import org.eclipse.glsp.api.action.kind.RequestModelAction;
import org.eclipse.glsp.api.jsonrpc.GLSPJsonrpcClient;
import org.eclipse.glsp.api.jsonrpc.GLSPJsonrpcServer;
import org.eclipse.glsp.api.model.ModelStateProvider;
import org.eclipse.glsp.api.protocol.ClientSessionManager;
import org.eclipse.glsp.api.protocol.GLSPClient;
import org.eclipse.glsp.api.protocol.GLSPServerException;
import org.eclipse.glsp.api.protocol.InitializeParameters;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.inject.Inject;

public class DefaultGLSPServer<T> implements GLSPJsonrpcServer {
   private static Logger log = Logger.getLogger(DefaultGLSPServer.class);

   @Inject
   protected ModelStateProvider modelStateProvider;

   @Inject
   protected ClientSessionManager sessionManager;

   @Inject
   protected ActionDispatcher actionDispatcher;

   private GLSPClient clientProxy;
   private final Class<T> optionsClazz;
   private boolean initialized;

   private String applicationId;

   public DefaultGLSPServer() {
      this(null);
   }

   public DefaultGLSPServer(final Class<T> optionsClazz) {
      this.optionsClazz = optionsClazz;
   }

   @Override
   @SuppressWarnings("checkstyle:IllegalCatch")
   public CompletableFuture<Boolean> initialize(final InitializeParameters params) {
      try {
         boolean completed;
         this.applicationId = params.getApplicationId();
         if (optionsClazz != null && params.getOptions() instanceof JsonElement) {
            T options = new Gson().fromJson((JsonElement) params.getOptions(), optionsClazz);
            completed = handleOptions(options);
         } else {
            completed = handleOptions(null);
         }
         this.initialized = completed;
         return CompletableFuture.completedFuture(completed);
      } catch (Throwable ex) {
         log.error("Could not initialize server due to corrupted options: " + params.getOptions(), ex);
         this.initialized = false;
         return CompletableFuture.completedFuture(false);
      }
   }

   protected boolean handleOptions(final T options) {
      return true;
   }

   @Override
   public void connect(final GLSPJsonrpcClient clientProxy) {
      this.clientProxy = clientProxy;
      if (clientProxy != null) {
         this.sessionManager.connectClient(clientProxy);
      }
   }

   @Override
   @SuppressWarnings("checkstyle:IllegalCatch")
   public void process(final ActionMessage message) {
      log.debug("process " + message);
      String clientId = message.getClientId();
      try {
         if (message.getAction() instanceof RequestModelAction) {
            if (!this.sessionManager.createClientSession(clientProxy, clientId)) {
               throw new GLSPServerException(String.format(
                  "Could not create session for client id '%s'. Another session with the same id already exists",
                  clientId));
            }
         }

         if (!initialized) {
            throw new GLSPServerException(
               String.format("Could not process action message '%s'. The server has not been initalized yet", message));
         }
         actionDispatcher.dispatch(message);
      } catch (RuntimeException e) {
         String errorMsg = "Could not process message:" + message;
         log.error("[ERROR] " + errorMsg, e);
         actionDispatcher.dispatch(clientId, error("[GLSP-Server] " + errorMsg, e));
      }
   }

   @Override
   public CompletableFuture<Boolean> shutdown() {
      boolean completed = false;
      if (this.clientProxy != null) {
         this.initialized = false;
         completed = sessionManager.disconnectClient(this.clientProxy);
         this.clientProxy = null;
      }
      return CompletableFuture.completedFuture(completed);
   }

   public String getApplicationId() { return applicationId; }
}
