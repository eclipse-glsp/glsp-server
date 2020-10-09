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
package org.eclipse.glsp.server.actions;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.protocol.GLSPClient;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class ClientActionHandler implements ActionHandler {
   public static final String CLIENT_ACTIONS = "ClientActionHandler";

   @Inject
   protected Provider<GLSPClient> client;

   private final List<Class<? extends Action>> handledActionTypes;

   @Inject
   public ClientActionHandler(@Named(CLIENT_ACTIONS) final Set<Action> clientActions) {
      this.handledActionTypes = clientActions.stream().map(Action::getClass).collect(Collectors.toList());
   }

   @Override
   public List<Class<? extends Action>> getHandledActionTypes() { return handledActionTypes; }

   @Override
   public List<Action> execute(final Action action, final GModelState modelState) {
      send(modelState.getClientId(), action);
      return Collections.emptyList();
   }

   protected void send(final String clientId, final Action action) {
      ActionMessage message = new ActionMessage(clientId, action);
      client.get().process(message);
   }
}
