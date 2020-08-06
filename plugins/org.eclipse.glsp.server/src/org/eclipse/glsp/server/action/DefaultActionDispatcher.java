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
package org.eclipse.glsp.server.action;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.ActionDispatcher;
import org.eclipse.glsp.api.action.kind.ResponseAction;
import org.eclipse.glsp.api.handler.ActionHandler;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.model.ModelStateProvider;
import org.eclipse.glsp.api.registry.ActionHandlerRegistry;

import com.google.inject.Inject;

public class DefaultActionDispatcher implements ActionDispatcher {
   private static Logger LOG = Logger.getLogger(DefaultActionDispatcher.class);

   @Inject
   protected ActionHandlerRegistry actionHandlerRegistry;

   @Inject
   protected ModelStateProvider modelStateProvider;

   @Override
   public void dispatch(final String clientId, final Action action) {
      List<ActionHandler> actionHandlers = actionHandlerRegistry.get(action);
      if (actionHandlers.isEmpty()) {
         LOG.warn("No handler registered for action: " + action);
         return;
      }
      GraphicalModelState modelState = modelStateProvider.getModelState(clientId)
         .orElseGet(() -> modelStateProvider.create(clientId));

      for (ActionHandler actionHandler : actionHandlers) {
         List<Action> responses = actionHandler.execute(action, modelState).stream()
            .map(response -> ResponseAction.respond(action, response))
            .collect(Collectors.toList());
         dispatchAll(clientId, responses);
      }
   }
}
