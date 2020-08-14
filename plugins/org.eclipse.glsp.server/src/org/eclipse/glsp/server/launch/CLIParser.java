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

import java.util.function.Predicate;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.eclipse.glsp.server.utils.LaunchUtil;

public abstract class CLIParser {
   private static final Logger LOG = Logger.getLogger(CLIParser.class);
   protected static final String INVALID_ARGUMENT_MESSAGE = "%s' is not a valid argument for option '--%s'! The default value '%s' is used.";

   protected final CommandLine cmd;
   protected final Options options;
   protected final String processName;

   public CLIParser(final String[] args, final Options options, final String processName) throws ParseException {
      this.cmd = new DefaultParser().parse(options, args);
      this.options = options;
      this.processName = processName;
   }

   public boolean hasOption(final String optionName) {
      return cmd.hasOption(optionName);
   }

   public String parseOption(final String optionName, final String defaultValue) {
      return parseOption(optionName, defaultValue, null);
   }

   public String parseOption(final String optionName, final String defaultValue, final Predicate<String> validator) {
      String arg = cmd.getOptionValue(optionName);
      if (arg != null) {
         if (validator == null || validator.test(arg)) {
            return arg;
         }
         LOG.warn(String.format(INVALID_ARGUMENT_MESSAGE,
            arg, optionName, defaultValue));
      }
      return defaultValue;
   }

   public int parseIntOption(final String optionName, final int defaultValue) {
      return parseIntOption(optionName, defaultValue, null);
   }

   public int parseIntOption(final String optionName, final int defaultValue, final Predicate<Integer> validator) {
      String intArg = cmd.getOptionValue(optionName);
      int value = defaultValue;
      if (intArg != null) {
         try {
            value = Integer.parseInt(intArg);
            if (validator != null && !validator.test(value)) {
               throw new NumberFormatException();
            }
         } catch (NumberFormatException e) {
            LOG.warn(String.format(INVALID_ARGUMENT_MESSAGE,
               intArg, optionName, defaultValue));
         }
      }
      return value;
   }

   public boolean parseBoolOption(final String optionName, final boolean defaultValue) {
      String arg = cmd.getOptionValue(optionName);
      return arg != null ? Boolean.parseBoolean(arg) : defaultValue;
   }

   public void printHelp() {
      LaunchUtil.printHelp(this.processName, options);
   }

   public CommandLine getCmd() { return cmd; }
}
