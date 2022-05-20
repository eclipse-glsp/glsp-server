/********************************************************************************
 * Copyright (c) 2022 EclipseSource and others.
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

import org.eclipse.glsp.graph.GDimension;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.GShapePreRenderedElement;
import org.eclipse.glsp.graph.util.GraphUtil;

public abstract class AbstractGShapePrenderedElementBuilder<T extends GShapePreRenderedElement, E extends AbstractGShapePrenderedElementBuilder<T, E>>
   extends AbstractGPreRenderedElementBuilder<T, E> {

   protected GDimension size;
   protected GPoint position;

   public AbstractGShapePrenderedElementBuilder(final String type) {
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

   @Override
   protected void setProperties(final T element) {
      super.setProperties(element);
      element.setSize(size);
      element.setPosition(position);
   }

}
