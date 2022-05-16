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

import com.google.common.collect.Lists;

public abstract class AbstractCreateOperationHandler<T extends CreateOperation> extends AbstractOperationHandler<T>
   implements CreateOperationHandler {

   protected List<String> handledElementTypeIds;

   public AbstractCreateOperationHandler(final String... elementTypeIds) {
      this(Lists.newArrayList(elementTypeIds));
   }

   public AbstractCreateOperationHandler(final List<String> handledElementTypeIds) {
      this.handledElementTypeIds = handledElementTypeIds;
   }

   @Override
   public List<String> getHandledElementTypeIds() { return handledElementTypeIds; }

   public void setHandledElementTypeIds(final List<String> handledElementTypeIds) {
      this.handledElementTypeIds = handledElementTypeIds;
   }

}
