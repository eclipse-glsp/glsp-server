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
package org.eclipse.glsp.server.operations.gmodel;

import java.util.Optional;

import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.operations.BasicOperationHandler;
import org.eclipse.glsp.server.operations.CompoundOperation;
import org.eclipse.glsp.server.operations.Operation;
import org.eclipse.glsp.server.operations.OperationActionHandler;
import org.eclipse.glsp.server.operations.OperationHandler;
import org.eclipse.glsp.server.operations.OperationHandlerRegistry;

import com.google.inject.Inject;

public class CompoundOperationHandler extends BasicOperationHandler<CompoundOperation> {
   @Inject
   protected OperationHandlerRegistry operationHandlerRegistry;

   @Override
   protected void executeOperation(final CompoundOperation operation, final GModelState modelState) {
      operation.getOperationList().forEach(nestedOperation -> executeNestedOperation(nestedOperation, modelState));
   }

   protected void executeNestedOperation(final Operation operation, final GModelState modelState) {
      Optional<? extends OperationHandler> operationHandler = OperationActionHandler.getOperationHandler(operation,
         operationHandlerRegistry);
      if (operationHandler.isPresent()) {
         operationHandler.get().execute(operation, modelState);
      }
   }

}
