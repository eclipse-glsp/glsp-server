/********************************************************************************
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
 ********************************************************************************/
package org.eclipse.glsp.server.internal.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.actions.ActionHandlerRegistry;
import org.eclipse.glsp.server.actions.ClientActionForwarder;
import org.eclipse.glsp.server.actions.RejectAction;
import org.eclipse.glsp.server.actions.RequestAction;
import org.eclipse.glsp.server.actions.ResponseAction;
import org.eclipse.glsp.server.features.core.model.SetModelAction;
import org.eclipse.glsp.server.types.GLSPServerException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "checkstyle:IllegalThrows", "checkstyle:IllegalCatch", "checkstyle:VisibilityModifier" })
class DefaultActionDispatcherTest {

   private static final long AWAIT_MS = 1_000L;

   private DefaultActionDispatcher dispatcher;
   private StubForwarder forwarder;
   private MapHandlerRegistry registry;

   @BeforeEach
   void setUp() {
      dispatcher = new DefaultActionDispatcher();
      dispatcher.clientId = "test";
      registry = new MapHandlerRegistry();
      dispatcher.actionHandlerRegistry = registry;
      forwarder = new StubForwarder();
      dispatcher.clientActionForwarder = forwarder;
   }

   @AfterEach
   void tearDown() {
      // Restore default in case a test toggled the forwarder off.
      forwarder.shouldHandle = true;
      dispatcher.dispose();
   }

   // -----------------------------------------------------------------------
   // ID generation, request stamping
   // -----------------------------------------------------------------------

   @Test
   void generateRequestIdProducesServerPrefixedId() {
      assertEquals("server_test_1", dispatcher.generateRequestId());
      assertEquals("server_test_2", dispatcher.generateRequestId());
   }

   @Test
   void requestStampsRequestIdWhenUnset() {
      TestRequest request = new TestRequest();
      assertEquals("", request.getRequestId());

      dispatcher.request(request);

      assertNotNull(request.getRequestId());
      assertTrue(request.getRequestId().startsWith("server_test_"));
   }

   @Test
   void requestKeepsExistingRequestId() {
      TestRequest request = new TestRequest("preset-id");

      dispatcher.request(request);

      assertEquals("preset-id", request.getRequestId());
   }

   @Test
   void requestUntilStampsTimeoutOnAction() {
      TestRequest request = new TestRequest();

      dispatcher.requestUntil(request, 1234L, false);

      assertEquals(1234L, request.getTimeout());
   }

   // -----------------------------------------------------------------------
   // Request / response correlation
   // -----------------------------------------------------------------------

   @Test
   void requestResolvesWithMatchingResponse() throws Exception {
      TestRequest request = new TestRequest();
      CompletableFuture<TestResponse> future = dispatcher.request(request);

      TestResponse response = new TestResponse(request.getRequestId());
      dispatcher.dispatch(response);

      assertEquals(response, future.get(AWAIT_MS, TimeUnit.MILLISECONDS));
   }

   @Test
   void requestRejectsOnRejectAction() {
      TestRequest request = new TestRequest();
      CompletableFuture<TestResponse> future = dispatcher.request(request);

      RejectAction reject = new RejectAction(request.getRequestId(), "boom", "details");
      dispatcher.dispatch(reject);

      ExecutionException error = assertThrows(ExecutionException.class,
         () -> future.get(AWAIT_MS, TimeUnit.MILLISECONDS));
      assertInstanceOf(GLSPServerException.class, error.getCause());
      assertTrue(error.getCause().getMessage().contains("boom"));
      assertTrue(error.getCause().getMessage().contains("details"));
   }

   @Test
   void multiplePendingRequestsResolveIndependently() throws Exception {
      TestRequest r1 = new TestRequest();
      TestRequest r2 = new TestRequest();
      CompletableFuture<TestResponse> f1 = dispatcher.request(r1);
      CompletableFuture<TestResponse> f2 = dispatcher.request(r2);

      // Respond to r2 first; r1 should stay pending.
      TestResponse resp2 = new TestResponse(r2.getRequestId());
      dispatcher.dispatch(resp2);
      assertEquals(resp2, f2.get(AWAIT_MS, TimeUnit.MILLISECONDS));
      assertFalse(f1.isDone());

      TestResponse resp1 = new TestResponse(r1.getRequestId());
      dispatcher.dispatch(resp1);
      assertEquals(resp1, f1.get(AWAIT_MS, TimeUnit.MILLISECONDS));
   }

