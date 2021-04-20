/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
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

import java.util.Map;
import java.util.Optional;

public final class GArguments {
   public static final String KEY_EDGE_PADDING = "edgePadding";

   public static final String KEY_RADIUS_TOP_LEFT = "radiusTopLeft";
   public static final String KEY_RADIUS_TOP_RIGHT = "radiusTopRight";
   public static final String KEY_RADIUS_BOTTOM_RIGHT = "radiusBottomRight";
   public static final String KEY_RADIUS_BOTTOM_LEFT = "radiusBottomLeft";

   private GArguments() {}

   public static Map<String, Object> cornerRadius(final double radius) {
      return cornerRadius(radius, radius, radius, radius);
   }

   public static Map<String, Object> cornerRadius(final double topLeftBottomRight, final double topRightBottomLeft) {
      return cornerRadius(topLeftBottomRight, topRightBottomLeft, topLeftBottomRight, topRightBottomLeft);
   }

   public static Map<String, Object> cornerRadius(final double topLeft, final double topRight,
      final double bottomRight, final double bottomLeft) {
      return Map.of(KEY_RADIUS_TOP_LEFT, topLeft,
         KEY_RADIUS_TOP_RIGHT, topRight,
         KEY_RADIUS_BOTTOM_RIGHT, bottomRight,
         KEY_RADIUS_BOTTOM_LEFT, bottomLeft);
   }

   public static Map.Entry<String, Object> edgePadding(final double edgePadding) {
      return Map.entry(KEY_EDGE_PADDING, edgePadding);
   }

   public static Optional<Double> getEdgePadding(final Map<String, Object> arguments) {
      return getDouble(arguments, KEY_EDGE_PADDING);
   }

   public static Optional<Double> getDouble(final Map<String, Object> arguments, final String key) {
      return getNumber(arguments, key).map(Number::doubleValue);
   }

   public static Optional<Integer> getInteger(final Map<String, Object> arguments, final String key) {
      return getNumber(arguments, key).map(Number::intValue);
   }

   public static Optional<Number> getNumber(final Map<String, Object> arguments, final String key) {
      Object value = arguments.get(key);
      return value instanceof Number ? Optional.of((Number) value) : Optional.empty();
   }

   public static Optional<Boolean> getBoolean(final Map<String, Object> arguments, final String key) {
      Object value = arguments.get(key);
      return value instanceof Boolean ? Optional.of((Boolean) value) : Optional.empty();
   }

   public static Optional<String> getString(final Map<String, Object> arguments, final String key) {
      Object value = arguments.get(key);
      return value instanceof String ? Optional.of((String) value) : Optional.empty();
   }
}
