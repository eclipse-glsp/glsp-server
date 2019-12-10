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
import java.util.Map;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.types.LabeledAction;

public class SetContextActions extends ResponseAction {

   private List<LabeledAction> actions;
   private Map<String, String> args;

   public SetContextActions() {
      super(Action.Kind.SET_CONTEXT_ACTIONS);
   }

   public SetContextActions(final List<LabeledAction> actions, final Map<String, String> map) {
      this();
      this.actions = actions;
      this.args = map;
   }

   public List<LabeledAction> getActions() { return actions; }

   public void setActions(final List<LabeledAction> commandPaletteActions) { this.actions = commandPaletteActions; }

   public Map<String, String> getArgs() { return args; }

   public void setArgs(final Map<String, String> args) { this.args = args; }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + ((actions == null) ? 0 : actions.hashCode());
      result = prime * result + ((args == null) ? 0 : args.hashCode());
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
      SetContextActions other = (SetContextActions) obj;
      if (actions == null) {
         if (other.actions != null) {
            return false;
         }
      } else if (!actions.equals(other.actions)) {
         return false;
      }
      if (args == null) {
         if (other.args != null) {
            return false;
         }
      } else if (!args.equals(other.args)) {
         return false;
      }
      return true;
   }

}
