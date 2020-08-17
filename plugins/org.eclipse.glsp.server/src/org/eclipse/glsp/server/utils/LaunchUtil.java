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
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.glsp.server.launch.DefaultCLIParser;

public final class LaunchUtil {
   private static Logger LOG = Logger.getLogger(LaunchUtil.class);

   private LaunchUtil() {}

   public static final class DefaultOptions {
      public static final int SERVER_PORT = 5007;
      public static final Level LOG_LEVEL = Level.INFO;
      public static final String LOG_DIR = new File("./logs/").getAbsolutePath();
      public static final boolean CONSOLE_LOG_ENABLED = true;
      public static final boolean FILE_LOG_ENABLED = false;
   }

   public static boolean isValidPort(final Integer port) {
      return port >= 0 && port <= 65535;
   }

   public static void configure(final DefaultCLIParser cli) throws ParseException, IOException {
      if (cli.isHelp()) {
         cli.printHelp();
         System.exit(0);
      }
      configureLogger(cli);
      if (cli.hasOption(DefaultCLIParser.OPTION_LOG_DIR) && !cli.isFileLog()) {
         LOG.warn(String.format("File logging is disabled. The option '--%s' will be ignored.",
            DefaultCLIParser.OPTION_LOG_DIR));
      }
   }

   public static void configureLogger(final DefaultCLIParser cli) throws ParseException, IOException {
      if (cli.isFileLog()) {
         configureLogger(cli.isConsoleLog(), cli.parseLogDir(), cli.parseLogLevel());
      } else {
         configureLogger(cli.isConsoleLog(), cli.parseLogLevel());
      }
   }

   public static void configureLogger(final boolean logToConsole, final Level logLevel) throws IOException {
      configureLogger(logToConsole, null, logLevel);
   }

   public static void configureLogger(final boolean logToConsole, final String logDir, final Level logLevel)
      throws IOException {
      Logger root = Logger.getRootLogger();
      List<ConsoleAppender> consoleAppenders = getAppenders(root, ConsoleAppender.class);
      if (logToConsole) {
         if (consoleAppenders.isEmpty()) {
            root.addAppender(new ConsoleAppender(
               new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));
         }
      } else {
         // Remove all console log appenders
         consoleAppenders.forEach(root::removeAppender);
      }
      if (logDir != null && !logDir.isEmpty()) {
         SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
         String fileName = formatter.format(new Date()) + ".log";
         String logFile = new File(logDir, fileName).getAbsolutePath();
         root.addAppender(
            new FileAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN), logFile));
      }
      root.setLevel(logLevel);
   }

   @SuppressWarnings("unchecked")
   public static <T extends Appender> List<T> getAppenders(final Logger logger, final Class<T> clazz) {
      List<T> result = new ArrayList<>();
      Enumeration<Appender> allAppenders = logger.getAllAppenders();
      while (allAppenders.hasMoreElements()) {
         Appender appender = allAppenders.nextElement();
         if (clazz.isInstance(appender)) {
            result.add(clazz.cast(appender));
         }
      }
      return result;
   }

   public static void printHelp(final String processName, final Options options) {
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp(90, processName, "\noptions:", options, "", true);
   }
}
