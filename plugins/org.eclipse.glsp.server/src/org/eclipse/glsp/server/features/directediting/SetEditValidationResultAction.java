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
package org.eclipse.glsp.server.features.directediting;

import java.util.Collections;
import java.util.Map;

import org.eclipse.glsp.server.actions.ResponseAction;

public class SetEditValidationResultAction extends ResponseAction {

   public static final String ID = "setEditValidationResult";

   private ValidationStatus status;
   private Map<String, String> args;

   public SetEditValidationResultAction() {
      super(ID);
   }

   public SetEditValidationResultAction(final ValidationStatus status, final Map<String, String> args) {
      this();
      this.status = status;
      this.args = args;
   }

   public SetEditValidationResultAction(final ValidationStatus status) {
      this(status, Collections.emptyMap());
   }

   public ValidationStatus getStatus() { return status; }

   public void setStatus(final ValidationStatus status) { this.status = status; }

   public Map<String, String> getArgs() { return args; }

   public void setArgs(final Map<String, String> args) { this.args = args; }
}
