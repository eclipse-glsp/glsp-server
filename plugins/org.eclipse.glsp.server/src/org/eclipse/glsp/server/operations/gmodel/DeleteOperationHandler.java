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
package org.eclipse.glsp.server.operations.gmodel;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GModelIndex;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.operations.BasicOperationHandler;
import org.eclipse.glsp.server.operations.DeleteOperation;

/**
 * Generic handler implementation for {@link DeleteOperation}.
 */
public class DeleteOperationHandler extends BasicOperationHandler<DeleteOperation> {
   private static Logger log = Logger.getLogger(DeleteOperationHandler.class);
   private Set<String> allDependantsIds;

   @Override
   public void executeOperation(final DeleteOperation operation, final GModelState modelState) {
      List<String> elementIds = operation.getElementIds();
      if (elementIds == null || elementIds.size() == 0) {
         log.warn("Elements to delete are not specified");
         return;
      }
      GModelIndex index = modelState.getIndex();
      allDependantsIds = new HashSet<>();
      boolean success = elementIds.stream().allMatch(eId -> delete(eId, index, modelState));
      if (!success) {
         log.warn("Could not delete all elements as requested (see messages above to find out why)");
      }
   }

   protected boolean delete(final String elementId, final GModelIndex index, final GModelState modelState) {
      if (allDependantsIds.contains(elementId)) {
         // The element as already been deleted as dependent of a previously deleted
         // element
         return true;
      }

      Optional<GModelElement> element = index.get(elementId);
      if (!element.isPresent()) {
         log.warn("Element not found: " + elementId);
         return false;
      }

      // Always delete the top-level node
      GModelElement nodeToDelete = findTopLevelElement(element.get());
      if (nodeToDelete.getParent() == null) {
         log.warn("The requested node doesn't have a parent; it can't be deleted");
         return false; // Can't delete the root, or an element that doesn't belong to the model
      }

      Set<GModelElement> dependents = new LinkedHashSet<>();
      collectDependents(dependents, nodeToDelete, modelState);

      dependents.forEach(EcoreUtil::delete);
      allDependantsIds.addAll(dependents.stream().map(GModelElement::getId).collect(Collectors.toSet()));
      return true;
   }

   protected void collectDependents(final Set<GModelElement> dependents, final GModelElement nodeToDelete,
      final GModelState modelState) {
      if (dependents.contains(nodeToDelete)) {
         return;
      }

      // First, children
      if (nodeToDelete.getChildren() != null) {
         for (GModelElement child : nodeToDelete.getChildren()) {
            collectDependents(dependents, child, modelState);
         }
      }

      // Then, incoming/outgoing edges for nodes
      if (nodeToDelete instanceof GNode) {
         GModelIndex index = modelState.getIndex();

         // Then, incoming/outgoing links
         for (GModelElement incoming : index.getIncomingEdges(nodeToDelete)) {
            collectDependents(dependents, incoming, modelState);
         }
         for (GModelElement outgoing : index.getOutgoingEdges(nodeToDelete)) {
            collectDependents(dependents, outgoing, modelState);
         }
      }

      // Finally, the node to delete
      dependents.add(nodeToDelete);
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
