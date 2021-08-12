/********************************************************************************
 * Copyright (c) 2019-2021 EclipseSource and others.
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

/**
 * Java-implementation of the `ActionMessage` interface.
 * An action message serves as an envelope carrying an action to be transmitted between the client and the server and a
 * `clientId` to identify the intended client session
 * for this message.
 */
public class ActionMessage {
   /**
    * The action to execute.
    */
   private final Action action;

   /**
    * Used to identify a specific client session.
    */
   private final String clientId;

   public ActionMessage(final String clientId, final Action action) {
      this.clientId = clientId;
      this.action = action;
   }

   public Action getAction() { return action; }

   public String getClientId() { return clientId; }

   @Override
   public String toString() {
      return "ActionMessage [action=" + action.getKind() + ", clientId=" + clientId + "]";
   }

}
