/********************************************************************************
 * Copyright (c) 2019-2021 EclipseSource and others.
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
package org.eclipse.glsp.graph;

import java.util.Arrays;
import java.util.stream.Stream;

public final class GResizeLocation {
   public static final String TOP_LEFT = "top-left";
   public static final String TOP = "top";
   public static final String TOP_RIGHT = "top-right";
   public static final String RIGHT = "right";
   public static final String BOTTOM_RIGHT = "bottom-right";
   public static final String BOTTOM = "bottom";
   public static final String BOTTOM_LEFT = "bottom-left";
   public static final String LEFT = "left";

   public static final String[] CORNERS = new String[] { TOP_LEFT, TOP_RIGHT, BOTTOM_RIGHT, BOTTOM_LEFT };
   public static final String[] CROSS = new String[] { TOP, RIGHT, BOTTOM, LEFT };
   public static final String[] ALL = Stream.concat(Arrays.stream(CORNERS), Arrays.stream(CROSS))
      .toArray(String[]::new);

   private GResizeLocation() {
      // utility class
   }
}
