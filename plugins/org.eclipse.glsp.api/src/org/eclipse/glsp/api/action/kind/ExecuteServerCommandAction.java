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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.glsp.api.action.Action;

public class ExecuteServerCommandAction extends Action {

   private String commandId;
   private Map<String, String> options;

   public ExecuteServerCommandAction() {
      super(Action.Kind.EXECUTE_SERVER_COMMAND);
      options = new HashMap<>();
   }

   public ExecuteServerCommandAction(final String commandId, final Map<String, String> options) {
      this();
      this.commandId = commandId;
      this.options = options;
   }

   public String getCommandId() { return commandId; }

   public Map<String, String> getOptions() { return options; }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + ((commandId == null) ? 0 : commandId.hashCode());
      result = prime * result + ((options == null) ? 0 : options.hashCode());
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
      ExecuteServerCommandAction other = (ExecuteServerCommandAction) obj;
      if (commandId == null) {
         if (other.commandId != null) {
            return false;
         }
      } else if (!commandId.equals(other.commandId)) {
         return false;
      }
      if (options == null) {
         if (other.options != null) {
            return false;
         }
      } else if (!options.equals(other.options)) {
         return false;
      }
      return true;
   }

}
