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
package org.eclipse.glsp.server.emf;

import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.server.operations.CreateNodeOperation;
import org.eclipse.glsp.server.operations.Operation;
import org.eclipse.glsp.server.operations.gmodel.CreateNodeOperationHandler;

/**
 * A special {@link EMFOperationHandler} that is responsible for the handling of {@link CreateNodeOperation}s.
 * It provides an EMF command to create the specific node.
 */
public abstract class EMFCreateNodeOperationHandler extends CreateNodeOperationHandler implements EMFOperationHandler {

   public EMFCreateNodeOperationHandler(final String elementTypeIds) {
      super(elementTypeIds);
   }

   @Override
   public Optional<Command> getCommand(final Operation operation) {
      if (handles(operation)) {
         return createCommand(operationType.cast(operation));
      }
      return Optional.empty();
   }

   /**
    * Creates a command that performs the node creation in the EMF source model(s).
    *
    * @param operation The operation to process.
    * @return The created command to be executed on the command stack.
    */
   protected abstract Optional<Command> createCommand(CreateNodeOperation operation);

   @Override
   protected GNode createNode(final Optional<GPoint> relativeLocation, final Map<String, String> args) {
      // no-op, because this class returns a command directly instead of executing the command directly on a model
      return null;
   }

}
