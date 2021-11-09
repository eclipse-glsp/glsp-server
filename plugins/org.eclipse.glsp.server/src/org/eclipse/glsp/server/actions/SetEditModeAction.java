/********************************************************************************
 * Copyright (c) 2020-2021 EclipseSource and others.
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
package org.eclipse.glsp.server.actions;

public class SetEditModeAction extends Action {
   public static final String KIND = "setEditMode";
   public static final String EDIT_MODE_READONLY = "readonly";
   public static final String EDIT_MODE_EDITABLE = "editable";

   private String editMode;

   public SetEditModeAction() {
      super(KIND);
   }

   public SetEditModeAction(final String editMode) {
      this();
      this.editMode = editMode;
   }

   public String getEditMode() { return editMode; }

   public void setEditMode(final String editMode) { this.editMode = editMode; }

}
