/*******************************************************************************
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
 ******************************************************************************/
package org.eclipse.glsp.server.features.navigation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NavigationTarget {

   public static final String ELEMENT_IDS = "elementIds";
   public static final String ELEMENT_IDS_SEPARATOR = "&";
   public static final String TEXT_LINE = "line";
   public static final String TEXT_COLUMN = "column";

   private final String uri;
   private final String label;
   private Map<String, String> args;

   public NavigationTarget(final String uri) {
      this(uri, new HashMap<String, String>());
   }

   public NavigationTarget(final String uri, final String label) {
      this(uri, label, new HashMap<String, String>());
   }

   public NavigationTarget(final String uri, final Map<String, String> args) {
      this(uri, null, args);
   }

   public NavigationTarget(final String uri, final String label, final Map<String, String> args) {
      super();
      this.uri = uri;
      this.label = label;
      this.args = args;
   }

   public String getUri() { return uri; }

   public String getLabel() { return label; }

   public Map<String, String> getArgs() { return args; }

   public List<String> getElementIds() {
      if (args == null || args.get(ELEMENT_IDS) == null || args.get(ELEMENT_IDS).isEmpty()) {
         return Arrays.asList();
      }
      return Arrays.asList(this.args.get(ELEMENT_IDS).split(ELEMENT_IDS_SEPARATOR));
   }

   public void setElementIds(final List<String> elementIds) {
      if (args == null) {
         args = new HashMap<>();
      }
      args.put(ELEMENT_IDS, elementIds.stream().collect(Collectors.joining(ELEMENT_IDS_SEPARATOR)));
   }

   public boolean hasTextPosition() {
      final boolean hasValues = args != null && args.containsKey(TEXT_LINE) && args.containsKey(TEXT_COLUMN);
      if (!hasValues) {
         return false;
      }
      try {
         Double.valueOf(args.get(TEXT_LINE));
         Double.valueOf(args.get(TEXT_COLUMN));
         return true;
      } catch (final NumberFormatException nfe) {
         return false;
      }
   }

   public void setTextPosition(final int line, final int column) {
      args.put(TEXT_LINE, String.valueOf(line));
      args.put(TEXT_COLUMN, String.valueOf(column));
   }

   public int getTextPositionLine() { return Double.valueOf(args.get(TEXT_LINE)).intValue(); }

   public int getTextPositionColumn() { return Double.valueOf(args.get(TEXT_COLUMN)).intValue(); }

}
