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

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.graph.GBounds;

public class RequestPopupModelAction extends Action {

   private String elementId;
   private GBounds bounds;

   public RequestPopupModelAction() {
      super(Action.Kind.REQUEST_POPUP_MODEL);
   }

   public RequestPopupModelAction(final String elementId, final GBounds bounds) {
      this();
      this.elementId = elementId;
      this.bounds = bounds;
   }

   public String getElementId() { return elementId; }

   public GBounds getBounds() { return bounds; }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + ((bounds == null) ? 0 : bounds.hashCode());
      result = prime * result + ((elementId == null) ? 0 : elementId.hashCode());
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
      RequestPopupModelAction other = (RequestPopupModelAction) obj;
      if (bounds == null) {
         if (other.bounds != null) {
            return false;
         }
      } else if (!bounds.equals(other.bounds)) {
         return false;
      }
      if (elementId == null) {
         if (other.elementId != null) {
            return false;
         }
      } else if (!elementId.equals(other.elementId)) {
         return false;
      }
      return true;
   }

}
