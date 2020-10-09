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

import java.util.Collection;
import java.util.Set;

/**
 * An Action to notify the client about the list of actions that the server
 * can handle. With this, the client doesn't need to be manually configured
 * to distinguish between client-side actions and server-side actions; adding
 * a new Action Handler on the server configuration is sufficient.
 */
public class ConfigureServerHandlersAction extends Action {

   public static final String ID = "configureServerHandlers";

   public static final String ACTION_HANDLERS = "actionHandlers";
   public static final String OPERATION_HANDLERS = "operationHandlers";

   private Collection<String> actionKinds;

   public ConfigureServerHandlersAction() {
      super(ID);
   }

   public ConfigureServerHandlersAction(final Set<String> allServerActions) {
      this();
      actionKinds = allServerActions;
   }

   public void setActions(final Collection<String> actionKinds) { this.actionKinds = actionKinds; }

   public Collection<String> getActionKinds() { return actionKinds; }

}
