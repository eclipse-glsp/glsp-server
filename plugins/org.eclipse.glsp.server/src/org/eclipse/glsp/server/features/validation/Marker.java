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
package org.eclipse.glsp.server.features.validation;

public class Marker {

   private final String label;
   private final String description;
   private final String elementId;
   private final String kind;

   public Marker(final String label, final String description, final String elementId, final String kind) {
      super();
      this.label = label;
      this.description = description;
      this.elementId = elementId;
      this.kind = kind;
   }

   public String getLabel() { return label; }

   public String getDescription() { return description; }

   public String getElementId() { return elementId; }

   public String getType() { return kind; }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((description == null) ? 0 : description.hashCode());
      result = prime * result + ((elementId == null) ? 0 : elementId.hashCode());
      result = prime * result + ((label == null) ? 0 : label.hashCode());
      result = prime * result + ((kind == null) ? 0 : kind.hashCode());
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
      Marker other = (Marker) obj;
      if (description == null) {
         if (other.description != null) {
            return false;
         }
      } else if (!description.equals(other.description)) {
         return false;
      }
      if (elementId == null) {
         if (other.elementId != null) {
            return false;
         }
      } else if (!elementId.equals(other.elementId)) {
         return false;
      }
      if (label == null) {
         if (other.label != null) {
            return false;
         }
      } else if (!label.equals(other.label)) {
         return false;
      }
      if (kind != other.kind) {
         return false;
      }
      return true;
   }

}
