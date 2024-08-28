/********************************************************************************
 * Copyright (c) 2020-2021 EclipseSource and others.
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
package org.eclipse.glsp.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.glsp.graph.gson.GraphGsonConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class GModelIndexWhenDeserializedFromJsonTest {

   private static final String RESOURCE_PATH = "resources/";

   private GraphGsonConfigurator gsonConfigurator;

   @BeforeEach
   void initializeGsonConfigurator() {
      gsonConfigurator = new GraphGsonConfigurator().withDefaultTypes();
   }

   @Test
   void testFindById() throws IOException {
      GGraph graph = loadResource("graphWithTwoNodesAndOneEdge.graph");
      GModelIndex index = GModelIndex.get(graph);

      assertEquals(graph, index.get("graphId").get());
      assertEquals(graph.getChildren().get(0), index.get("node1").get());
      assertEquals(graph.getChildren().get(1), index.get("node2").get());
      assertEquals(graph.getChildren().get(2), index.get("edge12").get());
   }

   @Test
   void testGetIncoming() throws IOException {
      GGraph graph = loadResource("graphWithTwoNodesAndOneEdge.graph");
      GModelElement node1 = graph.getChildren().get(0);
      GModelElement node2 = graph.getChildren().get(1);
      GModelElement edge = graph.getChildren().get(2);
      GModelIndex index = GModelIndex.get(graph);

      Collection<GEdge> incomingEdgesOfNode1 = index.getIncomingEdges(node1);
      Collection<GEdge> outgoingEdgesOfNode1 = index.getOutgoingEdges(node1);
      assertEquals(0, incomingEdgesOfNode1.size());
      assertEquals(1, outgoingEdgesOfNode1.size());
      assertTrue(outgoingEdgesOfNode1.contains(edge));

      Collection<GEdge> incomingEdgesOfNode2 = index.getIncomingEdges(node2);
      Collection<GEdge> outgoingEdgesOfNode2 = index.getOutgoingEdges(node2);
      assertEquals(1, incomingEdgesOfNode2.size());
      assertEquals(0, outgoingEdgesOfNode2.size());
      assertTrue(incomingEdgesOfNode2.contains(edge));
   }

   @Test
   void testFindByIdIfEdgeBeforeSourceOrTarget() throws IOException {
      GGraph graph = loadResource("graphEdgeBeforeSourceAndTarget.graph");
      GModelIndex index = GModelIndex.get(graph);

      assertEquals(graph, index.get("graphId").get());
      assertEquals(graph.getChildren().get(0), index.get("edge12").get());
      assertEquals(graph.getChildren().get(1), index.get("node1").get());
      assertEquals(graph.getChildren().get(2), index.get("node2").get());
   }

   @Test
   void testGetIncomingIfEdgeBeforeSourceOrTarget() throws IOException {
      GGraph graph = loadResource("graphEdgeBeforeSourceAndTarget.graph");
      GModelElement edge = graph.getChildren().get(0);
      GModelElement node1 = graph.getChildren().get(1);
      GModelElement node2 = graph.getChildren().get(2);
      GModelIndex index = GModelIndex.get(graph);

      Collection<GEdge> incomingEdgesOfNode1 = index.getIncomingEdges(node1);
      Collection<GEdge> outgoingEdgesOfNode1 = index.getOutgoingEdges(node1);
      assertEquals(0, incomingEdgesOfNode1.size());
      assertEquals(1, outgoingEdgesOfNode1.size());
      assertTrue(outgoingEdgesOfNode1.contains(edge));

      Collection<GEdge> incomingEdgesOfNode2 = index.getIncomingEdges(node2);
      Collection<GEdge> outgoingEdgesOfNode2 = index.getOutgoingEdges(node2);
      assertEquals(1, incomingEdgesOfNode2.size());
      assertEquals(0, outgoingEdgesOfNode2.size());
      assertTrue(incomingEdgesOfNode2.contains(edge));
   }

   @Test
   void testGetAllSvgDefaulTypes() throws IOException {
      GGraph graph = loadResource("graphWithAllDefaultTypes.graph");
      EList<GModelElement> children = graph.getChildren();
      assertType(children.get(0), GEdge.class, DefaultTypes.EDGE);
      assertType(children.get(1), GNode.class, DefaultTypes.NODE);
      assertType(children.get(2), GNode.class, DefaultTypes.NODE_CIRCLE);
      assertType(children.get(3), GNode.class, DefaultTypes.NODE_RECTANGLE);
      assertType(children.get(4), GNode.class, DefaultTypes.NODE_DIAMOND);
      assertType(children.get(5), GButton.class, DefaultTypes.BUTTON);
      assertType(children.get(6), GButton.class, DefaultTypes.EXPAND_BUTTON);
      assertType(children.get(7), GPreRenderedElement.class, DefaultTypes.PRE_RENDERED);
      assertType(children.get(8), GShapePreRenderedElement.class, DefaultTypes.SHAPE_PRE_RENDERED);
      assertType(children.get(9), GForeignObjectElement.class, DefaultTypes.FOREIGN_OBJECT);
      EList<GModelElement> nodeChidlren = children.get(1).getChildren();
      assertType(nodeChidlren.get(0), GCompartment.class, DefaultTypes.COMPARTMENT_HEADER);
      assertType(nodeChidlren.get(1), GCompartment.class, DefaultTypes.COMPARTMENT);
      assertType(nodeChidlren.get(2), GLabel.class, DefaultTypes.LABEL);
      assertType(nodeChidlren.get(3), GPort.class, DefaultTypes.PORT);

   }

   private <T extends GModelElement> void assertType(final GModelElement element, final Class<T> clazz,
      final String type) {
      assertTrue(clazz.isInstance(element));
      assertTrue(element.getType().equals(type));
   }

   private GGraph loadResource(final String file) throws IOException {
      Gson gson = gsonConfigurator.configureGsonBuilder(new GsonBuilder()).create();
      JsonReader jsonReader = new JsonReader(new FileReader(RESOURCE_PATH + file));
      return gson.fromJson(jsonReader, GGraph.class);
   }
}
