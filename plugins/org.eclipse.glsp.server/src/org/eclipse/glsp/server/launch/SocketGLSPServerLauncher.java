/********************************************************************************
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
 ********************************************************************************/
package org.eclipse.glsp.server.launch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channels;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.glsp.server.di.ServerModule;
import org.eclipse.glsp.server.gson.ServerGsonConfigurator;
import org.eclipse.glsp.server.protocol.GLSPClient;
import org.eclipse.glsp.server.protocol.GLSPServer;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;

import com.google.gson.GsonBuilder;
import com.google.inject.Injector;
import com.google.inject.Module;

public class SocketGLSPServerLauncher extends GLSPServerLauncher {
   public static final String START_UP_COMPLETE_MSG = "[GLSP-Server]:Startup completed";
   private static Logger LOGGER = LogManager.getLogger(SocketGLSPServerLauncher.class);

   private ExecutorService threadPool;
   private AsynchronousServerSocketChannel serverSocket;
   private CompletableFuture<Void> onShutdown;

   public SocketGLSPServerLauncher(final ServerModule serverModule, final Module... additionalModules) {
      super(serverModule, additionalModules);
   }

   @Override
   public void start(final String hostname, final int port) {
      Future<Void> onClose;
      try {
         onClose = asyncRun(hostname, port);
         onClose.get();
         LOGGER.info("Stopped GLSP server");
      } catch (IOException e) {
         LOGGER.error("Error during server startup!", e);
      } catch (InterruptedException | ExecutionException e) {
         LOGGER.error("Error during server shutdown!", e);
      }
   }

   protected String getStartupCompleteMessage() { return START_UP_COMPLETE_MSG; }

   public Future<Void> asyncRun(final String hostname, final int port)
      throws IOException, InterruptedException, ExecutionException {
      onShutdown = new CompletableFuture<>();

      serverSocket = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(hostname, port));
      threadPool = Executors.newCachedThreadPool();

      CompletionHandler<AsynchronousSocketChannel, Void> handler = new CompletionHandler<>() {
         @Override
         public void completed(final AsynchronousSocketChannel result, final Void attachment) {
            serverSocket.accept(null, this); // Prepare for the next connection
            SocketGLSPServerLauncher.this.createClientConnection(result);
         }

         @Override
         public void failed(final Throwable exc, final Void attachment) {
            LOGGER.error("Client Connection Failed: " + exc.getMessage(), exc);
         }
      };

      serverSocket.accept(null, handler);
      LOGGER.info("The GLSP server is ready to accept new client requests on port: " + port);
      // Print a message to the output stream that indicates that the start is completed.
      // This indicates to the client that the sever process is ready (in an embedded scenario).
      System.out.println(getStartupCompleteMessage());

      return onShutdown;
   }

   protected void createClientConnection(final AsynchronousSocketChannel socketChannel) {
      Injector injector = createInjector();
      GLSPServer glspServer = null;
      try {
         InputStream in = Channels.newInputStream(socketChannel);
         OutputStream out = Channels.newOutputStream(socketChannel);

         glspServer = injector.getInstance(GLSPServer.class);
         Launcher<GLSPClient> launcher = Launcher.createIoLauncher(glspServer, GLSPClient.class, in, out,
            threadPool, messageWrapper(injector), configureGson(injector));
         glspServer.connect(launcher.getRemoteProxy());
         LOGGER.info("Starting GLSP server connection for client " + socketChannel.getRemoteAddress());
         launcher.startListening().get();
         LOGGER.info("Stopping GLSP server connection for client " + socketChannel.getRemoteAddress());
      } catch (IOException | InterruptedException | ExecutionException ex) {
         LOGGER.error("Failed to create client connection " + ex.getMessage(), ex);
      } finally {
         try {
            socketChannel.close();
            if (glspServer != null) {
               glspServer.shutdown();
            }
         } catch (IOException e) {
            LOGGER.error("Excpetion occured when trying to close socketChannel", e);
         }
      }
   }

   protected Consumer<GsonBuilder> configureGson(final Injector injector) {
      ServerGsonConfigurator gsonConf = injector.getInstance(ServerGsonConfigurator.class);
      return (final GsonBuilder builder) -> gsonConf.configureGsonBuilder(builder);
   }

   protected Function<MessageConsumer, MessageConsumer> messageWrapper(final Injector injector) {
      return Function.identity();
   }

   @Override
   public void shutdown() {
      LOGGER.info("Closing all connections to the GLSP server...");
      if (serverSocket.isOpen()) {
         try {
            serverSocket.close();
         } catch (IOException e) {
            LOGGER.error("Failed to close server socket: " + e.getMessage(), e);
         }
      }

      threadPool.shutdown();
      onShutdown.complete(null);
      LOGGER.info("Shutdown GLSP server");
   }
}
