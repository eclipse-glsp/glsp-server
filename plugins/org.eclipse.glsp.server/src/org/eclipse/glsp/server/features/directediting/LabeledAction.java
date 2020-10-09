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
package org.eclipse.glsp.server.features.directediting;

import java.util.List;
import java.util.Optional;

import org.eclipse.glsp.server.actions.Action;

public class LabeledAction {

   private String label;
   private List<Action> actions;
   private String icon;

   public LabeledAction(final String label, final List<Action> actions, final String icon) {
      this.label = label;
      this.actions = actions;
      this.icon = icon;
   }

   public LabeledAction(final String label, final List<Action> actions) {
      this(label, actions, null);
   }

   public void setLabel(final String label) { this.label = label; }

   public String getLabel() { return label; }

   public void setActions(final List<Action> actions) { this.actions = actions; }

   public List<Action> getActions() { return actions; }

   public void setIcon(final String icon) { this.icon = icon; }

   public Optional<String> getIcon() { return Optional.ofNullable(icon); }

}
