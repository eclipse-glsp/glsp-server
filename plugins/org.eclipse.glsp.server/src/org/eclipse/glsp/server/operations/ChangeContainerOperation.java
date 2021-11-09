/********************************************************************************
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
 ********************************************************************************/
package org.eclipse.glsp.server.operations;

import java.util.Optional;

import org.eclipse.glsp.graph.GPoint;

public class ChangeContainerOperation extends Operation {

   public static final String KIND = "changeContainer";

   private String elementId;
   private String targetContainerId;
   private GPoint location;

   public ChangeContainerOperation() {
      super(KIND);
   }

   public ChangeContainerOperation(final String elementId, final GPoint location) {
      this();
      this.elementId = elementId;
      this.location = location;
   }

   public ChangeContainerOperation(final String elementId, final GPoint location,
      final String targetContainerId) {
      this(elementId, location);
      this.targetContainerId = targetContainerId;
   }

   public ChangeContainerOperation(final String elementId, final String targetContainerId) {
      this(elementId, null, targetContainerId);
   }

   public String getElementId() { return elementId; }

   public void setElementId(final String elementId) { this.elementId = elementId; }

   public String getTargetContainerId() { return targetContainerId; }

   public void setTargetContainerId(final String targetContainerId) { this.targetContainerId = targetContainerId; }

   public Optional<GPoint> getLocation() { return Optional.ofNullable(location); }

   public void setLocation(final GPoint location) { this.location = location; }

}
