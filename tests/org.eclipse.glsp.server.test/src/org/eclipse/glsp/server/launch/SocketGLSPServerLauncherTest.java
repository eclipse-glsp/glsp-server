/*******************************************************************************
 * Copyright (c) 2026 EclipseSource and others.
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
package org.eclipse.glsp.server.launch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.glsp.server.actions.ActionMessage;
import org.eclipse.glsp.server.di.ServerModule;
import org.eclipse.glsp.server.gson.ServerGsonConfigurator;
import org.eclipse.glsp.server.protocol.DisposeClientSessionParameters;
import org.eclipse.glsp.server.protocol.GLSPClient;
import org.eclipse.glsp.server.protocol.GLSPServer;
import org.eclipse.glsp.server.protocol.GLSPServerListener;
import org.eclipse.glsp.server.protocol.InitializeClientSessionParameters;
import org.eclipse.glsp.server.protocol.InitializeParameters;
import org.eclipse.glsp.server.protocol.InitializeResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonParser;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Runs the {@link SocketGLSPServerLauncher} on a real socket and talks to it with the LSP base protocol framing.
 */
@SuppressWarnings({ "checkstyle:IllegalThrows", "checkstyle:VisibilityModifier" })
class SocketGLSPServerLauncherTest {
   private static final int TIMEOUT_MS = 5000;
   private static final int CHUNK_PAUSE_MS = 100;

   private TestLauncher launcher;
   private final List<Socket> clients = new ArrayList<>();

   @BeforeEach
   void startLauncher() throws Exception {
      launcher = new TestLauncher();
      launcher.asyncRun("localhost", 0);
   }

   @AfterEach
   void tearDown() {
      clients.forEach(client -> {
         try {
            client.close();
         } catch (IOException e) {
            // ignore, the test is over
         }
      });
      clients.clear();
      launcher.shutdown();
   }

   @Test
   void framedRequestIsProcessed() throws Exception {
      assertEquals(Optional.of(1), sendAndReadResponseId(frame(initializeRequest(1))));

      assertTrue(launcher.initialized.get());
      assertEquals(1, launcher.createdInjectors.get());
   }

   @Test
   void framedRequestStartingWithContentTypeIsProcessed() throws Exception {
      String withContentType = "Content-Type: application/vscode-jsonrpc; charset=utf-8\r\n"
         + frame(initializeRequest(1));

      assertEquals(Optional.of(1), sendAndReadResponseId(withContentType));
   }

   @Test
   void headerSplitAcrossWritesIsProcessed() throws Exception {
      String framed = frame(initializeRequest(1));

      assertEquals(Optional.of(1), sendAndReadResponseId(framed.substring(0, 4), framed.substring(4)));
   }

   @Test
   void unframedMessageIsRejected() throws Exception {
      assertEquals(Optional.empty(), sendAndReadResponseId(initializeRequest(1)));

      assertEquals(0, launcher.createdInjectors.get(), "A rejected connection must not create a server instance");
   }

   @Test
   void httpRequestIsRejected() throws Exception {
      String body = initializeRequest(1);

      // What a browser sends for a cross-origin `fetch(..., { method: 'POST', mode: 'no-cors', body })`.
      assertEquals(Optional.empty(), sendAndReadResponseId("POST / HTTP/1.1\r\nHost: localhost\r\n"
         + "Content-Type: text/plain;charset=UTF-8\r\nContent-Length: " + body.length() + "\r\n\r\n" + body));

      assertFalse(launcher.initialized.get(), "The smuggled request must not reach the server");
      assertEquals(0, launcher.createdInjectors.get(), "A rejected connection must not create a server instance");
   }

   @Test
   void connectionWithoutDataDoesNotCreateAServerInstance() throws Exception {
      Socket client = new Socket("localhost", launcher.port());
      client.close();

      assertTrue(launcher.handledConnection.await(TIMEOUT_MS, TimeUnit.MILLISECONDS),
         "The launcher did not finish handling the connection");
      assertEquals(0, launcher.createdInjectors.get());
   }

