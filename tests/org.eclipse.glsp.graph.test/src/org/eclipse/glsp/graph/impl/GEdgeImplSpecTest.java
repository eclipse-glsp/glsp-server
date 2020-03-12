/********************************************************************************
 * Copyright (c) 2020 EclipseSource and others.
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
package org.eclipse.glsp.graph.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GGraph;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.graph.GraphFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GEdgeImplSpecTest {

   private GGraph graph;
   private GNode node1;
   private GNode node2;

   @BeforeEach
   void setUpGraphWithTwoNodes() {
      graph = GraphFactory.eINSTANCE.createGGraph();
      graph.setRevision(42);
      graph.setId("graphId");

      node1 = GraphFactory.eINSTANCE.createGNode();
      node1.setId("node1");
      node1.setPosition(GraphFactory.eINSTANCE.createGPoint());
      node1.getPosition().setX(10.0);
      node1.getPosition().setY(20.0);

      node2 = GraphFactory.eINSTANCE.createGNode();
      node2.setId("node2");
      node2.setPosition(GraphFactory.eINSTANCE.createGPoint());
      node2.getPosition().setX(30.0);
      node2.getPosition().setY(40.0);

      graph.getChildren().add(node1);
      graph.getChildren().add(node2);
   }

   @Test
   void testCreatingEdgeById() {
      GEdge edge = GraphFactory.eINSTANCE.createGEdge();
      edge.setId("edge12");
      edge.setSourceId(node1.getId());
      edge.setTargetId(node2.getId());

      graph.getChildren().add(edge);

      assertEquals(node1, edge.getSource());
      assertEquals(node2, edge.getTarget());
   }

   @Test
   void testCreatingEdgeByReference() {
      GEdge edge = GraphFactory.eINSTANCE.createGEdge();
      edge.setId("edge12");
      edge.setSource(node1);
      edge.setTarget(node2);

      graph.getChildren().add(edge);

      assertEquals(node1.getId(), edge.getSourceId());
      assertEquals(node2.getId(), edge.getTargetId());
   }
}
