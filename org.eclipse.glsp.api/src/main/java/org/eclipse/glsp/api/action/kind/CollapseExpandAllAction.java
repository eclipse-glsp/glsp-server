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

import org.eclipse.glsp.api.action.Action;

public class CollapseExpandAllAction extends Action {
   private boolean expand = true;

   public CollapseExpandAllAction() {
      super(Action.Kind.COLLAPSE_EXPAND_ALL);

   }

   public CollapseExpandAllAction(final boolean expand) {
      this();
      this.expand = expand;
   }

   public boolean isExpand() { return expand; }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + (expand ? 1231 : 1237);
      return result;
   }

   @Override
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
      CollapseExpandAllAction other = (CollapseExpandAllAction) obj;
      if (expand != other.expand) {
         return false;
      }
      return true;
   }

}
