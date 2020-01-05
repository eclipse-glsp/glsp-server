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

import java.util.List;
import java.util.Set;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.kind.CollapseExpandAction;
import org.eclipse.glsp.api.action.kind.CollapseExpandAllAction;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.model.ModelExpansionListener;

import com.google.inject.Inject;

public class CollapseExpandActionHandler extends AbstractActionHandler {
   @Inject
   protected ModelExpansionListener expansionListener;

   @Override
   public boolean handles(final Action action) {
      return action instanceof CollapseExpandAction || action instanceof CollapseExpandAllAction;
   }

   @Override
   public List<Action> execute(final Action action, final GraphicalModelState modelState) {
      switch (action.getKind()) {
         case Action.Kind.COLLAPSE_EXPAND:
            return handleCollapseExpandAction((CollapseExpandAction) action, modelState);
         case Action.Kind.COLLAPSE_EXPAND_ALL:
            return handleCollapseExpandAllAction((CollapseExpandAllAction) action, modelState);
         default:
            return none();
      }
   }

   private List<Action> handleCollapseExpandAllAction(final CollapseExpandAllAction action,
      final GraphicalModelState modelState) {
      Set<String> expandedElements = modelState.getExpandedElements();
      expandedElements.clear();
      if (action.isExpand()) {
         modelState.getIndex().allIds().forEach(id -> expandedElements.add(id));
      }
      if (expansionListener != null) {
         expansionListener.expansionChanged(action);
      }
      return none();
   }

   private List<Action> handleCollapseExpandAction(final CollapseExpandAction action,
      final GraphicalModelState modelState) {
      Set<String> expandedElements = modelState.getExpandedElements();
      if (action.getCollapseIds() != null) {
         expandedElements.removeAll(action.getCollapseIds());
      }
      if (action.getExpandIds() != null) {
         expandedElements.addAll(action.getExpandIds());
      }

      if (expansionListener != null) {
         expansionListener.expansionChanged(action);
      }

      return none();
   }

}
