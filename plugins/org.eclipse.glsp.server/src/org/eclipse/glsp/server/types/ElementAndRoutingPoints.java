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
package org.eclipse.glsp.server.types;

import java.util.List;

import org.eclipse.glsp.graph.GPoint;

public class ElementAndRoutingPoints {

   private String elementId;
   private List<GPoint> newRoutingPoints;

   public ElementAndRoutingPoints() {}

   public ElementAndRoutingPoints(final String elementId, final List<GPoint> newRoutingPoints) {
      super();
      this.elementId = elementId;
      this.newRoutingPoints = newRoutingPoints;
   }

   public String getElementId() { return elementId; }

   public void setElementId(final String elementId) { this.elementId = elementId; }

   public List<GPoint> getNewRoutingPoints() { return newRoutingPoints; }

   public void setNewRoutingPoints(final List<GPoint> newRoutingPoints) { this.newRoutingPoints = newRoutingPoints; }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((elementId == null) ? 0 : elementId.hashCode());
      result = prime * result + ((newRoutingPoints == null) ? 0 : newRoutingPoints.hashCode());
      return result;
   }

   @Override
   @SuppressWarnings("checkstyle:CyclomaticComplexity")
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
      ElementAndRoutingPoints other = (ElementAndRoutingPoints) obj;
      if (elementId == null) {
         if (other.elementId != null) {
            return false;
         }
      } else if (!elementId.equals(other.elementId)) {
         return false;
      }
      if (newRoutingPoints == null) {
         if (other.newRoutingPoints != null) {
            return false;
         }
      } else if (!newRoutingPoints.equals(other.newRoutingPoints)) {
         return false;
      }
      return true;
   }

}
