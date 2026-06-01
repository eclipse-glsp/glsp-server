/********************************************************************************
 * Copyright (c) 2019-2026 EclipseSource and others.
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
package org.eclipse.glsp.graph.builder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.glsp.graph.GDimension;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.GShapeElement;
import org.eclipse.glsp.graph.util.GraphUtil;

public abstract class GShapeElementBuilder<T extends GShapeElement, E extends GShapeElementBuilder<T, E>>
   extends GModelElementBuilder<T, E> {

   protected GDimension size;
   protected GPoint position;
   protected List<String> resizeLocations;
   protected Map<String, Object> layoutOptions;

   public GShapeElementBuilder(final String type) {
      super(type);
   }

   public E size(final GDimension size) {
      this.size = size;
      return self();
   }

   public E size(final double width, final double height) {
      return size(GraphUtil.dimension(width, height));
   }

   public E position(final GPoint position) {
      this.position = position;
      return self();
   }

   public E position(final double x, final double y) {
      return position(GraphUtil.point(x, y));
   }

   public E resizeLocations(final String... locations) {
      this.resizeLocations = List.of(locations);
      return self();
   }

   public E layoutOptions(final Map<String, Object> layoutOptions) {
      addLayoutOptions(layoutOptions);
      return self();
   }

   public E addLayoutOptions(final Map<String, Object> layoutOptions) {
      if (this.layoutOptions == null) {
         this.layoutOptions = new LinkedHashMap<>();
      }
      this.layoutOptions.putAll(layoutOptions);
      return self();
   }

   @Override
   protected void setProperties(final T element) {
      super.setProperties(element);
      element.setSize(size);
      element.setPosition(position);
      if (this.resizeLocations != null) {
         element.getResizeLocations().addAll(this.resizeLocations);
      }
      if (this.layoutOptions != null) {
         element.getLayoutOptions().putAll(this.layoutOptions);
      }
   }

}
