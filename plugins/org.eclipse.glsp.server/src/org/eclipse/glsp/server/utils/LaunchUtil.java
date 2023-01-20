/********************************************************************************
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
 ********************************************************************************/
package org.eclipse.glsp.server.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.eclipse.glsp.server.launch.DefaultCLIParser;

@SuppressWarnings("rawtypes")
public final class LaunchUtil {
   private static Logger LOGGER = LogManager.getLogger(LaunchUtil.class);

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

   public static void configure(final DefaultCLIParser cli) throws ParseException {
      if (cli.isHelp()) {
         cli.printHelp();
         System.exit(0);
      }
      configureLogger(cli);
      if (cli.hasOption(DefaultCLIParser.OPTION_LOG_DIR) && !cli.isFileLog()) {
         LOGGER.warn(String.format("File logging is disabled. The option '--%s' will be ignored.",
            DefaultCLIParser.OPTION_LOG_DIR));
      }
   }

   public static void configureLogger(final DefaultCLIParser cli) throws ParseException {
      if (cli.isFileLog()) {
         configureLogger(cli.isConsoleLog(), cli.parseLogDir(), cli.parseLogLevel());
      } else {
         configureLogger(cli.isConsoleLog(), cli.parseLogLevel());
      }
   }

   public static void configureLogger(final boolean logToConsole, final Level logLevel) {
      configureLogger(logToConsole, null, logLevel);
   }

   /**
    * The default logging pattern layout provides the following information
    * <ul>
    * <li><code>%d{DEFAULT_NANOS}</code> outputs the timestamp of the logging event with nanosecond precision</li>
    * <li><code>[%t]</code> outputs the name of the thread that generated the logging event</li>
    * <li><code>%-5level</code> outputs the level of the logging event, formatted to a length of 5 spaces to the
    * right</li>
    * <li><code>%logger{1}</code> outputs the name of the logger that published the logging event with a precision of 1.
    * That means that the right most component of the logger name will be printed, i.e. the Logger class name.</li>
    * <li><code>%msg%n</code> outputs the supplied message of the logging event and ends with the platform dependent
    * line separator character or characters</li>
    * </ul>
    *
    * @return the default logging pattern layout
    * @see <a href="https://logging.apache.org/log4j/log4j-2.1/manual/layouts.html">Log4j2 Manual</a>
    */
   protected static String getLoggingPatternLayout() {
      return "%d{DEFAULT_NANOS} [%t] %-5level %logger{1} - %msg%n";
   }

   /**
    * Returns the default log file name for the GLSP server.
    * Default format: <code>glsp-server_dd-MM-yyyy_HH:mm:ss.log</code>
    *
    * @return the default log file name for GLSP
    */
   protected static String getLogFileName() {
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
      return "glsp-server_" + formatter.format(new Date()) + ".log";
   }

   /**
    * @return the maximum log file size before rolling process starts
    */
   protected static String getRollingFileSize() { return "5MB"; }

   public static void configureLogger(final boolean logToConsole, final String logDir, final Level logLevel) {
      ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();

      // Configure new root logger
      RootLoggerComponentBuilder rootLogger = builder.newRootLogger(logLevel);

      // Configure pattern layout
      LayoutComponentBuilder patternLayout = builder.newLayout("PatternLayout")
         .addAttribute("pattern", getLoggingPatternLayout());

      // Configure console logging
      if (logToConsole) {
         AppenderComponentBuilder consoleAppender = builder.newAppender("ConsoleLogger", "CONSOLE")
            .addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT)
            .add(patternLayout);
         builder.add(consoleAppender);
         rootLogger.add(builder.newAppenderRef("ConsoleLogger"));
      }

      // Configure rolling file logging
      if (logDir != null && !logDir.isEmpty()) {
         String logFilePath = new File(logDir, getLogFileName()).getAbsolutePath();
         // specify policy for rolling logfile
         ComponentBuilder triggeringPolicy = builder.newComponent("Policies")
            .addComponent(builder.newComponent("SizeBasedTriggeringPolicy").addAttribute("size", getRollingFileSize()));
         // specify rolling file logger appender
         AppenderComponentBuilder fileAppender = builder.newAppender("RollingFileLogger", "RollingFile")
            .addAttribute("fileName", logFilePath)
            .addAttribute("filePattern", logFilePath + "-%i.log.gz")
            .addComponent(triggeringPolicy)
            .add(patternLayout);
         builder.add(fileAppender);
         rootLogger.add(builder.newAppenderRef("RollingFileLogger"));
      }

      // Add root logger and reconfigure to use created configuration
      builder.add(rootLogger);
      Configurator.reconfigure(builder.build());
   }

   public static <T extends Appender> List<T> getAppenders(final LoggerContext context, final Class<T> clazz) {
      List<T> result = new ArrayList<>();
      Map<String, Appender> allAppenders = context.getRootLogger().getAppenders();
      for (Appender appender : allAppenders.values()) {
         if (clazz.isInstance(appender)) {
            result.add(clazz.cast(appender));
         }
      }
      return result;
   }

   public static void printHelp(final String processName, final Options options) {
      HelpFormatter formatter = new HelpFormatter();
      String cmdLineSyntax = "java -jar " + processName;
      formatter.printHelp(90, cmdLineSyntax, "\noptions:", options, "", true);
   }
}
