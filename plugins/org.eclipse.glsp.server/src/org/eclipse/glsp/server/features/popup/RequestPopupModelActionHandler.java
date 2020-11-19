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
package org.eclipse.glsp.server.features.popup;

import java.util.List;
import java.util.Optional;

import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.BasicActionHandler;
import org.eclipse.glsp.server.model.GModelState;

import com.google.inject.Inject;

public class RequestPopupModelActionHandler extends BasicActionHandler<RequestPopupModelAction> {
   @Inject
   protected PopupModelFactory popupModelFactory;

   @Override
   public List<Action> executeAction(final RequestPopupModelAction action, final GModelState modelState) {
      Optional<GModelElement> element = modelState.getIndex().get(action.getElementId());
      if (popupModelFactory != null && element.isPresent()) {
         return listOf(popupModelFactory.createPopupModel(element.get(), action, modelState)
            .map(popupModel -> new SetPopupModelAction(popupModel, action.getBounds())));
      }
      return none();
   }

}
