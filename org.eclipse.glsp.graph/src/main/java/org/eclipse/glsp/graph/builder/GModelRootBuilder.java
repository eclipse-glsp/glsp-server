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
package org.eclipse.glsp.graph.builder;

import org.eclipse.glsp.graph.GBounds;
import org.eclipse.glsp.graph.GDimension;
import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.builder.impl.GBoundsBuilder;

public abstract class GModelRootBuilder<T extends GModelRoot, E extends GModelRootBuilder<T, E>>
   extends GModelElementBuilder<T, E> {

   protected GBounds canvasBounds;
   protected int revision;

   public GModelRootBuilder(String type) {
      super(type);
   }

   public E canvasBounds(GBounds canvasBounds) {
      this.canvasBounds = canvasBounds;
      return self();
   }

   public E canvasBounds(GDimension dimension, GPoint position) {
      this.canvasBounds = new GBoundsBuilder() //
         .dimension(dimension) //
         .position(position) //
         .build();
      return self();
   }

   public E canvasBounds(double x, double y, double width, double height) {
      this.canvasBounds = new GBoundsBuilder() //
         .x(x) //
         .y(y) //
         .width(width) //
         .height(height) //
         .build();
      return self();
   }

   public E revision(int revision) {
      this.revision = revision;
      return self();
   }

   @Override
   protected void setProperties(T element) {
      super.setProperties(element);
      element.setCanvasBounds(canvasBounds);
      element.setRevision(revision);
   }

}
