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

import java.util.List;
import java.util.Optional;

import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.server.actions.AbstractActionHandler;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.diagram.DiagramConfiguration;
import org.eclipse.glsp.server.model.GModelState;

import com.google.inject.Inject;

/**
 * Default handler implementation for {@link RequestCheckEdgeAction}.
 * Delegates the edge check to an {@link EdegeCreationChecker} if the given edge information is applicable
 * (i.e. a checker is bound,the given edge type has a dynamic type hint and source/target elemnent are present in the
 * diagram).
 * Otherwise returns `false`.
 */
public class RequestCheckEdgeTargetActionHandler extends AbstractActionHandler<RequestCheckEdgeAction> {

   @Inject
   protected Optional<EdegeCreationChecker> edgeTargetChecker;

   @Inject
   protected DiagramConfiguration diagramConfiguration;

   @Inject
   protected GModelState modelState;

   @Override
   protected List<Action> executeAction(final RequestCheckEdgeAction action) {
      return List.of(new CheckEdgeTargetResultAction(validate(action), action));
   }

   protected boolean validate(final RequestCheckEdgeAction action) {
      if (!edgeTargetChecker.isPresent()) {
         return false;
      }
      boolean hasDynamicHint = diagramConfiguration.getEdgeTypeHints().stream()
         .filter(hint -> hint.getElementTypeId().equals(action.getEdgeType()) && hint.isDynamic()).findAny()
         .isPresent();
      if (!hasDynamicHint) {
         return false;
      }
      Optional<GModelElement> sourceElement = modelState.getIndex().get(action.getSourceElementId());
      Optional<GModelElement> targetElement = action.getTargetElementId()
         .flatMap(targetId -> modelState.getIndex().get(targetId));

      if (sourceElement.isEmpty()) {
         return false;
      }
      return targetElement.isPresent()
         ? edgeTargetChecker.get().isValidTarget(action.getEdgeType(), sourceElement.get(), targetElement.get())
         : edgeTargetChecker.get().isValidSource(action.getEdgeType(), sourceElement.get());

   }

}
