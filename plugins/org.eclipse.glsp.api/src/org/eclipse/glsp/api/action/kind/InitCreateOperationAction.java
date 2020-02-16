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
package org.eclipse.glsp.api.action.kind;

import java.util.Map;

import org.eclipse.glsp.api.action.Action;

public class InitCreateOperationAction extends Action {
   private String elementTypeId;
   private String operationKind;
   private Map<String, String> args;

   public InitCreateOperationAction() {
      super(Action.Kind.INIT_CREATE_OPERATION);
   }

   public InitCreateOperationAction(final String elementTypeId, final String operationKind) {
      this(elementTypeId, operationKind, null);
   }

   public InitCreateOperationAction(final String elementTypeId, final String operationKind,
      final Map<String, String> args) {
      this();
      this.elementTypeId = elementTypeId;
      this.operationKind = operationKind;
      this.args = args;
   }

   public String getElementTypeId() { return elementTypeId; }

   public void setElementTypeId(final String elementTypeId) { this.elementTypeId = elementTypeId; }

   public String getOperationKind() { return operationKind; }

   public void setOperationKind(final String operationKind) { this.operationKind = operationKind; }

   public Map<String, String> getArgs() { return args; }

   public void setArgs(final Map<String, String> args) { this.args = args; }

}
