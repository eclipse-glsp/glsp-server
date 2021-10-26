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
package org.eclipse.glsp.server.operations;

import org.eclipse.glsp.server.actions.Action;

/**
 *
 * An Operation is an {@link Action} that directly manipulates the model representation on server side.
 * Operations are handled by instances of {@link OperationHandler}. The operation handler is responsible
 * of processing the operation and updates the model representation accordingly.
 *
 */
public abstract class Operation extends Action {

   public Operation(final String operationKind) {
      super(operationKind);
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Operation [kind=");
      builder.append(getKind());
      builder.append("]");
      return builder.toString();
   }

}
