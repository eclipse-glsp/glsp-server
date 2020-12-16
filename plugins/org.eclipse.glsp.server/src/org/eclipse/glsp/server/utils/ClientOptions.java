/********************************************************************************
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
 ********************************************************************************/
package org.eclipse.glsp.server.utils;

import java.io.File;
import java.util.Map;
import java.util.Optional;

public final class ClientOptions {
   public static final String DIAGRAM_TYPE = "diagramType";
   public static final String SOURCE_URI = "sourceUri";
   private static final String FILE_PREFIX = "file://";

   private ClientOptions() {}

   public static Optional<String> getValue(final Map<String, String> options, final String key) {
      return Optional.ofNullable(options).map(opt -> opt.get(key));
   }

   public static Optional<Integer> getIntValue(final Map<String, String> options, final String key) {
      try {
         return Optional.ofNullable(options).map(opt -> Integer.parseInt(opt.get(key)));
      } catch (NumberFormatException ex) {
         return Optional.empty();
      }
   }

   public static Optional<Float> getFloatValue(final Map<String, String> options, final String key) {
      try {
         return Optional.ofNullable(options).map(opt -> Float.parseFloat(opt.get(key)));
      } catch (NumberFormatException ex) {
         return Optional.empty();
      }
   }

   public static boolean getBoolValue(final Map<String, String> options, final String key) {
      return Optional.ofNullable(options).map(opt -> Boolean.parseBoolean(opt.get(key))).orElse(false);
   }

   public static Optional<File> getSourceUriAsFile(final Map<String, String> options) {
      final Optional<String> uriString = getValue(options, SOURCE_URI);
      return uriString.map(uri -> uri.replace(FILE_PREFIX, "")).map(path -> new File(path));
   }
}
