/********************************************************************************
 * Copyright (c) 2020-2022 EclipseSource and others.
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

import org.eclipse.glsp.server.internal.util.GenericsUtil;

/**
 * Basic {@link OperationHandler} implementation that can handle exactly one {@link Operation} type.
 * It handles the overhead of casting the operation object received via the {@link OperationHandler#execute(Operation)}
 * method to the correct handled subtype. Subclasses only have to implement the
 * {@link AbstractOperationHandler#executeOperation(Operation)} method
 * and can work directly with the correct subtype instead of having to manually cast it.
 *
 * @param <O> class of the handled action type
 */
public abstract class AbstractOperationHandler<O extends Operation> implements OperationHandler {

   protected final Class<O> operationType;

   public AbstractOperationHandler() {
      this.operationType = deriveOperationType();
   }

   @SuppressWarnings("unchecked")
   protected Class<O> deriveOperationType() {
      return (Class<O>) GenericsUtil.getActualTypeArgument(getClass(), Operation.class);
   }

   @Override
   public Class<O> getHandledOperationType() { return operationType; }

   @Override
   public void execute(final Operation operation) {
      if (handles(operation)) {
         executeOperation(operationType.cast(operation));
      }
   }

   protected abstract void executeOperation(O operation);

   @Override
   public String getLabel() { return operationType.getSimpleName(); }
}
