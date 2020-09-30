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
package org.eclipse.glsp.server.utils;

import org.eclipse.glsp.server.actions.GLSPServerStatusAction;
import org.eclipse.glsp.server.actions.ServerStatusAction;
import org.eclipse.glsp.server.types.Severity;

public final class ServerStatusUtil {
   private ServerStatusUtil() {}

   public static ServerStatusAction status(final Severity severity, final String message) {
      return new ServerStatusAction(severity, message);
   }

   public static ServerStatusAction info(final String message) {
      return new ServerStatusAction(Severity.INFO, message);
   }

   public static ServerStatusAction warn(final String message) {
      return new ServerStatusAction(Severity.WARNING, message);
   }

   public static ServerStatusAction error(final String message) {
      return new ServerStatusAction(Severity.ERROR, message);
   }

   public static ServerStatusAction status(final Severity severity, final String message,
      final int timeout) {
      return new GLSPServerStatusAction(severity, message, timeout);
   }

   public static ServerStatusAction info(final String message, final int timeout) {
      return new GLSPServerStatusAction(Severity.INFO, message, timeout);
   }

   public static ServerStatusAction warn(final String message, final int timeout) {
      return new GLSPServerStatusAction(Severity.WARNING, message, timeout);
   }

   public static ServerStatusAction error(final String message, final int timeout) {
      return new GLSPServerStatusAction(Severity.ERROR, message, timeout);
   }

   public static ServerStatusAction clear() {
      return new ServerStatusAction(Severity.NONE, "");
   }
}
