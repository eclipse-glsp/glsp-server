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
package org.eclipse.glsp.server.session;

import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.actions.ActionMessage;
import org.eclipse.glsp.server.disposable.IDisposable;
import org.eclipse.glsp.server.protocol.GLSPServer;

import com.google.inject.Injector;

/**
 * Stores the core information that the {@link GLSPServer} needs to know about a client session.
 * When handling session specific requests (i.e. {@link ActionMessage}s the server retrieves the corresponding
 * client session from the {@link ClientSessionManager} and delegates the action message to its
 * {@link ActionDispatcher};
 */
public interface ClientSession extends IDisposable {

   /**
    * Retrieve the id of the client session.
    *
    * @return The client session id.
    */
   String getId();

   /**
    * Retrieve the diagram type of the client session.
    *
    * @return The diagram type.
    */
   String getDiagramType();

   /**
    * Return the action dispatcher of this diagram type. The action dispatcher is typically created by the session
    * specific injector and is basically the entrypoint to the session specific injection context.
    *
    * @return The action dispatcher of this diagram type.
    */
   ActionDispatcher getActionDispatcher();

   /**
    * Retrieve the session specific {@link Injector}. Use this method with care. Normally it should not be necessary to
    * construct additional instances using the session specific injector and this can cause unintended side effects.
    *
    * @return The session specific injector.
    */
   Injector getInjector();

}
