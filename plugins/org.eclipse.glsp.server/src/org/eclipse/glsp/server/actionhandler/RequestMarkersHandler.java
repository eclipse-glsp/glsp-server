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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.kind.RequestMarkersAction;
import org.eclipse.glsp.api.action.kind.SetMarkersAction;
import org.eclipse.glsp.api.markers.Marker;
import org.eclipse.glsp.api.markers.ModelValidator;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GModelIndex;

import com.google.inject.Inject;

public class RequestMarkersHandler extends BasicActionHandler<RequestMarkersAction> {

   @Inject
   protected ModelValidator validator;

   @Override
   public List<Action> executeAction(final RequestMarkersAction action, final GraphicalModelState modelState) {
      List<String> elementsIDs = action.getElementsIDs();

      // if no element ID is provided, compute the markers for the complete model
      if (elementsIDs == null || elementsIDs.size() == 0
         || (elementsIDs.size() == 1 && "EMPTY".equals(elementsIDs.get(0)))) {
         elementsIDs = Arrays.asList(modelState.getRoot().getId());
      }
      List<Marker> markers = new ArrayList<>();
      GModelIndex currentModelIndex = modelState.getIndex();
      for (String elementID : elementsIDs) {
         Optional<GModelElement> modelElement = currentModelIndex.get(elementID);
         if (modelElement.isPresent()) {
            markers.addAll(validator.validate(modelState, modelElement.get()));
         }

      }

      return listOf(new SetMarkersAction(markers));
   }
}
