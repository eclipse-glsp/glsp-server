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
package org.eclipse.glsp.api.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.model.GraphicalModelState;

public interface ActionHandler extends Handler<Action> {
   List<Class<? extends Action>> handledActionTypes();

   @Override
   default boolean handles(final Action action) {
      return handledActionTypes().stream().anyMatch(clazz -> clazz.isInstance(action));
   }

   default List<Action> listOf(final Action... action) {
      return Arrays.asList(action);
   }

   default List<Action> listOf(final Optional<Action> optionalAction) {
      List<Action> actions = new ArrayList<>();
      optionalAction.ifPresent(action -> actions.add(action));
      return actions;
   }

   List<Action> execute(Action action, GraphicalModelState modelState);

   default List<Action> none() {
      return Collections.emptyList();
   }
}
