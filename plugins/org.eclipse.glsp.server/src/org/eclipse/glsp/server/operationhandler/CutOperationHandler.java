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
package org.eclipse.glsp.server.operationhandler;

import java.util.List;

import org.eclipse.glsp.api.action.ActionDispatcher;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.operation.kind.CutOperation;
import org.eclipse.glsp.api.operation.kind.DeleteOperation;

import com.google.inject.Inject;

public class CutOperationHandler extends BasicOperationHandler<CutOperation> {

   @Inject
   protected ActionDispatcher actionDispatcher;

   @Override
   public void executeOperation(final CutOperation operation, final GraphicalModelState modelState) {
      List<String> cutableElementIds = getElementToCut(operation, modelState);
      if (!cutableElementIds.isEmpty()) {
         actionDispatcher.dispatch(modelState.getClientId(), new DeleteOperation(cutableElementIds));
      }
   }

   protected List<String> getElementToCut(final CutOperation cutAction,
      final GraphicalModelState modelState) {
      return cutAction.getEditorContext().getSelectedElementIds();
   }
}
