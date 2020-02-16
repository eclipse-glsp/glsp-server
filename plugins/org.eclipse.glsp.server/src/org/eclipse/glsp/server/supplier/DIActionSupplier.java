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
package org.eclipse.glsp.server.supplier;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.handler.ActionHandler;
import org.eclipse.glsp.api.handler.OperationHandler;
import org.eclipse.glsp.api.supplier.ActionSupplier;

import com.google.inject.Inject;

public class DIActionSupplier implements ActionSupplier {
   private static Logger LOG = Logger.getLogger(DIActionSupplier.class);
   protected Set<Action> actions;

   @Inject
   public DIActionSupplier(final Set<Action> actions, final Set<ActionHandler> actionHandlers,
      final Set<OperationHandler> operationHandlers) {
      this.actions = new HashSet<>();
      this.actions.addAll(actions);

      // Add derived from the handledActionTypes of action & operation handlers
      this.actions.addAll(actionHandlers.stream().flatMap(h -> h.handledActionTypes().stream())
         .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
         .map(this::construct)
         .filter(Optional::isPresent)
         .map(Optional::get)
         .collect(Collectors.toList()));

      this.actions.addAll(operationHandlers.stream().flatMap(h -> h.handledOperationTypes().stream())
         .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
         .map(this::construct)
         .filter(Optional::isPresent)
         .map(Optional::get)
         .collect(Collectors.toList()));

   }

   protected Optional<Action> construct(final Class<? extends Action> actionClass) {
      try {
         return Optional.of(actionClass.getConstructor().newInstance());
      } catch (ReflectiveOperationException | SecurityException e) {
         LOG.error(e);
         return Optional.empty();
      }
   }

   @Override
   public Set<Action> get() {
      return actions;
   }

}
