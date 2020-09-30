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

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.glsp.server.actions.TriggerEdgeCreationAction;
import org.eclipse.glsp.server.actions.TriggerElementCreationAction;
import org.eclipse.glsp.server.actions.TriggerNodeCreationAction;

import com.google.common.collect.Lists;

public interface CreateOperationHandler extends OperationHandler {

   @Override
   Class<? extends CreateOperation> getHandledOperationType();

   default List<TriggerElementCreationAction> getTriggerActions() {
      if (CreateNodeOperation.class.isAssignableFrom(getHandledOperationType())) {
         return getHandledElementTypeIds().stream().map(TriggerNodeCreationAction::new).collect(Collectors.toList());
      } else if (CreateEdgeOperation.class.isAssignableFrom(getHandledOperationType())) {
         return getHandledElementTypeIds().stream().map(TriggerEdgeCreationAction::new).collect(Collectors.toList());
      }
      return Lists.newArrayList();
   }

   List<String> getHandledElementTypeIds();
}
