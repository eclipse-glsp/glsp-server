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
package org.eclipse.glsp.server.emf.notation.util;

import java.util.Map;
import java.util.Optional;

import org.eclipse.glsp.graph.builder.impl.GEdgeBuilder;
import org.eclipse.glsp.graph.builder.impl.GLayoutOptions;
import org.eclipse.glsp.graph.builder.impl.GNodeBuilder;
import org.eclipse.glsp.graph.util.GraphUtil;
import org.eclipse.glsp.server.emf.model.notation.Edge;
import org.eclipse.glsp.server.emf.model.notation.NotationElement;
import org.eclipse.glsp.server.emf.model.notation.Shape;

public final class NotationUtil {
   /** The default extension for notation resources. */
   public static final String DEFAULT_EXTENSION = "notation";

   private NotationUtil() {}

   /**
    * True if the given element is unresolved but may be resolved.
    *
    * @param notationElement
    * @return true if the given element is unresolved but may be resolved.
    */
   public static boolean isUnresolved(final NotationElement notationElement) {
      return notationElement.getSemanticElement() != null
         && notationElement.getSemanticElement().getResolvedSemanticElement() == null;
   }

   /**
    * Applies all layout related data from the Edge notation to the given builder.
    *
    * @param edge    edge
    * @param builder edge builder
    * @return the given builder
    */
   public static GEdgeBuilder applyEdgeData(final Edge edge, final GEdgeBuilder builder) {
      if (edge.getBendPoints() != null) {
         edge.getBendPoints().stream().map(GraphUtil::copy).forEachOrdered(builder::addRoutingPoint);
      }
      return builder;
   }

   /**
    * Applies all layout related data from the Edge notation to the given builder.
    *
    * @param shape   shape
    * @param builder node builder
    * @return the given builder
    */
   public static GNodeBuilder applyShapeData(final Shape shape, final GNodeBuilder builder) {
      Optional.ofNullable(shape.getPosition()).map(GraphUtil::copy).ifPresent(builder::position);
      Optional.ofNullable(shape.getSize()).map(GraphUtil::copy).ifPresent(newSize -> {
         builder.size(newSize);
         builder.addLayoutOptions(Map.of(
            GLayoutOptions.KEY_PREF_WIDTH, newSize.getWidth(),
            GLayoutOptions.KEY_PREF_HEIGHT, newSize.getHeight()));
      });
      return builder;
   }
}
