/********************************************************************************
 * Copyright (c) 2020 EclipseSource and others.
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

import org.eclipse.glsp.api.action.Action;

public class SetDirtyStateAction extends Action {

   private boolean isDirty;

   public SetDirtyStateAction() {
      super(Action.Kind.SET_DIRTY_STATE);
   }

   public SetDirtyStateAction(final boolean isDirty) {
      this();
      this.isDirty = isDirty;
   }

   public boolean isDirty() { return isDirty; }

   public void setDirty(final boolean isDirty) { this.isDirty = isDirty; }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + (isDirty ? 1231 : 1237);
      return result;
   }

   @Override
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
      SetDirtyStateAction other = (SetDirtyStateAction) obj;
      if (isDirty != other.isDirty) {
         return false;
      }
      return true;
   }

}
