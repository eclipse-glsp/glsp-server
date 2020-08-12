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
package org.eclipse.glsp.server.launch;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.glsp.server.utils.LaunchUtil;

public final class CLIParser {
   private static final Logger LOG = Logger.getLogger(CLIParser.class);
   private final CommandLine cmd;
   private final Options options;
   private final String processName;

   public CLIParser(final String[] args, final String processName) throws ParseException {
      this(args, getDefaultCLIOptions(), processName);
   }

   public CLIParser(final String[] args, final Options options, final String processName) throws ParseException {
      this.cmd = new DefaultParser().parse(options, args);
      this.options = options;
      this.processName = processName;
   }

   public boolean optionExists(final String identifier) {
      return cmd.hasOption(identifier);
   }

   public Integer parsePort() {
      String portArg = cmd.getOptionValue("p");
      int port = LaunchUtil.DEFAULT_SERVER_PORT;
      if (portArg != null) {
         try {
            port = Integer.parseInt(portArg);
            if (!LaunchUtil.isValidPort(port)) {
               throw new NumberFormatException();
            }
         } catch (NumberFormatException e) {
            LOG.warn(String.format("'%s' is not a valid port! The default port '%s' is used",
               portArg, LaunchUtil.DEFAULT_SERVER_PORT));
         }
      }

      return port;
   }

   public String parseLogDir() {
      String logDirArg = cmd.getOptionValue("d");
      String logDir = LaunchUtil.DEFAULT_LOG_DIR;
      if (logDirArg != null) {
         File file = new File(logDirArg);
         if (!file.exists() || !file.isDirectory()) {
            LOG.warn(String.format("'%s' is not a valid directory! The default log path directory '%s' is used",
               logDirArg, logDir));
         } else {
            logDir = file.getAbsolutePath();
         }
      }
      return logDir;
   }

   public Level parseLogLevel() {
      String levelArg = cmd.getOptionValue("ll");
      return Level.toLevel(levelArg, LaunchUtil.DEFAULT_LOG_LEVEL);
   }

   public boolean isConsoleLog() { return optionExists("c"); }

   public void printHelp() {
      printHelp(this.processName, options);
   }

   public static void printHelp(final String processName, final Options options) {
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp(90, processName, "\noptions:", options, "", true);
   }

   public static Options getDefaultCLIOptions() {
      Options options = new Options();
      options.addOption("h", "help", false, "Display usage information about GLSPServerLauncher");
      options.addOption("p", "port", true,
         String.format("Set server port, otherwise default port %s is used", LaunchUtil.DEFAULT_SERVER_PORT));
      options.addOption("c", "consoleLog", false, "Print logout in console");
      options.addOption("d", "logDir", true, "Set the directory for log files");
      options.addOption("l", "logLevel", true, "Set the log level");
      return options;
   }
}
