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
package org.eclipse.glsp.server.actions;

import java.util.Arrays;
import java.util.List;

import org.eclipse.glsp.server.internal.util.GenericsUtil;
import org.eclipse.glsp.server.model.GModelState;

public abstract class BasicActionHandler<T extends Action> implements ActionHandler {
   protected final Class<T> actionType;

   public BasicActionHandler() {
      this.actionType = deriveActionType();
   }

   @SuppressWarnings("unchecked")
   protected Class<T> deriveActionType() {
      return (Class<T>) GenericsUtil.getGenericTypeParameterClass(getClass(), BasicActionHandler.class);
   }

   @Override
   public boolean handles(final Action action) {
      return actionType.isInstance(action);
   }

   @Override
   public List<Action> execute(final Action action, final GModelState modelState) {
      if (handles(action)) {
         T actualAction = actionType.cast(action);
         return executeAction(actualAction, modelState);
      }
      return none();
   }

   protected abstract List<Action> executeAction(T actualAction, GModelState modelState);

   public Class<T> getActionType() { return actionType; }

   @Override
   public List<Class<? extends Action>> getHandledActionTypes() {
      return Arrays.asList(actionType);
   }

}
