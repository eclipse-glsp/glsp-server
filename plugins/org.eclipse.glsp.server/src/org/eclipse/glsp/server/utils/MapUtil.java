/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
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

import java.util.Map;
import java.util.Optional;

public final class MapUtil {

   private MapUtil() {}

   public static Optional<String> getValue(final Map<String, String> args, final String key) {
      return Optional.ofNullable(args).map(arg -> arg.get(key));
   }

   public static Optional<Integer> getIntValue(final Map<String, String> args, final String key) {
      try {
         return Optional.ofNullable(args).map(arg -> Integer.parseInt(arg.get(key)));
      } catch (NumberFormatException ex) {
         return Optional.empty();
      }
   }

   public static Optional<Float> getFloatValue(final Map<String, String> args, final String key) {
      try {
         return Optional.ofNullable(args).map(opt -> Float.parseFloat(opt.get(key)));
      } catch (NumberFormatException ex) {
         return Optional.empty();
      }
   }

   public static boolean getBoolValue(final Map<String, String> args, final String key) {
      return Optional.ofNullable(args).map(arg -> Boolean.parseBoolean(arg.get(key))).orElse(false);
   }

}
