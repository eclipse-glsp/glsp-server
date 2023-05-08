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
package org.eclipse.glsp.server.gmodel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.actions.SelectAction;
import org.eclipse.glsp.server.operations.CreateEdgeOperation;
import org.eclipse.glsp.server.operations.CreateNodeOperation;
import org.eclipse.glsp.server.utils.LayoutUtil;

import com.google.inject.Inject;

/**
 * Abstract base class for applying an {@link CreateEdgeOperation} directly to the GModel.
 */
public abstract class GModelCreateNodeOperationHandler
   extends GModelCreateOperationHandler<CreateNodeOperation> {

   @Inject
   protected ActionDispatcher actionDispatcher;

   public GModelCreateNodeOperationHandler(final String... elementTypeIds) {
      super(elementTypeIds);
   }

   public GModelCreateNodeOperationHandler(final List<String> handledElementTypeIds) {
      super(handledElementTypeIds);
   }

   @Override
   public Optional<Command> createCommand(final CreateNodeOperation operation) {
      return commandOf(() -> executeCreation(operation));
   }

   public void executeCreation(final CreateNodeOperation operation) {
      GModelElement container = getContainer(operation).orElseGet(modelState::getRoot);
      Optional<GPoint> absoluteLocation = getLocation(operation);
      Optional<GPoint> relativeLocation = getRelativeLocation(container, absoluteLocation);
      GModelElement element = createNode(relativeLocation, operation.getArgs());
      container.getChildren().add(element);
      actionDispatcher.dispatchAfterNextUpdate(
              Action.addSubclientId(operation, new SelectAction()),
              Action.addSubclientId(operation, new SelectAction(List.of(element.getId())))
      );
   }

   protected Optional<GPoint> getLocation(final CreateNodeOperation operation) {
      return operation.getLocation();
   }

   protected Optional<GPoint> getRelativeLocation(final GModelElement container,
      final Optional<GPoint> absoluteLocation) {
      return absoluteLocation.map(location -> LayoutUtil.getRelativeLocation(location, container));
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
      return modelState.getIndex().get(operation.getContainerId());
   }

   /**
    * Create and return the new Node at the specified (optional) location. The location
    * is given in coordinates relative to the
    * {@link AbstractGModelCreateNodeOperationHandler#getContainer(CreateNodeOperation)}
    * container.
    *
    * @param relativeLocation
    * @param args
    * @return
    *         The created {@link GNode Node}.
    */
   protected abstract GNode createNode(Optional<GPoint> relativeLocation, Map<String, String> args);

}
