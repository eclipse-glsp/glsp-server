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

import java.util.List;
import java.util.Optional;

import org.eclipse.glsp.server.actions.AbstractActionHandler;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.di.DiagramModule;
import org.eclipse.glsp.server.features.sourcemodelwatcher.SourceModelWatcher;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.utils.ClientOptionsUtil;
import org.eclipse.glsp.server.utils.ServerMessageUtil;
import org.eclipse.glsp.server.utils.ServerStatusUtil;

import com.google.inject.Inject;

/**
 * Default handler for {@link RequestModelAction}.
 *
 * <p>
 * The {@link RequestModelAction} is the first request sent by the client in order to obtain a GModel for rendering from
 * the server. The server then uses the {@link SourceModelStorage} configured in the {@link DiagramModule} to load the
 * source model and transforms it with the configured {@link GModelFactory} into a GModel and send this GModel to the
 * client via the {@link ModelSubmissionHandler}.
 * </p>
 */
public class RequestModelActionHandler extends AbstractActionHandler<RequestModelAction> {

   @Inject
   protected SourceModelStorage sourceModelStorage;

   @Inject
   protected ActionDispatcher actionDispatcher;

   @Inject
   protected Optional<SourceModelWatcher> sourceModelWatcher;

   @Inject
   protected ModelSubmissionHandler modelSubmissionHandler;

   @Inject
   protected GModelState modelState;

   @Override
   public List<Action> executeAction(final RequestModelAction action) {
      // only reload if not initialized
      if (!ClientOptionsUtil.disableReloadIsTrue(action.getOptions()) || modelState.getRoot() == null) {
         modelState.setClientOptions(action.getOptions());

         notifyStartLoading();
         sourceModelStorage.loadSourceModel(action);
         notifyFinishedLoading();

         sourceModelWatcher.ifPresent(watcher -> watcher.startWatching());
      }

      return modelSubmissionHandler.submitModel(action.getSubclientId());
   }

   protected void notifyStartLoading() {
      actionDispatcher.dispatch(ServerStatusUtil.info("Model loading in progress!"));
      actionDispatcher.dispatch(ServerMessageUtil.info("Model loading in progress!"));
   }

   protected void notifyFinishedLoading() {
      actionDispatcher.dispatch(ServerStatusUtil.clear());
      actionDispatcher.dispatch(ServerMessageUtil.clear());
   }

}
