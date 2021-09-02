/********************************************************************************
 * Copyright (c) 2019-2021 EclipseSource and others.
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

/**
 * Constants for commonly used client option keys as well as utility methods to retrieve them form a arguments map.
 */
public final class ClientOptionsUtil {
   public static final String DIAGRAM_TYPE = "diagramType";
   public static final String SOURCE_URI = "sourceUri";
   private static final String FILE_PREFIX = "file://";

   private ClientOptionsUtil() {}

   public static Optional<String> getSourceUri(final Map<String, String> options) {
      return MapUtil.getValue(options, SOURCE_URI);
   }

   public static Optional<String> getDiagramType(final Map<String, String> options) {
      return MapUtil.getValue(options, DIAGRAM_TYPE);

   }

   public static Optional<File> getSourceUriAsFile(final Map<String, String> options) {
      return MapUtil.getValue(options, SOURCE_URI).map(ClientOptionsUtil::getAsFile);
   }

   public static String adaptUri(final String uri) {
      return uri.replace(FILE_PREFIX, "");
   }

   public static File getAsFile(final String uri) {
      return new File(adaptUri(uri));
   }
}
