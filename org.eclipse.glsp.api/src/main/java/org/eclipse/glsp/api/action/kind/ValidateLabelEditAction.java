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

public class ValidateLabelEditAction extends Action {

   private String value;
   private String labelId;

   public ValidateLabelEditAction() {
      super(Action.Kind.VALIDATE_LABEL_EDIT_ACTION);
   }

   public ValidateLabelEditAction(final String value, final String labelId) {
      super(Action.Kind.VALIDATE_LABEL_EDIT_ACTION);
      this.value = value;
      this.labelId = labelId;
   }

   public String getValue() { return value; }

   public void setValue(final String value) { this.value = value; }

   public String getLabelId() { return labelId; }

   public void setLabelId(final String labelId) { this.labelId = labelId; }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + ((labelId == null) ? 0 : labelId.hashCode());
      result = prime * result + ((value == null) ? 0 : value.hashCode());
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
      ValidateLabelEditAction other = (ValidateLabelEditAction) obj;
      if (labelId == null) {
         if (other.labelId != null) {
            return false;
         }
      } else if (!labelId.equals(other.labelId)) {
         return false;
      }
      if (value == null) {
         if (other.value != null) {
            return false;
         }
      } else if (!value.equals(other.value)) {
         return false;
      }
      return true;
   }

}
