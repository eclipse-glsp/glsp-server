/********************************************************************************
 * Copyright (c) 2020-2021 EclipseSource and others.
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

import com.google.inject.Inject;

/**
 * Deprecated, will be removed with version 1.0.
 * Please use {@link AbstractOperationHandler} instead and directly inject the {@link GModelState}.
 */
@Deprecated
public abstract class BasicOperationHandler<T extends Operation> extends AbstractOperationHandler<T> {

   @Inject
   protected GModelState modelState;

   @Override
   protected void executeOperation(final T operation) {
      executeOperation(operation, modelState);
   }

   @Override
   @SuppressWarnings("unchecked")
   protected Class<T> deriveOperationType() {
      return (Class<T>) (GenericsUtil.getParametrizedType(getClass(), BasicOperationHandler.class))
         .getActualTypeArguments()[0];
   }

   protected abstract void executeOperation(T operation, GModelState modelState);

}
