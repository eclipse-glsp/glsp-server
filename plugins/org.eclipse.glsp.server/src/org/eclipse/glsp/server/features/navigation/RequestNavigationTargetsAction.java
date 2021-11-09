/*******************************************************************************
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
 ******************************************************************************/
package org.eclipse.glsp.server.features.navigation;

import org.eclipse.glsp.server.actions.RequestAction;
import org.eclipse.glsp.server.types.EditorContext;

public class RequestNavigationTargetsAction extends RequestAction<SetNavigationTargetsAction> {

   public static final String KIND = "requestNavigationTargets";

   private String targetTypeId;
   private EditorContext editorContext;

   public RequestNavigationTargetsAction() {
      super(KIND);
   }

   public RequestNavigationTargetsAction(final String targetType, final EditorContext editorContext) {
      this();
      this.targetTypeId = targetType;
      this.editorContext = editorContext;
   }

   public String getTargetTypeId() { return targetTypeId; }

   public void setTargetTypeId(final String targetTypeId) { this.targetTypeId = targetTypeId; }

   public EditorContext getEditorContext() { return editorContext; }

   public void setEditorContext(final EditorContext editorContext) { this.editorContext = editorContext; }

}
