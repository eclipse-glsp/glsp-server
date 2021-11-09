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

import java.util.HashMap;
import java.util.Map;

public class CreateEdgeOperation extends CreateOperation {

   public static final String KIND = "createEdge";

   private String sourceElementId;

   private String targetElementId;

   public CreateEdgeOperation() {
      super(KIND);
   }

   public CreateEdgeOperation(final String elementTypeId, final String sourceElementId,
      final String targetElementId) {
      this(elementTypeId, sourceElementId, targetElementId, new HashMap<>());
   }

   public CreateEdgeOperation(final String elementTypeId, final String sourceElementId,
      final String targetElementId, final Map<String, String> args) {
      super(KIND, elementTypeId, args);
      this.sourceElementId = sourceElementId;
      this.targetElementId = targetElementId;
   }

   public String getSourceElementId() { return sourceElementId; }

   public void setSourceElementId(final String sourceElementId) { this.sourceElementId = sourceElementId; }

   public String getTargetElementId() { return targetElementId; }

   public void setTargetElementId(final String targetElementId) { this.targetElementId = targetElementId; }

}
