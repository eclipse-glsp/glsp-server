/********************************************************************************
 * Copyright (c) 2019-2026 EclipseSource and others.
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
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channels;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
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
   public static final String START_UP_COMPLETE_MSG = "[GLSP-Server]:Startup completed. Accepting requests on port:";

   /** Common prefix of the only headers lsp4j understands, {@code Content-Length} and {@code Content-Type}. */
   private static final String BASE_PROTOCOL_HEADER_PREFIX = "Content-";
   protected static Logger LOGGER = LogManager.getLogger(SocketGLSPServerLauncher.class);

   protected ExecutorService threadPool;
   protected AsynchronousServerSocketChannel serverSocket;
   protected CompletableFuture<Void> onShutdown;

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

   public Future<Void> asyncRun(final String hostname, int port)
      throws IOException, InterruptedException, ExecutionException {
      onShutdown = new CompletableFuture<>();

      serverSocket = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(hostname, port));
      if (port == 0) {
         port = ((InetSocketAddress) serverSocket.getLocalAddress()).getPort();
      }

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
      System.out.println(getStartupCompleteMessage() + port);

      return onShutdown;
   }

   protected void createClientConnection(final AsynchronousSocketChannel socketChannel) {
      GLSPServer glspServer = null;
      try {
         // Read once, so the pushback buffer always fits the peeked bytes.
         int prefixLength = getStreamPrefixLength();
         PushbackInputStream in = new PushbackInputStream(Channels.newInputStream(socketChannel), prefixLength);
         // Both checks run before the injector, so a connection that is not a GLSP client never allocates a server.
         String streamPrefix = peekStreamPrefix(in, prefixLength);
         if (streamPrefix.isEmpty()) {
            LOGGER.debug("Client closed the connection before sending anything");
            return;
         }
         if (!isBaseProtocolMessage(streamPrefix)) {
            LOGGER.warn("Rejecting client connection from " + socketChannel.getRemoteAddress()
               + ": the stream does not start with a JSON-RPC base protocol header.");
            return;
         }
         OutputStream out = Channels.newOutputStream(socketChannel);
         Injector injector = createInjector();

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

   /**
    * Reads the first bytes of the stream and pushes them back, so the client is unaffected. Blocks without a timeout
    * until the client sends enough bytes or hangs up. A GLSP client sends its first message right away.
    *
    * @return the first bytes as ASCII, or an empty string if the client hung up without sending anything
    */
   protected String peekStreamPrefix(final PushbackInputStream in, final int length) throws IOException {
      byte[] prefix = new byte[length];
      int read = in.readNBytes(prefix, 0, prefix.length);
      if (read <= 0) {
         return "";
      }
      in.unread(prefix, 0, read);
      return new String(prefix, 0, read, StandardCharsets.US_ASCII);
   }

   /**
    * Number of bytes passed to {@link #isBaseProtocolMessage(String)}. Override it if that check needs more bytes.
    */
   protected int getStreamPrefixLength() { return BASE_PROTOCOL_HEADER_PREFIX.length(); }

   /**
    * Whether the stream starts with a JSON-RPC base protocol header. Anything else, an HTTP request in particular, is
    * not a GLSP client: the framing is close enough to HTTP that a request posted by a web page would otherwise be
    * read as a message. The match is case-sensitive like the header parsing of lsp4j. A client that sends another
    * header first is rejected, but lsp4j and vscode-jsonrpc always start with {@code Content-Length}.
    */
   protected boolean isBaseProtocolMessage(final String streamPrefix) {
      return streamPrefix.startsWith(BASE_PROTOCOL_HEADER_PREFIX);
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
