/********************************************************************************
 * Copyright (c) 2020 EclipseSource and others.
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

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.ActionMessage;
import org.eclipse.glsp.api.handler.ActionHandler;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.protocol.ClientSessionManager;
import org.eclipse.glsp.api.protocol.GLSPClient;
import org.eclipse.glsp.api.protocol.GLSPServerException;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class ClientActionHandler implements ActionHandler {
   public static final String CLIENT_ACTIONS = "ClientActionHandler";

   @Inject()
   protected ClientSessionManager sessionManager;

   private final List<Class<? extends Action>> handledActionTypes;

   @Inject
   public ClientActionHandler(@Named(CLIENT_ACTIONS) final Set<Action> clientActions) {
      this.handledActionTypes = clientActions.stream().map(Action::getClass).collect(Collectors.toList());
   }

   @Override
   public List<Class<? extends Action>> getHandledActionTypes() { return handledActionTypes; }

   @Override
   public List<Action> execute(final Action action, final GraphicalModelState modelState) {
      send(modelState.getClientId(), action);
      return Collections.emptyList();
   }

   protected void send(final String clientId, final Action action) {
      GLSPClient client = GLSPServerException.getOrThrow(sessionManager.resolve(clientId),
         "Unable to send a message to Client ID:" + clientId
            + ". This ID does not match any known client (Client disconnected or not initialized yet?)");

      ActionMessage message = new ActionMessage(clientId, action);
      client.process(message);
   }
}
