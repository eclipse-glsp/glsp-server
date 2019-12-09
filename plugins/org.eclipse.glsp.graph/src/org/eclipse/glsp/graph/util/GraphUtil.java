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
package org.eclipse.glsp.graph.util;

import org.eclipse.glsp.graph.GBounds;
import org.eclipse.glsp.graph.GDimension;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.GraphFactory;

public final class GraphUtil {

   private GraphUtil() {}

   public static GBounds bounds(double x, double y, double width, double height) {
      GBounds bounds = GraphFactory.eINSTANCE.createGBounds();
      bounds.setX(x);
      bounds.setY(y);
      bounds.setWidth(width);
      bounds.setHeight(height);
      return bounds;
   }

   public static GBounds copy(GBounds toCopy) {
      return bounds(toCopy.getX(), toCopy.getY(), toCopy.getWidth(), toCopy.getHeight());
   }

   public static GPoint point(double x, double y) {
      GPoint point = GraphFactory.eINSTANCE.createGPoint();
      point.setX(x);
      point.setY(y);
      return point;
   }

   public static GPoint copy(GPoint toCopy) {
      return point(toCopy.getX(), toCopy.getY());
   }

   public static GDimension dimension(double width, double height) {
      GDimension dimension = GraphFactory.eINSTANCE.createGDimension();
      dimension.setWidth(width);
      dimension.setHeight(height);
      return dimension;
   }

   public static GDimension copy(GDimension toCopy) {
      return dimension(toCopy.getWidth(), toCopy.getHeight());
   }
}
