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
package org.eclipse.glsp.api.operation;

import org.eclipse.glsp.api.action.Action;

/**
 *
 * An Operation is an action that directly manipulates the model representation on server side.
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

   public static class Kind {
      public static final String CREATE_EDGE = "createEdge";
      public static final String RECONNECT_EDGE = "reconnectEdge";
      public static final String CHANGE_ROUTING_POINTS = "changeRoutingPoints";
      public static final String CREATE_NODE = "createNode";
      public static final String DELETE_ELEMENT = "deleteElement";
      public static final String CHANGE_BOUNDS = "changeBounds";
      public static final String CHANGE_CONTAINER = "changeContainer";
      public static final String APPLY_LABEL_EDIT = "applyLabelEdit";
      public static final String PASTE = "paste";
      public static final String CUT = "cut";
      public static final String LAYOUT = "layout";
      public static final String COMPOUND = "compound";
   }
}
