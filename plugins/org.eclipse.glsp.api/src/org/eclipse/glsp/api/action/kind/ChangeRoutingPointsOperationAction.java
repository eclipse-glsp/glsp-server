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
import org.eclipse.glsp.api.types.ElementAndRoutingPoints;

public class ChangeRoutingPointsOperationAction extends AbstractOperationAction {

   private List<ElementAndRoutingPoints> newRoutingPoints;

   public ChangeRoutingPointsOperationAction() {
      super(Action.Kind.CHANGE_ROUTING_POINTS_OPERATION);
   }

   public ChangeRoutingPointsOperationAction(final List<ElementAndRoutingPoints> newRoutingPoints) {
      this();
      this.newRoutingPoints = newRoutingPoints;
   }

   public List<ElementAndRoutingPoints> getNewRoutingPoints() { return newRoutingPoints; }

   public void setNewRoutingPoints(final List<ElementAndRoutingPoints> newRoutingPoints) {
      this.newRoutingPoints = newRoutingPoints;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + ((newRoutingPoints == null) ? 0 : newRoutingPoints.hashCode());
      return result;
   }

   @Override
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
      ChangeRoutingPointsOperationAction other = (ChangeRoutingPointsOperationAction) obj;
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
