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

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.kind.ComputedBoundsAction;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.utils.LayoutUtil;
import org.eclipse.glsp.graph.GModelRoot;

import com.google.inject.Inject;

public class ComputedBoundsActionHandler extends BasicActionHandler<ComputedBoundsAction> {
   @Inject
   protected ModelSubmissionHandler submissionHandler;

   @Override
   public List<Action> executeAction(final ComputedBoundsAction action, final GraphicalModelState modelState) {

      synchronized (submissionHandler.getModelLock()) {
         GModelRoot model = modelState.getRoot();
         if (model != null && model.getRevision() == action.getRevision()) {
            LayoutUtil.applyBounds(model, action, modelState);
            return submissionHandler.doSubmitModel(true, modelState);
         }
      }

      return none();
   }

}
