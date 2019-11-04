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
package org.eclipse.glsp.api.labeledit;

public class EditLabelValidationResult {

   public static final EditLabelValidationResult OK_RESULT = new EditLabelValidationResult(SeverityKind.OK, null);

   private String severity;
   private String message;

   public EditLabelValidationResult(final String severity, final String message) {
      super();
      this.severity = severity;
      this.message = message;
   }

   public String getSeverity() { return severity; }

   public void setSeverity(final String severity) { this.severity = severity; }

   public String getMessage() { return message; }

   public void setMessage(final String message) { this.message = message; }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((message == null) ? 0 : message.hashCode());
      result = prime * result + ((severity == null) ? 0 : severity.hashCode());
      return result;
   }

   @Override
   @SuppressWarnings({ "checkstyle:CyclomaticComplexity", "checkstyle:NPathComplexity" })
   public boolean equals(final Object obj) {
      if (this == obj) {
         return true;
      }
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      EditLabelValidationResult other = (EditLabelValidationResult) obj;
      if (message == null) {
         if (other.message != null) {
            return false;
         }
      } else if (!message.equals(other.message)) {
         return false;
      }
      if (severity != other.severity) {
         return false;
      }
      return true;
   }

}
