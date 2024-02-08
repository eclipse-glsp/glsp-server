/********************************************************************************
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
 ********************************************************************************/
package org.eclipse.glsp.server.features.undoredo;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.actions.SetDirtyStateAction;
import org.eclipse.glsp.server.features.core.model.ModelSubmissionHandler;
import org.eclipse.glsp.server.model.GModelState;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class UndoRedoActionHandler implements ActionHandler {
   private static final Logger LOGGER = LogManager.getLogger(UndoRedoActionHandler.class);

   @Inject
   protected ModelSubmissionHandler modelSubmissionHandler;

   @Inject
   protected GModelState modelState;

   @Override
   public List<Action> execute(final Action action) {
      if (action instanceof UndoAction && modelState.canUndo(action.getSubclientId())) {
         modelState.undo(action.getSubclientId());
         return modelSubmissionHandler.submitModel(SetDirtyStateAction.Reason.UNDO, action.getSubclientId());
      } else if (action instanceof RedoAction && modelState.canRedo(action.getSubclientId())) {
         modelState.redo(action.getSubclientId());
         return modelSubmissionHandler.submitModel(SetDirtyStateAction.Reason.REDO, action.getSubclientId());
      }
      LOGGER.warn("Cannot undo or redo");
      return none();
   }

   @Override
   public List<Class<? extends Action>> getHandledActionTypes() {
      return Lists.newArrayList(UndoAction.class, RedoAction.class);
   }
}
