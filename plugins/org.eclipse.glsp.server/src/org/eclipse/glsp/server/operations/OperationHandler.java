/*******************************************************************************
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
 ******************************************************************************/
package org.eclipse.glsp.server.operations;

import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.internal.util.GenericsUtil;
import org.eclipse.glsp.server.model.GModelState;

/**
 * An operation handler can execute {@link Operation}s of a certain type (subclass).
 * The operation handler processes the operation in the {@link OperationHandler#execute(Operation)} method. The result
 * of the execution is an update of the {@link GModelState} state.
 * This update is reversible (undo) and can be reapplied (redo). For basic diagram languages these updates are typically
 * applied directly on the {@link GModelState} using EMF {@link Command}s and the
 * {@link GModelState#execute(org.eclipse.emf.common.command.Command)} method. For more complex diagram languages the
 * GModel state might be updated indirectly and the operation handler manipulates a custom model representation.
 *
 * The {@link OperationActionHandler} is responsible for retrieving all available (valid) operation handlers for an
 * operation that is dispatched via {@link ActionDispatcher}.
 */
public interface OperationHandler<O extends Operation> {

   /**
    * Returns the class of the operation type that can be handled by this operation handler.
    *
    * @return the {@link Operation} (sub)class that can be handled.
    */
   @SuppressWarnings("unchecked")
   default Class<O> getHandledOperationType() {
      return (Class<O>) GenericsUtil.getActualTypeArgument(getClass(), Operation.class);
   }

   default String getLabel() { return getHandledOperationType().getSimpleName(); }

   /**
    * Creates a command that performs the operation in the source model(s). If an empty command is returned, no update
    * is performed on the model(s).
    *
    * @param operation The operation to process.
    * @return The created command to be executed on the command stack or empty if nothing should be done.
    */
   Optional<Command> createCommand(O operation);

   /**
    * Executes the operation handler for the given {@link Operation}. If the given action cannot be handled by this
    * operation handler an empty command is returned an no changes are executed.
    *
    * @param operation The operation that should be executed or empty if nothing should be done.
    */
   default Optional<Command> execute(final Operation operation) {
      return handles(operation)
         ? createCommand(getHandledOperationType().cast(operation))
         : Optional.empty();
   }

   /**
    * Validates whether the given {@link Operation} can be handled by this operation handler.
    * The default implementation the handled operation type ({@link OperationHandler#getHandledOperationType()}
    * to determine whether this handler can handle an operation. Only operations that are instances of these operation
    * type class can be handled.
    *
    * @param operation The operation that should be validated.
    * @return `true` if the given operation can be handled, `false` otherwise.
    */
   default boolean handles(final Operation operation) {
      return getHandledOperationType().isInstance(operation);
   }
}
