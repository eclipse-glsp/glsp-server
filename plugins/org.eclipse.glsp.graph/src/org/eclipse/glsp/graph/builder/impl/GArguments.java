/********************************************************************************
 * Copyright (c) 2020 EclipseSource and others.
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

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EMap;

public final class GArguments extends LinkedHashMap<String, Object> {
   private static final long serialVersionUID = 6774411049255781611L;

   public static final String KEY_EDGE_PADDING = "edgePadding";

   public static final String KEY_RADIUS_TOP_LEFT = "radiusTopLeft";
   public static final String KEY_RADIUS_TOP_RIGHT = "radiusTopRight";
   public static final String KEY_RADIUS_BOTTOM_RIGHT = "radiusBottomRight";
   public static final String KEY_RADIUS_BOTTOM_LEFT = "radiusBottomLeft";

   public GArguments() {}

   public GArguments(final Map<String, Object> values) {
      putAll(values);
   }

   public GArguments(final EMap<String, Object> values) {
      putAll(values.map());
   }

   public GArguments cornerRadius(final double radius) {
      cornerRadius(radius, radius, radius, radius);
      return this;
   }

   public GArguments cornerRadius(final double topLeftBottomRight, final double topRightBottomLeft) {
      cornerRadius(topLeftBottomRight, topRightBottomLeft, topLeftBottomRight, topRightBottomLeft);
      return this;
   }

   public GArguments cornerRadius(final double topLeft, final double topRight, final double bottomRight,
      final double bottomLeft) {
      put(KEY_RADIUS_TOP_LEFT, topLeft);
      put(KEY_RADIUS_TOP_RIGHT, topRight);
      put(KEY_RADIUS_BOTTOM_RIGHT, bottomRight);
      put(KEY_RADIUS_BOTTOM_LEFT, bottomLeft);
      return this;
   }

   public GArguments edgePadding(final double edgePadding) {
      put(KEY_EDGE_PADDING, edgePadding);
      return this;
   }

   public Double getEdgePadding() { return getDouble(KEY_EDGE_PADDING); }

   protected Double getDouble(final String key) {
      Object value = get(key);
      return value instanceof Double ? (Double) value : null;
   }

   protected Boolean getBoolean(final String key) {
      Object value = get(key);
      return value instanceof Boolean ? (Boolean) value : null;
   }

   protected String getString(final String key) {
      Object value = get(key);
      return value != null ? value.toString() : null;
   }
}
