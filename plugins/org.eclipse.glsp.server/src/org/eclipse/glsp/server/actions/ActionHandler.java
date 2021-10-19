/********************************************************************************
 * Copyright (c) 2020-2021 EclipseSource and others.
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * An action handler can execute certain {@link Action} types (subclasses) that are dispatched by the
 * {@link ActionDispatcher}. The action handler processes the action in the {@link ActionHandler#execute(Action)}
 * method and returns a list of response actions to be dispatched as a result of processing the original action.
 * One action handler can handle multiple different action types, see {@link ActionHandler#getHandledActionTypes()}.
 */
public interface ActionHandler {
   /**
    * Returns the list of action type (subclasses) that can be handled by this action handler.
    *
    * @return A list of {@link Action} classes that can be handled.
    */
   List<Class<? extends Action>> getHandledActionTypes();

   /**
    * Validates whether the given {@link Action} can be handled by this action handler.
    * The default implementation uses the list of handled action types ({@link ActionHandler#getHandledActionTypes()}
    * to determine whether this handler can handle an action. Only actions that are instances of one of these types can
    * be handled.
    *
    * @param action The action that should be validated.
    * @return `true` if the given action can be handled, `false` otherwise.
    */
   default boolean handles(final Action action) {
      return getHandledActionTypes().stream().anyMatch(clazz -> clazz.isInstance(action));
   }

   /**
    * Executes the action handler for the given {@link Action} and returns a list of response actions that should be
    * dispatched as a result of processing the original action. This list can be empty, if no more actions need to be executed.
    * list is returned.
    *
    * @param action The action that should be processed.
    * @return A list of response actions that should be dispatched.
    */
   List<Action> execute(Action action);

   /**
    * Helper method to convert the given {@link Action} to a {@link List}.
    *
    * @param action One ore more action objects that should be converted.
    * @return The given action objects as list.
    */
   default List<Action> listOf(final Action... action) {
      return Arrays.asList(action);
   }

   /**
    * Helper method to convert the given {@link Optional} action to a {@link List}.
    *
    * @param optionalAction The optional action that should be converted.
    * @return A list of the given action or an empty list if no value was present.
    */
   default List<Action> listOf(final Optional<Action> optionalAction) {
      List<Action> actions = new ArrayList<>();
      optionalAction.ifPresent(action -> actions.add(action));
      return actions;
   }

   /**
    * Helper method that can be used to return an empty {@link List} of {@link Action}s.
    *
    * @return An empty action list.
    */
   default List<Action> none() {
      return Collections.emptyList();
   }

   /**
    * Returns the priority of this action handler. The priority is used to derive the execution order if multiple
    * action handlers should execute the same {@link Action}. The default priority is `0` and the priority is sorted
    * descending. This means handlers with a priority >0 are executed before handlers with a default priority and
    * handlers with a
    * priority <0 are executed afterwards.
    *
    * @return the priority as integer.
    */
   default int getPriority() { return 0; }

}
