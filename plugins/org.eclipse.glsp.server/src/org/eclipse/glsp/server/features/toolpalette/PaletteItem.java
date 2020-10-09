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
package org.eclipse.glsp.server.features.toolpalette;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.glsp.server.actions.TriggerElementCreationAction;
import org.eclipse.glsp.server.features.directediting.LabeledAction;

public class PaletteItem extends LabeledAction {
   private String id;
   private String sortString;
   private List<PaletteItem> children;

   public PaletteItem(final String id, final String label) {
      super(label, Collections.emptyList());
      this.id = id;
      this.sortString = ("" + label.charAt(0));
   }

   public PaletteItem(final String id, final String label, final TriggerElementCreationAction initalizeAction) {
      this(id, label, initalizeAction, null);
   }

   public PaletteItem(final String id, final String label, final TriggerElementCreationAction initalizeAction,
      final String icon) {
      super(label, Arrays.asList(initalizeAction), icon);
      this.id = id;
      this.sortString = ("" + label.charAt(0));
   }

   public String getId() { return id; }

   public void setId(final String id) { this.id = id; }

   public String getSortString() { return sortString; }

   public void setSortString(final String sortString) { this.sortString = sortString; }

   public List<PaletteItem> getChildren() { return children; }

   public void setChildren(final List<PaletteItem> children) { this.children = children; }

   public static PaletteItem createPaletteGroup(final String id, final String label, final List<PaletteItem> children) {
      PaletteItem item = new PaletteItem(id, label);
      item.setChildren(children);
      return item;
   }

   public static PaletteItem createPaletteGroup(final String id, final String label, final List<PaletteItem> children,
      final String icon) {
      PaletteItem item = new PaletteItem(id, label);
      item.setChildren(children);
      item.setIcon(icon);
      return item;
   }

   public static PaletteItem createPaletteGroup(final String id, final String label, final List<PaletteItem> children,
      final String icon, final String sortString) {
      PaletteItem item = new PaletteItem(id, label);
      item.setChildren(children);
      item.setIcon(icon);
      item.setSortString(sortString);
      return item;
   }
}
