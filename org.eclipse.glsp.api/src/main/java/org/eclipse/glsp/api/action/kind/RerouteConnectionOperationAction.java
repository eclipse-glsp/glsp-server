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
import org.eclipse.glsp.graph.GPoint;

public class RerouteConnectionOperationAction extends AbstractOperationAction {

   private String connectionElementId;
   private List<GPoint> routingPoints;

   public RerouteConnectionOperationAction() {
      super(Action.Kind.REROUTE_CONNECTION_OPERATION);
   }

   public RerouteConnectionOperationAction(final String operationKind, final String connectionElementId,
      final List<GPoint> routingPoints) {
      super(operationKind);
      this.connectionElementId = connectionElementId;
      this.routingPoints = routingPoints;
   }

   public String getConnectionElementId() { return connectionElementId; }

   public void setConnectionElementId(final String connectionElementId) {
      this.connectionElementId = connectionElementId;
   }

   public List<GPoint> getRoutingPoints() { return routingPoints; }

   public void setRoutingPoints(final List<GPoint> routingPoints) { this.routingPoints = routingPoints; }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + ((connectionElementId == null) ? 0 : connectionElementId.hashCode());
      result = prime * result + ((routingPoints == null) ? 0 : routingPoints.hashCode());
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
      RerouteConnectionOperationAction other = (RerouteConnectionOperationAction) obj;
      if (connectionElementId == null) {
         if (other.connectionElementId != null) {
            return false;
         }
      } else if (!connectionElementId.equals(other.connectionElementId)) {
         return false;
      }
      if (routingPoints == null) {
         if (other.routingPoints != null) {
            return false;
         }
      } else if (!routingPoints.equals(other.routingPoints)) {
         return false;
      }
      return true;
   }
}
