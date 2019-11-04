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

public class ApplyLabelEditOperationAction extends AbstractOperationAction {
   private String labelId;
   private String text;

   public ApplyLabelEditOperationAction() {
      super(Action.Kind.APPLY_LABEL_EDIT_OPERATION);
   }

   public ApplyLabelEditOperationAction(final String labelId, final String text) {
      this();
      this.labelId = labelId;
      this.text = text;
   }

   public String getLabelId() { return labelId; }

   public String getText() { return text; }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + ((labelId == null) ? 0 : labelId.hashCode());
      result = prime * result + ((text == null) ? 0 : text.hashCode());
      return result;
   }

   @Override
   @SuppressWarnings("checkstyle:CyclomaticComplexity")
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
      ApplyLabelEditOperationAction other = (ApplyLabelEditOperationAction) obj;
      if (labelId == null) {
         if (other.labelId != null) {
            return false;
         }
      } else if (!labelId.equals(other.labelId)) {
         return false;
      }
      if (text == null) {
         if (other.text != null) {
            return false;
         }
      } else if (!text.equals(other.text)) {
         return false;
      }
      return true;
   }
}
