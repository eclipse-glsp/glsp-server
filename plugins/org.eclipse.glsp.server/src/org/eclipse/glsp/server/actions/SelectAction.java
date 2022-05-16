/*******************************************************************************
 * Copyright (c) 2019-2022 EclipseSource and others.
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
package org.eclipse.glsp.server.actions;

import java.util.ArrayList;
import java.util.List;

/**
 * Instructs the client to select one or more specified elements.
 */
public class SelectAction extends Action {

   public static final String KIND = "elementSelected";

   private List<String> selectedElementsIDs;
   private List<String> deselectedElementsIDs;

   public SelectAction() {
      this(new ArrayList<>());
   }

   public SelectAction(final List<String> selectedElementsIDs) {
      this(selectedElementsIDs, new ArrayList<>());
   }

   public SelectAction(final List<String> selectedElementsIDs, final List<String> deselectedElementsIDs) {
      super(KIND);
      this.selectedElementsIDs = selectedElementsIDs;
      this.deselectedElementsIDs = deselectedElementsIDs;
   }

   public List<String> getSelectedElementsIDs() { return selectedElementsIDs; }

   public void setSelectedElementsIDs(final List<String> selectedElementsIDs) {
      this.selectedElementsIDs = selectedElementsIDs;
   }

   public List<String> getDeselectedElementsIDs() { return deselectedElementsIDs; }

   public void setDeselectedElementsIDs(final List<String> deselectedElementsIDs) {
      this.deselectedElementsIDs = deselectedElementsIDs;
   }

}
