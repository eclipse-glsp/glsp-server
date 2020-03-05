/*******************************************************************************
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
 ******************************************************************************/
package org.eclipse.glsp.api.action.kind;

import org.eclipse.glsp.api.types.Severity;
import org.eclipse.glsp.api.types.ServerStatus;

public class GLSPServerStatusAction extends ServerStatusAction {
   private static final int NO_TIMEOUT = -1;

   private int timeout;

   public GLSPServerStatusAction() {
      super();
   }

   public GLSPServerStatusAction(final Severity severity, final String message) {
      this(severity, message, NO_TIMEOUT);
   }

   public GLSPServerStatusAction(final ServerStatus status) {
      this(status.getSeverity(), status.getMessage(), NO_TIMEOUT);
   }

   public GLSPServerStatusAction(final Severity severity, final String message, final int timeout) {
      super(severity, message);
      this.timeout = timeout;
   }

   public int getTimeout() { return timeout; }

   public void setTimeout(final int timeout) { this.timeout = timeout; }

}
