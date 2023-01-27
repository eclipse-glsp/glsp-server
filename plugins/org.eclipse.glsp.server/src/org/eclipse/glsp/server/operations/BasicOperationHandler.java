/********************************************************************************
 * Copyright (c) 2023 EclipseSource and others.
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
import org.eclipse.glsp.server.model.GModelState;

import com.google.inject.Inject;

public abstract class BasicOperationHandler<O extends Operation> implements OperationHandler<O> {

   protected final Class<O> operationType;

   @Inject
   protected GModelState modelState;

   public BasicOperationHandler() {
      this.operationType = OperationHandler.super.getHandledOperationType();
   }

   @Override
   public boolean handles(final Operation operation) {
      return modelState.getRoot() != null && OperationHandler.super.handles(operation);
   }

   @Override
   public Class<O> getHandledOperationType() { return operationType; }

   @Override
   public String getLabel() { return operationType.getSimpleName(); }

   protected static Optional<Command> doNothing() {
      return Optional.empty();
   }
}