   @Test
   void reentrantRequestFromHandlerCorrelatesResponse() throws Exception {
      AtomicReference<TestResponse> innerResponse = new AtomicReference<>();
      AtomicReference<Throwable> innerError = new AtomicReference<>();

      registry.register(TriggerAction.class, new ActionHandler() {
         @Override
         public List<Class<? extends Action>> getHandledActionTypes() {
            return List.of(TriggerAction.class);
         }

         @Override
         public List<Action> execute(final Action action) {
            TestRequest req = new TestRequest();
            CompletableFuture<TestResponse> future = dispatcher.request(req);
            // Dispatch the matching response from another thread so the dispatcher thread can
            // unblock via interceptPendingResponse. The pending future is already registered by
            // request() so a race-free completion is safe regardless of when this thread runs.
            new Thread(() -> dispatcher.dispatch(new TestResponse(req.getRequestId()))).start();
            try {
               innerResponse.set(future.get(AWAIT_MS, TimeUnit.MILLISECONDS));
            } catch (Exception ex) {
               innerError.set(ex);
            }
            return List.of();
         }
      });

      dispatcher.dispatch(new TriggerAction()).get(AWAIT_MS, TimeUnit.MILLISECONDS);

      assertNull(innerError.get());
      assertNotNull(innerResponse.get());
   }

   // -----------------------------------------------------------------------
   // Timeouts and stale markers
   // -----------------------------------------------------------------------

   @Test
   void requestUntilRejectsOnHardTimeout() {
      TestRequest request = new TestRequest();
      CompletableFuture<TestResponse> future = dispatcher.requestUntil(request, 50L, true);

      ExecutionException error = assertThrows(ExecutionException.class,
         () -> future.get(AWAIT_MS, TimeUnit.MILLISECONDS));
      assertInstanceOf(TimeoutException.class, error.getCause());
   }

   @Test
   void requestUntilResolvesNullOnSoftTimeout() throws Exception {
      TestRequest request = new TestRequest();
      CompletableFuture<TestResponse> future = dispatcher.requestUntil(request, 50L, false);

      assertNull(future.get(AWAIT_MS, TimeUnit.MILLISECONDS));
   }

   @Test
   void requestUntilNoArgsUsesActionTimeout() throws Exception {
      TestRequest request = new TestRequest();
      request.setTimeout(50L);

      assertNull(dispatcher.requestUntil(request).get(AWAIT_MS, TimeUnit.MILLISECONDS));
   }

   @Test
   void zeroTimeoutFiresImmediately() {
      TestRequest request = new TestRequest();
      CompletableFuture<TestResponse> future = dispatcher.requestUntil(request, 0L, true);

      ExecutionException error = assertThrows(ExecutionException.class,
         () -> future.get(AWAIT_MS, TimeUnit.MILLISECONDS));
      assertInstanceOf(TimeoutException.class, error.getCause());
   }

   @Test
   void lateResponseWithinGraceHasResponseIdCleared() throws Exception {
      TestRequest request = new TestRequest();
      CompletableFuture<TestResponse> future = dispatcher.requestUntil(request, 50L, false);
      future.get(AWAIT_MS, TimeUnit.MILLISECONDS);

      TestResponse late = new TestResponse(request.getRequestId());
      dispatcher.dispatch(late);

      assertEquals("", late.getResponseId());
   }

   @Test
   void staleMarkerIsCleanedUpAfterGrace() throws Exception {
      TestableDispatcher fastGraceDispatcher = new TestableDispatcher(50L);
      fastGraceDispatcher.clientId = "test";
      fastGraceDispatcher.actionHandlerRegistry = new MapHandlerRegistry();
      fastGraceDispatcher.clientActionForwarder = new StubForwarder();
      try {
         TestRequest request = new TestRequest();
         fastGraceDispatcher.requestUntil(request, 50L, false).get(AWAIT_MS, TimeUnit.MILLISECONDS);

         // Wait until the grace cleanup actually removed the marker (bounded by AWAIT_MS).
         long deadline = System.currentTimeMillis() + AWAIT_MS;
         while (fastGraceDispatcher.requestTimeouts.containsKey(request.getRequestId())
            && System.currentTimeMillis() < deadline) {
            Thread.sleep(10);
         }
         assertFalse(fastGraceDispatcher.requestTimeouts.containsKey(request.getRequestId()),
            "Stale marker was not cleaned up within timeout");

         TestResponse late = new TestResponse(request.getRequestId());
         fastGraceDispatcher.dispatch(late);

         // Marker is gone; responseId must remain so the action follows the normal path.
         assertEquals(request.getRequestId(), late.getResponseId());
      } finally {
         fastGraceDispatcher.dispose();
      }
   }

