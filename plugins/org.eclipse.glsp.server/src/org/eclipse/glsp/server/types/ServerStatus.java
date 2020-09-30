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
package org.eclipse.glsp.server.types;

public class ServerStatus {
   private final Severity severity;
   private final String message;
   private String details;

   public ServerStatus(final Severity severity, final String message) {
      super();
      this.severity = severity;
      this.message = message;
   }

   public ServerStatus(final Severity severity, final String message, final String details) {
      this(severity, message);
      this.details = details;
   }

   public String getMessage() { return message; }

   public Severity getSeverity() { return severity; }

   public String getDetails() { return details; }
}
