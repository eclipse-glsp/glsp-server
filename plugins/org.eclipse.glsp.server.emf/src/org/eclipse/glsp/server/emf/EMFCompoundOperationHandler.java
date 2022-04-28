/********************************************************************************
 * Copyright (c) 2022 EclipseSource and others.
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
package org.eclipse.glsp.server.emf;

import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.glsp.server.operations.CompoundOperation;
import org.eclipse.glsp.server.operations.Operation;
import org.eclipse.glsp.server.operations.OperationActionHandler;
import org.eclipse.glsp.server.operations.OperationHandler;
import org.eclipse.glsp.server.operations.OperationHandlerRegistry;

import com.google.inject.Inject;

/**
 * Creates a compound command to wrap multiple commands into one command that is executed on the command stack.
 */
public class EMFCompoundOperationHandler extends AbstractEMFBaseOperationHandler<CompoundOperation> {

   @Inject
   protected OperationHandlerRegistry operationHandlerRegistry;

   @Override
   protected Optional<Command> createCommand(final CompoundOperation operation) {
      CompoundCommand compoundCommand = new CompoundCommand();
      operation.getOperationList().forEach(nestedOperation -> {
         Optional<Command> nestedCommand = collectNestedCommands(nestedOperation);
         if (nestedCommand.isPresent()) {
            compoundCommand.append(nestedCommand.get());
         }
      });
      if (compoundCommand.getCommandList().isEmpty()) {
         return Optional.empty();
      }
      return Optional.of(compoundCommand);
   }

   protected Optional<Command> collectNestedCommands(final Operation operation) {
      Optional<? extends OperationHandler> operationHandler = OperationActionHandler.getOperationHandler(operation,
         operationHandlerRegistry);
      if (operationHandler.isPresent()) {
         if (operationHandler.get() instanceof EMFOperationHandler) {
            return EMFOperationHandler.class.cast(operationHandler.get()).getCommand(operation);
         }
      }
      return Optional.empty();
   }

}
