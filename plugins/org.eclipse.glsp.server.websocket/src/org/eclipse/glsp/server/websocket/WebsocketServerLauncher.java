/*******************************************************************************
 * Copyright (c) 2019-2022 EclipseSource and others.
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

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.eclipse.glsp.server.di.ServerModule;
import org.eclipse.glsp.server.launch.GLSPServerLauncher;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import com.google.inject.Module;

public class WebsocketServerLauncher extends GLSPServerLauncher {
   private static Logger LOGGER = LogManager.getLogger(WebsocketServerLauncher.class);
   protected Server server;
   protected String clientAppPath;
   protected final String endpointPath;
   protected final Level websocketLogLevel;

   public WebsocketServerLauncher(final ServerModule serverModule, final String endpointPath,
      final Module... additionalModules) {
      this(serverModule, endpointPath, Level.INFO, additionalModules);
   }

   public WebsocketServerLauncher(final ServerModule serverModule, final String endpointPath, final Level websocketLogLevel,
      final Module... additionalModules) {
      super(serverModule, additionalModules);
      this.endpointPath = endpointPath.startsWith("/") ? endpointPath.substring(1) : endpointPath;
      this.websocketLogLevel = websocketLogLevel;
   }

   @Override
   @SuppressWarnings("checkstyle:IllegalCatch")
   public void start(final String hostname, final int port) {
      try {
         Log.setLog(new Log4j2Logger("WebsocketServerLauncher"));
         Configurator.setLevel("org.eclipse.jetty", this.websocketLogLevel);

         // Setup Jetty Server
         server = new Server(new InetSocketAddress(hostname, port));
         ServletContextHandler webAppContext;

         // (If a clientAppPath is given)setup client app serving
         if (clientAppPath != null && !clientAppPath.isEmpty()) {
            LOGGER.info("Serving client app from :" + clientAppPath);
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

         @SuppressWarnings("deprecation")
         ServerContainer container = WebSocketServerContainerInitializer.configureContext(webAppContext);
         ServerEndpointConfig.Builder builder = ServerEndpointConfig.Builder.create(GLSPServerEndpoint.class,
            "/" + endpointPath);
         builder.configurator(new GLSPConfigurator(this::createInjector));
         container.addEndpoint(builder.build());

         // Start the server
         try {
            server.start();
            LOGGER.info("GLSP server is running and listening on Endpoint : " + server.getURI() + endpointPath);
            LOGGER.info("Press enter to stop the server...");
            new Thread(() -> {
               try {
                  int key = System.in.read();
                  this.shutdown();
                  if (key == -1) {
                     LOGGER.warn("The standard input stream is empty");
                  }
               } catch (IOException e) {
                  LOGGER.warn(e);
               }

            }).start();

            server.join();
         } catch (Exception exception) {
            LOGGER.warn("Shutting down due to exception", exception);
            System.exit(1);
         }
      } catch (Exception ex) {
         LOGGER.error("Failed to start Websocket GLSP server " + ex.getMessage(), ex);
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
            LOGGER.error("Failed to stop Websocket GLSP server " + ex.getMessage(), ex);
         }
      }

   }
}
