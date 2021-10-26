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
package org.eclipse.glsp.server.features.navigation;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.AbstractActionHandler;
import org.eclipse.glsp.server.model.GModelState;

import com.google.inject.Inject;

public class ResolveNavigationTargetActionHandler extends AbstractActionHandler<ResolveNavigationTargetAction> {

   private static final Logger LOG = Logger.getLogger(ResolveNavigationTargetActionHandler.class);

   @Inject
   protected Optional<NavigationTargetResolver> navigationTargetResolver;

   @Inject
   protected GModelState modelState;

   @Override
   public List<Action> executeAction(final ResolveNavigationTargetAction action) {
      if (navigationTargetResolver.isEmpty()) {
         LOG.warn("Could not resolve navigation target. No implementation for: "
            + NavigationTargetResolver.class.getName() + " has been bound.");
         return none();
      }
      NavigationTarget target = action.getNavigationTarget();
      NavigationTargetResolution resolution = this.navigationTargetResolver.get().resolve(target, modelState);
      return listOf(new SetResolvedNavigationTargetAction(resolution.getElementIds(), resolution.getArgs()));
   }
}
