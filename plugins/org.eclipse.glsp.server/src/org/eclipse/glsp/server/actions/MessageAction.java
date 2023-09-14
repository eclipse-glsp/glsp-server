/*******************************************************************************
 * Copyright (c) 2019-2023 EclipseSource and others.
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

import java.util.Optional;

import org.eclipse.glsp.server.types.Severity;

/**
 * Instructs the client to show a notification message to the user.
 */
public class MessageAction extends Action {

   public static final String KIND = "message";

   private String severity;
   private String message;
   private String details;

   public MessageAction() {
      super(KIND);
   }

   public MessageAction(final Severity severity, final String message) {
      this(severity, message, null);
   }

   public MessageAction(final Severity severity, final String message, final String details) {
      this();
      this.severity = Severity.toString(severity);
      this.message = message;
      this.details = details;
   }

   public String getSeverity() { return severity; }

   public String getMessage() { return message; }

   public Optional<String> getDetails() { return Optional.ofNullable(details); }

   public void setDetails(final String details) { this.details = details; }

}
