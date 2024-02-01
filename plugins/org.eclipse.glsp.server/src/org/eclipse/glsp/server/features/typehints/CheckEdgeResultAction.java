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

import org.eclipse.glsp.server.actions.ResponseAction;

/**
 * Response Action for a {@link RequestCheckEdgeAction}. It provides
 * a boolean indicating whether the requested element is a valid target
 * for the edge being created and the context edge context information (type, source, target).
 */
public class CheckEdgeResultAction extends ResponseAction {

   public static final String KIND = "checkEdgeResult";

   /**
    * true if the selected element is a valid target for this edge,
    * false otherwise.
    */
   private boolean isValid;

   /**
    * The element type of the Edge that has been checked.
    */
   private String edgeType;

   /**
    * The ID of the source element of the edge that has been checked.
    */
   private String sourceElementId;
   /**
    * The ID of the target element of the edge that has been checked.
    */
   private String targetElementId;

   public CheckEdgeResultAction() {
      super(KIND);
   }

   public CheckEdgeResultAction(final boolean isValid, final RequestCheckEdgeAction requestAction) {
      super(KIND);
      this.setValid(isValid);
      this.setEdgeType(requestAction.getEdgeType());
      this.setSourceElementId(requestAction.getSourceElementId());
      this.setTargetElementId(requestAction.getTargetElementId().orElse(null));
   }

   public String getEdgeType() { return edgeType; }

   public void setEdgeType(final String edgeType) { this.edgeType = edgeType; }

   public String getSourceElementId() { return sourceElementId; }

   public void setSourceElementId(final String sourceElementId) { this.sourceElementId = sourceElementId; }

   public Optional<String> getTargetElementId() { return Optional.ofNullable(this.targetElementId); }

   public void setTargetElementId(final String targetElementId) { this.targetElementId = targetElementId; }

   public boolean isValid() { return isValid; }

   public void setValid(final boolean isValid) { this.isValid = isValid; }

}
