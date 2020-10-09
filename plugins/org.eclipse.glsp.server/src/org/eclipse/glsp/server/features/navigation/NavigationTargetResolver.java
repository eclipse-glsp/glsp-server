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
package org.eclipse.glsp.server.features.navigation;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.glsp.server.model.GModelState;

public interface NavigationTargetResolver {

   String INFO = "info";
   String WARNING = "warning";
   String ERROR = "error";

   NavigationTargetResolution resolve(NavigationTarget navigationTarget, GModelState modelState);

   default Map<String, String> createArgs() {
      return new HashMap<>();
   }

   default Map<String, String> createArgsWithInfo(final String message) {
      Map<String, String> args = createArgs();
      addInfo(message, args);
      return args;
   }

   default Map<String, String> createArgsWithWarning(final String message) {
      Map<String, String> args = createArgs();
      addWarning(message, args);
      return args;
   }

   default Map<String, String> createArgsWithError(final String message) {
      Map<String, String> args = createArgs();
      addError(message, args);
      return args;
   }

   default void addInfo(final String message, final Map<String, String> args) {
      args.put(INFO, message);
   }

   default void addWarning(final String message, final Map<String, String> args) {
      args.put(WARNING, message);
   }

   default void addError(final String message, final Map<String, String> args) {
      args.put(ERROR, message);
   }

   class NullImpl implements NavigationTargetResolver {
      @Override
      public NavigationTargetResolution resolve(final NavigationTarget navigationTarget,
         final GModelState modelState) {
         return NavigationTargetResolution.EMPTY;
      }
   }
}
