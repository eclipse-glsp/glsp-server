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

import org.eclipse.glsp.server.protocol.ClientSessionListener;
import org.eclipse.glsp.server.protocol.ClientSessionManager;

/**
 * Initialize a new session. One client may open several sessions.
 * Each session is associated to a unique clientId. The session
 * is used to track the client lifecycle: it should be the first
 * action sent by a client to the server. The server will then
 * be able to dispose any resources associated to this client when
 * either:
 * <ol>
 * <li>the client disconnects</li>
 * <li>the client sends a DisposeClientSessionAction</li>
 * </ol>
 *
 * @see ClientSessionManager
 * @see ClientSessionListener
 */
public class InitializeClientSessionAction extends Action {

   public static final String ID = "initializeClientSession";

   private String clientId;

   public InitializeClientSessionAction() {
      super(ID);
   }

   public InitializeClientSessionAction(final String clientId) {
      this();
      this.clientId = clientId;
   }

   public String getClientId() { return clientId; }

   public void setClientId(final String clientId) { this.clientId = clientId; }

}
