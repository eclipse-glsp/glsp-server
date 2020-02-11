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
package org.eclipse.glsp.api.action.kind;

import java.util.Optional;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.graph.GPoint;

public class CreateNodeOperationAction extends AbstractOperationAction {

   private String elementTypeId;
   private GPoint location;

   private String containerId;

   public CreateNodeOperationAction() {
      this(null, null, null);
   }

   public CreateNodeOperationAction(final String elementTypeId) {
      this(elementTypeId, null, null);
   }

   public CreateNodeOperationAction(final String elementTypeId, final Optional<GPoint> location) {
      this(elementTypeId, location.orElse(null), null);
   }

   public CreateNodeOperationAction(final String elementTypeId, final GPoint location) {
      this(elementTypeId, location, null);
   }

   public CreateNodeOperationAction(final String elementTypeId, final String containerId) {
      this(elementTypeId, null, containerId);
   }

   public CreateNodeOperationAction(final String elementTypeId, final GPoint location, final String containerId) {
      super(Action.Kind.CREATE_NODE_OPERATION);
      this.elementTypeId = elementTypeId;
      this.location = location;
      this.containerId = containerId;
   }

   public String getElementTypeId() { return elementTypeId; }

   public void setElementTypeId(final String elementTypeId) { this.elementTypeId = elementTypeId; }

   public GPoint getLocation() { return location; }

   public void setLocation(final GPoint location) { this.location = location; }

   public String getContainerId() { return containerId; }

   public void setContainerId(final String containerId) { this.containerId = containerId; }
}
