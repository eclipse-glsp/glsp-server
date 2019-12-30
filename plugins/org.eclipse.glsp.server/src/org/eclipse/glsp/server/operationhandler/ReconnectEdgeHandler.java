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

import static org.eclipse.glsp.api.jsonrpc.GLSPServerException.getOrThrow;
import static org.eclipse.glsp.server.util.GModelUtil.IS_CONNECTABLE;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.kind.AbstractOperationAction;
import org.eclipse.glsp.api.action.kind.ReconnectConnectionOperationAction;
import org.eclipse.glsp.api.handler.OperationHandler;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GModelIndex;

public class ReconnectEdgeHandler implements OperationHandler {

   @Override
   public Class<? extends Action> handlesActionType() {
      return ReconnectConnectionOperationAction.class;
   }

   @Override
   @SuppressWarnings("checkstyle:CyclomaticComplexity")
   public void execute(final AbstractOperationAction operationAction, final GraphicalModelState modelState) {
      if (!(operationAction instanceof ReconnectConnectionOperationAction)) {
         throw new IllegalArgumentException("Unexpected action " + operationAction);
      }

      // check for null-values
      final ReconnectConnectionOperationAction action = (ReconnectConnectionOperationAction) operationAction;
      if (action.getConnectionElementId() == null || action.getSourceElementId() == null
         || action.getTargetElementId() == null) {
         throw new IllegalArgumentException("Incomplete reconnect connection action");
      }

      // check for existence of matching elements
      GModelIndex index = modelState.getIndex();

      GEdge edge = getOrThrow(index.findElementByClass(action.getConnectionElementId(), GEdge.class),
         "Invalid edge: edge ID " + action.getConnectionElementId());
      GModelElement source = getOrThrow(index.findElement(action.getSourceElementId(), IS_CONNECTABLE),
         "Invalid source: source ID " + action.getSourceElementId());
      GModelElement target = getOrThrow(index.findElement(action.getTargetElementId(), IS_CONNECTABLE),
         "Invalid target: target ID: " + action.getTargetElementId());

      // reconnect
      edge.setSourceId(source.getId());
      edge.setTargetId(target.getId());
      edge.getRoutingPoints().clear();
   }

   @Override
   public String getLabel(final AbstractOperationAction action) {
      return "Reconnect edge";
   }
}
