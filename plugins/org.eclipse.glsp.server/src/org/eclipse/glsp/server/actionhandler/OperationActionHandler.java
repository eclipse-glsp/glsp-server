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

import java.util.Optional;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.kind.AbstractOperationAction;
import org.eclipse.glsp.api.action.kind.RequestBoundsAction;
import org.eclipse.glsp.api.handler.OperationHandler;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.provider.OperationHandlerProvider;
import org.eclipse.glsp.server.command.GModelRecordingCommand;

import com.google.inject.Inject;

public class OperationActionHandler extends AbstractActionHandler {
   @Inject
   protected OperationHandlerProvider operationHandlerProvider;

   @Override
   public boolean handles(final Action action) {
      return action instanceof AbstractOperationAction;
   }

   @Override
   public Optional<Action> execute(final Action action, final GraphicalModelState modelState) {
      if (action instanceof AbstractOperationAction
         && operationHandlerProvider.isHandled((AbstractOperationAction) action)) {
         return doHandle((AbstractOperationAction) action, modelState);
      }
      return Optional.empty();
   }

   public Optional<Action> doHandle(final AbstractOperationAction action, final GraphicalModelState modelState) {
      if (operationHandlerProvider.isHandled(action)) {
         OperationHandler handler = operationHandlerProvider.getHandler(action).get();
         String label = handler.getLabel(action);
         GModelRecordingCommand command = new GModelRecordingCommand(modelState.getRoot(), label,
            () -> handler.execute(action, modelState));
         modelState.execute(command);
         return Optional.of(new RequestBoundsAction(modelState.getRoot()));
      }
      return Optional.empty();
   }

}
