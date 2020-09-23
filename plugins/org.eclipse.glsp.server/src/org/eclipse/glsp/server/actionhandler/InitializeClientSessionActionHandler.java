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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.kind.ConfigureServerHandlersAction;
import org.eclipse.glsp.api.action.kind.InitializeClientSessionAction;
import org.eclipse.glsp.api.handler.ActionHandler;
import org.eclipse.glsp.api.handler.OperationHandler;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.protocol.ClientSessionManager;
import org.eclipse.glsp.api.protocol.GLSPClient;
import org.eclipse.glsp.api.protocol.GLSPServerException;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Initializes the lifecycle for a ClientSession via the {@link ClientSessionManager},
 * and notifies the client about all actions that this server can handle.
 */
public class InitializeClientSessionActionHandler extends BasicActionHandler<InitializeClientSessionAction> {

   private static Logger LOG = Logger.getLogger(InitializeClientSessionActionHandler.class);

   @Inject
   protected ClientSessionManager clientSessionManager;

   @Inject
   protected Provider<GLSPClient> client;

   @Inject
   protected Provider<Set<ActionHandler>> serverActions;

   @Inject
   protected Provider<Set<OperationHandler>> serverOperations;

   @Override
   protected List<Action> executeAction(final InitializeClientSessionAction action,
      final GraphicalModelState modelState) {
      if (clientSessionManager.createClientSession(client.get(), action.getClientId())) {
         modelState.setClientId(action.getClientId());
      } else {
         throw new GLSPServerException(String.format(
            "Could not create session for client id '%s'. Another session with the same id already exists",
            action.getClientId()));
      }

      return listOf(configureServerHandlers());
   }

   private Action configureServerHandlers() {
      Set<String> actions = serverActions.get().stream()
         .flatMap(handler -> getActionKinds(handler).stream())
         .filter(Objects::nonNull)
         .collect(Collectors.toSet());
      Set<String> operations = serverOperations.get().stream()
         .map(this::getActionKind)
         .filter(Objects::nonNull)
         .collect(Collectors.toSet());

      Set<String> allServerActions = Stream.concat(actions.stream(), operations.stream())
         .collect(Collectors.toSet());

      return new ConfigureServerHandlersAction(allServerActions);
   }

   protected Collection<String> getActionKinds(final ActionHandler actionHandler) {
      if (actionHandler instanceof OperationActionHandler) {
         // This is a special case, which delegates operations to operation handlers. As such,
         // we can't determine its action kinds (But we'll retrieve all operation kinds in the next
         // step anyway)
         return Collections.emptyList();
      }

      List<String> actionKinds = actionHandler.getHandledActionTypes().stream().map(this::getActionKind)
         .collect(Collectors.toList());
      if (actionKinds.stream().anyMatch(Objects::isNull)) {
         LOG.error("Unable to determine all action kinds for ActionHandler: " + actionHandler);
      }
      return actionKinds;
   }

   protected String getActionKind(final OperationHandler operationHandler) {
      String actionKind = getActionKind(operationHandler.getHandledOperationType());
      if (actionKind == null) {
         LOG.error("Unable to determine action kind for OperationHandler: " + operationHandler);
      }
      return actionKind;
   }

   protected String getActionKind(final Class<? extends Action> actionClass) {
      try {
         Constructor<?> declaredConstructor = actionClass.getDeclaredConstructor();
         Action a = actionClass.cast(declaredConstructor.newInstance());
         return a.getKind();
      } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException
         | InvocationTargetException ex) {
         // Action doesn't have a valid 0-arg constructor, so we can't automatically determine the action kind.
         // Extensions may need to override this method to handle this case.
         LOG.error(
            "Unable to determine the action kind for class " + actionClass + ". Manual declaration may be required.",
            ex);
         return null;
      }
   }

}
