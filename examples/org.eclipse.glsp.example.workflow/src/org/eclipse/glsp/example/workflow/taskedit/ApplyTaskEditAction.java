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

import org.eclipse.glsp.server.actions.Action;

public class ApplyTaskEditAction extends Action {

   private String taskId;
   private String expression;

   public ApplyTaskEditAction() {
      super("applyTaskEdit");
   }

   public ApplyTaskEditAction(final String taskId, final String expression) {
      this();
      this.taskId = taskId;
      this.expression = expression;
   }

   public String getTaskId() { return taskId; }

   public void setTaskId(final String taskId) { this.taskId = taskId; }

   public String getExpression() { return expression; }

   public void setExpression(final String expression) { this.expression = expression; }

}
