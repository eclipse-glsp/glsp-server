/********************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
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
package org.eclipse.glsp.server.actionhandler;

import java.util.List;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.kind.RequestBoundsAction;
import org.eclipse.glsp.api.action.kind.SetDirtyStateAction;
import org.eclipse.glsp.api.handler.OperationHandler;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.operation.Operation;
import org.eclipse.glsp.api.supplier.OperationHandlerSupplier;
import org.eclipse.glsp.server.command.GModelRecordingCommand;

import com.google.inject.Inject;

public class OperationActionHandler extends BasicActionHandler<Operation> {
   @Inject
   protected OperationHandlerSupplier operationHandlerProvider;

   @Override
   public boolean handles(final Action action) {
      return action instanceof Operation;
   }

   @Override
   public List<Action> executeAction(final Operation operation, final GraphicalModelState modelState) {
      if (operationHandlerProvider.isHandled(operation)) {
         OperationHandler handler = operationHandlerProvider.getHandler(operation).get();
         GModelRecordingCommand command = new GModelRecordingCommand(modelState.getRoot(), handler.getLabel(),
            () -> handler.execute(operation, modelState));
         modelState.execute(command);
         return listOf(new RequestBoundsAction(modelState.getRoot()), new SetDirtyStateAction(modelState.isDirty()));
      }
      return none();

   }
}
