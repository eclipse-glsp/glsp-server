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
import java.util.Optional;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.graph.GPoint;

public class RequestCommandPaletteActions extends Action {

   private List<String> selectedElementIds;
   private String text;
   private GPoint lastMousePosition;

   public RequestCommandPaletteActions() {
      super(Action.Kind.REQUEST_COMMAND_PALETTE_ACTIONS);
   }

   public RequestCommandPaletteActions(final List<String> selectedElementIds, final String text) {
      this(selectedElementIds, text, null);
   }

   public RequestCommandPaletteActions(final List<String> selectedElementIds, final String text,
      final GPoint lastMousePosition) {
      this();
      this.selectedElementIds = selectedElementIds;
      this.text = text;
      this.lastMousePosition = lastMousePosition;
   }

   public List<String> getSelectedElementIds() { return selectedElementIds; }

   public void setSelectedElementsIds(final List<String> selectedElementsIDs) {
      this.selectedElementIds = selectedElementsIDs;
   }

   public String getText() { return text; }

   public void setText(final String text) { this.text = text; }

   public Optional<GPoint> getLastMousePosition() { return Optional.ofNullable(lastMousePosition); }

   public void setLastMousePosition(final GPoint lastMousePosition) { this.lastMousePosition = lastMousePosition; }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + ((lastMousePosition == null) ? 0 : lastMousePosition.hashCode());
      result = prime * result + ((selectedElementIds == null) ? 0 : selectedElementIds.hashCode());
      result = prime * result + ((text == null) ? 0 : text.hashCode());
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
      RequestCommandPaletteActions other = (RequestCommandPaletteActions) obj;
      if (lastMousePosition == null) {
         if (other.lastMousePosition != null) {
            return false;
         }
      } else if (!lastMousePosition.equals(other.lastMousePosition)) {
         return false;
      }
      if (selectedElementIds == null) {
         if (other.selectedElementIds != null) {
            return false;
         }
      } else if (!selectedElementIds.equals(other.selectedElementIds)) {
         return false;
      }
      if (text == null) {
         if (other.text != null) {
            return false;
         }
      } else if (!text.equals(other.text)) {
         return false;
      }
      return true;
   }

}
