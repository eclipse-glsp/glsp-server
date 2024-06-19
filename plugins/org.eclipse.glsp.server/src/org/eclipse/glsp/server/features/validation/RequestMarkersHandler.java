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
package org.eclipse.glsp.server.features.validation;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.server.actions.AbstractActionHandler;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.model.GModelState;

import com.google.inject.Inject;

public class RequestMarkersHandler extends AbstractActionHandler<RequestMarkersAction> {

   protected static final Logger LOGGER = LogManager.getLogger(RequestMarkersHandler.class);

   @Inject
   protected Optional<ModelValidator> validator;

   @Inject
   protected GModelState modelState;

   @Override
   @SuppressWarnings("checkstyle:cyclomaticComplexity")
   public List<Action> executeAction(final RequestMarkersAction action) {
      List<String> elementIds = action.getElementsIDs();
      if (validator.isEmpty()) {
         LOGGER.warn("Cannot compute markers! No implementation for " + ModelValidator.class + " has been bound");
         return none();
      }

      // if no element ID is provided, compute the markers for the complete model
      if (elementIds == null || elementIds.size() == 0
         || (elementIds.size() == 1 && "EMPTY".equals(elementIds.get(0)))) {
         elementIds = Arrays.asList(modelState.getRoot().getId());
      }

      List<GModelElement> elements = elementIds.stream()
         .flatMap(element -> modelState.getIndex().get(element).stream())
         .collect(Collectors.toList());
      List<Marker> markers = validator.get().validate(elements, action.getReason());

      return listOf(new SetMarkersAction(markers, action.getReason()));
   }
}
