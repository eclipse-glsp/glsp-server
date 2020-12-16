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
package org.eclipse.glsp.server.features.core.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.actions.SetDirtyStateAction;
import org.eclipse.glsp.server.diagram.DiagramConfiguration;
import org.eclipse.glsp.server.diagram.DiagramConfigurationRegistry;
import org.eclipse.glsp.server.layout.ILayoutEngine;
import org.eclipse.glsp.server.layout.ServerLayoutKind;
import org.eclipse.glsp.server.model.GModelState;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ModelSubmissionHandler {

   @Inject(optional = true)
   protected ILayoutEngine layoutEngine = new ILayoutEngine.NullImpl();

   @Inject
   protected DiagramConfigurationRegistry diagramConfigurationRegistry;

   private final Object modelLock = new Object();
   private final int revision = 0;

   /**
    * <p>
    * Compute and return a list of actions to update the client-side model, based on the specified modelState.
    * These actions are not processed by this {@link ModelSubmissionHandler}, but should be either manually
    * dispatched to the {@link ActionDispatcher}, or simply returned as the result of an
    * {@link ActionHandler#execute(Action, GModelState)} method.
    * </p>
    * <p>
    * Equivalent to <code>doSubmitModel(update, modelState, true)</code>.
    * </p>
    *
    * @param update
    *                      <code>true</code> if this is an update to an existing model; <code>false</code>
    *                      if this is a new model (e.g. after loading a model)
    * @param modelState
    *                      The {@link GModelState}
    * @return
    *         A list of Actions to be processed in order to submit the model
    */
   public List<Action> submitModel(final boolean update, final GModelState modelState) {
      return submitModel(update, modelState, true);
   }

   /**
    * <p>
    * Compute and return a list of actions to update the client-side model, based on the specified modelState.
    * These actions are not processed by this {@link ModelSubmissionHandler}, but should be either manually
    * dispatched to the {@link ActionDispatcher}, or simply returned as the result of an
    * {@link ActionHandler#execute(Action, GModelState)} method.
    * </p>
    *
    * @param update
    *                      <code>true</code> if this is an update to an existing model; <code>false</code>
    *                      if this is a new model (e.g. after loading a model)
    * @param modelState
    *                      The {@link GModelState}
    * @param layout
    *                      Whether layout should be processed. This should be <code>true</code> for most actions;
    *                      <code>false</code> for actions that already react to client-layout changes
    *                      (i.e. {@link ComputedBoundsAction} ).
    * @return
    *         A list of Actions to be processed in order to submit the model.
    */
   public List<Action> submitModel(final boolean update, final GModelState modelState, final boolean layout) {
      GModelRoot newRoot = modelState.getRoot();
      DiagramConfiguration diagramConfiguration = diagramConfigurationRegistry.get(modelState);
      if (diagramConfiguration.getLayoutKind() == ServerLayoutKind.AUTOMATIC) {
         layoutEngine.layout(modelState);
      }

      boolean needsClientLayout = layout && diagramConfiguration.needsClientLayout();

      synchronized (modelLock) {
         if (newRoot.getRevision() == revision) {
            if (update) {
               Action updateModel = needsClientLayout ? new RequestBoundsAction(modelState.getRoot())
                  : new UpdateModelAction(newRoot, true);
               return Arrays.asList(updateModel, new SetDirtyStateAction(modelState.isDirty()));
            }
            Action setModel = needsClientLayout ? new RequestBoundsAction(modelState.getRoot())
               : new SetModelAction(newRoot);
            return Arrays.asList(setModel);
         }
      }
      return Collections.emptyList();
   }

   public synchronized Object getModelLock() { return modelLock; }
}
