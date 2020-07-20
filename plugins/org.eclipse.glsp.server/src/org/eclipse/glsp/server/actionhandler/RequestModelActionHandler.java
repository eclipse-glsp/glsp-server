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
import org.eclipse.glsp.api.action.kind.RequestBoundsAction;
import org.eclipse.glsp.api.action.kind.RequestModelAction;
import org.eclipse.glsp.api.action.kind.SetModelAction;
import org.eclipse.glsp.api.factory.ModelFactory;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.utils.ClientOptions;
import org.eclipse.glsp.graph.GModelRoot;

import com.google.inject.Inject;

public class RequestModelActionHandler extends BasicActionHandler<RequestModelAction> {

   @Inject
   protected ModelFactory modelFactory;

   @Override
   public List<Action> executeAction(final RequestModelAction action, final GraphicalModelState modelState) {

      GModelRoot model = modelFactory.loadModel(action, modelState);
      modelState.setRoot(model);
      modelState.setClientOptions(action.getOptions());
      boolean needsClientLayout = ClientOptions.getBoolValue(action.getOptions(),
         ClientOptions.NEEDS_CLIENT_LAYOUT);

      Action responseAction = needsClientLayout ? new RequestBoundsAction(modelState.getRoot())
         : new SetModelAction(modelState.getRoot());
      return listOf(responseAction);

   }
}
