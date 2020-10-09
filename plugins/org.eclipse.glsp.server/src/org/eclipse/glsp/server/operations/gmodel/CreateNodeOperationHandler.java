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

import java.util.Optional;

import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GModelIndex;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.operations.BasicCreateOperationHandler;
import org.eclipse.glsp.server.operations.CreateNodeOperation;

public abstract class CreateNodeOperationHandler extends BasicCreateOperationHandler<CreateNodeOperation> {

   public CreateNodeOperationHandler(final String elementTypeId) {
      super(elementTypeId);
   }

   @Override
   public void executeOperation(final CreateNodeOperation operation, final GModelState modelState) {
      GModelIndex index = modelState.getIndex();

      Optional<GModelElement> container = index.get(operation.getContainerId());
      if (!container.isPresent()) {
         container = Optional.of(modelState.getRoot());
      }

      GModelElement element = createNode(getLocation(operation), modelState);
      container.get().getChildren().add(element);
   }

   protected Optional<GPoint> getLocation(final CreateNodeOperation operation) {
      return operation.getLocation();
   }

   protected abstract GNode createNode(Optional<GPoint> point, GModelState modelState);
}
