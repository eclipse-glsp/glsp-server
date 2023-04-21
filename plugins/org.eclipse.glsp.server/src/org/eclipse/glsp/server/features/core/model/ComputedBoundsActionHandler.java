/********************************************************************************
 * Copyright (c) 2019-2023 EclipseSource and others.
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
package org.eclipse.glsp.server.features.core.model;

import java.util.List;

import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.server.actions.AbstractActionHandler;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.utils.LayoutUtil;

import com.google.inject.Inject;

/**
 * Syncs the bounds computed by the client (i.e. the actual bounds after applying CSS styles) back to the GModel. In
 * this default implementation the updated bounds are stored transient. This means they are applied to the graphical
 * model but are not persisted to the source model.
 */
public class ComputedBoundsActionHandler extends AbstractActionHandler<ComputedBoundsAction> {

   @Inject
   protected ModelSubmissionHandler submissionHandler;

   @Inject
   protected GModelState modelState;

   @Override
   public List<Action> executeAction(final ComputedBoundsAction action) {
      synchronized (submissionHandler.getModelLock()) {
         GModelRoot model = modelState.getRoot();
         if (model != null && action.getRevision().isPresent()
            && action.getRevision().get().doubleValue() == model.getRevision()) {
            LayoutUtil.applyBounds(model, action, modelState);
            return submissionHandler.submitModelDirectly(action.getSubclientId());
         }
      }
      return none();
   }

}
