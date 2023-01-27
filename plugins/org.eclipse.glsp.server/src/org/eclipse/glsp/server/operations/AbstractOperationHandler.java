/********************************************************************************
 * Copyright (c) 2020-2023 EclipseSource and others.
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

import java.util.Optional;

import org.eclipse.emf.common.command.Command;

/**
 * Basic {@link OperationHandler} implementation that can handle exactly one {@link Operation} type.
 * It handles the overhead of casting the operation object received via the {@link OperationHandler#execute(Operation)}
 * method to the correct handled subtype. Subclasses only have to implement the
 * {@link AbstractOperationHandler#executeOperation(Operation)} method
 * and can work directly with the correct subtype instead of having to manually cast it.
 *
 * @param <O> class of the handled action type
 *
 * @deprecated Use {@link GModelOperationHandler} with optional command instead.
 */
@Deprecated
public abstract class AbstractOperationHandler<O extends Operation> extends GModelOperationHandler<O> {
   @Override
   public Optional<Command> createCommand(final O operation) {
      return preExecute(operation) ? commandOf(() -> executeOperation(operation)) : doNothing();
   }

   /**
    * Returns whether the execution of the operation should continue. If <code>false</code> is returned,
    * {@link #executeOperation(Operation)} is not called and no update is performed on the model. Otherwise, the
    * execution continues normally and the model will be updated and marked dirty.
    *
    * @param operation operation
    * @return <code>true</code> if the execution should continue, <code>false</code> if we should abort the execution.
    */
   protected boolean preExecute(final O operation) {
      return true;
   }

   protected abstract void executeOperation(O operation);
}
