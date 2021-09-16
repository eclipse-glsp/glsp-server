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
package org.eclipse.glsp.server.diagram;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.glsp.graph.GraphExtension;
import org.eclipse.glsp.server.layout.ServerLayoutKind;
import org.eclipse.glsp.server.types.EdgeTypeHint;
import org.eclipse.glsp.server.types.ShapeTypeHint;

/**
 * Provides configuration constants for a specific diagram implementation (i.e. diagram language),
 * The corresponding configuration for a diagram implementation is identified via its diagram type.
 */
public interface DiagramConfiguration {

   /**
    * Returns the diagram type of the diagram implementation that corresponds to this configuration.
    *
    * @return The diagram type.
    */
   String getDiagramType();

   /**
    * Returns the type mappings for the diagram implementation. Type mappings are used by GSON to construct the correct
    * {@link EClass} based on the "type" property of the JSON object.
    *
    * @return A complete map of all type mappings for the diagram implementation.
    */
   Map<String, EClass> getTypeMappings();

   /**
    * Returns the shape type hints for the diagram implementation. Shape type hints are sent to the client and used to
    * validate whether certain operations for shapes/nodes are allowed without having to query the server again.
    *
    * @return List of all shape type hints for the diagram implementation.
    */
   List<ShapeTypeHint> getShapeTypeHints();

   /**
    * Returns the edge type hints for the diagram implementation. Edge type hints are sent to the client and used to
    * validate whether certain operations for edges are allowed without having to query the server again.
    *
    * @return List of all edge type hints for the diagram implementation.
    */
   List<EdgeTypeHint> getEdgeTypeHints();

   /**
    * Returns the optional {@link GraphExtension} that is needed for this diagram implementation. Graph extensions
    * provide the possibility to extend the GModel with custom types.
    *
    * @return An optional of the configured graph extension.
    */
   Optional<GraphExtension> getGraphExtension();

   default EdgeTypeHint createDefaultEdgeTypeHint(final String elementId) {
      return new EdgeTypeHint(elementId, true, true, true, null, null);
   }

   default ShapeTypeHint createDefaultShapeTypeHint(final String elementId) {
      return new ShapeTypeHint(elementId, true, true, true, false);
   }

   /**
    * Returns the supported layout kind for this diagram implementation.
    *
    * @return The layout kind.
    */
   default ServerLayoutKind getLayoutKind() { return ServerLayoutKind.NONE; }

   /**
    * Boolean flag to specific whether the diagram implementation expects the client to provide layout information for
    * certain diagram element. Default is 'true'.
    *
    * @return Boolean flag to indicate whether the client needs to be involved in the layout process.
    */
   default boolean needsClientLayout() {
      return true;
   }

   /**
    * Boolean flag to tell the client whether changed caused by an update should be animated or not.
    *
    * @return Boolean flag to indicate whether the client should animate update changes.
    */
   default boolean animatedUpdate() {
      return true;
   }
}
