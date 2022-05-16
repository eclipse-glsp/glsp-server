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
import java.util.Optional;

/**
 * Instructs the client to fit one or more specified elements into viewport.
 *
 * This will adjust the viewport by panning and zooming so that the specified elements
 * are visible and take the full space in the viewport.
 */
public class FitToScreenAction extends Action {

   public static final String KIND = "fit";

   private List<String> elementIds = new ArrayList<>();
   private double padding;
   private double maxZoom = 9999;
   private boolean animate = true;

   public FitToScreenAction() {
      super(KIND);
   }

   public FitToScreenAction(final String kind, final List<String> elementIds, final double padding,
      final double maxZoom, final boolean animate) {
      super(kind);
      this.elementIds = elementIds;
      this.padding = padding;
      this.maxZoom = maxZoom;
      this.animate = animate;
   }

   public List<String> getElementIds() { return elementIds; }

   public void setElementIds(final List<String> elementIds) { this.elementIds = elementIds; }

   public Optional<Double> getPadding() { return Optional.ofNullable(padding); }

   public void setPadding(final double padding) { this.padding = padding; }

   public Optional<Double> getMaxZoom() { return Optional.ofNullable(maxZoom); }

   public void setMaxZoom(final double maxZoom) { this.maxZoom = maxZoom; }

   public boolean isAnimate() { return animate; }

   public void setAnimate(final boolean animate) { this.animate = animate; }

}
