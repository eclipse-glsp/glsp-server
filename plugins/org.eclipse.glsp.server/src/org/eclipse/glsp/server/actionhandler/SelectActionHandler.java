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
package org.eclipse.glsp.server.actionhandler;

import java.util.Optional;
import java.util.Set;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.kind.SelectAction;
import org.eclipse.glsp.api.action.kind.SelectAllAction;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.model.ModelSelectionListener;

import com.google.inject.Inject;

public class SelectActionHandler extends AbstractActionHandler {
   @Inject
   protected ModelSelectionListener modelSelectionListener;

   @Override
   public boolean handles(final Action action) {
      return action instanceof SelectAction || action instanceof SelectAllAction;
   }

   @Override
   public Optional<Action> execute(final Action action, final GraphicalModelState modelState) {
      switch (action.getKind()) {
         case Action.Kind.SELECT:
            return handleSelectAction((SelectAction) action, modelState);
         case Action.Kind.SELECT_ALL:
            return handleSelectAllAction((SelectAllAction) action, modelState);
         default:
            return Optional.empty();
      }

   }

   private Optional<Action> handleSelectAllAction(final SelectAllAction action, final GraphicalModelState modelState) {
      Set<String> selectedElements = modelState.getSelectedElements();
      if (action.isSelect()) {
         modelState.getIndex().allIds().forEach(id -> selectedElements.add(id));
      } else {
         selectedElements.clear();
      }
      if (modelSelectionListener != null) {
         modelSelectionListener.selectionChanged(action);
      }
      return Optional.empty();
   }

   private Optional<Action> handleSelectAction(final SelectAction action, final GraphicalModelState modelState) {
      Set<String> selectedElements = modelState.getSelectedElements();
      if (action.getDeselectedElementsIDs() != null) {
         selectedElements.removeAll(action.getDeselectedElementsIDs());
      }
      if (action.getSelectedElementsIDs() != null) {
         selectedElements.addAll(action.getSelectedElementsIDs());
      }
      if (modelSelectionListener != null) {
         modelSelectionListener.selectionChanged(action);
      }
      return Optional.empty();
   }

}
