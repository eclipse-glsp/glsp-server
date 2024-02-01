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
package org.eclipse.glsp.server.features.typehints;

import static org.eclipse.glsp.server.types.GLSPServerException.getOrThrow;

import java.util.List;
import java.util.Optional;

import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.server.actions.AbstractActionHandler;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.diagram.DiagramConfiguration;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.types.GLSPServerException;

import com.google.inject.Inject;

/**
 * Default handler implementation for {@link RequestCheckEdgeAction}.
 * Delegates the edge check to an {@link EdgeCreationChecker} if the given edge information is applicable
 * (i.e. a checker is bound,the given edge type has a dynamic type hint and source/target elemnent are present in the
 * diagram).
 * Returns a valid {@link CheckEdgeResultAction} if no edge creation checker is bound or the type hint associated with
 * the given edge information is not dynamic.
 */
public class RequestCheckEdgeActionHandler extends AbstractActionHandler<RequestCheckEdgeAction> {

   @Inject
   protected Optional<EdgeCreationChecker> edgeCreationChecker;

   @Inject
   protected DiagramConfiguration diagramConfiguration;

   @Inject
   protected GModelState modelState;

   @Override
   protected List<Action> executeAction(final RequestCheckEdgeAction action) {
      boolean hasDynamicHint = diagramConfiguration.getEdgeTypeHints().stream()
         .filter(hint -> hint.getElementTypeId().equals(action.getEdgeType()) && hint.isDynamic()).findAny()
         .isPresent();

      if (!edgeCreationChecker.isPresent() || !hasDynamicHint) {
         return listOf(new CheckEdgeResultAction(true, action));
      }
      return listOf(new CheckEdgeResultAction(validate(action), action));
   }

   protected boolean validate(final RequestCheckEdgeAction action) {
      GModelElement sourceElement = getOrThrow(modelState.getIndex().get(action.getSourceElementId()),
         "Invalid `RequestCheckEdgeTargetAction`!. Could not find a source elemment with id: "
            + action.getSourceElementId());
      Optional<GModelElement> targetElement = action.getTargetElementId()
         .flatMap(targetId -> modelState.getIndex().get(targetId));

      if (action.getTargetElementId().isPresent() && targetElement.isEmpty()) {
         throw new GLSPServerException(
            "Invalid `RequestCheckEdgeTargetAction`! Could not find a target element with id: "
               + action.getTargetElementId().get());
      }
      return targetElement.isPresent()
         ? edgeCreationChecker.get().isValidTarget(action.getEdgeType(), sourceElement, targetElement.get())
         : edgeCreationChecker.get().isValidSource(action.getEdgeType(), sourceElement);

   }

}
