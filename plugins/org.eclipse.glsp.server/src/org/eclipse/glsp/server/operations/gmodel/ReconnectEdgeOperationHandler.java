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
import static org.eclipse.glsp.server.utils.GModelUtil.IS_CONNECTABLE;

import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GModelIndex;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.operations.BasicOperationHandler;
import org.eclipse.glsp.server.operations.ReconnectEdgeOperation;

public class ReconnectEdgeOperationHandler extends BasicOperationHandler<ReconnectEdgeOperation> {
   @Override
   @SuppressWarnings("checkstyle:CyclomaticComplexity")
   public void executeOperation(final ReconnectEdgeOperation operation, final GModelState modelState) {

      if (operation.getEdgeElementId() == null || operation.getSourceElementId() == null
         || operation.getTargetElementId() == null) {
         throw new IllegalArgumentException("Incomplete reconnect connection action");
      }

      // check for existence of matching elements
      GModelIndex index = modelState.getIndex();

      GEdge edge = getOrThrow(index.findElementByClass(operation.getEdgeElementId(), GEdge.class),
         "Invalid edge: edge ID " + operation.getEdgeElementId());
      GModelElement source = getOrThrow(index.findElement(operation.getSourceElementId(), IS_CONNECTABLE),
         "Invalid source: source ID " + operation.getSourceElementId());
      GModelElement target = getOrThrow(index.findElement(operation.getTargetElementId(), IS_CONNECTABLE),
         "Invalid target: target ID: " + operation.getTargetElementId());

      // reconnect
      edge.setSourceId(source.getId());
      edge.setTargetId(target.getId());
      edge.getRoutingPoints().clear();
   }
}
