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
package org.eclipse.glsp.example.workflow.typehints;

import static org.eclipse.glsp.example.workflow.utils.ModelTypes.DECISION_NODE;
import static org.eclipse.glsp.example.workflow.utils.ModelTypes.FORK_NODE;
import static org.eclipse.glsp.example.workflow.utils.ModelTypes.JOIN_NODE;
import static org.eclipse.glsp.example.workflow.utils.ModelTypes.WEIGHTED_EDGE;

import org.eclipse.glsp.example.workflow.wfgraph.TaskNode;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.server.features.typehints.EdgeCreationChecker;

public class WorkflowEdgeCreationChecker implements EdgeCreationChecker {

   @Override
   public boolean isValidSource(final String edgeType, final GModelElement sourceElement) {
      return edgeType.equals(WEIGHTED_EDGE) && sourceElement.getType().equals(DECISION_NODE);
   }

   @Override
   public boolean isValidTarget(final String edgeType, final GModelElement sourceElement,
      final GModelElement targetElement) {

      if (!edgeType.equals(WEIGHTED_EDGE)) {
         return false;
      }
      String targetType = targetElement.getType();
      return targetElement instanceof TaskNode || targetType.equals(FORK_NODE)
         || targetType.equals(JOIN_NODE);

   }
}
