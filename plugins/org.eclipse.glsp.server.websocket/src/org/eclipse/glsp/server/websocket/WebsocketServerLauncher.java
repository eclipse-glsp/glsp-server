/*******************************************************************************
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
 ******************************************************************************/
package org.eclipse.glsp.server.websocket;

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;

import org.apache.log4j.Logger;
import org.eclipse.glsp.server.di.GLSPModule;
import org.eclipse.glsp.server.launch.GLSPServerLauncher;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

public class WebsocketServerLauncher extends GLSPServerLauncher {
   private static Logger LOG = Logger.getLogger(WebsocketServerLauncher.class);
   private Server server;
   private String clientAppPath;
   private final String endpointPath;

   public WebsocketServerLauncher(final GLSPModule module, final String endpointPath) {
      super(module);
      this.endpointPath = endpointPath.startsWith("/") ? endpointPath.substring(1) : endpointPath;
      addAdditionalModules(new WebsocketModule());
   }

   public WebsocketServerLauncher(final GLSPModule module, final String endpointPath, final String clientAppPath) {
      this(module, endpointPath);
      this.clientAppPath = clientAppPath;
   }

   @Override
   @SuppressWarnings("checkstyle:IllegalCatch")
   public void start(final String hostname, final int port) {
      try {
         // Setup Jetty Server
         server = new Server(new InetSocketAddress(hostname, port));
         ServletContextHandler webAppContext;

         // (If a clientAppPath is given)setup client app serving
         if (clientAppPath != null && !clientAppPath.isEmpty()) {
            LOG.info("Serving client app from :" + clientAppPath);
            webAppContext = new WebAppContext();
            webAppContext.setResourceBase(clientAppPath);
            String[] welcomeFiles = { "index.html" };
            webAppContext.setWelcomeFiles(welcomeFiles);
            webAppContext.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
            webAppContext.setInitParameter("org.eclipse.jetty.servlet.Default.useFileMappedBuffer", "false");
         } else {
            webAppContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
            webAppContext.setContextPath("/");
         }

         server.setHandler(webAppContext);
         // Configure web socket

         ServerContainer container = WebSocketServerContainerInitializer.configureContext(webAppContext);
         ServerEndpointConfig.Builder builder = ServerEndpointConfig.Builder.create(GLSPServerEndpoint.class,
            "/" + endpointPath);
         builder.configurator(new GLSPConfigurator(this::createInjector));
         container.addEndpoint(builder.build());

         // Start the server
         try {
            server.start();
            LOG.info("GLSP server is running and listening on Endpoint : " + server.getURI() + endpointPath);
            LOG.info("Press enter to stop the server...");
            new Thread(() -> {
               try {
                  int key = System.in.read();
                  this.shutdown();
                  if (key == -1) {
                     LOG.warn("The standard input stream is empty");
                  }
               } catch (IOException e) {
                  LOG.warn(e);
               }

            }).start();

            server.join();
         } catch (Exception exception) {
            LOG.warn("Shutting down due to exception", exception);
            System.exit(1);
         }
      } catch (Exception ex) {
         LOG.error("Failed to start Websocket GLSP server " + ex.getMessage(), ex);
      }
   }

   public String getClientAppPath() { return clientAppPath; }

   public void setClientAppPath(final String clientAppPath) { this.clientAppPath = clientAppPath; }

   @Override
   @SuppressWarnings("checkstyle:IllegalCatch")
   public void shutdown() {
      if (server.isRunning()) {
         try {
            server.stop();
         } catch (Exception ex) {
            LOG.error("Failed to stop Websocket GLSP server " + ex.getMessage(), ex);
         }
      }

   }
}
