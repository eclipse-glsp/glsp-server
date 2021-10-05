/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
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
package org.eclipse.glsp.server.protocol;

/**
 * A listener to track the connection status of {@link GLSPClient}s (i.e. client applications).
 * Gets notified when a new GLSP client connects or disconnects.
 */
public interface ServerConnectionListener {

   /**
    * Triggered when a new client application ({@link GLSPClient}) connects to the {@link GLSPServer}.
    *
    * @param client The newly connected client.
    */
   default void clientConnected(final GLSPClient client) {
      // No-op as default. This enables partial interface implementation.
   }

   /**
    * Triggered when a client application ({@link GLSPClient}) disconnects for the {@link GLSPServer}.
    *
    * @param client The disconnected client
    */
   default void clientDisconnected(final GLSPClient client) {
      // No-op as default. This enables partial interface implementation.
   }
}
