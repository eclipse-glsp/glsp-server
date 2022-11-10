/********************************************************************************
 * Copyright (c) 2019-2022 EclipseSource and others.
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
package org.eclipse.glsp.server.features.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.types.ElementAndAlignment;
import org.eclipse.glsp.server.types.ElementAndBounds;
import org.eclipse.glsp.server.types.ElementAndRoutingPoints;

public class ComputedBoundsAction extends Action {

   public static final String KIND = "computedBounds";

   private List<ElementAndBounds> bounds;
   private List<ElementAndAlignment> alignments;
   private List<ElementAndRoutingPoints> routes;
   private int revision;

   public ComputedBoundsAction() {
      super(KIND);
      this.bounds = new ArrayList<>();
      this.alignments = new ArrayList<>();
      this.routes = new ArrayList<>();
   }

   public ComputedBoundsAction(final List<ElementAndBounds> bounds, final List<ElementAndAlignment> alignments,
      final List<ElementAndRoutingPoints> route, final int revision) {
      super(KIND);
      this.bounds = bounds;
      this.alignments = alignments;
      this.routes = route;
      this.revision = revision;
   }

   public List<ElementAndBounds> getBounds() { return bounds; }

   public void setBounds(final List<ElementAndBounds> bounds) { this.bounds = bounds; }

   public List<ElementAndAlignment> getAlignments() { return alignments; }

   public void setAlignments(final List<ElementAndAlignment> alignments) { this.alignments = alignments; }

   public List<ElementAndRoutingPoints> getRoutes() { return routes; }

   public void setRoutes(final List<ElementAndRoutingPoints> routes) { this.routes = routes; }

   public Optional<Integer> getRevision() { return Optional.ofNullable(revision); }

   public void setRevision(final int revision) { this.revision = revision; }

}
