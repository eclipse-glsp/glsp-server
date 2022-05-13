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

import java.util.List;

import org.eclipse.glsp.server.operations.CreateEdgeOperation;
import org.eclipse.glsp.server.operations.CreateEdgeOperationHandler;

/**
 * A special {@link EMFOperationHandler} that is responsible for the handling of {@link CreateEdgeOperation}s.
 * It provides an EMF command to create the specific edge.
 */
public abstract class AbstractEMFCreateEdgeOperationHandler
   extends AbstractEMFCreateOperationHandler<CreateEdgeOperation> implements CreateEdgeOperationHandler {

   public AbstractEMFCreateEdgeOperationHandler(final String... elementTypeIds) {
      super(elementTypeIds);
   }

   public AbstractEMFCreateEdgeOperationHandler(final List<String> handledElementTypeIds) {
      super(handledElementTypeIds);
   }

}
