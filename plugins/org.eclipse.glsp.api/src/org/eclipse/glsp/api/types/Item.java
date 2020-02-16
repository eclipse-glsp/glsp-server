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
package org.eclipse.glsp.api.types;

import java.util.List;

import org.eclipse.glsp.api.action.Action;

public class Item extends LabeledAction {
   private String id;
   private String sortString;
   private List<? extends Item> children;

   public Item(final String id, final String label, final List<Action> actions) {
      this(id, label, actions, null);
   }

   public Item(final String id, final String label, final List<Action> actions, final String icon) {
      super(label, actions, icon);
      this.id = id;
   }

   public String getId() { return id; }

   public void setId(final String id) { this.id = id; }

   public String getSortString() { return sortString; }

   public void setSortString(final String sortString) { this.sortString = sortString; }

   public List<? extends Item> getChildren() { return children; }

   public void setChildren(final List<? extends Item> children) { this.children = children; }

}
