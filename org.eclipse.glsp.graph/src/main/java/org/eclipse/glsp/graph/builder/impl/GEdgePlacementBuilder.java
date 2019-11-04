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

import org.eclipse.glsp.graph.GEdgePlacement;
import org.eclipse.glsp.graph.GraphFactory;
import org.eclipse.glsp.graph.builder.GBuilder;

public class GEdgePlacementBuilder extends GBuilder<GEdgePlacement> {

   private double position;
   private double offset;
   private String side;
   private boolean rotate;

   @Override
   protected GEdgePlacement instantiate() {
      return GraphFactory.eINSTANCE.createGEdgePlacement();
   }

   public GEdgePlacementBuilder position(double position) {

      this.position = Math.min(1, Math.max(0, position));
      return this;
   }

   public GEdgePlacementBuilder offset(double offset) {
      this.offset = offset;
      return this;
   }

   public GEdgePlacementBuilder side(String side) {
      this.side = side;
      return this;
   }

   public GEdgePlacementBuilder rotate(boolean rotate) {
      this.rotate = rotate;
      return this;
   }

   @Override
   protected void setProperties(GEdgePlacement placement) {
      placement.setPosition(position);
      placement.setOffset(offset);
      placement.setSide(side);
      placement.setRotate(rotate);
   }

}
