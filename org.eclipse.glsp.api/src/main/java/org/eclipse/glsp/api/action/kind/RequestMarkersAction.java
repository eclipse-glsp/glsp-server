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

public class RequestMarkersAction extends Action {

   private List<String> elementsIDs;

   public RequestMarkersAction() {
      super(Action.Kind.REQUEST_MARKERS);
   }

   public RequestMarkersAction(final List<String> elementsIDs) {
      this();
      this.elementsIDs = elementsIDs;
   }

   public List<String> getElementsIDs() { return elementsIDs; }

   public void setElementsIDs(final List<String> elementsIDs) { this.elementsIDs = elementsIDs; }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + ((elementsIDs == null) ? 0 : elementsIDs.hashCode());
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
      RequestMarkersAction other = (RequestMarkersAction) obj;
      if (elementsIDs == null) {
         if (other.elementsIDs != null) {
            return false;
         }
      } else if (!elementsIDs.equals(other.elementsIDs)) {
         return false;
      }
      return true;
   }

}
