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

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.types.ValidationStatus;

public class SetEditValidationResultAction extends ResponseAction {

   private ValidationStatus status;

   public SetEditValidationResultAction() {
      super(Action.Kind.SET_EDIT_VALIDATION_RESULT);
   }

   public SetEditValidationResultAction(final ValidationStatus status) {
      this();
      this.status = status;
   }

   public ValidationStatus getStatus() { return status; }

   public void setStatus(final ValidationStatus status) { this.status = status; }

}
