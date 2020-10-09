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
package org.eclipse.glsp.server.operations.gmodel;

import static org.eclipse.glsp.server.protocol.GLSPServerException.getOrThrow;

import org.eclipse.emf.common.util.EList;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelIndex;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.operations.BasicOperationHandler;
import org.eclipse.glsp.server.operations.ChangeRoutingPointsOperation;
import org.eclipse.glsp.server.types.ElementAndRoutingPoints;

public class ChangeRoutingPointsHandler extends BasicOperationHandler<ChangeRoutingPointsOperation> {

   @Override
   protected void executeOperation(final ChangeRoutingPointsOperation operation, final GModelState modelState) {

      // check for null-values
      if (operation.getNewRoutingPoints() == null) {
         throw new IllegalArgumentException("Incomplete change routingPoints  action");
      }

      // check for existence of matching elements
      GModelIndex index = modelState.getIndex();

      for (ElementAndRoutingPoints ear : operation.getNewRoutingPoints()) {
         GEdge edge = getOrThrow(index.findElementByClass(ear.getElementId(), GEdge.class),
            "Invalid edge: edge ID " + ear.getElementId());

         // reroute
         EList<GPoint> routingPoints = edge.getRoutingPoints();
         routingPoints.clear();
         routingPoints.addAll(ear.getNewRoutingPoints());
      }

   }
}
