/*******************************************************************************
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
 ******************************************************************************/
package org.eclipse.glsp.server.actions;

/**
 * An action that expects a response.
 *
 * @param <RESPONSE> The type of the {@link ResponseAction}.
 */
public abstract class RequestAction<RESPONSE extends ResponseAction> extends Action {
   private String requestId;

   public RequestAction(final String kind) {
      super(kind);
   }

   public String getRequestId() { return requestId; }
}
