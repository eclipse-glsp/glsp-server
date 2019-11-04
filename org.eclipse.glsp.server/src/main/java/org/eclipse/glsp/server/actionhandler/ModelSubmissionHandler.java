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

import java.util.Optional;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.kind.SetModelAction;
import org.eclipse.glsp.api.action.kind.UpdateModelAction;
import org.eclipse.glsp.api.configuration.ServerConfiguration;
import org.eclipse.glsp.api.layout.ILayoutEngine;
import org.eclipse.glsp.api.layout.ServerLayoutKind;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.graph.GModelRoot;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ModelSubmissionHandler {
   @Inject(optional = true)
   protected ILayoutEngine layoutEngine = new ILayoutEngine.NullImpl();
   @Inject
   protected ServerConfiguration serverConfiguration;
   private final Object modelLock = new Object();
   private final int revision = 0;

   public Optional<Action> doSubmitModel(final boolean update, final GraphicalModelState modelState) {
      GModelRoot newRoot = modelState.getRoot();
      if (serverConfiguration.getLayoutKind() == ServerLayoutKind.AUTOMATIC) {
         layoutEngine.layout(modelState);
      }
      synchronized (modelLock) {
         if (newRoot.getRevision() == revision) {
            if (update) {
               return Optional.of(new UpdateModelAction(newRoot, true));
            } else {
               return Optional.of(new SetModelAction(newRoot));
            }
         }
      }
      return Optional.empty();
   }

   public synchronized Object getModelLock() { return modelLock; }
}
