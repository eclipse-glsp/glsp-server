/********************************************************************************
 * Copyright (c) 2019-2025 EclipseSource and others.
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

   public static GBounds bounds(final double x, final double y, final double width, final double height) {
      GBounds bounds = GraphFactory.eINSTANCE.createGBounds();
      bounds.setX(x);
      bounds.setY(y);
      bounds.setWidth(width);
      bounds.setHeight(height);
      return bounds;
   }

   public static GBounds copy(final GBounds toCopy) {
      return bounds(toCopy.getX(), toCopy.getY(), toCopy.getWidth(), toCopy.getHeight());
   }

   public static GPoint point(final double x, final double y) {
      GPoint point = GraphFactory.eINSTANCE.createGPoint();
      point.setX(x);
      point.setY(y);
      return point;
   }

   public static GPoint copy(final GPoint toCopy) {
      return point(toCopy.getX(), toCopy.getY());
   }

   public static int compare(final GPoint left, final GPoint right) {
      int xCompare = Double.compare(left.getX(), right.getX());
      return xCompare != 0 ? xCompare : Double.compare(left.getY(), right.getY());
   }

   public static boolean equals(final GPoint left, final GPoint right) {
      return compare(left, right) == 0;
   }

   public static GDimension dimension(final double width, final double height) {
      GDimension dimension = GraphFactory.eINSTANCE.createGDimension();
      dimension.setWidth(width);
      dimension.setHeight(height);
      return dimension;
   }

   public static GDimension copy(final GDimension toCopy) {
      return dimension(toCopy.getWidth(), toCopy.getHeight());
   }

   public static int compare(final GDimension left, final GDimension right) {
      int widthCompare = Double.compare(left.getWidth(), right.getWidth());
      return widthCompare != 0 ? widthCompare : Double.compare(left.getHeight(), right.getHeight());
   }

   public static boolean equals(final GDimension left, final GDimension right) {
      return compare(left, right) == 0;
   }

}
