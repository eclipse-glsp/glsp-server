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
package org.eclipse.glsp.server.internal.di;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.actions.ActionHandlerRegistry;

import com.google.inject.Inject;

public class DIActionHandlerRegistry extends MapMultiRegistry<Class<? extends Action>, ActionHandler>
   implements ActionHandlerRegistry {

   @Inject
   public DIActionHandlerRegistry(final Set<ActionHandler> handlers) {
      handlers.forEach(handler -> {
         handler.getHandledActionTypes().forEach(action -> register(action, handler));
      });
   }

   @Override
   public List<ActionHandler> get(final Class<? extends Action> key) {
      List<ActionHandler> result = super.get(key);
      if (result.isEmpty()) {
         // Check if the given is key is a subtype of one of the keys in the registry
         Optional<Class<? extends Action>> potentialKey = elements.keySet()
            .stream()
            .filter(actionClass -> actionClass.isAssignableFrom(key)).findFirst();
         if (potentialKey.isPresent()) {
            result = super.get(potentialKey.get());
            // also register the actionClasses for the subtype key
            result.forEach(actionClass -> register(key, actionClass));
         }
      }
      result.sort(Comparator.comparing(ActionHandler::getPriority));
      return result;
   }
}
