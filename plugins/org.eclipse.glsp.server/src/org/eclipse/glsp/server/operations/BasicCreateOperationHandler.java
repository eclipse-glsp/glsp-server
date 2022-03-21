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

import java.util.List;

import org.eclipse.glsp.server.model.GModelState;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Deprecated, will be removed with version 1.0.
 * Please use {@link AbstractCreateOperationHandler} instead and directly inject the {@link GModelState}.
 */
@Deprecated
public abstract class BasicCreateOperationHandler<T extends CreateOperation> extends AbstractCreateOperationHandler<T> {

   @Inject
   protected GModelState modelState;

   public BasicCreateOperationHandler(final String... elementTypeIds) {
      this(Lists.newArrayList(elementTypeIds));
   }

   public BasicCreateOperationHandler(final List<String> handledElementTypeIds) {
      this.handledElementTypeIds = handledElementTypeIds;
   }

   @Override
   protected void executeOperation(final T operation) {
      executeOperation(operation, modelState);
   }

   protected abstract void executeOperation(T operation, GModelState modelState);

}
