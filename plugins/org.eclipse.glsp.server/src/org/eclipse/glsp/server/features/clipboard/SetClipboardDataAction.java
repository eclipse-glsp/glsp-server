/*******************************************************************************
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
 ******************************************************************************/
package org.eclipse.glsp.server.features.clipboard;

import java.util.Map;

import org.eclipse.glsp.server.actions.ResponseAction;

public class SetClipboardDataAction extends ResponseAction {

   public static final String ID = "setClipboardData";

   private Map<String, String> clipboardData;

   public SetClipboardDataAction() {
      super(ID);
   }

   public SetClipboardDataAction(final Map<String, String> clipboardData) {
      this();
      this.clipboardData = clipboardData;
   }

   public Map<String, String> getClipboardData() { return clipboardData; }

   public void setClipboardData(final Map<String, String> clipboardData) { this.clipboardData = clipboardData; }

}
