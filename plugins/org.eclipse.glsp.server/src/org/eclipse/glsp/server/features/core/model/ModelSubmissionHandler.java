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
package org.eclipse.glsp.server.features.core.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.actions.SetDirtyStateAction;
import org.eclipse.glsp.server.diagram.DiagramConfiguration;
import org.eclipse.glsp.server.layout.LayoutEngine;
import org.eclipse.glsp.server.layout.ServerLayoutKind;
import org.eclipse.glsp.server.model.GModelState;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ModelSubmissionHandler {

   @Inject
   protected Optional<LayoutEngine> layoutEngine;

   @Inject
   protected DiagramConfiguration diagramConfiguration;

   @Inject
   protected GModelFactory modelFactory;

   @Inject
   protected GModelState modelState;

   private final Object modelLock = new Object();

   /**
    * Returns a list of actions to update the client-side model, based on the specified <code>modelState</code>.
    * <p>
    * These actions are not processed by this {@link ModelSubmissionHandler}, but should be either manually dispatched
    * to the {@link ActionDispatcher}, or simply returned as the result of an
    * {@link ActionHandler#execute(Action)} method.
    * </p>
    *
    * @param reason The optional reason that caused the model update.
    * @return A list of actions to be processed in order to submit the model.
    */
   public List<Action> submitModel(final String reason, final String subclientId) {
      modelFactory.createGModel();
      modelState.getRoot().setRevision(modelState.getRoot().getRevision() + 1);
      boolean needsClientLayout = diagramConfiguration.needsClientLayout();
      if (needsClientLayout) {
         synchronized (modelLock) {
            return Arrays.asList(new RequestBoundsAction(modelState.getRoot()),
               new SetDirtyStateAction(modelState.isDirty(subclientId), reason));
         }
      }
      return submitModelDirectly(reason, subclientId);
   }

   public List<Action> submitModel(final String subclientId) {
      return submitModel(null, subclientId);
   }

   /**
    * Returns a list of actions to directly update the client-side model without any server- or client-side layouting.
    * <p>
    * Typically {@link ActionHandler action handlers} don't invoke this method but use
    * {@link #submitModel(String, String)}
    * instead, as this is only used to eventually submit the model on the client directly after all layouting is already
    * performed before. The only foreseen caller of this method is {@link ComputedBoundsActionHandler}.
    * </p>
    * <p>
    * These actions are not processed by this {@link ModelSubmissionHandler}, but should be either manually dispatched
    * to the {@link ActionDispatcher}, or simply returned as the result of an
    * {@link ActionHandler#execute(Action)} method.
    * </p>
    *
    * @param reason The optional reason that caused the model update.
    * @return A list of actions to be processed in order to submit the model.
    */
   public List<Action> submitModelDirectly(final String reason, final String subclientId) {
      GModelRoot gModel = modelState.getRoot();
      if (diagramConfiguration.getLayoutKind() == ServerLayoutKind.AUTOMATIC && layoutEngine.isPresent()) {
         layoutEngine.get().layout();
      }
      Action modelAction = gModel.getRevision() == 0 ? new SetModelAction(gModel)
         : new UpdateModelAction(gModel, diagramConfiguration.animatedUpdate());
      synchronized (modelLock) {
         List<Action> result = new ArrayList<>();
         result.add(modelAction);
         if (!diagramConfiguration.needsClientLayout()) {
            result.add(new SetDirtyStateAction(modelState.isDirty(subclientId), reason));
         }
         return result;
      }
   }

   public List<Action> submitModelDirectly(final String subclientId) {
      return submitModelDirectly(null, subclientId);
   }

   public synchronized Object getModelLock() { return modelLock; }
}
