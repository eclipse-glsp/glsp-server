/*******************************************************************************
 * Copyright (c) 2019-2021 EclipseSource and others.
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
package org.eclipse.glsp.server.features.contextactions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.glsp.server.actions.ResponseAction;
import org.eclipse.glsp.server.features.directediting.LabeledAction;

public class SetContextActions extends ResponseAction {

   public static final String KIND = "setContextActions";

   private List<LabeledAction> actions;
   private Map<String, String> args;

   public SetContextActions() {
      this(new ArrayList<>(), new HashMap<>());
   }

   public SetContextActions(final List<LabeledAction> actions, final Map<String, String> map) {
      super(KIND);
      this.actions = actions;
      this.args = map;
   }

   public List<LabeledAction> getActions() { return actions; }

   public void setActions(final List<LabeledAction> commandPaletteActions) { this.actions = commandPaletteActions; }

   public Map<String, String> getArgs() { return args; }

   public void setArgs(final Map<String, String> args) { this.args = args; }
}
