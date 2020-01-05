/********************************************************************************
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
 ********************************************************************************/
package org.eclipse.glsp.server.actionhandler;

import java.util.List;

import org.eclipse.glsp.api.action.ActionProcessor;
import org.eclipse.glsp.api.action.kind.AbstractOperationAction;
import org.eclipse.glsp.api.action.kind.CutOperationAction;
import org.eclipse.glsp.api.action.kind.DeleteOperationAction;
import org.eclipse.glsp.api.handler.OperationHandler;
import org.eclipse.glsp.api.model.GraphicalModelState;

import com.google.inject.Inject;

public class CutOperationHandler implements OperationHandler {

   @Inject
   protected ActionProcessor actionProcessor;

   @Override
   public Class<?> handlesActionType() {
      return CutOperationAction.class;
   }

   @Override
   public void execute(final AbstractOperationAction abstractAction, final GraphicalModelState modelState) {
      CutOperationAction cutAction = (CutOperationAction) abstractAction;
      List<String> cutableElementIds = getElementToCut(cutAction, modelState);
      if (!cutableElementIds.isEmpty()) {
         actionProcessor.dispatch(modelState.getClientId(), new DeleteOperationAction(cutableElementIds));
      }
   }

   protected List<String> getElementToCut(final CutOperationAction cutAction,
      final GraphicalModelState modelState) {
      return cutAction.getSelectedElementIds();
   }

   @Override
   public String getLabel(final AbstractOperationAction action) {
      return "Cut";
   }

}
