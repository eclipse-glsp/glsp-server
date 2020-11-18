/********************************************************************************
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

import org.apache.log4j.Logger;
import org.eclipse.glsp.server.di.GLSPModule;
import org.eclipse.glsp.server.jsonrpc.GLSPJsonrpcClient;
import org.eclipse.glsp.server.jsonrpc.GLSPJsonrpcServer;
import org.eclipse.glsp.server.jsonrpc.GsonConfigurator;
import org.eclipse.glsp.server.protocol.GLSPServer;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;

import com.google.gson.GsonBuilder;
import com.google.inject.Injector;

public class DefaultGLSPServerLauncher extends GLSPServerLauncher {
   public static final String START_UP_COMPLETE_MSG = "[GLSP-Server]:Startup completed";
   private static Logger log = Logger.getLogger(DefaultGLSPServerLauncher.class);

   private ExecutorService threadPool;
   private AsynchronousServerSocketChannel serverSocket;
   private CompletableFuture<Void> onShutdown;

   public DefaultGLSPServerLauncher(final GLSPModule glspModule) {
      super(glspModule);
   }

   @Override
   public void start(final String hostname, final int port) {
      Future<Void> onClose;
      try {
         onClose = asyncRun(hostname, port);
         onClose.get();
         log.info("Stopped GLSP server");
      } catch (IOException | InterruptedException | ExecutionException e) {
         log.error("Error during server close!", e);
      }
   }

   public Future<Void> asyncRun(final String hostname, final int port)
      throws IOException, InterruptedException, ExecutionException {
      onShutdown = new CompletableFuture<>();

      serverSocket = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(hostname, port));
      threadPool = Executors.newCachedThreadPool();

      CompletionHandler<AsynchronousSocketChannel, Void> handler = new CompletionHandler<>() {
         @Override
         public void completed(final AsynchronousSocketChannel result, final Void attachment) {
            serverSocket.accept(null, this); // Prepare for the next connection
            DefaultGLSPServerLauncher.this.createClientConnection(result);
         }

         @Override
         public void failed(final Throwable exc, final Void attachment) {
            log.error("Client Connection Failed: " + exc.getMessage(), exc);
         }
      };

      serverSocket.accept(null, handler);
      log.info("The GLSP server is ready to accept new client requests on port: " + port);
      // Print a message to the output stream that indicates that the start is completed.
      // This indicates to the client that the sever process is ready (in an embedded scenario).
      System.out.println(START_UP_COMPLETE_MSG);

      return onShutdown;
   }

   private void createClientConnection(final AsynchronousSocketChannel socketChannel) {
      Injector injector = createInjector();
      GsonConfigurator gsonConf = injector.getInstance(GsonConfigurator.class);
      try {
         InputStream in = Channels.newInputStream(socketChannel);
         OutputStream out = Channels.newOutputStream(socketChannel);

         Consumer<GsonBuilder> configureGson = (final GsonBuilder builder) -> gsonConf.configureGsonBuilder(builder);
         Function<MessageConsumer, MessageConsumer> wrapper = Function.identity();
         GLSPJsonrpcServer glspServer = (GLSPJsonrpcServer) injector.getInstance(GLSPServer.class);
         Launcher<GLSPJsonrpcClient> launcher = Launcher.createIoLauncher(glspServer, GLSPJsonrpcClient.class, in, out,
            threadPool, wrapper, configureGson);
         glspServer.connect(launcher.getRemoteProxy());
         log.info("Starting GLSP server connection for client " + socketChannel.getRemoteAddress());
         launcher.startListening().get();
         log.info("Stopping GLSP server connection for client" + socketChannel.getRemoteAddress());
         glspServer.shutdown();
      } catch (IOException | InterruptedException | ExecutionException ex) {
         log.error("Failed to create client connection " + ex.getMessage(), ex);
      } finally {
         try {
            socketChannel.close();
         } catch (IOException e) {
            log.error("Excpetion occured when trying to close socketChannel", e);
         }
      }
   }

   @Override
   public void shutdown() {
      log.info("Stopping all connections to the GLSP server...");
      if (serverSocket.isOpen()) {
         try {
            serverSocket.close();
         } catch (IOException e) {
            log.error("Failed to close serverSocket: " + e.getMessage(), e);
         }
      }

      threadPool.shutdown();
      onShutdown.complete(null);
      log.info("Stopped GLSP server");
   }
}
