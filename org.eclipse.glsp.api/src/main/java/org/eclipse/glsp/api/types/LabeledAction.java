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
package org.eclipse.glsp.api.types;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.eclipse.glsp.api.action.Action;

public class LabeledAction {

   private String label;
   private List<Action> actions;
   private String icon;

   public LabeledAction(final String label, final String icon, final List<Action> actions) {
      this.label = label;
      this.actions = actions;
      this.icon = icon;
   }

   public LabeledAction(final String label, final List<Action> actions) {
      this(label, null, actions);
   }

   public LabeledAction(final String label, final Action... actions) {
      this(label, Arrays.asList(actions));
   }

   public LabeledAction(final String label, final String icon, final Action... actions) {
      this(label, icon, Arrays.asList(actions));
   }

   public void setLabel(final String label) { this.label = label; }

   public String getLabel() { return label; }

   public void setActions(final List<Action> actions) {
      if (actions == null || actions.isEmpty()) {
         throw new IllegalArgumentException("Invalid LabeledAction: Require at least one action to execute.");
      }
      this.actions = actions;
   }

   public Collection<Action> getActions() { return actions; }

   public void setIcon(final String icon) { this.icon = icon; }

   public Optional<String> getIcon() { return Optional.ofNullable(icon); }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((actions == null) ? 0 : actions.hashCode());
      result = prime * result + ((icon == null) ? 0 : icon.hashCode());
      result = prime * result + ((label == null) ? 0 : label.hashCode());
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
      LabeledAction other = (LabeledAction) obj;
      if (actions == null) {
         if (other.actions != null) {
            return false;
         }
      } else if (!actions.equals(other.actions)) {
         return false;
      }
      if (icon == null) {
         if (other.icon != null) {
            return false;
         }
      } else if (!icon.equals(other.icon)) {
         return false;
      }
      if (label == null) {
         if (other.label != null) {
            return false;
         }
      } else if (!label.equals(other.label)) {
         return false;
      }
      return true;
   }

}
