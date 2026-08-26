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
package org.eclipse.glsp.server.features.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GGraph;
import org.eclipse.glsp.graph.GModelIndex;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.GraphFactory;
import org.eclipse.glsp.graph.util.GraphUtil;
import org.eclipse.glsp.server.model.DefaultGModelState;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.types.ElementAndAlignment;
import org.eclipse.glsp.server.types.ElementAndBounds;
import org.eclipse.glsp.server.types.ElementAndRoutingPoints;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComputedBoundsActionHandlerTest {

   private static final String NODE_ID = "node0";
   private static final String EDGE_ID = "edge0";

   /** Records which per-kind methods the apply step reached, and drops the routes. */
   private static class TestHandler extends ComputedBoundsActionHandler {
      private final List<String> applied = new ArrayList<>();

      TestHandler(final GModelState state) {
         this.modelState = state;
      }

      void apply(final GGraph root, final ComputedBoundsAction action) {
         applyBounds(root, action);
      }

      @Override
      protected void applyElementBounds(final List<ElementAndBounds> allBounds, final GModelIndex index) {
         applied.add("bounds");
         super.applyElementBounds(allBounds, index);
      }

      @Override
      protected void applyAlignments(final List<ElementAndAlignment> alignments, final GModelIndex index) {
         applied.add("alignments");
         super.applyAlignments(alignments, index);
      }

      @Override
      protected void applyRoutes(final List<ElementAndRoutingPoints> routes, final GModelIndex index) {
         applied.add("routes");
         // deliberately not delegating, an adopter may drop what the client reported
      }
   }

   private GGraph graph;
   private GNode node;
   private GEdge edge;
   private TestHandler handler;

   @BeforeEach
   void setUpGraph() {
      graph = GraphFactory.eINSTANCE.createGGraph();
      graph.setId("graphId");
      graph.setRevision(1);

      node = GraphFactory.eINSTANCE.createGNode();
      node.setId(NODE_ID);
      node.setSize(GraphUtil.dimension(1, 1));

      GNode target = GraphFactory.eINSTANCE.createGNode();
      target.setId("node1");

      edge = GraphFactory.eINSTANCE.createGEdge();
      edge.setId(EDGE_ID);
      edge.setSourceId(NODE_ID);
      edge.setTargetId(target.getId());

      graph.getChildren().add(node);
      graph.getChildren().add(target);
      graph.getChildren().add(edge);

      DefaultGModelState modelState = new DefaultGModelState();
      modelState.updateRoot(graph);
      handler = new TestHandler(modelState);
   }

   private ComputedBoundsAction computedBounds() {
      ElementAndBounds bounds = new ElementAndBounds(entry -> {
         entry.setElementId(NODE_ID);
         entry.setNewSize(GraphUtil.dimension(10, 20));
      });
      // three points, so a routing point survives the source and target being split off
      List<GPoint> route = List.of(GraphUtil.point(0, 0), GraphUtil.point(5, 5), GraphUtil.point(10, 10));
      return new ComputedBoundsAction(List.of(bounds), List.of(),
         List.of(new ElementAndRoutingPoints(EDGE_ID, route)), graph.getRevision());
   }

   @Test
   void appliesEveryKindOfComputedBounds() {
      handler.apply(graph, computedBounds());

      assertEquals(List.of("bounds", "alignments", "routes"), handler.applied);
   }

   @Test
   void letsAnOverrideReplaceASingleKind() {
      handler.apply(graph, computedBounds());

      assertEquals(10, node.getSize().getWidth());
      assertTrue(edge.getRoutingPoints().isEmpty());
   }
}
