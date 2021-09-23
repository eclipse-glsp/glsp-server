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
package org.eclipse.glsp.example.workflow.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.eclipse.glsp.example.workflow.action.HighlightPathAction;
import org.eclipse.glsp.example.workflow.wfgraph.TaskNode;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.server.actions.AbstractActionHandler;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.features.core.model.UpdateModelAction;
import org.eclipse.glsp.server.model.GModelState;

import com.google.inject.Inject;

public class HighlightPathActionHandler extends AbstractActionHandler<HighlightPathAction> {

   private static final String PULSE = "pulse";

   @Inject
   protected GModelState modelState;

   @Override
   protected List<Action> executeAction(final HighlightPathAction action) {
      Optional<TaskNode> taskNode = action.getTaskNode(modelState);
      if (taskNode.isEmpty()) {
         return Collections.emptyList();
      }

      clearCssClass(modelState);
      addCssClassToIncomingEdges(modelState, taskNode);

      return listOf(new UpdateModelAction(modelState.getRoot()));
   }

   private void clearCssClass(final GModelState modelState) {
      modelState.getIndex().getAllByClass(GEdge.class)
         .forEach(edge -> edge.getCssClasses().remove(PULSE));
   }

   private void addCssClassToIncomingEdges(final GModelState modelState, final Optional<TaskNode> taskNode) {
      Collection<GEdge> edges = collectIncomingEdges(taskNode.get(), modelState);
      edges.forEach(edge -> edge.getCssClasses().add(PULSE));
   }

   private Collection<GEdge> collectIncomingEdges(final GModelElement node, final GModelState modelState) {
      List<GEdge> edges = new ArrayList<>();
      for (GEdge incomingEdge : modelState.getIndex().getIncomingEdges(node)) {
         edges.add(incomingEdge);
         edges.addAll(collectIncomingEdges(incomingEdge.getSource(), modelState));
      }
      return edges;
   }

}
