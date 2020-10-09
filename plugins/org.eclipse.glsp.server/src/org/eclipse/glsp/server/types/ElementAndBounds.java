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

import java.util.function.Consumer;

import org.eclipse.glsp.graph.GDimension;
import org.eclipse.glsp.graph.GPoint;

public class ElementAndBounds {

   private String elementId;
   private GPoint newPosition;
   private GDimension newSize;

   public ElementAndBounds() {}

   public ElementAndBounds(final Consumer<ElementAndBounds> initializer) {
      initializer.accept(this);
   }

   public String getElementId() { return elementId; }

   public void setElementId(final String elementId) { this.elementId = elementId; }

   public GPoint getNewPosition() { return newPosition; }

   public void setNewPosition(final GPoint newPosition) { this.newPosition = newPosition; }

   public GDimension getNewSize() { return newSize; }

   public void setNewSize(final GDimension newSize) { this.newSize = newSize; }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((elementId == null) ? 0 : elementId.hashCode());
      result = prime * result + ((newPosition == null) ? 0 : newPosition.hashCode());
      result = prime * result + ((newSize == null) ? 0 : newSize.hashCode());
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
      ElementAndBounds other = (ElementAndBounds) obj;
      if (elementId == null) {
         if (other.elementId != null) {
            return false;
         }
      } else if (!elementId.equals(other.elementId)) {
         return false;
      }
      if (newPosition == null) {
         if (other.newPosition != null) {
            return false;
         }
      } else if (!newPosition.equals(other.newPosition)) {
         return false;
      }
      if (newSize == null) {
         if (other.newSize != null) {
            return false;
         }
      } else if (!newSize.equals(other.newSize)) {
         return false;
      }
      return true;
   }
}
