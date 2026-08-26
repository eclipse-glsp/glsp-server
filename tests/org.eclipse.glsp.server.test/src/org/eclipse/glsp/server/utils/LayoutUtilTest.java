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
package org.eclipse.glsp.server.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GGraph;
import org.eclipse.glsp.graph.GModelIndex;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.GraphFactory;
import org.eclipse.glsp.graph.builder.impl.GArguments;
import org.eclipse.glsp.graph.util.GraphUtil;
import org.eclipse.glsp.server.features.core.model.ComputedBoundsAction;
import org.eclipse.glsp.server.types.ElementAndAlignment;
import org.eclipse.glsp.server.types.ElementAndBounds;
import org.eclipse.glsp.server.types.ElementAndRoutingPoints;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LayoutUtilTest {

   private static final String NODE_ID = "node0";
   private static final String EDGE_ID = "edge0";
   private static final String UNKNOWN_ID = "does-not-exist";

   private GGraph graph;
   private GNode node;
   private GModelIndex index;

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

      GEdge edge = GraphFactory.eINSTANCE.createGEdge();
      edge.setId(EDGE_ID);
      edge.setSourceId(NODE_ID);
      edge.setTargetId(target.getId());

      graph.getChildren().add(node);
      graph.getChildren().add(target);
      graph.getChildren().add(edge);

      index = GModelIndex.create(graph);
   }

   private static ElementAndBounds bounds(final String elementId) {
      return new ElementAndBounds(bounds -> {
         bounds.setElementId(elementId);
         bounds.setNewSize(GraphUtil.dimension(10, 20));
      });
   }

   private static ElementAndAlignment alignment(final String elementId) {
      return new ElementAndAlignment(alignment -> {
         alignment.setElementId(elementId);
         alignment.setNewAlignment(GraphUtil.point(1, 1));
      });
   }

   private static ElementAndRoutingPoints route(final String elementId, final GPoint... points) {
      return new ElementAndRoutingPoints(elementId, List.of(points));
   }

   private void assertNodeResized() {
      assertEquals(10, node.getSize().getWidth());
      assertEquals(20, node.getSize().getHeight());
   }

   @Test
   void appliesTheReportedBounds() {
      assertTrue(LayoutUtil.applyBounds(bounds(NODE_ID), index).isPresent());
      assertNodeResized();
   }

   @Test
   void reportsBoundsOfAnUnresolvableElementAsNotApplied() {
      assertTrue(LayoutUtil.applyBounds(bounds(UNKNOWN_ID), index).isEmpty());
   }

   @Test
   void reportsAlignmentOfAnUnresolvableElementAsNotApplied() {
      assertTrue(LayoutUtil.applyAlignment(alignment(UNKNOWN_ID), index).isEmpty());
   }

   @Test
   void appliesTheReportedRoute() {
      GPoint source = GraphUtil.point(0, 0);
      GPoint middle = GraphUtil.point(5, 5);
      GPoint target = GraphUtil.point(10, 10);

      Optional<GEdge> applied = LayoutUtil.applyRoute(route(EDGE_ID, source, middle, target), index);

      assertTrue(applied.isPresent());
      // the source and target point are moved into the args, only the intermediate points remain as routing points
      assertEquals(List.of(middle), applied.get().getRoutingPoints());
      assertEquals(source, applied.get().getArgs().get(GArguments.KEY_EDGE_SOURCE_POINT));
      assertEquals(target, applied.get().getArgs().get(GArguments.KEY_EDGE_TARGET_POINT));
   }

   @Test
   void reportsRouteOfAnUnresolvableEdgeAsNotApplied() {
      assertTrue(
         LayoutUtil.applyRoute(route(UNKNOWN_ID, GraphUtil.point(0, 0), GraphUtil.point(10, 10)), index).isEmpty());
   }

   @Test
   void reportsRouteOfAnElementThatIsNoEdgeAsNotApplied() {
      assertTrue(
         LayoutUtil.applyRoute(route(NODE_ID, GraphUtil.point(0, 0), GraphUtil.point(10, 10)), index).isEmpty());
   }

   @Test
   void reportsRouteWithoutSourceAndTargetPointAsNotApplied() {
      assertTrue(LayoutUtil.applyRoute(route(EDGE_ID, GraphUtil.point(0, 0)), index).isEmpty());
   }

   @Test
   void keepsApplyingComputedBoundsAfterAnEntryThatCannotBeApplied() {
      ComputedBoundsAction action = new ComputedBoundsAction(List.of(bounds(UNKNOWN_ID), bounds(NODE_ID)),
         List.of(alignment(UNKNOWN_ID)), List.of(route(EDGE_ID, GraphUtil.point(0, 0))), graph.getRevision());

      LayoutUtil.applyElementBounds(action.getBounds(), index);
      LayoutUtil.applyAlignments(action.getAlignments(), index);
      LayoutUtil.applyRoutes(action.getRoutes(), index);

      assertNodeResized();
   }
}
