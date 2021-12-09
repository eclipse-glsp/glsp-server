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

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.glsp.graph.GBoundsAware;
import org.eclipse.glsp.graph.GGraph;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GModelIndex;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.util.GraphUtil;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.actions.SelectAction;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.operations.AbstractCreateOperationHandler;
import org.eclipse.glsp.server.operations.CreateNodeOperation;
import org.eclipse.glsp.server.utils.GeometryUtil;

import com.google.inject.Inject;

public abstract class CreateNodeOperationHandler extends AbstractCreateOperationHandler<CreateNodeOperation> {

   @Inject
   protected GModelState modelState;

   @Inject
   protected ActionDispatcher actionDispatcher;

   public CreateNodeOperationHandler(final String elementTypeId) {
      super(elementTypeId);
   }

   @Override
   public void executeOperation(final CreateNodeOperation operation) {

      Optional<GModelElement> container = getContainer(operation);
      if (!container.isPresent()) {
         container = Optional.of(modelState.getRoot());
      }

      Optional<GPoint> absoluteLocation = getLocation(operation);
      Optional<GPoint> relativeLocation = getRelativeLocation(operation, absoluteLocation, container);
      GModelElement element = createNode(relativeLocation, operation.getArgs());
      container.get().getChildren().add(element);
      actionDispatcher.dispatchAfterNextUpdate(new SelectAction(), new SelectAction(List.of(element.getId())));
   }

   /**
    * <p>
    * Return the GModelElement that will contain the newly created node. It is usually
    * the target element ({@link CreateNodeOperation#getContainerId()}), but could also
    * be e.g. an intermediate compartment, or even a different Node.
    * </p>
    *
    * @param operation
    * @return
    *         the GModelElement that will contain the newly created node.
    */
   protected Optional<GModelElement> getContainer(final CreateNodeOperation operation) {
      GModelIndex index = modelState.getIndex();
      return index.get(operation.getContainerId());
   }

   /**
    * Return the absolute location where the element should be created.
    *
    * @param operation
    * @return
    *         the absolute location where the element should be created.
    */
   protected Optional<GPoint> getLocation(final CreateNodeOperation operation) {
      return operation.getLocation();
   }

   /**
    * Retrieve the location at which the new node should be created, in the container
    * coordinates space.
    *
    * @param operation
    * @param absoluteLocation
    * @param container
    * @return
    *         A point relative to the container element, where the new node should be created
    */
   protected Optional<GPoint> getRelativeLocation(final CreateNodeOperation operation,
      final Optional<GPoint> absoluteLocation, final Optional<GModelElement> container) {
      if (absoluteLocation.isPresent() && container.isPresent()) {
         // When creating elements on a parent node (other than the root Graph),
         // prevent the node from using negative coordinates
         boolean allowNegativeCoordinates = container.get() instanceof GGraph;
         GModelElement modelElement = container.get();
         if (modelElement instanceof GBoundsAware) {
            try {
               GPoint relativePosition = GeometryUtil.absoluteToRelative(absoluteLocation.get(),
                  (GBoundsAware) modelElement);
               GPoint relativeLocation = allowNegativeCoordinates
                  ? relativePosition
                  : GraphUtil.point(Math.max(0, relativePosition.getX()), Math.max(0, relativePosition.getY()));
               return Optional.of(relativeLocation);
            } catch (IllegalArgumentException ex) {
               return absoluteLocation;
            }
         }
      }
      return Optional.empty();
   }

   /**
    * Create and return the new Node at the specified (optional) location. The location
    * is given in coordinates relative to the {@link CreateNodeOperationHandler#getContainer(CreateNodeOperation)}
    * container.
    *
    * @param relativeLocation
    * @param args
    * @return
    *         The created {@link GNode Node}.
    */
   protected abstract GNode createNode(Optional<GPoint> relativeLocation, Map<String, String> args);
}
