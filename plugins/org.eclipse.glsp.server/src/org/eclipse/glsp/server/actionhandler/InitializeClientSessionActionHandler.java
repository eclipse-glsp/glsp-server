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

import java.util.List;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.kind.InitializeClientSessionAction;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.protocol.ClientSessionManager;
import org.eclipse.glsp.api.protocol.GLSPClient;
import org.eclipse.glsp.api.protocol.GLSPServerException;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class InitializeClientSessionActionHandler extends BasicActionHandler<InitializeClientSessionAction> {

   @Inject
   protected ClientSessionManager clientSessionManager;

   @Inject
   protected Provider<GLSPClient> client;

   @Override
   protected List<Action> executeAction(final InitializeClientSessionAction action,
      final GraphicalModelState modelState) {
      if (clientSessionManager.createClientSession(client.get(), action.getClientId())) {
         modelState.setClientId(action.getClientId());
      } else {
         throw new GLSPServerException(String.format(
            "Could not create session for client id '%s'. Another session with the same id already exists",
            action.getClientId()));
      }
      return none();
   }

}
