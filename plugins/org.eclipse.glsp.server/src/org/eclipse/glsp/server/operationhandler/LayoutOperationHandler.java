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
package org.eclipse.glsp.server.operationhandler;

import org.eclipse.glsp.api.configuration.ServerConfiguration;
import org.eclipse.glsp.api.layout.ILayoutEngine;
import org.eclipse.glsp.api.layout.ServerLayoutKind;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.operation.kind.LayoutOperation;
import org.eclipse.glsp.server.actionhandler.ModelSubmissionHandler;

import com.google.inject.Inject;

public class LayoutOperationHandler extends BasicOperationHandler<LayoutOperation> {
   @Inject
   protected ILayoutEngine layoutEngine;
   @Inject
   protected ModelSubmissionHandler modelSubmissionHandler;
   @Inject
   protected ServerConfiguration serverConfiguration;

   @Override
   protected void executeOperation(final LayoutOperation action, final GraphicalModelState modelState) {
      if (serverConfiguration.getLayoutKind() == ServerLayoutKind.MANUAL) {
         if (layoutEngine != null) {
            layoutEngine.layout(modelState);
         }
      }
   }
}
