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

import java.util.List;

import org.eclipse.glsp.api.action.Action;

public class CollapseExpandAction extends Action {

   private List<String> expandIds;
   private List<String> collapseIds;
   private boolean collapse = true;

   public CollapseExpandAction() {
      super(Action.Kind.COLLAPSE_EXPAND);
   }

   public CollapseExpandAction(final List<String> expandIds, final List<String> collapseIds, final boolean collapse) {
      this();
      this.expandIds = expandIds;
      this.collapseIds = collapseIds;
      this.collapse = collapse;
   }

   public List<String> getExpandIds() { return expandIds; }

   public void setExpandIds(final List<String> expandIds) { this.expandIds = expandIds; }

   public List<String> getCollapseIds() { return collapseIds; }

   public void setCollapseIds(final List<String> collapseIds) { this.collapseIds = collapseIds; }

   public boolean isCollapse() { return collapse; }

   public void setCollapse(final boolean collapse) { this.collapse = collapse; }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + (collapse ? 1231 : 1237);
      result = prime * result + ((collapseIds == null) ? 0 : collapseIds.hashCode());
      result = prime * result + ((expandIds == null) ? 0 : expandIds.hashCode());
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
      CollapseExpandAction other = (CollapseExpandAction) obj;
      if (collapse != other.collapse) {
         return false;
      }
      if (collapseIds == null) {
         if (other.collapseIds != null) {
            return false;
         }
      } else if (!collapseIds.equals(other.collapseIds)) {
         return false;
      }
      if (expandIds == null) {
         if (other.expandIds != null) {
            return false;
         }
      } else if (!expandIds.equals(other.expandIds)) {
         return false;
      }
      return true;
   }

}
