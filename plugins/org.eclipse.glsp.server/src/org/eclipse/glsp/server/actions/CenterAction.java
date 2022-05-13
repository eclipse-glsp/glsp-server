/********************************************************************************
 * Copyright (c) 2019-2022 EclipseSource and others.
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

/**
 * Instructs the client to center one or more specified elements in the viewport.
 */
public class CenterAction extends Action {

   public static final String KIND = "center";

   private List<String> elementIds = new ArrayList<>();
   private boolean animate = true;
   private boolean retainZoom;

   public CenterAction() {
      super(KIND);
      this.elementIds = new ArrayList<>();
   }

   public CenterAction(final List<String> elementIDs, final boolean animate, final boolean retainZoom) {
      super(KIND);
      this.elementIds = elementIDs;
      this.animate = animate;
      this.retainZoom = retainZoom;
   }

   public List<String> getElementIDs() { return elementIds; }

   public void setElementIDs(final List<String> elementIDs) { this.elementIds = elementIDs; }

   public boolean isAnimate() { return animate; }

   public void setAnimate(final boolean animate) { this.animate = animate; }

   public List<String> getElementIds() { return elementIds; }

   public void setElementIds(final List<String> elementIds) { this.elementIds = elementIds; }

   public boolean isRetainZoom() { return retainZoom; }

   public void setRetainZoom(final boolean retainZoom) { this.retainZoom = retainZoom; }

}
