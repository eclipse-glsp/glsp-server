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
package org.eclipse.glsp.api.action.kind;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.labeledit.EditLabelValidationResult;

public class SetEditLabelValidationResultAction extends Action {

   private EditLabelValidationResult result;

   public SetEditLabelValidationResultAction() {
      super(Action.Kind.SET_LABEL_EDIT_VALIDATION_RESULT_ACTION);
   }

   public SetEditLabelValidationResultAction(final EditLabelValidationResult result) {
      super(Action.Kind.SET_LABEL_EDIT_VALIDATION_RESULT_ACTION);
      this.result = result;
   }

   public EditLabelValidationResult getResult() { return result; }

   public void setResult(final EditLabelValidationResult result) { this.result = result; }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
      return result;
   }

   @Override
   @SuppressWarnings({ "checkstyle:CyclomaticComplexity", "checkstyle:NPathComplexity" })
   public boolean equals(final Object obj) {
      if (this == obj) {
         return true;
      }
      if (!super.equals(obj)) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      SetEditLabelValidationResultAction other = (SetEditLabelValidationResultAction) obj;
      if (result == null) {
         if (other.result != null) {
            return false;
         }
      } else if (!result.equals(other.result)) {
         return false;
      }
      return true;
   }

}
