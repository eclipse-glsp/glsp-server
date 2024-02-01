/********************************************************************************
 * Copyright (c) 2023 EclipseSource and others.
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
package org.eclipse.glsp.server.actions;

import java.util.Optional;

/**
 * A reject action is a {@link ResponseAction} fired to indicate that a certain {@link RequestAction}
 * has been rejected. This tells the client to reject the corresponding promise for the request action.
 */
public class RejectAction extends ResponseAction {
   public static final String KIND = "rejectRequest";

   /**
    * A human-readable description of the reject reason. Typically this is an error message
    * that has been thrown when handling the corresponding {@link RequestAction}.
    */
   private String message;

   /**
    * Optional additional details.
    */
   private String detail;

   public RejectAction() {
      super(KIND);
   }

   public RejectAction(final String responseId, final String message) {
      this(responseId, message, null);
   }

   public RejectAction(final String responseId, final String message, final String detail) {
      this();
      this.setResponseId(responseId);
      this.message = message;
      this.detail = detail;
   }

   public String getMessage() { return message; }

   public void setMessage(final String message) { this.message = message; }

   public Optional<String> getDetail() { return Optional.ofNullable(detail); }

   public void setDetail(final String detail) { this.detail = detail; }

}
