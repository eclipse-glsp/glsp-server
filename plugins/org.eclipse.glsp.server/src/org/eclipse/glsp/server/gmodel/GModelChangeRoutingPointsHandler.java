/********************************************************************************
 * Copyright (c) 2019-2025 EclipseSource and others.
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
package org.eclipse.glsp.server.gmodel;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelIndex;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.util.GraphUtil;
import org.eclipse.glsp.server.operations.ChangeRoutingPointsOperation;
import org.eclipse.glsp.server.operations.GModelOperationHandler;
import org.eclipse.glsp.server.types.ElementAndRoutingPoints;
import org.eclipse.glsp.server.utils.LayoutUtil;

/**
 * Applies {@link ChangeRoutingPointsOperation} directly to the GModel.
 */
public class GModelChangeRoutingPointsHandler extends GModelOperationHandler<ChangeRoutingPointsOperation> {

   @Override
   public Optional<Command> createCommand(final ChangeRoutingPointsOperation operation) {
      List<ElementAndRoutingPoints> changedRoutingPoints = operation.getNewRoutingPoints().stream()
         .filter(this::hasChanged)
         .toList();
      return changedRoutingPoints.isEmpty()
         ? doNothing()
         : commandOf(() -> executeChangeRoutingPoints(new ChangeRoutingPointsOperation(changedRoutingPoints)));
   }

   protected boolean hasChanged(final ElementAndRoutingPoints routingPoints) {
      Optional<GEdge> edge = this.modelState.getIndex().getByClass(routingPoints.getElementId(),
         GEdge.class);
      if (edge.isEmpty() || edge.get().getRoutingPoints().size() != routingPoints.getNewRoutingPoints().size()) {
         return true;
      }
      return !Arrays.equals(edge.get().getRoutingPoints().toArray(new GPoint[0]),
         routingPoints.getNewRoutingPoints().toArray(new GPoint[0]), GraphUtil::compare);
   }

   protected void executeChangeRoutingPoints(final ChangeRoutingPointsOperation operation) {
      GModelIndex index = modelState.getIndex();
      operation.getNewRoutingPoints().forEach(routingPoints -> LayoutUtil.applyRoutingPoints(routingPoints, index));
   }

}
