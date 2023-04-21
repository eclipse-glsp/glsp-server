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
package org.eclipse.glsp.server.operations;

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.glsp.server.actions.AbstractActionHandler;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.SetDirtyStateAction;
import org.eclipse.glsp.server.features.core.model.ModelSubmissionHandler;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.utils.ServerMessageUtil;

import com.google.inject.Inject;

public class OperationActionHandler extends AbstractActionHandler<Operation> {

   @Inject
   protected OperationHandlerRegistry operationHandlerRegistry;

   @Inject
   protected ModelSubmissionHandler modelSubmissionHandler;

   @Inject
   protected GModelState modelState;

   @Override
   public boolean handles(final Action action) {
      return action instanceof Operation;
   }

   @Override
   public List<Action> executeAction(final Operation operation) {
      if (modelState.isReadonly()) {
         return listOf(ServerMessageUtil
            .warn("Server is in readonly-mode! Could not execute operation: " + operation.getKind()));
      }
      return executeOperation(operation);
   }

   protected List<Action> executeOperation(final Operation operation) {
      return operationHandlerRegistry.getOperationHandler(operation)
         .map(handler -> executeHandler(operation, handler))
         .orElseGet(this::none);
   }

   protected List<Action> executeHandler(final Operation operation, final OperationHandler<?> handler) {
      Optional<Command> command = handler.execute(operation);
      if (command.isPresent()) {
         exexcuteCommand(command.get(), operation.getSubclientId());
         return submitModel(operation.getSubclientId());
      }
      return none();
   }

   protected void exexcuteCommand(final Command command, final String subclientId) {
      modelState.execute(command, subclientId);
   }

   protected List<Action> submitModel(final String subclientId) {
      return modelSubmissionHandler.submitModel(SetDirtyStateAction.Reason.OPERATION, subclientId);
   }

   /**
    * Use {@link OperationHandlerRegistry#getOperationHandler(Operation) instead}.
    */
   @Deprecated
   public static Optional<? extends OperationHandler<?>> getOperationHandler(final Operation operation,
      final OperationHandlerRegistry registry) {
      return registry.getOperationHandler(operation);
   }
}
