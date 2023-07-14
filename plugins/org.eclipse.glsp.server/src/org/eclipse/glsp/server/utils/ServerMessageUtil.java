/*******************************************************************************
 * Copyright (c) 2020-2023 EclipseSource and others.
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

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.glsp.server.actions.ServerMessageAction;
import org.eclipse.glsp.server.types.Severity;

public final class ServerMessageUtil {
   private static Logger LOGGER = LogManager.getLogger(ServerMessageUtil.class);

   private ServerMessageUtil() {}

   public static ServerMessageAction message(final Severity severity, final String message) {
      return new ServerMessageAction(severity, message);
   }

   public static ServerMessageAction info(final String message) {
      return new ServerMessageAction(Severity.INFO, message);
   }

   public static ServerMessageAction warn(final String message) {
      return new ServerMessageAction(Severity.WARNING, message);
   }

   public static ServerMessageAction error(final String message) {
      return new ServerMessageAction(Severity.ERROR, message);
   }

   public static ServerMessageAction error(final String message, final String details) {
      return new ServerMessageAction(Severity.ERROR, message, details);
   }

   public static ServerMessageAction error(final String message, final Throwable cause) {
      return new ServerMessageAction(Severity.ERROR, message, getDetails(cause));
   }

   public static ServerMessageAction error(final Exception e) {
      return error(getMessage(e), getDetails(e));
   }

   public static ServerMessageAction clear() {
      return new ServerMessageAction(Severity.NONE, "");
   }

   private static String getDetails(final Throwable throwable) {
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
