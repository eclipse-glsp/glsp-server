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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.kind.RequestNavigationTargetsAction;
import org.eclipse.glsp.api.action.kind.SetNavigationTargetsAction;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.registry.NavigationTargetProviderRegistry;
import org.eclipse.glsp.api.types.EditorContext;
import org.eclipse.glsp.api.types.NavigationTarget;

import com.google.inject.Inject;

public class RequestNavigationTargetsActionHandler extends BasicActionHandler<RequestNavigationTargetsAction> {

   @Inject
   protected NavigationTargetProviderRegistry navigationTargetProviderRegistry;

   @Override
   public List<Action> executeAction(final RequestNavigationTargetsAction action,
      final GraphicalModelState modelState) {
      EditorContext editorContext = action.getEditorContext();
      List<NavigationTarget> allTargets = new ArrayList<>();
      navigationTargetProviderRegistry.get(action.getTargetTypeId())
         .map(provider -> provider.getTargets(editorContext, modelState))
         .ifPresent(targets -> allTargets.addAll(targets));
      return listOf(new SetNavigationTargetsAction(allTargets, action.getEditorContext().getArgs()));
   }
}
