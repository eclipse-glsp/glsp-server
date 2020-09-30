/********************************************************************************
 * Copyright (c) 2020 EclipseSource and others.
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
import org.eclipse.glsp.server.model.GModelState;

public abstract class BasicOperationHandler<T extends Operation> implements OperationHandler {

   protected final Class<T> operationType;

   public BasicOperationHandler() {
      this.operationType = deriveOperationType();
   }

   @SuppressWarnings("unchecked")
   protected Class<T> deriveOperationType() {
      return (Class<T>) (GenericsUtil.getParametrizedType(getClass(), BasicOperationHandler.class))
         .getActualTypeArguments()[0];
   }

   @Override
   public Class<T> getHandledOperationType() { return operationType; }

   @Override
   public void execute(final Operation operation, final GModelState modelState) {
      if (handles(operation)) {
         executeOperation(operationType.cast(operation), modelState);
      }
   }

   protected abstract void executeOperation(T operation, GModelState modelState);

   @Override
   public String getLabel() { return operationType.getSimpleName(); }
}
