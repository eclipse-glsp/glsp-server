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
import org.eclipse.glsp.server.operations.Operation;
import org.eclipse.glsp.server.operations.OperationHandler;
import org.eclipse.glsp.server.operations.OperationHandlerRegistry;

/**
 * {@link OperationHandler} for EMF source models that creates commands to be executed on the command stack.
 */
public interface EMFOperationHandler<O extends Operation> extends OperationHandler {

   @Override
   Class<O> getHandledOperationType();

   /**
    * @return the command to be applied to the EMF source model on the command stack for the given operation.
    */
   default Optional<Command> getCommand(final Operation operation) {
      if (handles(operation)) {
         return createCommand(getHandledOperationType().cast(operation));
      }
      return Optional.empty();
   }

   /**
    * Creates a command that performs the operation in the EMF source model(s).
    *
    * @param operation The operation to process.
    * @return The created command to be executed on the command stack.
    */
   Optional<Command> createCommand(O operation);

   @Override
   default void execute(final Operation operation) {
      // do nothing, we use EMF commands to manipulate the model, see EMFOperationActionHandler
   }

   /**
    * Returns the EMF operation handler for the given operation from the given operation handler registry.
    *
    * @param <O>       operation type
    * @param registry  operation handler registry
    * @param operation operation
    * @return the matching EMF operation handler from the registry or empty if no such handler is found
    */
   static <O extends Operation> Optional<EMFOperationHandler<O>> getOperationHandler(
      final OperationHandlerRegistry registry, final O operation) {
      return registry.getOperationHandler(operation)
         .filter(EMFOperationHandler.class::isInstance)
         .map(EMFOperationHandler.class::cast);
   }

   /**
    * Returns the matching EMF command for the given operation.
    *
    * @param registry  operation handler registry
    * @param operation operation
    * @return the matching EMF command for the given operation
    */
   static Optional<Command> getCommand(final OperationHandlerRegistry registry, final Operation operation) {
      return getOperationHandler(registry, operation).flatMap(handler -> handler.getCommand(operation));
   }
}
