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

import java.util.List;

import org.eclipse.glsp.api.action.Action;

public class SelectAction extends Action {
   private List<String> selectedElementsIDs;
   private List<String> deselectedElementsIDs;

   public SelectAction() {
      super(Action.Kind.SELECT);
   }

   public SelectAction(final List<String> selectedElementsIDs, final List<String> deselectedElementsIDs) {
      this();
      this.selectedElementsIDs = selectedElementsIDs;
      this.deselectedElementsIDs = deselectedElementsIDs;
   }

   public List<String> getSelectedElementsIDs() { return selectedElementsIDs; }

   public void setSelectedElementsIDs(final List<String> selectedElementsIDs) {
      this.selectedElementsIDs = selectedElementsIDs;
   }

   public List<String> getDeselectedElementsIDs() { return deselectedElementsIDs; }

   public void setDeselectedElementsIDs(final List<String> deselectedElementsIDs) {
      this.deselectedElementsIDs = deselectedElementsIDs;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + ((deselectedElementsIDs == null) ? 0 : deselectedElementsIDs.hashCode());
      result = prime * result + ((selectedElementsIDs == null) ? 0 : selectedElementsIDs.hashCode());
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
      SelectAction other = (SelectAction) obj;
      if (deselectedElementsIDs == null) {
         if (other.deselectedElementsIDs != null) {
            return false;
         }
      } else if (!deselectedElementsIDs.equals(other.deselectedElementsIDs)) {
         return false;
      }
      if (selectedElementsIDs == null) {
         if (other.selectedElementsIDs != null) {
            return false;
         }
      } else if (!selectedElementsIDs.equals(other.selectedElementsIDs)) {
         return false;
      }
      return true;
   }
}
