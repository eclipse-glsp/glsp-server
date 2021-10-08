/********************************************************************************
 * Copyright (c) 2019-2021 EclipseSource and others.
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
package org.eclipse.glsp.server.operations.gmodel;

import static org.eclipse.glsp.server.types.GLSPServerException.getOrThrow;

import org.apache.log4j.Logger;
import org.eclipse.glsp.graph.GDimension;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GModelIndex;
import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.builder.impl.GLayoutOptions;
import org.eclipse.glsp.graph.util.GraphUtil;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.operations.BasicOperationHandler;
import org.eclipse.glsp.server.operations.ChangeBoundsOperation;
import org.eclipse.glsp.server.types.ElementAndBounds;

/**
 * Generic handler implementation for {@link ChangeBoundsOperation}.
 */
public class ChangeBoundsOperationHandler extends BasicOperationHandler<ChangeBoundsOperation> {

   private static Logger log = Logger.getLogger(ChangeBoundsOperationHandler.class);

   @Override
   public void executeOperation(final ChangeBoundsOperation operation, final GModelState modelState) {
      for (ElementAndBounds element : operation.getNewBounds()) {
         changeElementBounds(element.getElementId(), element.getNewPosition(), element.getNewSize(), modelState);
      }
   }

   private void changeElementBounds(final String elementId, final GPoint newPosition, final GDimension newSize,
      final GModelState modelState) {
      if (elementId == null) {
         log.warn("Invalid ChangeBounds Action; missing mandatory arguments");
         return;
      }

      GModelIndex index = modelState.getIndex();
      GNode nodeToUpdate = getOrThrow(index.findElementByClass(elementId, GNode.class),
         "GNode with id " + elementId + " not found");

      GModelElement parent = nodeToUpdate.getParent();
      GPoint positionToSet = parent instanceof GModelRoot
         // For root nodes (Owned by the model root), allow negative coordinates
         ? newPosition
         // For child nodes (Owned by another node or compartment), restrict the movement
         // to positive coordinates, to avoid weird layout behavior
         : GraphUtil.point(Math.max(0, newPosition.getX()), Math.max(0, newPosition.getY()));

      nodeToUpdate.getLayoutOptions().put(GLayoutOptions.KEY_PREF_WIDTH, newSize.getWidth());
      nodeToUpdate.getLayoutOptions().put(GLayoutOptions.KEY_PREF_HEIGHT, newSize.getHeight());

      nodeToUpdate.setPosition(positionToSet);
   }
}
