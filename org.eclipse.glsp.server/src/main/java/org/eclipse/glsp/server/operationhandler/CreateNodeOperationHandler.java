/********************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
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
package org.eclipse.glsp.server.operationhandler;

import java.util.Optional;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.kind.AbstractOperationAction;
import org.eclipse.glsp.api.action.kind.CreateNodeOperationAction;
import org.eclipse.glsp.api.handler.OperationHandler;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GModelIndex;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.graph.GPoint;

public abstract class CreateNodeOperationHandler implements OperationHandler {

   protected String elementTypeId;

   public CreateNodeOperationHandler(final String elementTypeId) {
      this.elementTypeId = elementTypeId;
   }

   @Override
   public Class<? extends Action> handlesActionType() {
      return CreateNodeOperationAction.class;
   }

   @Override
   public boolean handles(final AbstractOperationAction action) {
      return OperationHandler.super.handles(action)
         ? ((CreateNodeOperationAction) action).getElementTypeId().equals(elementTypeId)
         : false;
   }

   @Override
   public String getLabel(final AbstractOperationAction action) {
      return "Create node";
   }

   @Override
   public void execute(final AbstractOperationAction action, final GraphicalModelState modelState) {
      CreateNodeOperationAction executeAction = (CreateNodeOperationAction) action;

      GModelIndex index = modelState.getIndex();

      Optional<GModelElement> container = index.get(executeAction.getContainerId());
      if (!container.isPresent()) {
         container = Optional.of(modelState.getRoot());
      }

      Optional<GPoint> point = Optional.of(executeAction.getLocation());
      GModelElement element = createNode(point, modelState);
      container.get().getChildren().add(element);
   }

   protected abstract GNode createNode(Optional<GPoint> point, GraphicalModelState modelState);
}
