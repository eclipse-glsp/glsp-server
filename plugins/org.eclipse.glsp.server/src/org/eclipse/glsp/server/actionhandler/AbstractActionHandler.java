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
import java.util.Optional;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.kind.ServerStatusAction;
import org.eclipse.glsp.api.handler.ActionHandler;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.model.ModelStateProvider;
import org.eclipse.glsp.api.types.ServerStatus;
import org.eclipse.glsp.api.types.ServerStatus.Severity;

import com.google.inject.Inject;

public abstract class AbstractActionHandler implements ActionHandler {
   @Inject
   protected ModelStateProvider modelStateProvider;
   protected String clientId;

   /**
    * Processes and action and returns the response action which should be send to
    * the client. If no response to the client is need a NoOpAction is returned
    */
   @Override
   public List<Action> execute(final String clientId, final Action action) {
      this.clientId = clientId;
      Optional<GraphicalModelState> modelState = modelStateProvider.getModelState(clientId);
      if (modelState.isPresent()) {
         return execute(action, modelState.get());
      }
      ServerStatus status = new ServerStatus(Severity.FATAL,
         "Could not retrieve the model state for client with id '" + clientId + "'");
      return listOf(new ServerStatusAction(status));
   }

   protected abstract List<Action> execute(Action action, GraphicalModelState modelState);
}
