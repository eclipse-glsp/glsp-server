/*******************************************************************************
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
 ******************************************************************************/
package org.eclipse.glsp.server.websocket;

import java.util.Collection;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import org.eclipse.glsp.server.jsonrpc.GLSPJsonrpcClient;
import org.eclipse.glsp.server.jsonrpc.GLSPJsonrpcServer;
import org.eclipse.glsp.server.jsonrpc.GsonConfigurator;
import org.eclipse.glsp.server.protocol.GLSPServer;
import org.eclipse.lsp4j.jsonrpc.Launcher.Builder;
import org.eclipse.lsp4j.websocket.WebSocketEndpoint;

import com.google.inject.Inject;

public class GLSPServerEndpoint extends WebSocketEndpoint<GLSPJsonrpcClient> {
   public static final int MAX_TEXT_MESSAGE_BUFFER_SIZE = 8388608;
   @SuppressWarnings("rawtypes")
   @Inject
   private GLSPServer glspServer;

   @Inject
   private GsonConfigurator gsonConfigurator;

   @Override
   protected void configure(final Builder<GLSPJsonrpcClient> builder) {
      builder.setLocalService(glspServer);
      builder.setRemoteInterface(GLSPJsonrpcClient.class);
      builder.configureGson(gsonConfigurator::configureGsonBuilder);
   }

   @Override
   protected void connect(final Collection<Object> localServices, final GLSPJsonrpcClient remoteProxy) {
      localServices.stream().filter(GLSPJsonrpcServer.class::isInstance).map(GLSPJsonrpcServer.class::cast)
         .forEach(ca -> ca.connect(remoteProxy));
   }

   @Override
   public void onOpen(final Session session, final EndpointConfig config) {
      session.setMaxTextMessageBufferSize(MAX_TEXT_MESSAGE_BUFFER_SIZE);
      super.onOpen(session, config);
   }

   @Override
   public void onClose(final Session session, final CloseReason closeReason) {
      glspServer.shutdown();
   }
}
