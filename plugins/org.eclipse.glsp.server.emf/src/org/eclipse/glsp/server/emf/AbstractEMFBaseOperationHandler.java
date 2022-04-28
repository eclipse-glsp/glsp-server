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
import org.eclipse.glsp.server.operations.AbstractOperationHandler;
import org.eclipse.glsp.server.operations.Operation;

/**
 * {@link AbstractOperationHandler} for EMF source models that can handle exactly one {@link Operation} type.
 *
 * Instead of executing the operation, this handler returns an EMF command which is later on executed on an EMF command
 * stack. Subclasses only have to implement {@link AbstractEMFBaseOperationHandler#createCommand(Operation)}
 * and can work directly with the correct subtype instead of having to manually cast it.
 *
 * @param <T> class of the handled action type
 */
public abstract class AbstractEMFBaseOperationHandler<T extends Operation> extends AbstractOperationHandler<T>
   implements EMFOperationHandler {

   @Override
   public Optional<Command> getCommand(final Operation operation) {
      if (handles(operation)) {
         return createCommand(operationType.cast(operation));
      }
      return Optional.empty();
   }

   /**
    * Creates a command that performs the operation in the EMF source model(s).
    *
    * @param operation The operation to process.
    * @return The created command to be executed on the command stack.
    */
   protected abstract Optional<Command> createCommand(T operation);

   @Override
   protected void executeOperation(final T operation) {
      // no-op, because this class returns a command directly instead of executing the command directly on a model
   }

}
