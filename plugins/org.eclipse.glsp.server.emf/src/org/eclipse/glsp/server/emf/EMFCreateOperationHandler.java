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
package org.eclipse.glsp.server.emf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.actions.GhostElement;
import org.eclipse.glsp.server.actions.TriggerElementCreationAction;
import org.eclipse.glsp.server.actions.TriggerNodeCreationAction;
import org.eclipse.glsp.server.operations.CreateNodeOperation;
import org.eclipse.glsp.server.operations.CreateOperation;
import org.eclipse.glsp.server.operations.CreateOperationHandler;

import com.google.inject.Inject;

public abstract class EMFCreateOperationHandler<T extends CreateOperation>
   extends EMFOperationHandler<T> implements CreateOperationHandler<T> {

   @Inject
   protected ActionDispatcher actionDispatcher;

   protected List<String> handledElementTypeIds;

   public EMFCreateOperationHandler(final String... elementTypeIds) {
      this(new ArrayList<>(List.of(elementTypeIds)));
   }

   public EMFCreateOperationHandler(final List<String> handledElementTypeIds) {
      this.handledElementTypeIds = handledElementTypeIds;
   }

   @Override
   public List<String> getHandledElementTypeIds() { return handledElementTypeIds; }

   public void setHandledElementTypeIds(final List<String> handledElementTypeIds) {
      this.handledElementTypeIds = handledElementTypeIds;
   }

   @Override
   public List<TriggerElementCreationAction> getTriggerActions() {
      if (CreateNodeOperation.class.isAssignableFrom(getHandledOperationType())) {
         return getHandledElementTypeIds().stream().map(this::createTriggerNodeCreationAction)
            .collect(Collectors.toList());
      }
      return CreateOperationHandler.super.getTriggerActions();
   }

   protected TriggerNodeCreationAction createTriggerNodeCreationAction(final String elementTypeId) {
      return new TriggerNodeCreationAction(elementTypeId,
         createTriggerArgs(elementTypeId),
         createTriggerGhostElement(elementTypeId));
   }

   protected GhostElement createTriggerGhostElement(final String elementTypeId) {
      return null;
   }

   protected Map<String, String> createTriggerArgs(final String elementTypeId) {
      return null;
   }
}
