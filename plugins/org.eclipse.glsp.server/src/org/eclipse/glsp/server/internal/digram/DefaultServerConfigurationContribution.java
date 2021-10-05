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
package org.eclipse.glsp.server.internal.digram;

import static org.eclipse.glsp.server.di.GLSPModule.CLIENT_ACTIONS;

import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.glsp.graph.gson.GraphGsonConfigurator;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.actions.ActionRegistry;
import org.eclipse.glsp.server.actions.ClientActionHandler;
import org.eclipse.glsp.server.di.DiagramType;
import org.eclipse.glsp.server.diagram.ServerConfigurationContribution;
import org.eclipse.glsp.server.gson.GraphGsonConfigurationFactory;
import org.eclipse.glsp.server.internal.util.ReflectionUtil;
import org.eclipse.glsp.server.operations.Operation;
import org.eclipse.glsp.server.operations.OperationHandler;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class DefaultServerConfigurationContribution implements ServerConfigurationContribution {

   @Inject()
   @DiagramType
   protected String diagramType;

   @Inject()
   protected GraphGsonConfigurationFactory graphGsonConfigurationFactory;

   @Inject()
   protected Set<ActionHandler> actionHandlers;

   @Inject()
   protected Set<OperationHandler> operationHandlers;

   @Inject()
   @Named(CLIENT_ACTIONS)
   protected Set<Action> clientActions;

   @Override
   public void configure(final ActionRegistry registry) {
      Stream<? extends Action> handledActions = ReflectionUtil.construct(actionHandlers.stream()
         .filter(handler -> !(handler instanceof ClientActionHandler))
         .flatMap(h -> h.getHandledActionTypes().stream()));

      Stream<? extends Operation> handledOperations = ReflectionUtil.construct(operationHandlers.stream()
         .map(OperationHandler::getHandledOperationType));

      handledActions.forEach(action -> registry.register(diagramType, action.getKind(), action.getClass(), true));
      handledOperations
         .forEach(operation -> registry.register(diagramType, operation.getKind(), operation.getClass(), true));

      clientActions.forEach(action -> registry.register(diagramType, action.getKind(), action.getClass(), false));
   }

   @Override
   public void configure(final GraphGsonConfigurator gsonConfigurator) {
      graphGsonConfigurationFactory.configure(gsonConfigurator);
   }

}