   private static String initializeRequest(final int id) {
      return "{\"jsonrpc\":\"2.0\",\"id\":" + id + ",\"method\":\"initialize\",\"params\":"
         + "{\"applicationId\":\"client-app\",\"protocolVersion\":\"1.0.0\"}}";
   }

   private static String frame(final String payload) {
      return "Content-Length: " + payload.getBytes(StandardCharsets.UTF_8).length + "\r\n\r\n" + payload;
   }

   /**
    * Sends the chunks on a new connection, pausing between them so that each arrives separately, and returns the id
    * of the response, or empty if the server hung up.
    */
   private Optional<Integer> sendAndReadResponseId(final String... chunks) throws IOException, InterruptedException {
      Socket client = new Socket("localhost", launcher.port());
      clients.add(client);
      client.setSoTimeout(TIMEOUT_MS);
      client.setTcpNoDelay(true);
      for (int i = 0; i < chunks.length; i++) {
         if (i > 0) {
            Thread.sleep(CHUNK_PAUSE_MS);
         }
         client.getOutputStream().write(chunks[i].getBytes(StandardCharsets.UTF_8));
         client.getOutputStream().flush();
      }

      String response;
      try {
         response = readMessage(client.getInputStream());
      } catch (SocketException e) {
         // The server closed the connection while the request was still in flight.
         return Optional.empty();
      }
      if (response == null) {
         return Optional.empty();
      }
      return Optional.of(JsonParser.parseString(response).getAsJsonObject().get("id").getAsInt());
   }

   private static String readMessage(final InputStream in) throws IOException {
      StringBuilder headers = new StringBuilder();
      while (!headers.toString().endsWith("\r\n\r\n")) {
         int next = in.read();
         if (next < 0) {
            return null;
         }
         headers.append((char) next);
      }
      int length = Integer.parseInt(headers.toString().replaceAll("(?s).*Content-Length: (\\d+).*", "$1"));
      return new String(in.readNBytes(length), StandardCharsets.UTF_8);
   }

   static class TestLauncher extends SocketGLSPServerLauncher {
      final AtomicBoolean initialized = new AtomicBoolean();
      final AtomicInteger createdInjectors = new AtomicInteger();
      final CountDownLatch handledConnection = new CountDownLatch(1);

      TestLauncher() {
         super(new ServerModule());
      }

      int port() {
         try {
            return ((InetSocketAddress) serverSocket.getLocalAddress()).getPort();
         } catch (IOException e) {
            throw new IllegalStateException("Launcher is not listening", e);
         }
      }

      @Override
      protected void createClientConnection(final AsynchronousSocketChannel socketChannel) {
         try {
            super.createClientConnection(socketChannel);
         } finally {
            handledConnection.countDown();
         }
      }

      @Override
      public Injector createInjector() {
         createdInjectors.incrementAndGet();
         List<Module> testModules = new ArrayList<>();
         testModules.add(binder -> {
            binder.bind(GLSPServer.class).toInstance(new StubGLSPServer(this));
            binder.bind(ServerGsonConfigurator.class).toInstance(builder -> builder);
         });
         return Guice.createInjector(testModules);
      }
   }

   static class StubGLSPServer implements GLSPServer {
      private final TestLauncher launcher;

      StubGLSPServer(final TestLauncher launcher) {
         this.launcher = launcher;
      }

      @Override
      public CompletableFuture<InitializeResult> initialize(final InitializeParameters params) {
         launcher.initialized.set(true);
         return CompletableFuture.completedFuture(new InitializeResult("1.0.0"));
      }

      @Override
      public CompletableFuture<Void> initializeClientSession(final InitializeClientSessionParameters params) {
         return CompletableFuture.completedFuture(null);
      }

      @Override
      public CompletableFuture<Void> disposeClientSession(final DisposeClientSessionParameters params) {
         return CompletableFuture.completedFuture(null);
      }

      @Override
      public void process(final ActionMessage message) {}

      @Override
      public void shutdown() {}

      @Override
      public void connect(final GLSPClient client) {}

      @Override
      public GLSPClient getClient() { return null; }

      @Override
      public boolean addListener(final GLSPServerListener listener) {
         return false;
      }

      @Override
      public boolean remove(final GLSPServerListener listener) {
         return false;
      }
   }
}
