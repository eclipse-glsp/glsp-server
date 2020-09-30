/*******************************************************************************
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
 ******************************************************************************/
package org.eclipse.glsp.server.diagram;

import java.util.List;

import org.eclipse.glsp.server.actions.Action;

public class SetTypeHintsAction extends Action {

   public static final String ID = "setTypeHints";

   private List<ShapeTypeHint> shapeHints;
   private List<EdgeTypeHint> edgeHints;

   public SetTypeHintsAction() {
      super(ID);
   }

   public SetTypeHintsAction(final List<ShapeTypeHint> nodeHints, final List<EdgeTypeHint> edgeHints) {
      this();
      this.shapeHints = nodeHints;
      this.edgeHints = edgeHints;
   }

   public List<ShapeTypeHint> getNodeHints() { return shapeHints; }

   public void setNodeHints(final List<ShapeTypeHint> nodeHints) { this.shapeHints = nodeHints; }

   public List<EdgeTypeHint> getEdgeHints() { return edgeHints; }

   public void setEdgeHints(final List<EdgeTypeHint> edgeHints) { this.edgeHints = edgeHints; }

}
