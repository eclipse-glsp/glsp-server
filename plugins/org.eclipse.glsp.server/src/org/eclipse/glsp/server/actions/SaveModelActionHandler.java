/********************************************************************************
 * Copyright (c) 2019-2022 EclipseSource and others.
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

import java.util.List;
import java.util.Optional;

import org.eclipse.glsp.server.features.core.model.SourceModelStorage;
import org.eclipse.glsp.server.features.sourcemodelwatcher.SourceModelWatcher;
import org.eclipse.glsp.server.model.GModelState;

import com.google.inject.Inject;

/**
 * Default handler for the {@link SaveModelAction}.
 *
 * The implementation delegates to the {@link SourceModelStorage} to perform the save.
 */
public class SaveModelActionHandler extends AbstractActionHandler<SaveModelAction> {

   @Inject
   protected Optional<SourceModelWatcher> modelSourceWatcher;

   @Inject
   protected GModelState modelState;

   @Inject
   protected SourceModelStorage sourceModelStorage;

   @Override
   public List<Action> executeAction(final SaveModelAction action) {
      modelSourceWatcher.ifPresent(watcher -> watcher.pauseWatching());
      try {
         sourceModelStorage.saveSourceModel(action);
         modelState.saveIsDone(action.getSubclientId());
      } finally {
         modelSourceWatcher.ifPresent(watcher -> watcher.continueWatching());
      }
      return listOf(new SetDirtyStateAction(modelState.isDirty(action.getSubclientId()), SetDirtyStateAction.Reason.SAVE));
   }

}
