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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.eclipse.glsp.api.action.kind.InitCreateOperationAction;

public class PaletteItem extends Item {

   public PaletteItem(final String id, final String label) {
      super(id, label, Collections.emptyList());
      setSortString("" + label.charAt(0));
   }

   public PaletteItem(final String id, final String label, final InitCreateOperationAction initalizeAction) {
      this(id, label, initalizeAction, null);
   }

   public PaletteItem(final String id, final String label, final InitCreateOperationAction initalizeAction,
      final String icon) {
      super(id, label, Arrays.asList(initalizeAction), icon);
      setSortString("" + label.charAt(0));
   }

   public Optional<InitCreateOperationAction> getInitAction() {
      return getActions().stream()
         .filter(InitCreateOperationAction.class::isInstance)
         .map(InitCreateOperationAction.class::cast)
         .findFirst();
   }

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
