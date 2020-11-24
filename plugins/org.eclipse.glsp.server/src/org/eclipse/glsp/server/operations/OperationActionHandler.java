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
package org.eclipse.glsp.server.operations;

import java.util.List;
import java.util.Optional;

import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.BasicActionHandler;
import org.eclipse.glsp.server.features.core.model.ModelSubmissionHandler;
import org.eclipse.glsp.server.internal.gmodel.commandstack.GModelRecordingCommand;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.utils.ServerMessageUtil;

import com.google.inject.Inject;

public class OperationActionHandler extends BasicActionHandler<Operation> {
   @Inject
   protected OperationHandlerRegistry operationHandlerRegistry;

   @Inject
   protected ModelSubmissionHandler modelSubmissionHandler;

   @Override
   public boolean handles(final Action action) {
      return action instanceof Operation;
   }

   @Override
   public List<Action> executeAction(final Operation operation, final GModelState modelState) {
      if (modelState.isReadonly()) {
         return listOf(ServerMessageUtil
            .warn("Server is in readonly-mode! Could not execute operation: " + operation.getKind()));
      }
      Optional<? extends OperationHandler> operationHandler = getOperationHandler(operation, operationHandlerRegistry);
      if (operationHandler.isPresent()) {
         return executeHandler(operation, operationHandler.get(), modelState);
      }
      return none();
   }

   protected List<Action> executeHandler(final Operation operation, final OperationHandler handler,
      final GModelState modelState) {
      GModelRecordingCommand command = new GModelRecordingCommand(modelState.getRoot(), handler.getLabel(),
         () -> handler.execute(operation, modelState));
      modelState.execute(command);
      return modelSubmissionHandler.submitModel(true, modelState);
   }

   public static Optional<? extends OperationHandler> getOperationHandler(final Operation operation,
      final OperationHandlerRegistry registry) {
      Optional<? extends OperationHandler> operationHandler;
      if (operation instanceof CreateOperation) {
         operationHandler = registry.get(operation)
            .filter(CreateOperationHandler.class::isInstance)
            .map(CreateOperationHandler.class::cast)
            .filter(
               handler -> handler.getHandledElementTypeIds()
                  .contains(((CreateOperation) operation).getElementTypeId()));
      } else {
         operationHandler = registry.get(operation);
      }
      return operationHandler;
   }
}
