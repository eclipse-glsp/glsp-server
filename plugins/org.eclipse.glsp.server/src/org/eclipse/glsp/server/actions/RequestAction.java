/*******************************************************************************
 * Copyright (c) 2019-2026 EclipseSource and others.
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
import java.util.function.Supplier;

/**
 * An action that expects a response.
 *
 * @param <RESPONSE> The type of the {@link ResponseAction}.
 */
public abstract class RequestAction<RESPONSE extends ResponseAction> extends Action {
   private String requestId;
   private Long timeout;

   public RequestAction(final String kind) {
      this(kind, "");
   }

   public RequestAction(final String kind, final String requestId) {
      super(kind);
      this.requestId = requestId;
   }

   public String getRequestId() { return requestId; }

   /**
    * Assigns a request id supplied by {@code idGenerator} to the given action if no id has been
    * set yet. No-op if a non-empty id is already present, so an id assigned at construction is
    * preserved.
    *
    * @param action      the request action to stamp
    * @param idGenerator supplier invoked only when an id needs to be assigned
    */
   public static void ensureRequestId(final RequestAction<?> action, final Supplier<String> idGenerator) {
      if (action.requestId == null || action.requestId.isEmpty()) {
         action.requestId = idGenerator.get();
      }
   }

   /**
    * Maximum wait time in milliseconds for a response, or {@link Optional#empty()} for no timeout.
    */
   public Optional<Long> getTimeout() { return Optional.ofNullable(timeout); }

   public void setTimeout(final Long timeout) { this.timeout = timeout; }
}
