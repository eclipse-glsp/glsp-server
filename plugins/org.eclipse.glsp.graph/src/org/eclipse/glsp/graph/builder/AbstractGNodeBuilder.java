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

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.glsp.graph.GEdgePlacement;
import org.eclipse.glsp.graph.GNode;

public abstract class AbstractGNodeBuilder<T extends GNode, E extends AbstractGNodeBuilder<T, E>>
   extends GShapeElementBuilder<T, E> {

   protected String layout;
   protected Map<String, Object> layoutOptions;
   protected GEdgePlacement edgePlacement;

   public AbstractGNodeBuilder(final String type) {
      super(type);
   }

   public E addLayoutOptions(final Map<String, Object> layoutOptions) {
      if (this.layoutOptions == null) {
         this.layoutOptions = new LinkedHashMap<>();
      }
      this.layoutOptions.putAll(layoutOptions);
      return self();
   }

   public E layoutOptions(final Map<String, Object> layoutOptions) {
      addLayoutOptions(layoutOptions);
      return self();
   }

   public E edgePlacement(final GEdgePlacement edgePlacement) {
      this.edgePlacement = edgePlacement;
      return self();
   }

   public E layout(final String layout) {
      this.layout = layout;
      return self();
   }

   public E layout(final String layout, final Map<String, Object> layoutOptions) {
      this.layout = layout;
      addLayoutOptions(layoutOptions);
      return self();
   }

   @Override
   protected void setProperties(final T node) {
      super.setProperties(node);
      node.setLayout(layout);
      if (layout != null && layoutOptions != null) {
         node.getLayoutOptions().putAll(layoutOptions);
      }
      node.setEdgePlacement(edgePlacement);
   }
}
