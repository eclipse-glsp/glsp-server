/********************************************************************************
 * Copyright (c) 2020-2021 EclipseSource and others.
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
package org.eclipse.glsp.server.features.navigation;

import java.util.HashMap;
import java.util.Map;

public interface NavigationTargetResolver {

   String INFO = "info";
   String WARNING = "warning";
   String ERROR = "error";

   /**
    * Returns a {@link NavigationTargetResolution} for a given {@link NavigationTarget}.
    *
    * @param navigationTarget The given {@link NavigationTarget} to be resolved.
    * @return A {@link NavigationTargetResolution} instance for the given target.
    */
   NavigationTargetResolution resolve(NavigationTarget navigationTarget);

   /**
    * Creates a map of string arguments.
    *
    * @return A new map of string arguments.
    */
   default Map<String, String> createArgs() {
      return new HashMap<>();
   }

   /**
    * Creates a map of string arguments and adds an information message as separate "info" argument.
    *
    * @param message The information message that should be added as "info" argument to the map.
    * @return A new map of string arguments with an additional information message.
    */
   default Map<String, String> createArgsWithInfo(final String message) {
      Map<String, String> args = createArgs();
      addInfo(message, args);
      return args;
   }

   /**
    * Creates a map of string arguments and adds a warning message as separate "warning" argument.
    *
    * @param message The warning message that should be added as "warning" argument to the map.
    * @return A new map of string arguments with an additional warning message.
    */
   default Map<String, String> createArgsWithWarning(final String message) {
      Map<String, String> args = createArgs();
      addWarning(message, args);
      return args;
   }

   /**
    * Creates a map of string arguments and adds an error message as separate "error" argument.
    *
    * @param message The error message that should be added as "error" argument to the map.
    * @return A new map of string arguments with an additional error message.
    */
   default Map<String, String> createArgsWithError(final String message) {
      Map<String, String> args = createArgs();
      addError(message, args);
      return args;
   }

   /**
    * Adds an information message as separate "info" argument to an existing map of string arguments.
    *
    * @param message The information message to add.
    * @param args    The map of string arguments to add the message to.
    */
   default void addInfo(final String message, final Map<String, String> args) {
      args.put(INFO, message);
   }

   /**
    * Adds a warning message as separate "warning" argument to an existing map of string arguments.
    *
    * @param message The warning message to add.
    * @param args    The map of string arguments to add the message to.
    */
   default void addWarning(final String message, final Map<String, String> args) {
      args.put(WARNING, message);
   }

   /**
    * Adds an error message as separate "error" argument to an existing map of string arguments.
    *
    * @param message The error message to add.
    * @param args    The map of string arguments to add the message to.
    */
   default void addError(final String message, final Map<String, String> args) {
      args.put(ERROR, message);
   }
}
