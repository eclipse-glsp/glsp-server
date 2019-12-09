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

public class FitToScreenAction extends Action {
   private List<String> elementIds;
   private double padding;
   private double maxZoom;
   private boolean animate;

   public FitToScreenAction() {
      super(Action.Kind.FIT_TO_SCREEN);
   }

   public FitToScreenAction(final String kind, final List<String> elementIds, final double padding,
      final double maxZoom, final boolean animate) {
      super(kind);
      this.elementIds = elementIds;
      this.padding = padding;
      this.maxZoom = maxZoom;
      this.animate = animate;
   }

   public List<String> getElementIds() { return elementIds; }

   public void setElementIds(final List<String> elementIds) { this.elementIds = elementIds; }

   public double getPadding() { return padding; }

   public void setPadding(final double padding) { this.padding = padding; }

   public double getMaxZoom() { return maxZoom; }

   public void setMaxZoom(final double maxZoom) { this.maxZoom = maxZoom; }

   public boolean isAnimate() { return animate; }

   public void setAnimate(final boolean animate) { this.animate = animate; }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + (animate ? 1231 : 1237);
      result = prime * result + ((elementIds == null) ? 0 : elementIds.hashCode());
      long temp;
      temp = Double.doubleToLongBits(maxZoom);
      result = prime * result + (int) (temp ^ (temp >>> 32));
      temp = Double.doubleToLongBits(padding);
      result = prime * result + (int) (temp ^ (temp >>> 32));
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
      FitToScreenAction other = (FitToScreenAction) obj;
      if (animate != other.animate) {
         return false;
      }
      if (elementIds == null) {
         if (other.elementIds != null) {
            return false;
         }
      } else if (!elementIds.equals(other.elementIds)) {
         return false;
      }
      if (Double.doubleToLongBits(maxZoom) != Double.doubleToLongBits(other.maxZoom)) {
         return false;
      }
      if (Double.doubleToLongBits(padding) != Double.doubleToLongBits(other.padding)) {
         return false;
      }
      return true;
   }

}
