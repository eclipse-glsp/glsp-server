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
package org.eclipse.glsp.api.provider;

import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.handler.ActionHandler;
import org.eclipse.glsp.api.handler.Handler;

public interface ActionHandlerProvider {
   Set<ActionHandler> getActionHandlers();

   default boolean isHandled(final Action action) {
      return getHandler(action).isPresent();
   }

   default Optional<ActionHandler> getHandler(final Action action) {
      return getActionHandlers().stream().sorted(Comparator.comparing(Handler::getPriority))
         .filter(ha -> ha.handles(action)).findFirst();
   }

   final class NullImpl implements ActionHandlerProvider {

      @Override
      public Set<ActionHandler> getActionHandlers() { return Collections.emptySet(); }

   }

}
