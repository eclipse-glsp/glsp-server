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

public final class GConstants {

   public static final class RouterKind {
      public static final String POLYLINE = "polyline";
      public static final String MANHATTAN = "manhattan";
   }

   public static final class EdgeSide {
      public static final String LEFT = "left";
      public static final String RIGHT = "right";
      public static final String TOP = "top";
      public static final String BOTTOM = "bottom";
      public static final String ON = "on";
   }

   public static final class Layout {
      public static final String VBOX = "vbox";
      public static final String HBOX = "hbox";
      public static final String STACK = "stack";
      public static final String FREEFORM = "freeform";
   }

   public static final class VAlign {
      public static final String TOP = "top";
      public static final String CENTER = "center";
      public static final String BOTTOM = "bottom";
   }

   public static final class HAlign {
      public static final String LEFT = "left";
      public static final String CENTER = "center";
      public static final String RIGHT = "right";
   }

   private GConstants() {

   }
}
