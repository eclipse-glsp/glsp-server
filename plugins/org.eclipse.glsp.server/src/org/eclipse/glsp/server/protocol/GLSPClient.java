/*******************************************************************************
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
 ******************************************************************************/
package org.eclipse.glsp.server.protocol;

import org.eclipse.glsp.server.actions.ActionMessage;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;

/**
 *
 * Json-rpc client proxy interface for the default {@link GLSPServer} implementation.
 *
 */
public interface GLSPClient {

   /**
    * A `process` notification is sent from the server to server to the client when the client should handle i.e.
    * process a specific {@link ActionMessage}. Any communication that is performed between initialization and shutdown
    * is handled by sending action messages, either from the client to the server or from the server to the client. This
    * is the core part of the Graphical Language Server Protocol.
    *
    * @param message The {@link ActionMessage} that should be processed.
    */
   @JsonNotification
   void process(ActionMessage message);
}
