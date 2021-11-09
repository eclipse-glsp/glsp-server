/********************************************************************************
 * Copyright (c) 2020-2021 EclipseSource and others.
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
package org.eclipse.glsp.server.features.directediting;

import org.eclipse.glsp.server.actions.RequestAction;

public class RequestEditValidationAction extends RequestAction<SetEditValidationResultAction> {

   public static final String KIND = "requestEditValidation";

   private String contextId;
   private String modelElementId;
   private String text;

   public RequestEditValidationAction() {
      super(KIND);
   }

   public RequestEditValidationAction(final String contextId, final String modelElementId, final String text) {
      this();
      this.contextId = contextId;
      this.modelElementId = modelElementId;
      this.text = text;
   }

   public String getContextId() { return contextId; }

   public void setContextId(final String contextId) { this.contextId = contextId; }

   public String getModelElementId() { return modelElementId; }

   public void setModelElementId(final String modelElementId) { this.modelElementId = modelElementId; }

   public String getText() { return text; }

   public void setText(final String text) { this.text = text; }

}
