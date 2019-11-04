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
package org.eclipse.glsp.api.operations;

public class Operation {

   public static class Kind {
      public static final String CHANGE_BOUNDS = "changeBoundsOperation";
      public static final String CREATE_NODE = "createNode";
      public static final String CREATE_CONNECTION = "createConnection";
      public static final String DELETE_ELEMENT = "delete";
      public static final String CHANGE_CONTAINER = "changeContainer";
      public static final String GENERIC = "generic";
      public static final String RECONNECT_CONNECTION = "reconnectConnection";
      public static final String REROUTE_CONNECTION = "rerouteConnection";
      public static final String APPLY_LABEL_EDIT = "applyLabelEdit";
   }

   private String label;
   private String elementTypeId;
   private String operationKind;
   private Group group;

   public Operation() {}

   public Operation(final String label, final String elementTypeId, final String operationKind) {
      this(label, elementTypeId, operationKind, null);
   }

   public Operation(final String label, final String elementTypeId, final String operationKind, final Group group) {
      super();
      this.label = label;
      this.elementTypeId = elementTypeId;
      this.operationKind = operationKind;
      this.group = group;
   }

   public String getLabel() { return label; }

   public String getElementTypeId() { return elementTypeId; }

   public String getOperationKind() { return operationKind; }

   public Group getGroup() { return group; }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((elementTypeId == null) ? 0 : elementTypeId.hashCode());
      result = prime * result + ((group == null) ? 0 : group.hashCode());
      result = prime * result + ((label == null) ? 0 : label.hashCode());
      result = prime * result + ((operationKind == null) ? 0 : operationKind.hashCode());
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
      Operation other = (Operation) obj;
      if (elementTypeId == null) {
         if (other.elementTypeId != null) {
            return false;
         }
      } else if (!elementTypeId.equals(other.elementTypeId)) {
         return false;
      }
      if (group == null) {
         if (other.group != null) {
            return false;
         }
      } else if (!group.equals(other.group)) {
         return false;
      }
      if (label == null) {
         if (other.label != null) {
            return false;
         }
      } else if (!label.equals(other.label)) {
         return false;
      }
      if (operationKind == null) {
         if (other.operationKind != null) {
            return false;
         }
      } else if (!operationKind.equals(other.operationKind)) {
         return false;
      }
      return true;
   }
}
