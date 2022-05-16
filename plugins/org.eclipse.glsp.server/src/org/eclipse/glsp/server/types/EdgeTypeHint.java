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
package org.eclipse.glsp.server.types;

import java.util.List;

/**
 * {@link ElementTypeHint Element type hints} for edges.
 */
public class EdgeTypeHint extends ElementTypeHint {

   private boolean routable;
   private List<String> sourceElementTypeIds;
   private List<String> targetElementTypeIds;

   public EdgeTypeHint() {}

   public EdgeTypeHint(final String elementTypeId, final boolean repositionable, final boolean deletable,
      final boolean routable,
      final List<String> sourceElementTypeIds, final List<String> targetElementTypeIds) {
      super(elementTypeId, repositionable, deletable);
      this.routable = routable;
      this.sourceElementTypeIds = sourceElementTypeIds;
      this.targetElementTypeIds = targetElementTypeIds;
   }

   public boolean isRoutable() { return routable; }

   public void setRoutable(final boolean routable) { this.routable = routable; }

   public List<String> getSourceElementTypeIds() { return sourceElementTypeIds; }

   public void setSourceElementTypeIds(final List<String> sourceElementTypeIds) {
      this.sourceElementTypeIds = sourceElementTypeIds;
   }

   public List<String> getTargetElementTypeIds() { return targetElementTypeIds; }

   public void setTargetElementTypeIds(final List<String> targetElementTypeIds) {
      this.targetElementTypeIds = targetElementTypeIds;
   }

}
