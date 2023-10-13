/********************************************************************************
 * Copyright (c) 2019-2023 EclipseSource and others.
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
package org.eclipse.glsp.server.gmodel;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GModelIndex;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.operations.DeleteOperation;
import org.eclipse.glsp.server.operations.GModelOperationHandler;

/**
 * Applies {@link DeleteOperation} directly to the GModel.
 */
public class GModelDeleteOperationHandler extends GModelOperationHandler<DeleteOperation> {
   private static Logger LOGGER = LogManager.getLogger(GModelDeleteOperationHandler.class);
   protected Set<String> allDependantsIds;

   @Override
   public Optional<Command> createCommand(final DeleteOperation operation) {
      List<String> elementIds = operation.getElementIds();
      if (elementIds == null || elementIds.size() == 0) {
         LOGGER.warn("Elements to delete are not specified");
         return doNothing();
      }
      return commandOf(() -> deleteElements(elementIds));
   }

   public void deleteElements(final List<String> elementIds) {
      GModelIndex index = modelState.getIndex();
      allDependantsIds = new HashSet<>();
      boolean success = elementIds.stream().allMatch(eId -> delete(eId, index));
      if (!success) {
         LOGGER.warn("Could not delete all elements as requested (see messages above to find out why)");
      }
   }

   protected boolean delete(final String elementId, final GModelIndex index) {
      if (allDependantsIds.contains(elementId)) {
         // The element as already been deleted as dependent of a previously deleted
         // element
         return true;
      }

      Optional<GModelElement> element = index.get(elementId);
      if (!element.isPresent()) {
         LOGGER.warn("Element not found: " + elementId);
         return false;
      }

      // Always delete the top-level element
      GModelElement elementToDelete = findTopLevelElement(element.get());
      if (elementToDelete.getParent() == null) {
         LOGGER.warn("The requested node doesn't have a parent; it can't be deleted");
         return false; // Can't delete the root, or an element that doesn't belong to the model
      }

      Set<GModelElement> dependents = new LinkedHashSet<>();
      collectDependents(dependents, elementToDelete, modelState, false);

      allDependantsIds.addAll(dependents.stream().map(GModelElement::getId).collect(Collectors.toSet()));
      dependents.forEach(EcoreUtil::delete);
      return true;
   }

   protected void collectDependents(final Set<GModelElement> dependents, final GModelElement nodeToDelete,
      final GModelState modelState, final boolean isChild) {

      if (dependents.contains(nodeToDelete)) {
         return;
      }

      // First, children
      if (nodeToDelete.getChildren() != null) {
         for (GModelElement child : nodeToDelete.getChildren()) {
            collectDependents(dependents, child, modelState, true);
         }
      }

      // Then, incoming/outgoing edges for nodes
      if (nodeToDelete instanceof GNode) {
         GModelIndex index = modelState.getIndex();
         dependents.addAll(index.getIncomingEdges(nodeToDelete));
         dependents.addAll(index.getOutgoingEdges(nodeToDelete));
      }

      // Finally, the node to delete
      if (!isChild) {
         dependents.add(nodeToDelete);
      }
   }

   protected GModelElement findTopLevelElement(final GModelElement element) {
      if (element instanceof GNode || element instanceof GEdge) {
         return element;
      }

      GModelElement parent = element.getParent();
      if (parent == null) {
         return element;
      }
      return findTopLevelElement(parent);
   }
}
