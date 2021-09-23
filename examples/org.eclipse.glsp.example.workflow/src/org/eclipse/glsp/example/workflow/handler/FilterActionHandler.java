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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.glsp.example.workflow.action.FilterAction;
import org.eclipse.glsp.example.workflow.utils.ModelTypes;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.server.actions.AbstractActionHandler;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.SetEditModeAction;
import org.eclipse.glsp.server.features.core.model.UpdateModelAction;
import org.eclipse.glsp.server.layout.LayoutEngine;
import org.eclipse.glsp.server.model.GModelState;

import com.google.common.collect.Streams;
import com.google.inject.Inject;

public class FilterActionHandler extends AbstractActionHandler<FilterAction> {

   @Inject
   protected LayoutEngine layoutEngine;

   @Inject
   protected GModelState modelState;

   @Override
   protected List<Action> executeAction(final FilterAction action) {
      if (!action.getType().equals("reset")) {

         modelState.setProperty("model", modelState.getRoot());
         modelState.updateRoot(EcoreUtil.copy(modelState.getRoot()));

         filter(modelState, action);

         layoutEngine.layout();
         modelState.getRoot().getCssClasses().add("filtered");

         return listOf(new UpdateModelAction(modelState.getRoot()),
            new SetEditModeAction(SetEditModeAction.EDIT_MODE_READONLY));
      }
      modelState.updateRoot(modelState.getProperty("model", GModelRoot.class).get());
      return listOf(new UpdateModelAction(modelState.getRoot()),
         new SetEditModeAction(SetEditModeAction.EDIT_MODE_EDITABLE));
   }

   private void filter(final GModelState modelState, final FilterAction action) {
      List<GModelElement> decisionNodes = modelState.getIndex().getStream(modelState.getRoot())
         .filter(element -> element.getType().equals(ModelTypes.DECISION_NODE))
         .collect(Collectors.toList());

      List<GEdge> incomingAndOutgoingEdges = decisionNodes.stream()
         .flatMap(node -> Streams.concat(
            modelState.getIndex().getIncomingEdges(node).stream(),
            modelState.getIndex().getOutgoingEdges(node).stream()))
         .collect(Collectors.toList());

      List<GModelElement> sourcesAndTargetsOfIncomingAndOutgoingEdges = incomingAndOutgoingEdges.stream()
         .flatMap(edge -> Arrays.asList(edge.getSource(), edge.getTarget()).stream())
         .collect(Collectors.toList());

      List<GModelElement> toHide = modelState.getIndex().getStream(modelState.getRoot())
         .filter(element -> !(sourcesAndTargetsOfIncomingAndOutgoingEdges.contains(element)
            || incomingAndOutgoingEdges.contains(element)))
         .collect(Collectors.toList());

      toHide.forEach(element -> EcoreUtil.delete(element));
   }

}
