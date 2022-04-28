/********************************************************************************
 * Copyright (c) 2022 EclipseSource and others.
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

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.SetDirtyStateAction;
import org.eclipse.glsp.server.operations.Operation;
import org.eclipse.glsp.server.operations.OperationActionHandler;
import org.eclipse.glsp.server.operations.OperationHandler;

/**
 * A special {@link OperationActionHandler} that executes provided EMF commands of {@link EMFOperationHandler}
 * on an EMF command stack via the {@link EMFModelState}.
 */
public class EMFOperationActionHandler extends OperationActionHandler {

   @Override
   protected List<Action> executeHandler(final Operation operation, final OperationHandler handler) {
      if (handler instanceof EMFOperationHandler) {
         Optional<Command> cmd = EMFOperationHandler.class.cast(handler).getCommand(operation);
         if (cmd.isPresent()) {
            exexcuteCommand(cmd.get());
            return submitModel();
         }
      }
      return super.executeHandler(operation, handler);
   }

   protected void exexcuteCommand(final Command cmd) {
      modelState.execute(cmd);
   }

   protected List<Action> submitModel() {
      return modelSubmissionHandler.submitModel(SetDirtyStateAction.Reason.OPERATION);
   }
}
