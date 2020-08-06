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
import org.eclipse.glsp.api.action.ActionMessage;
import org.eclipse.glsp.api.action.ActionDispatcher;
import org.eclipse.glsp.api.jsonrpc.GLSPClient;
import org.eclipse.glsp.api.jsonrpc.GLSPClientProvider;
import org.eclipse.glsp.api.jsonrpc.GLSPServer;
import org.eclipse.glsp.api.jsonrpc.InitializeParameters;
import org.eclipse.glsp.api.model.ModelStateProvider;
import org.eclipse.glsp.api.types.ServerStatus;
import org.eclipse.glsp.api.types.Severity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.inject.Inject;

public class DefaultGLSPServer<T> implements GLSPServer {

   @Inject
   protected ModelStateProvider modelStateProvider;

   @Inject
   protected GLSPClientProvider clientProxyProvider;
   @Inject
   protected ActionDispatcher actionDispatcher;
   private static Logger log = Logger.getLogger(DefaultGLSPServer.class);

   private ServerStatus status;

   private GLSPClient clientProxy;
   private Class<T> optionsClazz;

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
         if (optionsClazz != null && params.getOptions() instanceof JsonElement) {
            T options = new Gson().fromJson((JsonElement) params.getOptions(), optionsClazz);
            return handleOptions(options);
         }
         return handleOptions(null);
      } catch (Throwable ex) {
         log.error("Could not initialize server due to corrupted options: " + params.getOptions(), ex);
         return CompletableFuture.completedFuture(false);
      }
   }

   protected CompletableFuture<Boolean> handleOptions(final T options) {
      return CompletableFuture.completedFuture(true);
   }

   @Override
   public void connect(final GLSPClient clientProxy) {
      this.clientProxy = clientProxy;
      status = new ServerStatus(Severity.OK, "Connection successfull");
   }

   @Override
   @SuppressWarnings("checkstyle:IllegalCatch")
   public void process(final ActionMessage message) {
      log.debug("process " + message);
      String clientId = message.getClientId();
      try {
         // FIXME: It seems we don't get access to the clientId when the connection
         // is initialized. ClientId is only retrieved through messages; so this
         // is currently the earliest we can register the clientProxy
         this.clientProxyProvider.register(clientId, clientProxy);
         actionDispatcher.dispatch(message);
      } catch (RuntimeException e) {
         String errorMsg = "Could not process message:" + message;
         log.error("[ERROR] " + errorMsg, e);
         actionDispatcher.dispatch(clientId, error("[GLSP-Server] " + errorMsg, e));
      }
   }

   @Override
   public ServerStatus getStatus() { return status; }

   @Override
   public CompletableFuture<Object> shutdown() {
      return new CompletableFuture<>();
   }

   @Override
   public void exit(final String clientId) {
      modelStateProvider.remove(clientId);
      clientProxyProvider.remove(clientId);
   }
}
