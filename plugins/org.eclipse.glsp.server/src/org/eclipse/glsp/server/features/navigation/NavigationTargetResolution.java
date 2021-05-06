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
import java.util.List;
import java.util.Map;

public class NavigationTargetResolution {

   public static final NavigationTargetResolution EMPTY = new NavigationTargetResolution();

   private List<String> elementIds;
   private Map<String, String> args;

   public NavigationTargetResolution() {
      this(null, new HashMap<>());
   }

   public NavigationTargetResolution(final List<String> elementIds) {
      this(elementIds, new HashMap<>());
   }

   public NavigationTargetResolution(final Map<String, String> args) {
      this(null, args);
   }

   public NavigationTargetResolution(final List<String> elementIds, final Map<String, String> args) {
      super();
      this.elementIds = elementIds;
      this.args = args;
   }

   public List<String> getElementIds() { return elementIds; }

   public void setElementIds(final List<String> elementIds) { this.elementIds = elementIds; }

   public Map<String, String> getArgs() { return args; }

   public void setArgs(final Map<String, String> args) { this.args = args; }

}
