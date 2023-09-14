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
package org.eclipse.glsp.server.utils;

import org.eclipse.glsp.server.actions.StatusAction;
import org.eclipse.glsp.server.types.Severity;

/**
 * Utility methods to simplify the creation of {@link StatusAction}s.
 */
public final class StatusActionUtil {
   private StatusActionUtil() {}

   public static StatusAction status(final Severity severity, final String message) {
      return new StatusAction(severity, message);
   }

   public static StatusAction info(final String message) {
      return new StatusAction(Severity.INFO, message);
   }

   public static StatusAction warn(final String message) {
      return new StatusAction(Severity.WARNING, message);
   }

   public static StatusAction error(final String message) {
      return new StatusAction(Severity.ERROR, message);
   }

   public static StatusAction status(final Severity severity, final String message,
      final int timeout) {
      return new StatusAction(severity, message, timeout);
   }

   public static StatusAction info(final String message, final int timeout) {
      return new StatusAction(Severity.INFO, message, timeout);
   }

   public static StatusAction warn(final String message, final int timeout) {
      return new StatusAction(Severity.WARNING, message, timeout);
   }

   public static StatusAction error(final String message, final int timeout) {
      return new StatusAction(Severity.ERROR, message, timeout);
   }

   public static StatusAction clear() {
      return new StatusAction(Severity.NONE, "");
   }
}
