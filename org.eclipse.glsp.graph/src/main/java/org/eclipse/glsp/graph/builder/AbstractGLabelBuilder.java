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

import org.eclipse.glsp.graph.GEdgePlacement;
import org.eclipse.glsp.graph.GLabel;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.util.GraphUtil;

public abstract class AbstractGLabelBuilder<T extends GLabel, E extends AbstractGLabelBuilder<T, E>>
   extends GShapeElementBuilder<T, E> {
   protected GPoint alignment;
   protected GEdgePlacement edgePlacement;
   protected String text;

   public AbstractGLabelBuilder(String type) {
      super(type);
   }

   public E alignment(GPoint alignment) {
      this.alignment = alignment;
      return self();
   }

   public E alignment(double x, double y) {
      return alignment(GraphUtil.point(x, y));
   }

   public E edgePlacement(GEdgePlacement edgePlacement) {
      this.edgePlacement = edgePlacement;
      return self();
   }

   public E text(String text) {
      this.text = text;
      return self();
   }

   @Override
   public void setProperties(T label) {
      super.setProperties(label);
      label.setAlignment(alignment);
      label.setEdgePlacement(edgePlacement);
      label.setText(text);
   }

}
