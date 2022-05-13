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
import org.eclipse.glsp.server.operations.OperationHandlerRegistry;

import com.google.inject.Inject;

/**
 * Creates a compound command to wrap multiple commands into one command that is executed on the command stack.
 */
public class EMFCompoundOperationHandler extends AbstractEMFOperationHandler<CompoundOperation> {

   @Inject
   protected OperationHandlerRegistry operationHandlerRegistry;

   @Override
   public Optional<Command> createCommand(final CompoundOperation operation) {
      CompoundCommand compoundCommand = new CompoundCommand();
      operation.getOperationList()
         .forEach(nestedOperation -> getNestedCommand(nestedOperation).ifPresent(compoundCommand::append));
      return compoundCommand.getCommandList().isEmpty()
         ? Optional.empty()
         : Optional.of(compoundCommand);
   }

   protected Optional<Command> getNestedCommand(final Operation operation) {
      return EMFOperationHandler.getCommand(operationHandlerRegistry, operation);
   }

}