   // -----------------------------------------------------------------------
   // dispatchAll
   // -----------------------------------------------------------------------

   @Test
   void dispatchAllProcessesActionsInOrder() throws Exception {
      List<String> handled = Collections.synchronizedList(new ArrayList<>());
      registry.register(MarkerAction.class, new RecordingHandler(MarkerAction.class, action -> {
         if (action instanceof MarkerAction marker) {
            handled.add(marker.label);
         }
         return List.of();
      }));

      List<CompletableFuture<Void>> futures = dispatcher.dispatchAll(
         List.of(new MarkerAction("a"), new MarkerAction("b"), new MarkerAction("c")));

      for (CompletableFuture<Void> future : futures) {
         future.get(AWAIT_MS, TimeUnit.MILLISECONDS);
      }

      assertEquals(List.of("a", "b", "c"), handled);
   }

   @Test
   void dispatchAllAbortsAfterFailure() throws Exception {
      List<String> handled = Collections.synchronizedList(new ArrayList<>());
      registry.register(MarkerAction.class, new RecordingHandler(MarkerAction.class, action -> {
         if (action instanceof MarkerAction marker) {
            if (marker.shouldFail) {
               throw new RuntimeException("boom");
            }
            handled.add(marker.label);
         }
         return List.of();
      }));

      MarkerAction first = new MarkerAction("a");
      MarkerAction failing = new MarkerAction("b", true);
      MarkerAction third = new MarkerAction("c");
      List<CompletableFuture<Void>> futures = dispatcher.dispatchAll(List.of(first, failing, third));

      futures.get(0).get(AWAIT_MS, TimeUnit.MILLISECONDS);
      assertThrows(ExecutionException.class,
         () -> futures.get(1).get(AWAIT_MS, TimeUnit.MILLISECONDS));
      assertThrows(ExecutionException.class,
         () -> futures.get(2).get(AWAIT_MS, TimeUnit.MILLISECONDS));

      assertEquals(List.of("a"), handled);
   }

   // -----------------------------------------------------------------------
   // Post-update queue drain
   // -----------------------------------------------------------------------

   @Test
   void interceptedSetModelResponseDrainsPostUpdateQueue() throws Exception {
      List<String> handled = Collections.synchronizedList(new ArrayList<>());
      CountDownLatch postUpdateLatch = new CountDownLatch(2);
      registry.register(MarkerAction.class, new RecordingHandler(MarkerAction.class, action -> {
         if (action instanceof MarkerAction marker) {
            handled.add(marker.label);
            postUpdateLatch.countDown();
         }
         return List.of();
      }));

      TestRequest request = new TestRequest();
      CompletableFuture<TestResponse> future = dispatcher.request(request);

      dispatcher.dispatchAfterNextUpdate(new MarkerAction("post1"), new MarkerAction("post2"));

      SetModelAction response = new SetModelAction();
      response.setResponseId(request.getRequestId());
      dispatcher.dispatch(response);

      future.get(AWAIT_MS, TimeUnit.MILLISECONDS);
      assertTrue(postUpdateLatch.await(AWAIT_MS, TimeUnit.MILLISECONDS),
         "Post-update actions were not dispatched within timeout");
      assertEquals(List.of("post1", "post2"), handled);
   }

   // -----------------------------------------------------------------------
   // Failure cleanup
   // -----------------------------------------------------------------------

   @Test
   void requestRejectsWhenDispatchFails() {
      // Empty handler registry + forwarder that does NOT handle the action -> dispatch fails.
      forwarder.shouldHandle = false;

      TestRequest request = new TestRequest();
      CompletableFuture<TestResponse> future = dispatcher.request(request);

      ExecutionException error = assertThrows(ExecutionException.class,
         () -> future.get(AWAIT_MS, TimeUnit.MILLISECONDS));
      assertInstanceOf(IllegalArgumentException.class, error.getCause());
      assertFalse(dispatcher.pendingRequests.containsKey(request.getRequestId()));
      assertFalse(dispatcher.requestTimeouts.containsKey(request.getRequestId()));
   }

