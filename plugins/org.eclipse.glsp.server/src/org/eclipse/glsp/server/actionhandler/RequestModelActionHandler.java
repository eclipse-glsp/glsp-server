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

import java.util.Optional;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.kind.RequestBoundsAction;
import org.eclipse.glsp.api.action.kind.RequestModelAction;
import org.eclipse.glsp.api.action.kind.SetModelAction;
import org.eclipse.glsp.api.factory.ModelFactory;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.utils.ClientOptions;
import org.eclipse.glsp.graph.GModelRoot;

import com.google.inject.Inject;

public class RequestModelActionHandler extends AbstractActionHandler {

   @Inject
   protected ModelFactory modelFactory;

   @Override
   public Optional<Action> execute(final String clientId, final Action action) {
      this.clientId = clientId;
      Optional<GraphicalModelState> modelState = modelStateProvider.getModelState(clientId);
      if (modelState.isPresent()) {
         return execute(action, modelState.get());
      }
      return execute(action, modelStateProvider.create(clientId));
   }

   @Override
   public Optional<Action> execute(final Action action, final GraphicalModelState modelState) {
      if (action instanceof RequestModelAction) {
         RequestModelAction requestAction = (RequestModelAction) action;
         GModelRoot model = modelFactory.loadModel(requestAction, modelState);
         modelState.setRoot(model);
         modelState.setClientOptions(requestAction.getOptions());

         boolean needsClientLayout = ClientOptions.getBoolValue(requestAction.getOptions(),
            ClientOptions.NEEDS_CLIENT_LAYOUT);

         Action responseAction = needsClientLayout ? new RequestBoundsAction(modelState.getRoot())
            : new SetModelAction(modelState.getRoot());
         return Optional.of(responseAction);
      }
      return Optional.empty();
   }

   @Override
   public boolean handles(final Action action) {
      return action instanceof RequestModelAction;
   }

}
