/*******************************************************************************
 * Copyright (c) 2019-2023 EclipseSource and others.
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

import java.net.InetSocketAddress;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.eclipse.glsp.server.di.ServerModule;
import org.eclipse.glsp.server.launch.GLSPServerLauncher;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.websocket.jakarta.server.config.JakartaWebSocketServletContainerInitializer;
import org.eclipse.jetty.server.Server;

import com.google.inject.Module;

import jakarta.websocket.server.ServerEndpointConfig;

public class WebsocketServerLauncher extends GLSPServerLauncher {
   public static final String START_UP_COMPLETE_MSG = "[GLSP-Server]:Startup completed. Accepting requests on port:";
   protected static Logger LOGGER = LogManager.getLogger(WebsocketServerLauncher.class);
   protected Server server;
   protected final String endpointPath;
   protected final Level websocketLogLevel;

   public WebsocketServerLauncher(final ServerModule serverModule, final String endpointPath,
      final Module... additionalModules) {
      this(serverModule, endpointPath, Level.INFO, additionalModules);
   }

   public WebsocketServerLauncher(final ServerModule serverModule, final String endpointPath,
      final Level websocketLogLevel,
      final Module... additionalModules) {
      super(serverModule, additionalModules);
      this.endpointPath = endpointPath.startsWith("/") ? endpointPath.substring(1) : endpointPath;
      this.websocketLogLevel = websocketLogLevel;
   }

   protected String getStartupCompleteMessage() { return START_UP_COMPLETE_MSG; }

   @Override
   @SuppressWarnings("checkstyle:IllegalCatch")
   public void start(final String hostname, int port) {
      try {
         Configurator.setLevel("org.eclipse.jetty", this.websocketLogLevel);

         // Setup Jetty Server
         server = new Server(new InetSocketAddress(hostname, port));
         ServletContextHandler webAppContext;

         webAppContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
         webAppContext.setContextPath("/");

         // Configure web socket

         JakartaWebSocketServletContainerInitializer.configure(webAppContext, (servletContext, wsContainer) -> {
            ServerEndpointConfig.Builder builder = ServerEndpointConfig.Builder.create(GLSPServerEndpoint.class,
               "/" + endpointPath);
            builder.configurator(new GLSPConfigurator(this::createInjector));
            ServerEndpointConfig endPointConfig = builder.build();

            // wsContainer.setDefaultMaxSessionIdleTimeout(-1);
            wsContainer.addEndpoint(endPointConfig);
         });

         server.setHandler(webAppContext);

         // Start the server
         try {
            server.start();
            if (port == 0) {
               port = server.getURI().getPort();
            }
            LOGGER.info("GLSP server is running and listening on Endpoint : " + server.getURI() + endpointPath);
            System.out.println(getStartupCompleteMessage() + port);
            server.join();
         } catch (Exception exception) {
            LOGGER.warn("Shutting down due to exception", exception);
            System.exit(1);
         }
      } catch (Exception ex) {
         LOGGER.error("Failed to start Websocket GLSP server " + ex.getMessage(), ex);
      }
   }

   @Override
   @SuppressWarnings("checkstyle:IllegalCatch")
   public void shutdown() {
      if (server.isRunning()) {
         try {
            server.stop();
         } catch (Exception ex) {
            LOGGER.error("Failed to stop Websocket GLSP server " + ex.getMessage(), ex);
         }
      }

   }
}
