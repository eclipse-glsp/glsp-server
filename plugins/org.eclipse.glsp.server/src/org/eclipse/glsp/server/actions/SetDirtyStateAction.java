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

public class SetDirtyStateAction extends Action {
   public static final String ID = "setDirtyState";

   private boolean isDirty;
   private String reason;

   public SetDirtyStateAction() {
      super(ID);
   }

   public SetDirtyStateAction(final boolean isDirty) {
      this();
      this.isDirty = isDirty;
   }

   public SetDirtyStateAction(final boolean isDirty, final String reason) {
      this(isDirty);
      this.reason = reason;
   }

   public boolean isDirty() { return isDirty; }

   public void setDirty(final boolean isDirty) { this.isDirty = isDirty; }

   public String getReason() { return reason; }

   public void setReason(final String reason) { this.reason = reason; }

   public static class Reason {
      public static final String OPERATION = "operation";
      public static final String UNDO = "undo";
      public static final String REDO = "redo";
      public static final String SAVE = "save";
      public static final String EXTERNAL = "external";
   }

}
