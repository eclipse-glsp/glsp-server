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
package org.eclipse.glsp.server.diagram;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.BasicActionHandler;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.utils.ClientOptions;

import com.google.inject.Inject;

public class RequestTypeHintsActionHandler extends BasicActionHandler<RequestTypeHintsAction> {
   private final Logger log = Logger.getLogger(RequestTypeHintsActionHandler.class);
   @Inject
   protected DiagramConfigurationRegistry diagramConfigurationRegistry;

   @Override
   public List<Action> executeAction(final RequestTypeHintsAction action, final GModelState modelState) {

      Optional<String> diagramType = getDiagramType(action, modelState);
      if (!diagramType.isPresent()) {
         log.info("RequestTypeHintsAction failed: No diagram type is present");
         return none();
      }
      Optional<DiagramConfiguration> configuration = diagramConfigurationRegistry.get(diagramType.get());
      if (!configuration.isPresent()) {
         log.info("RequestTypeHintsAction failed: No diagram confiuration found for : " + diagramType.get());
         return none();
      }

      return listOf(new SetTypeHintsAction(configuration.get().getNodeTypeHints(),
         configuration.get().getEdgeTypeHints()));

   }

   private Optional<String> getDiagramType(final RequestTypeHintsAction action, final GModelState modelState) {
      if (action.getDiagramType() == null && !action.getDiagramType().isEmpty()) {
         return Optional.of(action.getDiagramType());
      }
      return ClientOptions.getValue(modelState.getClientOptions(), ClientOptions.DIAGRAM_TYPE);
   }
}
