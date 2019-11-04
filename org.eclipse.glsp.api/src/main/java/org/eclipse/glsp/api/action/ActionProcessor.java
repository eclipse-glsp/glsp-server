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
package org.eclipse.glsp.api.action;

import java.util.Optional;

import org.eclipse.glsp.api.action.kind.IdentifiableRequestAction;
import org.eclipse.glsp.api.action.kind.IdentifiableResponseAction;

public interface ActionProcessor {

   /**
    * <p>
    * Process the given action, dispatch to the corresponding handler, and optionally
    * send the reply Action to the client.
    * </p>
    *
    * @param clientId The client from which the action was received
    * @param action   The action to process
    */
   default void process(final String clientId, Action action) {
      Optional<String> requestId = Optional.empty();
      if (action instanceof IdentifiableRequestAction) {
         // unwrap identifiable request
         requestId = Optional.of(((IdentifiableRequestAction) action).getId());
         action = ((IdentifiableRequestAction) action).getAction();
      }

      Optional<Action> responseOpt = dispatch(clientId, action);

      if (responseOpt.isPresent()) {
         // wrap identifiable response if necessary
         Action response = requestId.<Action> map(id -> new IdentifiableResponseAction(id, responseOpt.get()))
            .orElse(responseOpt.get());
         send(clientId, response);
      }
   }

   /**
    * @see ActionProcessor#process(String, Action)
    *
    * @param message ActionMessage received from the client
    */
   default void process(final ActionMessage message) {
      process(message.getClientId(), message.getAction());
   }

   /**
    * <p>
    * Handle the given action, received from the specified clientId, and optionally
    * return a reply Action.
    * </p>
    *
    * @param clientId The client from which the action was received
    * @param action   The action to dispatch
    * @return An optional Action to be sent to the client as the result of handling
    *         the received <code>action</code>
    */
   Optional<Action> dispatch(String clientId, Action action);

   /**
    * Send the given action to the specified clientId.
    *
    * @param clientId The client to which the action should be sent
    * @param action   The action to send to the client
    */
   void send(String clientId, Action action);

   final class NullImpl implements ActionProcessor {

      @Override
      public Optional<Action> dispatch(final String clientId, final Action action) {
         return Optional.empty();
      }

      @Override
      public void send(final String clientId, final Action action) {
         return;
      }
   }
}
