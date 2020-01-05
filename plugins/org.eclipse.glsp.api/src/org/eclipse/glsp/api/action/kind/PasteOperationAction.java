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
package org.eclipse.glsp.api.action.kind;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.graph.GPoint;

public class PasteOperationAction extends AbstractOperationAction {

   private Map<String, String> clipboardData;
   private List<String> selectedElementIds;
   private Map<String, String> args;
   private GPoint lastMousePosition;

   public PasteOperationAction() {
      super(Action.Kind.PASTE_ACTION);
   }

   public Map<String, String> getClipboardData() { return clipboardData; }

   public void setClipboardData(final Map<String, String> clipboardData) { this.clipboardData = clipboardData; }

   public List<String> getSelectedElementIds() { return selectedElementIds; }

   public void setSelectedElementsIds(final List<String> selectedElementsIDs) {
      this.selectedElementIds = selectedElementsIDs;
   }

   public Map<String, String> getArgs() { return args; }

   public void setArgs(final Map<String, String> args) { this.args = args; }

   public Optional<GPoint> getLastMousePosition() { return Optional.ofNullable(lastMousePosition); }

   public void setLastMousePosition(final GPoint lastMousePosition) { this.lastMousePosition = lastMousePosition; }

}
