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
package org.eclipse.glsp.server.features.contextactions;

import java.util.ArrayList;

import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.features.directediting.LabeledAction;

public class SetAutoCompleteValueAction extends LabeledAction {

   private String text;

   public SetAutoCompleteValueAction(final String label, final String icon, final String text) {
      super(label, new ArrayList<Action>(), icon);
      this.text = text;
   }

   public String getText() { return text; }

   public void setText(final String text) { this.text = text; }

}
