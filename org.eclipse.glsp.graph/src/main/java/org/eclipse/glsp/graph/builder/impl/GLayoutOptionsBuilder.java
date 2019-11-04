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
package org.eclipse.glsp.graph.builder.impl;

import org.eclipse.glsp.graph.GLayoutOptions;
import org.eclipse.glsp.graph.GraphFactory;
import org.eclipse.glsp.graph.builder.GBuilder;

public class GLayoutOptionsBuilder extends GBuilder<GLayoutOptions> {

   private Double paddingLeft;
   private Double paddingRight;
   private Double paddingTop;
   private Double paddingBottom;
   private Double paddingFactor;
   private boolean resizeContainer;
   private Double vGap;
   private Double hGap;
   private String vAlign;
   private String hAlign;

   private Double minWidth;
   private Double minHeight;

   public GLayoutOptionsBuilder paddingLeft(Double paddingLeft) {
      this.paddingLeft = paddingLeft;
      return this;
   }

   public GLayoutOptionsBuilder paddingRight(Double paddingRight) {
      this.paddingRight = paddingRight;
      return this;
   }

   public GLayoutOptionsBuilder paddingTop(Double paddingTop) {
      this.paddingTop = paddingTop;
      return this;
   }

   public GLayoutOptionsBuilder paddingBottom(Double paddingBottom) {
      this.paddingBottom = paddingBottom;
      return this;
   }

   public GLayoutOptionsBuilder paddingFactor(Double paddingFactor) {
      this.paddingFactor = paddingFactor;
      return this;
   }

   public GLayoutOptionsBuilder resizeContainer(boolean resizeContainer) {
      this.resizeContainer = resizeContainer;
      return this;
   }

   public GLayoutOptionsBuilder vGap(Double vGap) {
      this.vGap = vGap;
      return this;
   }

   public GLayoutOptionsBuilder hGap(Double hGap) {
      this.hGap = hGap;
      return this;
   }

   public GLayoutOptionsBuilder vAlign(String vAlign) {
      this.vAlign = vAlign;
      return this;
   }

   public GLayoutOptionsBuilder hAlign(String hAlign) {
      this.hAlign = hAlign;
      return this;
   }

   public GLayoutOptionsBuilder minWidth(Double minWidth) {
      this.minWidth = minWidth;
      return this;
   }

   public GLayoutOptionsBuilder minHeight(Double minHeight) {
      this.minHeight = minHeight;
      return this;
   }

   @Override
   protected GLayoutOptions instantiate() {
      return GraphFactory.eINSTANCE.createGLayoutOptions();
   }

   @Override
   protected void setProperties(GLayoutOptions element) {
      element.setPaddingLeft(paddingLeft);
      element.setPaddingRight(paddingRight);
      element.setPaddingTop(paddingTop);
      element.setPaddingBottom(paddingBottom);
      element.setPaddingFactor(paddingFactor);
      element.setResizeContainer(resizeContainer);
      element.setVGap(vGap);
      element.setHGap(hGap);
      element.setVAlign(vAlign);
      element.setHAlign(hAlign);
      element.setMinHeight(minHeight);
      element.setMinWidth(minWidth);
   }

}
