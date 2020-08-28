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
import org.eclipse.glsp.api.action.kind.DisposeClientSessionAction;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.protocol.ClientSessionManager;
import org.eclipse.glsp.api.protocol.GLSPClient;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class DisposeClientSessionActionHandler extends BasicActionHandler<DisposeClientSessionAction> {

   @Inject
   protected ClientSessionManager sessionManager;

   @Inject
   protected Provider<GLSPClient> client;

   @Override
   protected List<Action> executeAction(final DisposeClientSessionAction action, final GraphicalModelState modelState) {
      sessionManager.disposeClientSession(client.get(), action.getClientId());
      return none();
   }

}
