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
import org.eclipse.glsp.api.types.ElementAndAlignment;
import org.eclipse.glsp.api.types.ElementAndBounds;

public class ComputedBoundsAction extends Action {

   private List<ElementAndBounds> bounds;
   private List<ElementAndAlignment> alignments;
   private int revision;

   public ComputedBoundsAction() {
      super(Action.Kind.COMPUTED_BOUNDS);
   }

   public ComputedBoundsAction(final List<ElementAndBounds> bounds, final List<ElementAndAlignment> alignments,
      final int revision) {
      this();
      this.bounds = bounds;
      this.alignments = alignments;
      this.revision = revision;
   }

   public List<ElementAndBounds> getBounds() { return bounds; }

   public void setBounds(final List<ElementAndBounds> bounds) { this.bounds = bounds; }

   public List<ElementAndAlignment> getAlignments() { return alignments; }

   public void setAlignments(final List<ElementAndAlignment> alignments) { this.alignments = alignments; }

   public int getRevision() { return revision; }

   public void setRevision(final int revision) { this.revision = revision; }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + ((alignments == null) ? 0 : alignments.hashCode());
      result = prime * result + ((bounds == null) ? 0 : bounds.hashCode());
      result = prime * result + revision;
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
      ComputedBoundsAction other = (ComputedBoundsAction) obj;
      if (alignments == null) {
         if (other.alignments != null) {
            return false;
         }
      } else if (!alignments.equals(other.alignments)) {
         return false;
      }
      if (bounds == null) {
         if (other.bounds != null) {
            return false;
         }
      } else if (!bounds.equals(other.bounds)) {
         return false;
      }
      if (revision != other.revision) {
         return false;
      }
      return true;
   }

}
