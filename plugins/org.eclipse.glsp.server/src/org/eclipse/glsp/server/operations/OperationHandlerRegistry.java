/*******************************************************************************
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
 ******************************************************************************/
package org.eclipse.glsp.server.operations;

import java.util.Optional;

import org.eclipse.glsp.server.registry.Registry;

/**
 * This registry contains {@link OperationHandler}s that are registered for certain {@link Operation} types.
 */
public interface OperationHandlerRegistry extends Registry<Operation, OperationHandler> {
   default Optional<? extends OperationHandler> getOperationHandler(final Operation operation) {
      Optional<? extends OperationHandler> operationHandler = get(operation);
      if (operation instanceof CreateOperation) {
         // create operations need to be handled by create operation handlers that support the element type id
         CreateOperation createOperation = (CreateOperation) operation;
         return operationHandler.filter(CreateOperationHandler.class::isInstance)
            .map(CreateOperationHandler.class::cast)
            .filter(handler -> handler.getHandledElementTypeIds().contains(createOperation.getElementTypeId()));
      }
      return operationHandler;
   }
}
