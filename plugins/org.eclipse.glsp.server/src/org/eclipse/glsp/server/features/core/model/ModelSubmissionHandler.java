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

import static java.util.concurrent.TimeUnit.MILLISECONDS;

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
import org.eclipse.glsp.server.features.validation.Marker;
import org.eclipse.glsp.server.features.validation.MarkersReason;
import org.eclipse.glsp.server.features.validation.ModelValidator;
import org.eclipse.glsp.server.features.validation.SetMarkersAction;
import org.eclipse.glsp.server.layout.LayoutEngine;
import org.eclipse.glsp.server.layout.ServerLayoutKind;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.utils.Debouncer;
import org.eclipse.glsp.server.utils.StatusActionUtil;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Helper class that provides utility methods to handle model updates i.e.
 * submit a new model to the client. In addition, to the core model update action this class
 * also takes care of related behavior like dirty state handling, validation and client/server side layouting.
 * Note that the submissions handler is only responsible for deriving the set of actions that comprise a model update
 * but does not actually dispatch them. The returned actions should be either manually dispatched
 * to the {@link ActionDispatcher}, or simply returned as the result of an
 * {@link ActionHandler#execute(Action)} method.
 */
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

   @Inject
   protected ActionDispatcher actionDispatcher;

   @Inject
   protected Optional<ModelValidator> validator;

   protected Debouncer<ModelValidator> liveValidationDebouncer;

   // we use a very slight delay by default to avoid sending very short status messages for very fast validations
   protected long liveValidationDelay = 100;

   protected final Object modelLock = new Object();
   protected Optional<RequestModelAction> requestModelAction = Optional.empty();

   /**
    * Returns a list of actions to submit the initial revision of the client-side model, based on the injected
    * {@link GModelState}. Typically this method is invoked by the {@link RequestModelActionHandler} when the diagram
    * is (re)loaded.
    * <p>
    * These actions are not processed by this {@link ModelSubmissionHandler}, but should be either manually dispatched
    * to the {@link ActionDispatcher}, or simply returned as the result of an
    * {@link ActionHandler#execute(Action)} method.
    * </p>
    *
    * @param requestAction The {@link RequestModelAction} that triggere the initial model update
    * @return A list of actions to be processed in order to submit the intial model.
    *
    */
   public List<Action> submitInitialModel(final RequestModelAction requestAction) {
      /*
       * In the default update action flow a `RequestModelAction` does not directly trigger a `SetModelAction` response
       * (RequestModelAction (C) -> RequestBoundsAction (S) -> ComputedBoundsAction (C) -> SetModelACtion (S)
       * Therefore we temporarily store the action later retrival
       */
      this.requestModelAction = Optional.of(requestAction);
      return submitModel();

   }

   /**
    * Returns a list of actions to update the client-side model, based on the injected {@link GModelState}
    * <p>
    * These actions are not processed by this {@link ModelSubmissionHandler}, but should be either manually dispatched
    * to the {@link ActionDispatcher}, or simply returned as the result of an
    * {@link ActionHandler#execute(Action)} method.
    * </p>
    *
    * @param reason The optional reason that caused the model update.
    * @return A list of actions to be processed in order to submit the model.
    */
   public List<Action> submitModel(final String reason) {
      modelFactory.createGModel();
      int revision = this.requestModelAction.isPresent() ? 0 : this.modelState.getRoot().getRevision() + 1;
      modelState.getRoot().setRevision(revision);

      boolean needsClientLayout = diagramConfiguration.needsClientLayout();
      if (needsClientLayout) {
         synchronized (modelLock) {
            return Arrays.asList(new RequestBoundsAction(modelState.getRoot()),
               new SetDirtyStateAction(modelState.isDirty(), reason));
         }
      }
      return submitModelDirectly(reason);
   }

   public List<Action> submitModel() {
      return submitModel(null);
   }

   /**
    * Returns a list of actions to directly update the client-side model without any server- or client-side layouting.
    * <p>
    * Typically {@link ActionHandler action handlers} don't invoke this method but use
    * {@link #submitModel(String)}
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
   public List<Action> submitModelDirectly(final String reason) {
      GModelRoot gModel = modelState.getRoot();
      if (diagramConfiguration.getLayoutKind() == ServerLayoutKind.AUTOMATIC && layoutEngine.isPresent()) {
         layoutEngine.get().layout();
      }
      Action modelAction = this.requestModelAction.isPresent()
         ? createSetModeAction(gModel)
         : new UpdateModelAction(gModel, diagramConfiguration.animatedUpdate());

      synchronized (modelLock) {
         List<Action> result = new ArrayList<>();
         result.add(modelAction);
         if (!diagramConfiguration.needsClientLayout()) {
            result.add(new SetDirtyStateAction(modelState.isDirty(), reason));
         }
         if (validator.isPresent()) {
            result.addAll(validateModel(validator.get()));
         }
         return result;
      }
   }

   protected List<Action> validateModel(final ModelValidator validator) {
      scheduleLiveValidation(validator);
      // we are using async live validation so there no actions to return for the model submission
      return List.of();
   }

   protected void scheduleLiveValidation(final ModelValidator validator) {
      if (liveValidationDebouncer == null) {
         liveValidationDebouncer = new Debouncer<>(this::performLiveValidation, getLiveValidationDelay(), MILLISECONDS);
      }
      liveValidationDebouncer.accept(validator);
   }

   public long getLiveValidationDelay() { return this.liveValidationDelay; }

   public void setLiveValidationDelay(final long liveValidationDelay) {
      this.liveValidationDelay = liveValidationDelay;
   }

   protected void performLiveValidation(final ModelValidator validator) {
      actionDispatcher.dispatch(StatusActionUtil.info("Validate Model..."));
      List<Marker> markers = validator.validate(Arrays.asList(modelState.getRoot()), MarkersReason.LIVE);
      SetMarkersAction markerAction = new SetMarkersAction(markers, MarkersReason.LIVE);
      actionDispatcher.dispatchAll(List.of(markerAction, StatusActionUtil.clear()));
   }

   public List<Action> submitModelDirectly() {
      return submitModelDirectly(null);
   }

   protected SetModelAction createSetModeAction(final GModelRoot newRoot) {
      String requestId = this.requestModelAction.map(action -> action.getRequestId()).orElse("");
      SetModelAction response = new SetModelAction(newRoot);
      response.setResponseId(requestId);
      this.requestModelAction = Optional.empty();
      return response;
   }

   public synchronized Object getModelLock() { return modelLock; }
}
