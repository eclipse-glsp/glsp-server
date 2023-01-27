/********************************************************************************
 * Copyright (c) 2020-2023 EclipseSource and others.
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
package org.eclipse.glsp.example.workflow.taskedit;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.glsp.example.workflow.wfgraph.TaskNode;
import org.eclipse.glsp.server.operations.GModelOperationHandler;

public class EditTaskOperationHandler extends GModelOperationHandler<EditTaskOperation> {

   @Override
   public Optional<Command> createCommand(final EditTaskOperation operation) {
      TaskNode task = modelState.getIndex().findElementByClass(operation.getTaskId(), TaskNode.class)
         .orElseThrow(() -> new RuntimeException("Cannot find task with id '" + operation.getTaskId() + "'"));
      switch (operation.getFeature()) {
         case "duration":
            int duration = Integer.parseInt(operation.getValue());
            return Objects.equals(task.getDuration(), duration)
               ? doNothing()
               : commandOf(() -> task.setDuration(duration));
         case "taskType":
            return Objects.equals(task.getTaskType(), operation.getValue())
               ? doNothing()
               : commandOf(() -> task.setTaskType(operation.getValue()));
         default:
            return doNothing();
      }
   }
}
