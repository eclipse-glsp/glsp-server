/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
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

import java.util.ArrayList;
import java.util.List;

public class SetUIExtensionVisibilityAction extends Action {
   public static final String ID = "setUIExtensionVisibility";

   protected String extensionId;
   protected boolean visible;
   protected List<String> contextElementsId = new ArrayList<>();

   public SetUIExtensionVisibilityAction() {
      super(ID);
   }

   public SetUIExtensionVisibilityAction(final String extensionId, final boolean visible) {
      this();
      this.extensionId = extensionId;
      this.visible = visible;
   }

   public String getExtensionId() { return extensionId; }

   public void setExtensionId(final String extensionId) { this.extensionId = extensionId; }

   public boolean isVisible() { return visible; }

   public void setVisible(final boolean visible) { this.visible = visible; }

   public List<String> getContextElementsId() { return contextElementsId; }

   public void setContextElementsId(final List<String> contextElementsId) {
      this.contextElementsId = contextElementsId;
   }

}
