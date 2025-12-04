/********************************************************************************
 * Copyright (c) 2019-2025 EclipseSource and others.
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
package org.eclipse.glsp.example.workflow.provider;

import static org.eclipse.glsp.example.workflow.utils.ModelTypes.AUTOMATED_TASK;
import static org.eclipse.glsp.example.workflow.utils.ModelTypes.MANUAL_TASK;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.glsp.example.workflow.handler.GridSnapper;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.server.actions.SetEditModeAction;
import org.eclipse.glsp.server.features.contextmenu.ContextMenuItemProvider;
import org.eclipse.glsp.server.features.contextmenu.MenuItem;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.operations.CreateNodeOperation;

import com.google.inject.Inject;

public class WorkflowContextMenuItemProvider implements ContextMenuItemProvider {

   @Inject
   protected GModelState modelState;

   @Override
   public List<MenuItem> getItems(final List<String> selectedElementIds, final GPoint position,
      final Map<String, String> args) {
      SetEditModeAction editModeAction = new SetEditModeAction(
         modelState.isReadonly() ? SetEditModeAction.EDIT_MODE_EDITABLE : SetEditModeAction.EDIT_MODE_READONLY);
      MenuItem editModeMenu = new MenuItem("editMode", "Readonly Mode", Arrays.asList(editModeAction), true);
      editModeMenu.setToggled(modelState.isReadonly());

      if (modelState.isReadonly()) {
         return Arrays.asList(editModeMenu);
      }

      GPoint snappedPosition = GridSnapper.snap(position);
      MenuItem newAutTask = new MenuItem("newAutoTask", "Automated Task",
         Arrays.asList(new CreateNodeOperation(AUTOMATED_TASK, snappedPosition)), true);
      MenuItem newManTask = new MenuItem("newManualTask", "Manual Task",
         Arrays.asList(new CreateNodeOperation(MANUAL_TASK, snappedPosition)), true);
      MenuItem hiddenItem = new MenuItem("hiddenItem", "Should be hidden", Arrays.asList(), false);
      MenuItem newChildMenu = new MenuItem("new", "New", Arrays.asList(newAutTask, newManTask, hiddenItem), "add",
         "0_new");
      return Arrays.asList(newChildMenu, editModeMenu);
   }

}
