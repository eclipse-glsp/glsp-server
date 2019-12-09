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
package org.eclipse.glsp.api.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.eclipse.glsp.api.action.kind.ServerStatusAction;
import org.eclipse.glsp.api.types.ServerStatus.Severity;

public final class ServerStatusUtil {
   private static Logger LOGGER = Logger.getLogger(ServerStatusUtil.class);

   public static ServerStatusAction info(final String message) {
      return new ServerStatusAction(Severity.INFO, message);
   }

   public static ServerStatusAction warn(final String message) {
      return new ServerStatusAction(Severity.WARNING, message);
   }

   public static ServerStatusAction error(final String message, final String details) {
      return new ServerStatusAction(Severity.ERROR, message, details);
   }

   public static ServerStatusAction error(final String message, final Throwable cause) {
      return new ServerStatusAction(Severity.ERROR, message, getDetails(cause));
   }

   public static ServerStatusAction error(final Exception e) {
      return error(getMessage(e), getDetails(e));
   }

   public static ServerStatusAction clear() {
      return new ServerStatusAction(Severity.OK, "");
   }

   private ServerStatusUtil() {}

   public static String getDetails(final Throwable throwable) {
      if (throwable == null) {
         return null;
      }
      StringBuilder result = new StringBuilder();
      // message
      if (throwable.getMessage() != null) {
         result.append(throwable.getMessage() + "\n");
      }
      // stacktrace
      try (StringWriter stackTraceWriter = new StringWriter();
         PrintWriter printWriter = new PrintWriter(stackTraceWriter)) {
         throwable.printStackTrace(printWriter);
         result.append(stackTraceWriter.toString());
      } catch (IOException ex) {
         LOGGER.error("Could not write stacktrace.", ex);
         return null;
      }
      return result.toString();
   }

   private static String getMessage(final Exception e) {
      if (e == null) {
         return "<no-message>";
      }
      if (e.getMessage() != null) {
         return e.getMessage();
      }
      return e.getClass().toString();
   }
}
