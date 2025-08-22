/*******************************************************************************
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
 ******************************************************************************/
package org.eclipse.glsp.server.operations;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.glsp.graph.GBounds;
import org.eclipse.glsp.server.types.Viewport;

public class LayoutOperation extends Operation {

   public static final String KIND = "layout";

   private List<String> elementIds;

   private GBounds canvasBounds;

   private Viewport viewport;

   public LayoutOperation() {
      this(new ArrayList<>());
   }

   public LayoutOperation(final List<String> elementIds) {
      super(KIND);
      this.elementIds = elementIds;
   }

   public LayoutOperation(final List<String> elementIds, final GBounds canvasBounds, final Viewport viewport) {
      this(elementIds);
      this.canvasBounds = canvasBounds;
      this.viewport = viewport;
   }

   public List<String> getElementIds() { return elementIds; }

   public void setElementIds(final List<String> elementIds) { this.elementIds = elementIds; }

   public GBounds getCanvasBounds() { return canvasBounds; }

   public void setCanvasBounds(final GBounds canvasBounds) { this.canvasBounds = canvasBounds; }

   public Viewport getViewport() { return viewport; }

   public void setViewport(final Viewport viewport) { this.viewport = viewport; }

}
