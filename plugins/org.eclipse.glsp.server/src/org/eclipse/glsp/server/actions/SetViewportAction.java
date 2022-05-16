/********************************************************************************
 * Copyright (c) 2021-2022 EclipseSource and others.
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

import org.eclipse.glsp.server.types.Viewport;

/**
 * Instructs the client to set a specific {@link Viewport} (zoom level and scroll coordinates).
 */
public class SetViewportAction extends Action {

   public static final String KIND = "viewport";

   private String elementId = "EMTPY";
   private Viewport newViewport = new Viewport();
   private boolean animate;

   public SetViewportAction() {
      super(KIND);
   }

   public SetViewportAction(final String elementId, final Viewport viewport, final boolean animate) {
      this();
      this.elementId = elementId;
      this.newViewport = viewport;
      this.animate = animate;
   }

   public String getElementId() { return elementId; }

   public void setElementId(final String elementId) { this.elementId = elementId; }

   public Viewport getNewViewport() { return newViewport; }

   public void setNewViewport(final Viewport newViewport) { this.newViewport = newViewport; }

   public boolean isAnimate() { return animate; }

   public void setAnimate(final boolean animate) { this.animate = animate; }

}
