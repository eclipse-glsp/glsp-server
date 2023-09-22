/********************************************************************************
 * Copyright (c) 2019-2023 EclipseSource and others.
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
package org.eclipse.glsp.server.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.glsp.server.features.typehints.RequestCheckEdgeAction;

/**
 * {@link ElementTypeHint Element type hints} for edges.
 */
public class EdgeTypeHint extends ElementTypeHint {

   /**
    * Specifies whether the routing points of the edge can be changed
    * i.e. edited by the user.
    */
   private boolean routable;
   /**
    * Allowed source element types for this edge type.
    * If not defined any element can be used as source element for this edge.
    */
   private List<String> sourceElementTypeIds;
   /**
    * Allowed target element types for this edge type
    * If not defined any element can be used as target element for this edge.
    */
   private List<String> targetElementTypeIds;
   /**
    * Indicates whether this type hint is dynamic or not. Dynamic edge type hints
    * require an additional runtime check before creating an edge, when checking
    * source and target element types is not sufficient.
    *
    * @see {@link RequestCheckEdgeAction}
    */
   private boolean dynamic;

   public EdgeTypeHint() {}

   public EdgeTypeHint(final String elementTypeId, final boolean repositionable, final boolean deletable,
      final boolean routable,
      final List<String> sourceElementTypeIds, final List<String> targetElementTypeIds) {
      this(elementTypeId, repositionable, deletable, routable, false, sourceElementTypeIds, targetElementTypeIds);
   }

   public EdgeTypeHint(final String elementTypeId, final boolean repositionable, final boolean deletable,
      final boolean routable, final boolean dynamic,
      final List<String> sourceElementTypeIds, final List<String> targetElementTypeIds) {
      super(elementTypeId, repositionable, deletable);
      this.routable = routable;
      this.sourceElementTypeIds = sourceElementTypeIds;
      this.targetElementTypeIds = targetElementTypeIds;
   }

   public boolean isRoutable() { return routable; }

   public void setRoutable(final boolean routable) { this.routable = routable; }

   public Optional<List<String>> getSourceElementTypeIds() { return Optional.ofNullable(sourceElementTypeIds); }

   public void setSourceElementTypeIds(final List<String> sourceElementTypeIds) {
      this.sourceElementTypeIds = sourceElementTypeIds;
   }

   public void addSourceElementTypeId(final String... elementTypeIds) {
      if (this.sourceElementTypeIds == null) {
         this.sourceElementTypeIds = new ArrayList<>();
      }
      Stream.of(elementTypeIds).forEach(id -> this.sourceElementTypeIds.add(id));
   }

   public Optional<List<String>> getTargetElementTypeIds() { return Optional.ofNullable(targetElementTypeIds); }

   public void setTargetElementTypeIds(final List<String> targetElementTypeIds) {
      this.targetElementTypeIds = targetElementTypeIds;
   }

   public void addTargetElementTypeId(final String... elementTypeIds) {
      if (this.targetElementTypeIds == null) {
         this.targetElementTypeIds = new ArrayList<>();
      }
      Stream.of(elementTypeIds).forEach(id -> this.targetElementTypeIds.add(id));
   }

   /**
    * Indicates whether this type hint is dynamic or not. Dynamic edge type hints
    * require an additional runtime check before creating an edge, when checking
    * source and target element types is not sufficient.
    *
    * @see RequestCheckEdgeAction
    */
   public boolean isDynamic() { return dynamic; }

   public void setDynamic(final boolean isDynamic) { this.dynamic = isDynamic; }

}
