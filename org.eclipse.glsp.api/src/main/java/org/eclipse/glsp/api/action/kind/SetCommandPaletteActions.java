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

import java.util.Set;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.types.LabeledAction;

public class SetCommandPaletteActions extends Action {

   private Set<LabeledAction> actions;

   public SetCommandPaletteActions() {
      super(Action.Kind.SET_COMMAND_PALETTE_ACTIONS);
   }

   public SetCommandPaletteActions(Set<LabeledAction> actions) {
      this();
      this.actions = actions;
   }

   public Set<LabeledAction> getActions() { return actions; }

   public void setActions(Set<LabeledAction> commandPaletteActions) { this.actions = commandPaletteActions; }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + ((actions == null) ? 0 : actions.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      }
      if (!super.equals(obj)) {
         return false;
      }
      if (!(obj instanceof SetCommandPaletteActions)) {
         return false;
      }
      SetCommandPaletteActions other = (SetCommandPaletteActions) obj;
      if (actions == null) {
         if (other.actions != null) {
            return false;
         }
      } else if (!actions.equals(other.actions)) {
         return false;
      }
      return true;
   }

}
