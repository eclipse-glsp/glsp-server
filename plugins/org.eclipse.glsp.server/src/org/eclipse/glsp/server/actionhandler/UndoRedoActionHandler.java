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

import org.apache.log4j.Logger;
import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.kind.RedoAction;
import org.eclipse.glsp.api.action.kind.RequestBoundsAction;
import org.eclipse.glsp.api.action.kind.UndoAction;
import org.eclipse.glsp.api.model.GraphicalModelState;

public class UndoRedoActionHandler extends AbstractActionHandler {
   private static final Logger LOG = Logger.getLogger(UndoRedoActionHandler.class);

   @Override
   public boolean handles(final Action action) {
      return action instanceof UndoAction || action instanceof RedoAction;
   }

   @Override
   public List<Action> execute(final Action action, final GraphicalModelState modelState) {
      if (action instanceof UndoAction && modelState.canUndo()) {
         modelState.undo();
         return listOf(new RequestBoundsAction(modelState.getRoot()));
      } else if (action instanceof RedoAction && modelState.canRedo()) {
         modelState.redo();
         return listOf(new RequestBoundsAction(modelState.getRoot()));
      }

      LOG.warn("Cannot undo or redo");
      return none();
   }
}
