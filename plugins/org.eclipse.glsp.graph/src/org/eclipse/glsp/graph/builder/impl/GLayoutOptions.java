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

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EMap;

public class GLayoutOptions extends LinkedHashMap<String, Object> {

   private static final long serialVersionUID = -6422190064984638890L;

   public static final String KEY_H_ALIGN = "hAlign";
   public static final String KEY_V_ALIGN = "vAlign";
   public static final String KEY_RESIZE_CONTAINER = "resizeContainer";
   public static final String KEY_PADDING_LEFT = "paddingLeft";
   public static final String KEY_PADDING_RIGHT = "paddingRight";
   public static final String KEY_PADDING_TOP = "paddingTop";
   public static final String KEY_PADDING_BOTTOM = "paddingBottom";
   public static final String KEY_PADDING_FACTOR = "paddingFactor";
   public static final String KEY_H_GAP = "hGap";
   public static final String KEY_V_GAP = "vGap";
   public static final String KEY_MIN_WIDTH = "minWidth";
   public static final String KEY_MIN_HEIGHT = "minHeight";

   public GLayoutOptions() {}

   public GLayoutOptions(final Map<String, Object> values) {
      putAll(values);
   }

   public GLayoutOptions(final EMap<String, Object> values) {
      putAll(values.map());
   }

   public GLayoutOptions paddingLeft(final Number paddingLeft) {
      putOrRemove(KEY_PADDING_LEFT, paddingLeft);
      return this;
   }

   public Double getPaddingLeft() { return getDouble(KEY_PADDING_LEFT); }

   public GLayoutOptions paddingRight(final Number paddingRight) {
      putOrRemove(KEY_PADDING_RIGHT, paddingRight);
      return this;
   }

   public Double getPaddingRight() { return getDouble(KEY_PADDING_RIGHT); }

   public GLayoutOptions paddingTop(final Number paddingTop) {
      putOrRemove(KEY_PADDING_TOP, paddingTop);
      return this;
   }

   public Double getPaddingTop() { return getDouble(KEY_PADDING_TOP); }

   public GLayoutOptions paddingBottom(final Double paddingBottom) {
      putOrRemove(KEY_PADDING_BOTTOM, paddingBottom);
      return this;
   }

   public Double getPaddingBottom() { return getDouble(KEY_PADDING_BOTTOM); }

   public GLayoutOptions paddingFactor(final Number paddingFactor) {
      putOrRemove(KEY_PADDING_FACTOR, paddingFactor);
      return this;
   }

   public Double getPaddingFactor() { return getDouble(KEY_PADDING_FACTOR); }

   public GLayoutOptions resizeContainer(final boolean resizeContainer) {
      putOrRemove(KEY_RESIZE_CONTAINER, resizeContainer);
      return this;
   }

   public Boolean getResizeContainer() { return getBoolean(KEY_RESIZE_CONTAINER); }

   public GLayoutOptions vGap(final Number vGap) {
      putOrRemove(KEY_V_GAP, vGap);
      return this;
   }

   public String getVGap() { return getString(KEY_V_GAP); }

   public GLayoutOptions hGap(final Number hGap) {
      putOrRemove(KEY_H_GAP, hGap);
      return this;
   }

   public String getHGap() { return getString(KEY_H_GAP); }

   public GLayoutOptions vAlign(final String vAlign) {
      putOrRemove(KEY_V_ALIGN, vAlign);
      return this;
   }

   public String getVAlign() { return getString(KEY_V_ALIGN); }

   public GLayoutOptions hAlign(final String hAlign) {
      putOrRemove(KEY_H_ALIGN, hAlign);
      return this;
   }

   public String getHAlign() { return getString(KEY_H_ALIGN); }

   public GLayoutOptions minWidth(final Number minWidth) {
      putOrRemove(KEY_MIN_WIDTH, minWidth);
      return this;
   }

   public Double getMinWidth() { return getDouble(KEY_MIN_WIDTH); }

   public GLayoutOptions minHeight(final Number minHeight) {
      putOrRemove(KEY_MIN_HEIGHT, minHeight);
      return this;
   }

   public Double getMinHeight() { return getDouble(KEY_MIN_HEIGHT); }

   protected void putOrRemove(final String key, final Object value) {
      if (value != null) {
         put(key, value);
      } else {
         remove(key);
      }
   }

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
