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
package org.eclipse.glsp.server.gmodel;

import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.glsp.graph.GModelIndex;
import org.eclipse.glsp.server.operations.ChangeRoutingPointsOperation;
import org.eclipse.glsp.server.operations.GModelOperationHandler;
import org.eclipse.glsp.server.utils.LayoutUtil;

/**
 * Applies {@link ChangeRoutingPointsOperation} directly to the GModel.
 */
public class GModelChangeRoutingPointsHandler extends GModelOperationHandler<ChangeRoutingPointsOperation> {

   @Override
   public Optional<Command> createCommand(final ChangeRoutingPointsOperation operation) {
      return commandOf(() -> executeChangeRoutingPoints(operation));
   }

   protected void executeChangeRoutingPoints(final ChangeRoutingPointsOperation operation) {
      if (operation.getNewRoutingPoints() == null) {
         throw new IllegalArgumentException("Incomplete change routingPoints  action");
      }

      GModelIndex index = modelState.getIndex();
      operation.getNewRoutingPoints().forEach(routingPoints -> LayoutUtil.applyRoutingPoints(routingPoints, index));
   }

}
