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
package org.eclipse.glsp.server.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.cli.ParseException;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.glsp.server.launch.CLIParser;

public final class LaunchUtil {
   private LaunchUtil() {}

   public static final int DEFAULT_SERVER_PORT = 5007;
   public static final Level DEFAULT_LOG_LEVEL = Level.INFO;
   public static final String DEFAULT_LOG_DIR = new File("./logs/").getAbsolutePath();

   public static boolean isValidPort(final Integer port) {
      return port >= 0 && port <= 65535;
   }

   public static void configureConsoleLogger() {
      configureConsoleLogger(DEFAULT_LOG_LEVEL);
   }

   public static void configureConsoleLogger(final Level logLevel) {
      Logger root = Logger.getRootLogger();
      if (!root.getAllAppenders().hasMoreElements()) {
         root.addAppender(new ConsoleAppender(
            new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));
      }
      root.setLevel(logLevel);

   }

   public static void configureLogger(
      final CLIParser parser) throws ParseException, IOException {
      if (parser.isConsoleLog()) {
         configureConsoleLogger();
      } else {
         String logDir = parser.parseLogDir();
         SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
         String fileName = formatter.format(new Date()) + ".log";
         File logFile = new File(logDir, fileName);
         Level level = parser.parseLogLevel();
         configureLogger(logFile.getAbsolutePath(), level);
      }
   }

   public static void configureLogger(final String logFile) throws IOException {
      configureLogger(logFile, DEFAULT_LOG_LEVEL);
   }

   public static void configureLogger(final String logFile, final Level logLevel) throws IOException {
      Logger root = Logger.getRootLogger();
      root.removeAllAppenders();
      root.addAppender(
         new FileAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN), logFile));
   }

}
