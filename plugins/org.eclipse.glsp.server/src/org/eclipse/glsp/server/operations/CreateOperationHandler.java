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

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.glsp.server.actions.TriggerEdgeCreationAction;
import org.eclipse.glsp.server.actions.TriggerElementCreationAction;
import org.eclipse.glsp.server.actions.TriggerNodeCreationAction;

import com.google.common.collect.Lists;

/**
 * A special {@link OperationHandler} that is responsible for the handling of {@link CreateOperation}s. Depending on its
 * operation type the triggered actions are {@link TriggerNodeCreationAction} or {@link TriggerEdgeCreationAction}s.
 */
public interface CreateOperationHandler extends OperationHandler {

   /**
    * Returns the {@link CreateOperation} type this handler has registered for.
    *
    * @return The {@link CreateOperation} type this handler has registered for.
    */
   @Override
   Class<? extends CreateOperation> getHandledOperationType();

   /**
    * Returns a list of {@link TriggerElementCreationAction}s for registered element types.
    *
    * @return A list of {@link TriggerElementCreationAction}s.
    */
   default List<TriggerElementCreationAction> getTriggerActions() {
      if (CreateNodeOperation.class.isAssignableFrom(getHandledOperationType())) {
         return getHandledElementTypeIds().stream().map(TriggerNodeCreationAction::new).collect(Collectors.toList());
      } else if (CreateEdgeOperation.class.isAssignableFrom(getHandledOperationType())) {
         return getHandledElementTypeIds().stream().map(TriggerEdgeCreationAction::new).collect(Collectors.toList());
      }
      return Lists.newArrayList();
   }

   /**
    * Returns the list of element types for which this handler has registered for.
    *
    * @return The list of element types for which this handler has registered for.
    */
   List<String> getHandledElementTypeIds();

   @Override
   default boolean handles(final Operation operation) {
      return OperationHandler.super.handles(operation)
         && getHandledElementTypeIds().contains(getHandledOperationType().cast(operation).getElementTypeId());
   }
}
