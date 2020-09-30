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
package org.eclipse.glsp.server.types;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.glsp.graph.GPoint;

public class EditorContext {

   private List<String> selectedElementIds;
   private GPoint lastMousePosition;
   private String sourceUri;
   private Map<String, String> args;

   public EditorContext() {}

   public List<String> getSelectedElementIds() { return selectedElementIds; }

   public void setSelectedElementIds(final List<String> selectedElementIds) {
      this.selectedElementIds = selectedElementIds;
   }

   public Optional<GPoint> getLastMousePosition() { return Optional.ofNullable(lastMousePosition); }

   public void setLastMousePosition(final GPoint lastMousePosition) { this.lastMousePosition = lastMousePosition; }

   public String getSourceUri() { return sourceUri; }

   public void setSourceUri(final String sourceUri) { this.sourceUri = sourceUri; }

   public Map<String, String> getArgs() { return args; }

   public void setArgs(final Map<String, String> args) { this.args = args; }

}
