/********************************************************************************
 * Copyright (c) 2019-2023 EclipseSource and others.
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
package org.eclipse.glsp.example.workflow;

import static org.eclipse.glsp.example.workflow.utils.ModelTypes.ACTIVITY_NODE;
import static org.eclipse.glsp.example.workflow.utils.ModelTypes.AUTOMATED_TASK;
import static org.eclipse.glsp.example.workflow.utils.ModelTypes.CATEGORY;
import static org.eclipse.glsp.example.workflow.utils.ModelTypes.COMP_HEADER;
import static org.eclipse.glsp.example.workflow.utils.ModelTypes.DECISION_NODE;
import static org.eclipse.glsp.example.workflow.utils.ModelTypes.FORK_NODE;
import static org.eclipse.glsp.example.workflow.utils.ModelTypes.ICON;
import static org.eclipse.glsp.example.workflow.utils.ModelTypes.JOIN_NODE;
import static org.eclipse.glsp.example.workflow.utils.ModelTypes.LABEL_HEADING;
import static org.eclipse.glsp.example.workflow.utils.ModelTypes.LABEL_ICON;
import static org.eclipse.glsp.example.workflow.utils.ModelTypes.LABEL_TEXT;
import static org.eclipse.glsp.example.workflow.utils.ModelTypes.MANUAL_TASK;
import static org.eclipse.glsp.example.workflow.utils.ModelTypes.MERGE_NODE;
import static org.eclipse.glsp.example.workflow.utils.ModelTypes.STRUCTURE;
import static org.eclipse.glsp.example.workflow.utils.ModelTypes.TASK;
import static org.eclipse.glsp.example.workflow.utils.ModelTypes.WEIGHTED_EDGE;
import static org.eclipse.glsp.graph.DefaultTypes.EDGE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.glsp.example.workflow.wfgraph.WfgraphPackage;
import org.eclipse.glsp.graph.DefaultTypes;
import org.eclipse.glsp.graph.GraphPackage;
import org.eclipse.glsp.server.diagram.BaseDiagramConfiguration;
import org.eclipse.glsp.server.layout.ServerLayoutKind;
import org.eclipse.glsp.server.types.EdgeTypeHint;
import org.eclipse.glsp.server.types.ShapeTypeHint;

public class WorkflowDiagramConfiguration extends BaseDiagramConfiguration {

   @Override
   public Map<String, EClass> getTypeMappings() {
      Map<String, EClass> mappings = DefaultTypes.getDefaultTypeMappings();
      mappings.put(LABEL_HEADING, GraphPackage.Literals.GLABEL);
      mappings.put(LABEL_TEXT, GraphPackage.Literals.GLABEL);
      mappings.put(COMP_HEADER, GraphPackage.Literals.GCOMPARTMENT);
      mappings.put(LABEL_ICON, GraphPackage.Literals.GLABEL);
      mappings.put(WEIGHTED_EDGE, WfgraphPackage.Literals.WEIGHTED_EDGE);
      mappings.put(ICON, WfgraphPackage.Literals.ICON);
      mappings.put(ACTIVITY_NODE, WfgraphPackage.Literals.ACTIVITY_NODE);
      mappings.put(TASK, WfgraphPackage.Literals.TASK_NODE);
      mappings.put(CATEGORY, WfgraphPackage.Literals.CATEGORY);
      mappings.put(STRUCTURE, GraphPackage.Literals.GCOMPARTMENT);
      return mappings;
   }

   @Override
   public List<ShapeTypeHint> getShapeTypeHints() {
      List<ShapeTypeHint> nodeHints = new ArrayList<>();
      nodeHints.add(createDefaultShapeTypeHint(MANUAL_TASK));
      nodeHints.add(createDefaultShapeTypeHint(AUTOMATED_TASK));
      ShapeTypeHint catHint = createDefaultShapeTypeHint(CATEGORY);
      catHint.setContainableElementTypeIds(
         Arrays.asList(TASK, ACTIVITY_NODE, CATEGORY));
      nodeHints.add(catHint);
      ShapeTypeHint forkHint = createDefaultShapeTypeHint(FORK_NODE);
      forkHint.setResizable(false);
      nodeHints.add(forkHint);
      ShapeTypeHint joinHint = createDefaultShapeTypeHint(JOIN_NODE);
      joinHint.setResizable(false);
      nodeHints.add(joinHint);
      nodeHints.add(createDefaultShapeTypeHint(DECISION_NODE));
      nodeHints.add(createDefaultShapeTypeHint(MERGE_NODE));
      return nodeHints;
   }

   @Override
   public ShapeTypeHint createDefaultShapeTypeHint(final String elementId) {
      // Override the default-default: for the Workflow example, we want all nodes
      // to be reparentable
      return new ShapeTypeHint(elementId, true, true, true, true);
   }

   @Override
   public List<EdgeTypeHint> getEdgeTypeHints() {
      List<EdgeTypeHint> edgeHints = new ArrayList<>();

      EdgeTypeHint hint = super.createDefaultEdgeTypeHint(EDGE);
      hint.addSourceElementTypeId(TASK, ACTIVITY_NODE, CATEGORY);
      hint.addTargetElementTypeId(TASK, ACTIVITY_NODE, CATEGORY);
      edgeHints.add(hint);

      EdgeTypeHint weightedEdgeHint = createDefaultEdgeTypeHint(WEIGHTED_EDGE);
      weightedEdgeHint.addSourceElementTypeId(ACTIVITY_NODE);
      weightedEdgeHint.addTargetElementTypeId(TASK, ACTIVITY_NODE);
      weightedEdgeHint.setDynamic(true);
      edgeHints.add(weightedEdgeHint);
      return edgeHints;
   }

   @Override
   public ServerLayoutKind getLayoutKind() { return ServerLayoutKind.MANUAL; }

   @Override
   public boolean needsClientLayout() {
      return true;
   }

}
