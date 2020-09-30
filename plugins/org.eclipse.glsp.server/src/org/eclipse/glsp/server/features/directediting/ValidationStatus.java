/********************************************************************************
 * Copyright (c) 2020 EclipseSource and others.
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

import org.eclipse.glsp.server.types.Severity;
import org.eclipse.lsp4j.jsonrpc.RemoteEndpoint;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;

@SuppressWarnings({ "PMD.AvoidFieldNameMatchingMethodName", "PMD.ShortMethodName" })
public class ValidationStatus {

   private Severity severity;
   private String message;
   private ResponseError error;

   public ValidationStatus(final Severity severity, final String message) {
      this(severity, message, null);
   }

   public ValidationStatus(final Severity severity, final String message, final Throwable throwable) {
      super();
      this.severity = severity;
      this.message = message;
      this.error = toError(throwable);
   }

   public Severity getSeverity() { return severity; }

   public void setState(final Severity severity) { this.severity = severity; }

   public String getMessage() { return message; }

   public void setMessage(final String message) { this.message = message; }

   public ResponseError getError() { return error; }

   public void setError(final ResponseError error) { this.error = error; }

   public static ValidationStatus ok() {
      return new ValidationStatus(Severity.OK, null);
   }

   public static ValidationStatus ok(final String message) {
      return new ValidationStatus(Severity.OK, message);
   }

   public static ValidationStatus warning(final String message) {
      return new ValidationStatus(Severity.WARNING, message);
   }

   public static ValidationStatus error(final String message) {
      return new ValidationStatus(Severity.ERROR, message);
   }

   public static ValidationStatus error(final String message, final Throwable throwable) {
      return new ValidationStatus(Severity.ERROR, message, throwable);
   }

   private static ResponseError toError(final Throwable throwable) {
      return throwable == null ? null : RemoteEndpoint.DEFAULT_EXCEPTION_HANDLER.apply(throwable);
   }
}
