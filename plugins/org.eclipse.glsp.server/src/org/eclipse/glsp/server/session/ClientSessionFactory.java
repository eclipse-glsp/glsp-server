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

/**
 * Handles the construction of new {@link ClientSession}. A client session factory has to know
 * how to derive the client session specific injector and its entrypoint (i.e. the {@link ActionDispatcher}
 * from a given client session id and a given diagram type.
 */
public interface ClientSessionFactory {

   /**
    * Create a new {@link ClientSession} based on the given client session id and diagram type.
    *
    * @param clientSessionId The client session id.
    * @param diagramType     The diagram type.
    * @return A new instance of {@link ClientSession} that correlates to the given input parameters.
    */
   ClientSession create(String clientSessionId, String diagramType);
}
