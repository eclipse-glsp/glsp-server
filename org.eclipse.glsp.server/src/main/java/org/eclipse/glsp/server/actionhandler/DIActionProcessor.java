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
import org.eclipse.glsp.api.action.ActionMessage;
import org.eclipse.glsp.api.action.ActionProcessor;
import org.eclipse.glsp.api.handler.ActionHandler;
import org.eclipse.glsp.api.jsonrpc.GLSPClient;
import org.eclipse.glsp.api.jsonrpc.GLSPClientProvider;
import org.eclipse.glsp.api.provider.ActionHandlerProvider;

import com.google.inject.Inject;

public class DIActionProcessor implements ActionProcessor {

   @Inject
   protected GLSPClientProvider clientProvider;

   @Inject
   protected ActionHandlerProvider handlerProvider;

   @Override
   public Optional<Action> dispatch(final String clientId, final Action action) {
      Optional<ActionHandler> handler = handlerProvider.getHandler(action);
      if (handler.isPresent()) {
         return handler.get().execute(clientId, action);
      }
      return Optional.empty();
   }

   @Override
   public void send(final String clientId, final Action action) {
      GLSPClient client = clientProvider.resolve(clientId);
      if (client == null) {
         throw new IllegalStateException("Unable to send a message to Client ID:" + clientId
            + ". This ID does not match any known client (Client disconnected or not initialized yet?)");
      }
      ActionMessage message = new ActionMessage(clientId, action);
      client.process(message);
   }
}
