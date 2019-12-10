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

public class Group {

   private String id;
   private String label;
   private Group parentGroup;

   public Group() {}

   public Group(final String id, final String label) {
      this(id, label, null);
   }

   public Group(final String id, final String label, final Group parentGroup) {
      super();
      this.id = id;
      this.parentGroup = parentGroup;
      this.label = label;
   }

   public String getId() { return id; }

   public void setId(final String id) { this.id = id; }

   public Group getParentGroup() { return parentGroup; }

   public void setParentGroup(final Group parentGroup) { this.parentGroup = parentGroup; }

   public String getLabel() { return label; }

   public void setLabel(final String label) { this.label = label; }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result + ((label == null) ? 0 : label.hashCode());
      result = prime * result + ((parentGroup == null) ? 0 : parentGroup.hashCode());
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
      Group other = (Group) obj;
      if (id == null) {
         if (other.id != null) {
            return false;
         }
      } else if (!id.equals(other.id)) {
         return false;
      }
      if (label == null) {
         if (other.label != null) {
            return false;
         }
      } else if (!label.equals(other.label)) {
         return false;
      }
      if (parentGroup == null) {
         if (other.parentGroup != null) {
            return false;
         }
      } else if (!parentGroup.equals(other.parentGroup)) {
         return false;
      }
      return true;
   }
}
