/********************************************************************************
 * Copyright (c) 2023 EclipseSource and others.
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
package org.eclipse.glsp.server.features.typehints;

import java.util.Optional;

import org.eclipse.glsp.server.actions.RequestAction;
import org.eclipse.glsp.server.types.EdgeTypeHint;

/**
 * A {@link RequestAction} used to check the validity of an edge being created. This is used
 * to update creation feedback on the client side, for edges configured with a dynamic {@link EdgeTypeHint}.
 *
 * @see EdgeTypeHint#isDynamic()
 * @see CheckEdgeResultAction
 */
public class RequestCheckEdgeAction extends RequestAction<CheckEdgeResultAction> {

   public static final String KIND = "requestCheckEdge";

   /**
    * The element type of the edge whose target should be checked.
    */
   private String edgeType;

   /**
    * The ID of the source element of the edge whose target should be checked.
    */
   private String sourceElementId;
   /**
    * The (optional) ID of the target element of the edge whose target should be checked.
    */
   private String targetElementId;

   public RequestCheckEdgeAction() {
      super(KIND);
   }

   public String getEdgeType() { return edgeType; }

   public void setEdgeType(final String edgeType) { this.edgeType = edgeType; }

   public String getSourceElementId() { return sourceElementId; }

   public void setSourceElementId(final String sourceElementId) { this.sourceElementId = sourceElementId; }

   public Optional<String> getTargetElementId() { return Optional.ofNullable(targetElementId); }

   public void setTargetElementId(final String targetElementId) { this.targetElementId = targetElementId; }

}
