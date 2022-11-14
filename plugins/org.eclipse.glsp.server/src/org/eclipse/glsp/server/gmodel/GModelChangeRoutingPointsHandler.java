/********************************************************************************
 * Copyright (c) 2019-2022 EclipseSource and others.
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

import org.eclipse.glsp.graph.GModelIndex;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.operations.AbstractOperationHandler;
import org.eclipse.glsp.server.operations.ChangeRoutingPointsOperation;
import org.eclipse.glsp.server.utils.LayoutUtil;

import com.google.inject.Inject;

/**
 * Applies {@link ChangeRoutingPointsOperation} directly to the GModel.
 */
public class GModelChangeRoutingPointsHandler extends AbstractOperationHandler<ChangeRoutingPointsOperation> {

   @Inject
   protected GModelState modelState;

   @Override
   protected void executeOperation(final ChangeRoutingPointsOperation operation) {
      if (operation.getNewRoutingPoints() == null) {
         throw new IllegalArgumentException("Incomplete change routingPoints  action");
      }

      GModelIndex index = modelState.getIndex();
      operation.getNewRoutingPoints().forEach(routingPoints -> LayoutUtil.applyRoutingPoints(routingPoints, index));
   }
}
