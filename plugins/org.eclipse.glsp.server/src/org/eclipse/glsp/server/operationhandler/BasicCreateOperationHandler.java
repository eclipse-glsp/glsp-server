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
package org.eclipse.glsp.server.operationhandler;

import org.eclipse.glsp.api.handler.CreateOperationHandler;
import org.eclipse.glsp.api.operation.CreateOperation;
import org.eclipse.glsp.api.operation.Operation;
import org.eclipse.glsp.api.utils.GenericsUtil;

public abstract class BasicCreateOperationHandler<T extends CreateOperation> extends BasicOperationHandler<T>
   implements CreateOperationHandler {

   private String elementTypeId;

   public BasicCreateOperationHandler(final String elementTypeId) {
      this.elementTypeId = elementTypeId;
   }

   @SuppressWarnings("unchecked")
   @Override
   protected Class<T> deriveOperationType() {
      return (Class<T>) (GenericsUtil.getParametrizedType(getClass(), BasicCreateOperationHandler.class))
         .getActualTypeArguments()[0];
   }

   @Override
   public boolean handles(final Operation operation) {
      return super.handles(operation) && getHandledOperationType()
         .cast(operation).getElementTypeId().equals(elementTypeId);
   }

   @Override
   public abstract String getLabel();

   @Override
   public String getElementTypeId() { return elementTypeId; }

   public void setElementTypeId(final String elementTypeId) { this.elementTypeId = elementTypeId; }

}
