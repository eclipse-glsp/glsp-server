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
package org.eclipse.glsp.server.actionhandler;

import java.util.List;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.kind.ResolveNavigationTargetAction;
import org.eclipse.glsp.api.action.kind.SetResolvedNavigationTargetAction;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.model.NavigationTargetResolver;
import org.eclipse.glsp.api.types.NavigationTarget;
import org.eclipse.glsp.api.types.NavigationTargetResolution;

import com.google.inject.Inject;

public class ResolveNavigationTargetActionHandler extends BasicActionHandler<ResolveNavigationTargetAction> {

   @Inject
   protected NavigationTargetResolver navigationTargetResolver;

   @Override
   public List<Action> executeAction(final ResolveNavigationTargetAction action, final GraphicalModelState modelState) {
      NavigationTarget target = action.getNavigationTarget();
      NavigationTargetResolution resolution = this.navigationTargetResolver.resolve(target, modelState);
      return listOf(new SetResolvedNavigationTargetAction(resolution.getElementIds(), resolution.getArgs()));
   }
}
