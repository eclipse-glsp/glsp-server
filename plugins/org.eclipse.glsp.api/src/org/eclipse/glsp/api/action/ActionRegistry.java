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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.glsp.api.provider.ActionProvider;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ActionRegistry {

   private final ActionProvider actionProvider;
   private Map<String, Class<? extends Action>> actions;

   @Inject
   public ActionRegistry(final ActionProvider actionProvider) {
      this.actionProvider = actionProvider;
      intialize();
   }

   private void intialize() {
      this.actions = new HashMap<>();
      actionProvider.getActions().forEach(a -> actions.put(a.getKind(), a.getClass()));
   }

   public Set<Action> getAllActions() { return actionProvider.getActions(); }

   /**
    * Queries the correspondent Java class object for the given action kind.
    *
    * @param kind The kind/type of action
    * @return correspondent Class
    */
   public Class<? extends Action> getActionClass(final String kind) {
      return actions.get(kind);
   }
}
