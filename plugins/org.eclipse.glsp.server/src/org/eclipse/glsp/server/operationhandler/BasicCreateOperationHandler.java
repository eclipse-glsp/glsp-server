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

import java.util.List;

import org.eclipse.glsp.api.action.kind.InitCreateOperationAction;
import org.eclipse.glsp.api.handler.CreateOperationHandler;
import org.eclipse.glsp.api.operation.CreateOperation;
import org.eclipse.glsp.api.operation.Operation;
import org.eclipse.glsp.api.utils.GenericUtil;

import com.google.common.collect.Lists;

public abstract class BasicCreateOperationHandler<T extends CreateOperation> extends BasicOperationHandler<T>
   implements CreateOperationHandler {

   private String elementTypeId;
   private String operationKind;

   public BasicCreateOperationHandler(final String elementTypeId, final String operationKind) {
      this.elementTypeId = elementTypeId;
      this.operationKind = operationKind;
   }

   @SuppressWarnings("unchecked")
   @Override
   protected Class<T> deriveOperationType() {
      return (Class<T>) (GenericUtil.getParametrizedType(getClass(), BasicCreateOperationHandler.class))
         .getActualTypeArguments()[0];
   }

   @Override
   public List<InitCreateOperationAction> getInitActions() {
      return Lists.newArrayList(new InitCreateOperationAction(elementTypeId, operationKind));
   }

   @Override
   public boolean handles(final Operation operation) {
      return super.handles(operation) && getOperationType()
         .cast(operation).getElementTypeId().equals(elementTypeId);
   }

   @Override
   public abstract String getLabel();

   @Override
   public String getElementTypeId() { return elementTypeId; }

   public void setElementTypeId(final String elementTypeId) { this.elementTypeId = elementTypeId; }

   @Override
   public String getOperationKind() { return operationKind; }

   public void setOperationKind(final String operationKind) { this.operationKind = operationKind; }

}
