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
package org.eclipse.glsp.server.features.contextmenu;

import java.util.Collections;
import java.util.List;

import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.features.directediting.LabeledAction;

public class MenuItem extends LabeledAction {
   private final String id;
   private final String sortString;
   private String group;
   private String parentId;
   private final List<MenuItem> children;
   private boolean isEnabled;
   private boolean isToggled;

   public MenuItem(final String id, final String label, final List<Action> actions, final boolean isEnabled) {
      this(id, label, actions, isEnabled, null);
   }

   public MenuItem(final String id, final String label, final List<Action> actions, final boolean isEnabled,
      final String icon) {
      this(id, label, actions, isEnabled, icon, null);
   }

   public MenuItem(final String id, final String label, final List<Action> actions, final boolean isEnabled,
      final String icon, final String sortString) {
      this(id, label, actions, icon, sortString, null, null, isEnabled, false, Collections.emptyList());
   }

   public MenuItem(final String id, final String label, final List<MenuItem> children) {
      this(id, label, children, null);
   }

   public MenuItem(final String id, final String label, final List<MenuItem> children, final String group) {
      this(id, label, children, group, null);
   }

   public MenuItem(final String id, final String label, final List<MenuItem> children, final String group,
      final String sortString) {
      this(id, label, Collections.emptyList(), null, sortString, group, null, true, false, children);
   }

   @SuppressWarnings("checkstyle:ParameterNumber")
   public MenuItem(final String id, final String label, final List<Action> actions, final String icon,
      final String sortString, final String group,
      final String parentId, final boolean isEnabled, final boolean isToggled, final List<MenuItem> children) {
      super(label, actions, icon);
      this.id = id;
      this.sortString = sortString;
      this.children = children;
      this.group = group;
      this.parentId = parentId;
      this.isEnabled = isEnabled;
      this.isToggled = isToggled;
   }

   public String getGroup() { return group; }

   public void setGroup(final String group) { this.group = group; }

   public String getParentId() { return parentId; }

   public void setParentId(final String parentId) { this.parentId = parentId; }

   public boolean isEnabled() { return isEnabled; }

   public void setEnabled(final boolean isEnabled) { this.isEnabled = isEnabled; }

   public boolean isToggled() { return isToggled; }

   public void setToggled(final boolean isToggled) { this.isToggled = isToggled; }

   public String getId() { return id; }

   public String getSortString() { return sortString; }

   public List<MenuItem> getChildren() { return children; }
}
