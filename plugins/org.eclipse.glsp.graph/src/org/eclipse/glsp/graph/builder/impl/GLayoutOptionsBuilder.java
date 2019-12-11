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

import org.eclipse.glsp.graph.builder.GBuilder;

/**
 * Deprecated, you can use {@link GLayoutOptions} directly.
 */
@Deprecated
public class GLayoutOptionsBuilder extends GBuilder<GLayoutOptions> {

   private Double paddingLeft;
   private Double paddingRight;
   private Double paddingTop;
   private Double paddingBottom;
   private Double paddingFactor;
   private Boolean resizeContainer;
   private Double vGap;
   private Double hGap;
   private String vAlign;
   private String hAlign;

   private Double minWidth;
   private Double minHeight;

   public GLayoutOptionsBuilder paddingLeft(final Double paddingLeft) {
      this.paddingLeft = paddingLeft;
      return this;
   }

   public GLayoutOptionsBuilder paddingRight(final Double paddingRight) {
      this.paddingRight = paddingRight;
      return this;
   }

   public GLayoutOptionsBuilder paddingTop(final Double paddingTop) {
      this.paddingTop = paddingTop;
      return this;
   }

   public GLayoutOptionsBuilder paddingBottom(final Double paddingBottom) {
      this.paddingBottom = paddingBottom;
      return this;
   }

   public GLayoutOptionsBuilder paddingFactor(final Double paddingFactor) {
      this.paddingFactor = paddingFactor;
      return this;
   }

   public GLayoutOptionsBuilder resizeContainer(final boolean resizeContainer) {
      this.resizeContainer = resizeContainer;
      return this;
   }

   public GLayoutOptionsBuilder vGap(final Double vGap) {
      this.vGap = vGap;
      return this;
   }

   public GLayoutOptionsBuilder hGap(final Double hGap) {
      this.hGap = hGap;
      return this;
   }

   public GLayoutOptionsBuilder vAlign(final String vAlign) {
      this.vAlign = vAlign;
      return this;
   }

   public GLayoutOptionsBuilder hAlign(final String hAlign) {
      this.hAlign = hAlign;
      return this;
   }

   public GLayoutOptionsBuilder minWidth(final Double minWidth) {
      this.minWidth = minWidth;
      return this;
   }

   public GLayoutOptionsBuilder minHeight(final Double minHeight) {
      this.minHeight = minHeight;
      return this;
   }

   @Override
   protected GLayoutOptions instantiate() {
      return new GLayoutOptions();
   }

   @Override
   protected void setProperties(final GLayoutOptions options) {
      options.hAlign(hAlign);
      options.vAlign(vAlign);
      options.resizeContainer(resizeContainer);
      options.paddingLeft(paddingLeft);
      options.paddingRight(paddingRight);
      options.paddingTop(paddingTop);
      options.paddingBottom(paddingBottom);
      options.paddingFactor(paddingFactor);
      options.vGap(vGap);
      options.hGap(hGap);
      options.minWidth(minWidth);
      options.minHeight(minHeight);
   }
}
