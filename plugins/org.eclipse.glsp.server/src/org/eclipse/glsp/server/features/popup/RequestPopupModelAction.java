/*******************************************************************************
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
 ******************************************************************************/
package org.eclipse.glsp.server.features.popup;

import org.eclipse.glsp.graph.GBounds;
import org.eclipse.glsp.server.actions.RequestAction;

public class RequestPopupModelAction extends RequestAction<SetPopupModelAction> {

   public static final String KIND = "requestPopupModel";

   private String elementId;
   private GBounds bounds;

   public RequestPopupModelAction() {
      super(KIND);
   }

   public RequestPopupModelAction(final String elementId, final GBounds bounds) {
      this();
      this.elementId = elementId;
      this.bounds = bounds;
   }

   public String getElementId() { return elementId; }

   public GBounds getBounds() { return bounds; }

}
