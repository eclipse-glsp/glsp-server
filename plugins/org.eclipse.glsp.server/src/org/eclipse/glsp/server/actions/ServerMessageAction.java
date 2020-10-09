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
package org.eclipse.glsp.server.actions;

import org.eclipse.glsp.server.types.ServerStatus;
import org.eclipse.glsp.server.types.Severity;

public class ServerMessageAction extends Action {

   public static final String ID = "serverMessage";

   private static final int NO_TIMEOUT = -1;

   private String severity;
   private String message;
   private String details;
   private int timeout;

   public ServerMessageAction() {
      super(ID);
   }

   public ServerMessageAction(final Severity severity, final String message) {
      this(severity, message, null, NO_TIMEOUT);
   }

   public ServerMessageAction(final Severity severity, final String message, final int timeout) {
      this(severity, message, null, timeout);
   }

   public ServerMessageAction(final Severity severity, final String message, final String details) {
      this(severity, message, details, NO_TIMEOUT);
   }

   public ServerMessageAction(final ServerStatus status) {
      this(status.getSeverity(), status.getMessage(), status.getDetails(), NO_TIMEOUT);
   }

   public ServerMessageAction(final Severity severity, final String message, final String details,
      final int timeout) {
      this();
      this.severity = Severity.toString(severity);
      this.message = message;
      this.details = details;
      this.timeout = timeout;
   }

   public String getSeverity() { return severity; }

   public String getMessage() { return message; }

   public String getDetails() { return details; }

   public void setDetails(final String details) { this.details = details; }

   public int getTimeout() { return timeout; }

   public void setTimeout(final int timeout) { this.timeout = timeout; }

}
