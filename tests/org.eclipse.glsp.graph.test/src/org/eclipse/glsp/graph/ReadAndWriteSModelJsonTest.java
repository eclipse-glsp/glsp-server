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
package org.eclipse.glsp.graph;

import static org.eclipse.glsp.graph.GraphPackage.Literals.GEDGE;
import static org.eclipse.glsp.graph.GraphPackage.Literals.GGRAPH;
import static org.eclipse.glsp.graph.GraphPackage.Literals.GNODE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.glsp.graph.gson.GGraphGsonConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

public class ReadAndWriteSModelJsonTest {

   private static final String RESOURCE_PATH = "resources/";

   private GGraphGsonConfigurator gsonConfigurator;

   @BeforeEach
   void initializeGsonConfigurator() {
      gsonConfigurator = new GGraphGsonConfigurator().withDefaultTypes();
   }

   @Test
   void testReadingGraphWithCustomTypeMap() throws IOException {
      Map<String, EClass> customTypes = new HashMap<>();
      customTypes.put("mygraph", GGRAPH);
      customTypes.put("mynode", GNODE);
      customTypes.put("myedge", GEDGE);
      gsonConfigurator.withTypes(customTypes);

      GGraph graph = loadResource("graphWithCustomTypeMap.graph");
      assertTrue(graph.getChildren().get(0) instanceof GNode);
      assertTrue(graph.getChildren().get(1) instanceof GEdge);
   }

   @Test
   void testReadingGraphWithTwoNodesAndOneEdge() throws IOException {
      GGraph graph = loadResource("graphWithTwoNodesAndOneEdge.graph");
      assertEquals(42, graph.getRevision());
      assertEquals("graphId", graph.getId());
      assertEquals(3, graph.getChildren().size());

      GNode node1 = (GNode) graph.getChildren().get(0);
      assertEquals("node1", node1.getId());
      assertEquals(10.0, node1.getPosition().getX());
      assertEquals(20.0, node1.getPosition().getY());

      GNode node2 = (GNode) graph.getChildren().get(1);
      assertEquals("node2", node2.getId());
      assertEquals(30.0, node2.getPosition().getX());
      assertEquals(40.0, node2.getPosition().getY());

      GEdge edge = (GEdge) graph.getChildren().get(2);
      assertEquals("edge12", edge.getId());
      assertEquals("node1", edge.getSourceId());
      assertEquals("node2", edge.getTargetId());
   }

   @Test
   void testWritingGraphWithTwoNodesAndOneEdge() {
      GGraph graph = GraphFactory.eINSTANCE.createGGraph();
      graph.setType("graph");
      graph.setRevision(42);
      graph.setId("graphId");

      GNode node1 = GraphFactory.eINSTANCE.createGNode();
      node1.setId("node1");
      node1.setType("node");
      node1.setPosition(GraphFactory.eINSTANCE.createGPoint());
      node1.getPosition().setX(10.0);
      node1.getPosition().setY(20.0);

      GNode node2 = GraphFactory.eINSTANCE.createGNode();
      node2.setType("node");
      node2.setId("node2");
      node2.setPosition(GraphFactory.eINSTANCE.createGPoint());
      node2.getPosition().setX(30.0);
      node2.getPosition().setY(40.0);

      GEdge edge = GraphFactory.eINSTANCE.createGEdge();
      edge.setId("edge12");
      edge.setType("edge");
      edge.setSourceId(node1.getId());
      edge.setTargetId(node2.getId());

      graph.getChildren().addAll(Lists.newArrayList(node1, node2, edge));

      JsonObject jsonGraph = writeToJson(graph).getAsJsonObject();
      assertEquals("graphId", jsonGraph.get("id").getAsString());
      assertEquals(42, jsonGraph.get("revision").getAsInt());
      assertEquals("graph", jsonGraph.get("type").getAsString());

      JsonArray children = jsonGraph.get("children").getAsJsonArray();
      assertEquals(3, children.size());

      JsonObject node1Json = children.get(0).getAsJsonObject();
      assertEquals("node1", node1Json.get("id").getAsString());
      assertEquals("node", node1Json.get("type").getAsString());
      JsonObject node1JsonPosition = node1Json.get("position").getAsJsonObject();
      assertEquals(10.0, node1JsonPosition.get("x").getAsInt());
      assertEquals(20.0, node1JsonPosition.get("y").getAsInt());

      JsonObject node2Json = children.get(1).getAsJsonObject();
      assertEquals("node2", node2Json.get("id").getAsString());
      assertEquals("node", node2Json.get("type").getAsString());
      JsonObject node2JsonPosition = node2Json.get("position").getAsJsonObject();
      assertEquals(30.0, node2JsonPosition.get("x").getAsInt());
      assertEquals(40.0, node2JsonPosition.get("y").getAsInt());

      JsonObject edgeJson = children.get(2).getAsJsonObject();
      assertEquals("edge12", edgeJson.get("id").getAsString());
      assertEquals("edge", edgeJson.get("type").getAsString());
      assertEquals("node1", edgeJson.get("sourceId").getAsString());
      assertEquals("node2", edgeJson.get("targetId").getAsString());
   }

   private GGraph loadResource(final String file) throws IOException {
      Gson gson = gsonConfigurator.configureGsonBuilder(new GsonBuilder()).create();
      JsonReader jsonReader = new JsonReader(new FileReader(RESOURCE_PATH + file));
      return gson.fromJson(jsonReader, GGraph.class);
   }

   private JsonElement writeToJson(final GGraph graph) {
      Gson gson = gsonConfigurator.configureGsonBuilder(new GsonBuilder()).create();
      return gson.toJsonTree(graph);
   }
}
