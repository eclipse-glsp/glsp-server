/********************************************************************************
 * Copyright (c) 2020-2023 EclipseSource and others.
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

import static org.eclipse.glsp.server.di.ClientSessionModule.CLIENT_ACTIONS;

import java.util.Set;

import org.eclipse.glsp.server.di.ClientId;
import org.eclipse.glsp.server.protocol.GLSPClient;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * Component responsible for forwarding actions that are (also) handled by the
 * client.
 */
@Singleton
public class ClientActionForwarder {

   @Inject
   protected Provider<GLSPClient> client;

   @Inject
   @ClientId
   protected String clientId;

   @Inject
   @Named(CLIENT_ACTIONS)
   protected Set<String> clientActionKinds;

   /**
    * Processes the given action and checks wether it is a
    * `clientAction` i.e. an action that should be forwarded to
    * the client to be handled there. If the check is successful
    * the action is wrapped in an {@link ActionMessage} and sent to the client.
    *
    * @param action The action to check and forward
    * @return `true` if the action was forwarded to the client, `false` otherwise
    */
   public boolean handle(final Action action) {
      if (shouldForwardToClient(action)) {
         ActionMessage message = new ActionMessage(clientId, action);
         client.get().process(message);
         return true;
      }
      return false;
   }

   protected boolean shouldForwardToClient(final Action action) {
      if (action.isReceivedFromClient()) {
         return false;
      }
      return (clientActionKinds.contains(action.getKind()) || action instanceof ResponseAction);
   }

}
