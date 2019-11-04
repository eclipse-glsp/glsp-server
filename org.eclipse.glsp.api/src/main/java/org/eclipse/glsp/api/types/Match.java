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

import org.eclipse.glsp.graph.GModelElement;

public class Match {

   private GModelElement left;
   private GModelElement right;

   public Match() {}

   public Match(GModelElement left, GModelElement right) {
      super();
      this.left = left;
      this.right = right;
   }

   public GModelElement getLeft() { return left; }

   public GModelElement getRight() { return right; }

   public String getLeftParentId() { return this.left.getParent().getId(); }

   public String getRightParentId() { return this.right.getParent().getId(); }

   public void setLeft(GModelElement left) { this.left = left; }

   public void setRight(GModelElement right) { this.right = right; }

}
