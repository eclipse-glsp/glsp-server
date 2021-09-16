/********************************************************************************
 * Copyright (c) 2019-2021 EclipseSource and others.
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
import java.util.Optional;

import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.actions.BasicActionHandler;
import org.eclipse.glsp.server.features.modelsourcewatcher.ModelSourceWatcher;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.utils.ServerMessageUtil;
import org.eclipse.glsp.server.utils.ServerStatusUtil;

import com.google.inject.Inject;

public class RequestModelActionHandler extends BasicActionHandler<RequestModelAction> {

   @Inject
   protected ModelSourceLoader sourceModelLoader;

   @Inject
   protected ActionDispatcher actionDispatcher;

   @Inject
   protected Optional<ModelSourceWatcher> modelSourceWatcher;

   @Inject
   protected ModelSubmissionHandler modelSubmissionHandler;

   @Override
   public List<Action> executeAction(final RequestModelAction action, final GModelState modelState) {
      modelState.setClientOptions(action.getOptions());

      notifyStartLoading(modelState);
      sourceModelLoader.loadSourceModel(action, modelState);
      notifyFinishedLoading(modelState);

      modelSourceWatcher.ifPresent(watcher -> watcher.startWatching(modelState));

      return modelSubmissionHandler.submitModel(modelState);
   }

   protected void notifyStartLoading(final GModelState modelState) {
      actionDispatcher.dispatch(ServerStatusUtil.info("Model loading in progress!"));
      actionDispatcher.dispatch(ServerMessageUtil.info("Model loading in progress!"));
   }

   protected void notifyFinishedLoading(final GModelState modelState) {
      actionDispatcher.dispatch(ServerStatusUtil.clear());
      actionDispatcher.dispatch(ServerMessageUtil.clear());
   }

}
