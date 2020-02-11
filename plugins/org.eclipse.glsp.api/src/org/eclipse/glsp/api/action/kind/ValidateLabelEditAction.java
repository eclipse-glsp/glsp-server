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

public class ValidateLabelEditAction extends RequestAction<SetEditLabelValidationResultAction> {

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
}
