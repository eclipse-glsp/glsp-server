/********************************************************************************
 * Copyright (c) 2022 EclipseSource and others.
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
package org.eclipse.glsp.server.operations;

import java.util.Optional;

import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.server.utils.LayoutUtil;

public interface CreateNodeOperationHandler extends CreateOperationHandler {
   @Override
   Class<? extends CreateNodeOperation> getHandledOperationType();

   /**
    * Return the absolute location where the element should be created.
    *
    * @param operation
    * @return
    *         the absolute location where the element should be created.
    */
   default Optional<GPoint> getLocation(final CreateNodeOperation operation) {
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
   default Optional<GPoint> getRelativeLocation(final CreateNodeOperation operation,
      final Optional<GPoint> absoluteLocation, final GModelElement container) {
      return absoluteLocation.map(location -> LayoutUtil.getRelativeLocation(location, container));
   }
}
