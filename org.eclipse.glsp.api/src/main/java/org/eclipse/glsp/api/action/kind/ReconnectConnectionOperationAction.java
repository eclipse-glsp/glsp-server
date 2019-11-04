/*******************************************************************************
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
 ******************************************************************************/
package org.eclipse.glsp.api.action.kind;

import org.eclipse.glsp.api.action.Action;

public class ReconnectConnectionOperationAction extends AbstractOperationAction {

   private String connectionElementId;
   private String sourceElementId;
   private String targetElementId;

   public ReconnectConnectionOperationAction() {
      super(Action.Kind.RECONNECT_CONNECTION_OPERATION);
   }

   public ReconnectConnectionOperationAction(final String connectionElementId, final String sourceElementId,
      final String targetElementId) {
      this();
      this.connectionElementId = connectionElementId;
      this.sourceElementId = sourceElementId;
      this.targetElementId = targetElementId;
   }

   public String getConnectionElementId() { return connectionElementId; }

   public void setConnectionElementId(final String connectionElementId) {
      this.connectionElementId = connectionElementId;
   }

   public String getSourceElementId() { return sourceElementId; }

   public void setSourceElementId(final String sourceElementId) { this.sourceElementId = sourceElementId; }

   public String getTargetElementId() { return targetElementId; }

   public void setTargetElementId(final String targetElementId) { this.targetElementId = targetElementId; }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + ((connectionElementId == null) ? 0 : connectionElementId.hashCode());
      result = prime * result + ((sourceElementId == null) ? 0 : sourceElementId.hashCode());
      result = prime * result + ((targetElementId == null) ? 0 : targetElementId.hashCode());
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
      ReconnectConnectionOperationAction other = (ReconnectConnectionOperationAction) obj;
      if (connectionElementId == null) {
         if (other.connectionElementId != null) {
            return false;
         }
      } else if (!connectionElementId.equals(other.connectionElementId)) {
         return false;
      }
      if (sourceElementId == null) {
         if (other.sourceElementId != null) {
            return false;
         }
      } else if (!sourceElementId.equals(other.sourceElementId)) {
         return false;
      }
      if (targetElementId == null) {
         if (other.targetElementId != null) {
            return false;
         }
      } else if (!targetElementId.equals(other.targetElementId)) {
         return false;
      }
      return true;
   }

}
