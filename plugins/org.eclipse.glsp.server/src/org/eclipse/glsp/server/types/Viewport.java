/********************************************************************************
 * Copyright (c) 2021-2025 EclipseSource and others.
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
package org.eclipse.glsp.server.types;

public class Viewport {

   private final Point scroll;
   private final double zoom;

   public Viewport() {
      this(0, 0, 1);
   }

   public Viewport(final double x, final double y, final double zoom) {
      super();
      this.scroll = new Point(x, y);
      this.zoom = zoom;
   }

   public double getScrollX() { return scroll.x; }

   public double getScrollY() { return scroll.y; }

   public double getZoom() { return zoom; }

   @Override
   public String toString() {
      StringBuilder result = new StringBuilder(getClass().getSimpleName());
      result.append(" [scroll: ");
      result.append(scroll);
      result.append(", zoom: ");
      result.append(zoom);
      result.append(']');
      return result.toString();
   }

   @SuppressWarnings("checkstyle:VisibilityModifier")
   class Point {
      final double x;
      final double y;

      Point(final double x, final double y) {
         super();
         this.x = x;
         this.y = y;
      }

      @Override
      public String toString() {
         StringBuilder result = new StringBuilder(getClass().getSimpleName());
         result.append(" [x: ");
         result.append(x);
         result.append(", y: ");
         result.append(y);
         result.append(']');
         return result.toString();
      }

   }

}
