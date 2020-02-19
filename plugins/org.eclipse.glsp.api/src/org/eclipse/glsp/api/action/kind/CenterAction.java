/********************************************************************************
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
 ********************************************************************************/
package org.eclipse.glsp.api.action.kind;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.glsp.api.action.Action;

public class CenterAction extends Action {

   private List<String> elementIds = new ArrayList<>();
   private boolean animate = true;
   private boolean retainZoom;

   public CenterAction() {
      super(Action.Kind.CENTER);
   }

   public CenterAction(final List<String> elementIDs, final boolean animate, final boolean retainZoom) {
      this();
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
