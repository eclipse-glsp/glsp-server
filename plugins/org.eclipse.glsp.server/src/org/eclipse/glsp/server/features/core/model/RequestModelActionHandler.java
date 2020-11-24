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
package org.eclipse.glsp.server.features.core.model;

import java.util.List;

import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.BasicActionHandler;
import org.eclipse.glsp.server.features.modelsourcewatcher.ModelSourceWatcher;
import org.eclipse.glsp.server.model.GModelState;

import com.google.inject.Inject;

public class RequestModelActionHandler extends BasicActionHandler<RequestModelAction> {

   @Inject
   protected ModelFactory modelFactory;

   @Inject
   private ModelSourceWatcher modelSourceWatcher;

   @Inject
   protected ModelSubmissionHandler modelSubmissionHandler;

   @Override
   public List<Action> executeAction(final RequestModelAction action, final GModelState modelState) {
      modelState.setClientOptions(action.getOptions());
      GModelRoot model = modelFactory.loadModel(action, modelState);
      modelState.setRoot(model);
      modelSourceWatcher.startWatching(modelState);
      return modelSubmissionHandler.submitModel(false, modelState);
   }

}
