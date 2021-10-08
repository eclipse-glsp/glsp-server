/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
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
package org.eclipse.glsp.server.utils;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.graph.GBoundsAware;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.util.GraphUtil;

/**
 * Utility class for geometry-related operations.
 */
public final class GeometryUtil {

   private GeometryUtil() {
      // No instances
   }

   /**
    * Convert a point in absolute coordinates to a point relative to the specified GBoundsAware.
    * Note: this method only works if the specified {@link GBoundsAware} is part of a
    * hierarchy of {@link GBoundsAware}. If any of its parents (recursively) does not implement
    * {@link GBoundsAware}, this method will throw an exception.
    *
    * @param absolutePoint
    * @param modelElement
    * @return
    *         A new point, relative to the coordinates space of the specified {@link GBoundsAware}
    * @throws IllegalArgumentException if the modelElement is not part of a {@link GBoundsAware} hierarchy
    */
   public static GPoint absoluteToRelative(final GPoint absolutePoint, final GBoundsAware modelElement) {
      if (modelElement == null) {
         return GraphUtil.copy(absolutePoint);
      }

      EObject parentElement;
      if (modelElement instanceof GModelElement) {
         parentElement = ((GModelElement) modelElement).getParent();
      } else {
         parentElement = modelElement.eContainer();
      }

      final GPoint relativeToParent;
      if (parentElement == null) {
         relativeToParent = GraphUtil.copy(absolutePoint);
      } else {
         if (!(parentElement instanceof GBoundsAware)) {
            throw new IllegalArgumentException("The element is not part of a GBoundsAware hierarchy: " + modelElement);
         }
         relativeToParent = absoluteToRelative(absolutePoint, (GBoundsAware) parentElement);
      }

      double x = modelElement.getPosition() == null ? 0 : modelElement.getPosition().getX();
      double y = modelElement.getPosition() == null ? 0 : modelElement.getPosition().getY();

      return GraphUtil.point(relativeToParent.getX() - x, relativeToParent.getY() - y);
   }

   public static void shift(final List<GModelElement> elements, final GPoint offset) {
      GModelUtil.filterByType(elements, GBoundsAware.class).forEach(boundsAware -> {
         boundsAware.getPosition().setX(boundsAware.getPosition().getX() + offset.getX());
         boundsAware.getPosition().setY(boundsAware.getPosition().getY() + offset.getY());
      });
   }

}
