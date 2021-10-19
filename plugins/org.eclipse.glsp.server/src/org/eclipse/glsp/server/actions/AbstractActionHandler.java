/********************************************************************************
 * Copyright (c) 2019-2021 EclipseSource and others.
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

import java.util.Arrays;
import java.util.List;

import org.eclipse.glsp.server.internal.util.GenericsUtil;

/**
 * Basic {@link ActionHandler} implementation that can handle exactly one {@link Action} type/class.
 * It handles the overhead of casting the action object received via the {@link ActionHandler#execute(Action)} method
 * to the correct handled subtype. Subclasses only have to implement the
 * {@link AbstractActionHandler#executeAction(Action)} method
 * and can work directly with the correct subtype instead of having to manually cast it.
 *
 * @param <A> class of the handled action type
 */
public abstract class AbstractActionHandler<A extends Action> implements ActionHandler {
   protected final Class<A> actionType;

   public AbstractActionHandler() {
      this.actionType = deriveActionType();
   }

   @SuppressWarnings("unchecked")
   protected Class<A> deriveActionType() {
      return (Class<A>) GenericsUtil.getGenericTypeParameterClass(getClass(), AbstractActionHandler.class);
   }

   @Override
   public boolean handles(final Action action) {
      return actionType.isInstance(action);
   }

   @Override
   public List<Action> execute(final Action action) {
      if (handles(action)) {
         A actualAction = actionType.cast(action);
         return executeAction(actualAction);
      }
      return none();
   }

   /**
    * Executes the action handler for the given {@link Action} and returns a list of response actions that should be
    * dispatched by the {@link ActionDispatcher}. If the given action cannot be handled by this action handler an empty
    * list is returned.
    *
    * @param actualAction The action that should be processed.
    * @return A list of response actions that should be dispatched.
    */
   protected abstract List<Action> executeAction(A actualAction);

   /**
    * Returns the {@link Class} of the {@link Action} subtype that can be handled by this action handler.
    *
    * @return the handled action subclass.
    */
   public Class<A> getActionType() { return actionType; }

   @Override
   public List<Class<? extends Action>> getHandledActionTypes() { return Arrays.asList(actionType); }

}
