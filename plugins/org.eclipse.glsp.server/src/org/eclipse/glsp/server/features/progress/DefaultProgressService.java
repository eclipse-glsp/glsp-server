/********************************************************************************
 * Copyright (c) 2023 EclipseSource and others.
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
package org.eclipse.glsp.server.features.progress;

import java.util.UUID;

import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.actions.UpdateProgressAction;

import com.google.inject.Inject;

public class DefaultProgressService implements ProgressService {

   @Inject
   protected ActionDispatcher actionDispatcher;

   @Override
   public ProgressMonitor start(final String title, final String message, final int percentage) {
      String progressId = UUID.randomUUID().toString();
      actionDispatcher.dispatch(new StartProgressAction(progressId, title, message, percentage));
      return new ProgressMonitor() {

         @Override
         public void update(final String message, final int percentage) {
            actionDispatcher.dispatch(new UpdateProgressAction(progressId, message, percentage));
         }

         @Override
         public void end() {
            actionDispatcher.dispatch(new EndProgressAction(progressId));
         }
      };
   }

}
