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

import org.eclipse.glsp.graph.GBounds;
import org.eclipse.glsp.graph.GDimension;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.GraphFactory;
import org.eclipse.glsp.graph.builder.GBuilder;

public class GBoundsBuilder extends GBuilder<GBounds> {
   private double x;
   private double y;
   private double width;
   private double height;

   public GBoundsBuilder x(double x) {
      this.x = x;
      return this;
   }

   public GBoundsBuilder y(double y) {
      this.y = y;
      return this;
   }

   public GBoundsBuilder width(double width) {
      this.width = width;
      return this;
   }

   public GBoundsBuilder height(double height) {
      this.height = height;
      return this;
   }

   public GBoundsBuilder position(GPoint position) {
      this.x = position.getX();
      this.y = position.getY();
      return this;
   }

   public GBoundsBuilder dimension(GDimension dimension) {
      this.height = dimension.getHeight();
      this.width = dimension.getWidth();
      return this;
   }

   @Override
   protected GBounds instantiate() {
      return GraphFactory.eINSTANCE.createGBounds();
   }

   @Override
   protected void setProperties(GBounds element) {
      element.setX(x);
      element.setY(y);
      element.setWidth(width);
      element.setHeight(height);
   }

}