   // -----------------------------------------------------------------------
   // Disposal
   // -----------------------------------------------------------------------

   @Test
   void disposeRejectsPendingRequests() {
      TestRequest request = new TestRequest();
      CompletableFuture<TestResponse> future = dispatcher.request(request);

      dispatcher.dispose();

      ExecutionException error = assertThrows(ExecutionException.class,
         () -> future.get(AWAIT_MS, TimeUnit.MILLISECONDS));
      assertInstanceOf(IllegalStateException.class, error.getCause());
      assertTrue(error.getCause().getMessage().contains("disposed"));
   }

   // -----------------------------------------------------------------------
   // Test fixtures
   // -----------------------------------------------------------------------

   static final class TestRequest extends RequestAction<TestResponse> {
      static final String KIND = "test.request";

      TestRequest() {
         super(KIND);
      }

      TestRequest(final String requestId) {
         super(KIND, requestId);
      }
   }

   static final class TestResponse extends ResponseAction {
      static final String KIND = "test.response";

      TestResponse() {
         super(KIND);
      }

      TestResponse(final String responseId) {
         super(KIND);
         setResponseId(responseId);
      }
   }

   static final class TriggerAction extends Action {
      static final String KIND = "test.trigger";

      TriggerAction() {
         super(KIND);
      }
   }

   static final class MarkerAction extends Action {
      static final String KIND = "test.marker";
      final String label;
      final boolean shouldFail;

      MarkerAction(final String label) {
         this(label, false);
      }

      MarkerAction(final String label, final boolean shouldFail) {
         super(KIND);
         this.label = label;
         this.shouldFail = shouldFail;
      }
   }

   /**
    * Forwarder stub that pretends the action is "handled by the client" by default. Set
    * {@link #shouldHandle} to {@code false} to simulate a forwarder that does not handle the
    * action (which combined with an empty handler registry causes the dispatch to fail).
    */
   static final class StubForwarder extends ClientActionForwarder {
      List<Action> forwarded = new ArrayList<>();
      boolean shouldHandle = true;

      @Override
      public boolean handle(final Action action) {
         if (!shouldHandle) {
            return false;
         }
         forwarded.add(action);
         return true;
      }
   }

   /** Action handler registry that allows test cases to register handlers for specific kinds. */
   static final class MapHandlerRegistry implements ActionHandlerRegistry {
      private final Map<Class<? extends Action>, List<ActionHandler>> handlers = new HashMap<>();

      @Override
      public boolean register(final Class<? extends Action> key, final ActionHandler element) {
         handlers.computeIfAbsent(key, k -> new ArrayList<>()).add(element);
         return true;
      }

      @Override
      public boolean deregister(final Class<? extends Action> key, final ActionHandler element) {
         return handlers.getOrDefault(key, List.of()).remove(element);
      }

      @Override
      public boolean deregisterAll(final Class<? extends Action> key) {
         return handlers.remove(key) != null;
      }

      @Override
      public boolean hasKey(final Class<? extends Action> key) {
         return handlers.containsKey(key);
      }

      @Override
      public List<ActionHandler> get(final Class<? extends Action> key) {
         return handlers.getOrDefault(key, List.of());
      }

      @Override
      public List<ActionHandler> getAll() {
         List<ActionHandler> all = new ArrayList<>();
         handlers.values().forEach(all::addAll);
         return all;
      }
   }

   /** Simple action handler that records or transforms actions of a given type. */
   static final class RecordingHandler implements ActionHandler {
      private final Class<? extends Action> handledType;
      private final Function<Action, List<Action>> fn;

      RecordingHandler(final Class<? extends Action> handledType, final Function<Action, List<Action>> fn) {
         this.handledType = handledType;
         this.fn = fn;
      }

      @Override
      public List<Class<? extends Action>> getHandledActionTypes() {
         return List.of(handledType);
      }

      @Override
      public List<Action> execute(final Action action) {
         return fn.apply(action);
      }
   }

   /** Subclass exposing an overridable stale-timeout grace period for fast tests. */
   static final class TestableDispatcher extends DefaultActionDispatcher {
      private final long graceMs;

      TestableDispatcher(final long graceMs) {
         this.graceMs = graceMs;
      }

      @Override
      protected long getStaleTimeoutGraceMs() {
         return graceMs;
      }
   }
}
