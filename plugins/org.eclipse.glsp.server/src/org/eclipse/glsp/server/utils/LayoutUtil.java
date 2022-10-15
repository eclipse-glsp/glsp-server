/*******************************************************************************
 * Copyright (c) 2019-2022 EclipseSource and others.
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
 ******************************************************************************/
package org.eclipse.glsp.server.utils;

import static org.eclipse.glsp.server.types.GLSPServerException.getOrThrow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.glsp.graph.GAlignable;
import org.eclipse.glsp.graph.GBounds;
import org.eclipse.glsp.graph.GBoundsAware;
import org.eclipse.glsp.graph.GDimension;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GGraph;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GModelIndex;
import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.GraphFactory;
import org.eclipse.glsp.graph.builder.impl.GArguments;
import org.eclipse.glsp.graph.util.GraphUtil;
import org.eclipse.glsp.server.features.core.model.ComputedBoundsAction;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.types.ElementAndAlignment;
import org.eclipse.glsp.server.types.ElementAndBounds;
import org.eclipse.glsp.server.types.ElementAndRoutingPoints;
import org.eclipse.glsp.server.types.GLSPServerException;

public final class LayoutUtil {

   private LayoutUtil() {}

   /**
    * Apply the computed bounds from the given {@link ComputedBoundsAction} to the model.
    *
    * @param root       The model root.
    * @param action     The computed bounds action.
    * @param modelState The model state
    */
   public static void applyBounds(final GModelRoot root, final ComputedBoundsAction action,
      final GModelState modelState) {
      GModelIndex index = modelState.getIndex();
      action.getBounds().forEach(bounds -> applyBounds(bounds, index));
      action.getAlignments().forEach(alignment -> applyAlignment(alignment, index));
      action.getRoutes().forEach(route -> applyRoute(route, index));
   }

   /**
    * Applies the new bounds to the model.
    *
    * @param bounds The new bounds.
    * @param index  The model index.
    * @return The changed element.
    */
   public static Optional<GBoundsAware> applyBounds(final ElementAndBounds bounds, final GModelIndex index) {
      GModelElement element = getOrThrow(index.get(bounds.getElementId()),
         "Model element not found! ID: " + bounds.getElementId());
      if (element instanceof GBoundsAware) {
         GBoundsAware bae = (GBoundsAware) element;
         if (bounds.getNewPosition() != null) {
            bae.setPosition(GraphUtil.copy(bounds.getNewPosition()));
         }
         if (bounds.getNewSize() != null) {
            bae.setSize(GraphUtil.copy(bounds.getNewSize()));
         }
         return Optional.of(bae);
      }
      return Optional.empty();
   }

   /**
    * Applies the new alignment to the model.
    *
    * @param alignment The new alignment.
    * @param index     The model index.
    * @return The changed element.
    */
   public static Optional<GAlignable> applyAlignment(final ElementAndAlignment alignment, final GModelIndex index) {
      GModelElement element = getOrThrow(index.get(alignment.getElementId()),
         "Model element not found! ID: " + alignment.getElementId());
      if (element instanceof GAlignable) {
         GAlignable alignable = (GAlignable) element;
         alignable.setAlignment(alignment.getNewAlignment());
         return Optional.of(alignable);
      }
      return Optional.empty();
   }

   /**
    * Applies the new route to the model.
    *
    * @param route The new route.
    * @param index The model index.
    * @return The changed element.
    */
   public static GEdge applyRoute(final ElementAndRoutingPoints route, final GModelIndex index) {
      List<GPoint> routingPoints = route.getNewRoutingPoints();
      if (routingPoints.size() < 2) {
         throw new GLSPServerException("Invalid Route!");
      }
      // first and last point mark the source and target point
      GEdge edge = applyRoutingPoints(route, index);
      EList<GPoint> edgeRoutingPoints = edge.getRoutingPoints();
      edge.getArgs().put(GArguments.KEY_EDGE_SOURCE_POINT, edgeRoutingPoints.remove(0));
      edge.getArgs().put(GArguments.KEY_EDGE_TARGET_POINT, edgeRoutingPoints.remove(edgeRoutingPoints.size() - 1));
      return edge;
   }

   /**
    * Returns the complete route of the given edge. The route, as opposed to the routing points, also contain the source
    * and target point.
    *
    * @param edge The edge from which we get the route
    * @return complete edge route
    */
   public static ElementAndRoutingPoints getRoute(final GEdge edge) {
      Optional<GPoint> sourcePoint = GArguments.getEdgeSourcePoint(edge.getArgs());
      if (sourcePoint.isEmpty()) {
         throw new GLSPServerException("Cannot get route without source point!");
      }
      Optional<GPoint> targetPoint = GArguments.getEdgeTargetPoint(edge.getArgs());
      if (targetPoint.isEmpty()) {
         throw new GLSPServerException("Cannot get route without target point!");
      }
      List<GPoint> route = new ArrayList<>(EcoreUtil.copyAll(edge.getRoutingPoints()));
      route.add(0, sourcePoint.get());
      route.add(targetPoint.get());
      return new ElementAndRoutingPoints(edge.getId(), route);
   }

   /**
    * Applies the new routing points to the model.
    *
    * @param routingPoints The new routing points.
    * @param index         The model index.
    * @return The changed element.
    */
   public static GEdge applyRoutingPoints(final ElementAndRoutingPoints routingPoints, final GModelIndex index) {
      GEdge edge = getOrThrow(index.findElementByClass(routingPoints.getElementId(), GEdge.class),
         "Model element not found! ID: " + routingPoints.getElementId());
      EList<GPoint> edgeRoutingPoints = edge.getRoutingPoints();
      edgeRoutingPoints.clear();
      edgeRoutingPoints.addAll(routingPoints.getNewRoutingPoints());
      return edge;
   }

   /**
    * Returns the relative location of the given absolute location within the container.
    *
    * @param absoluteLocation absolute location
    * @param container        container
    * @return relative location if it can be determined, absolute location in case of error and null if the container
    *         cannot contain any location.
    */
   public static GPoint getRelativeLocation(final GPoint absoluteLocation, final GModelElement container) {
      // Only allow negative coordinates on the root graph, otherwise coordinates must be positive within the container
      boolean allowNegativeCoordinates = container instanceof GGraph;
      GModelElement modelElement = container;
      if (modelElement instanceof GBoundsAware) {
         try {
            GPoint relativePosition = GeometryUtil.absoluteToRelative(absoluteLocation, (GBoundsAware) modelElement);
            GPoint relativeLocation = allowNegativeCoordinates
               ? relativePosition
               : GraphUtil.point(Math.max(0, relativePosition.getX()), Math.max(0, relativePosition.getY()));
            return relativeLocation;
         } catch (IllegalArgumentException ex) {
            return absoluteLocation;
         }
      }
      return null;
   }

   /**
    * Convert the given {@link GBounds} to a {@link GPoint}.
    *
    * @param bounds The bounds
    * @return Converted point
    */
   public static GPoint asPoint(final GBounds bounds) {
      GPoint point = GraphFactory.eINSTANCE.createGPoint();
      point.setX(bounds.getX());
      point.setY(bounds.getY());
      return point;
   }

   /**
    * Convert the given {@link GBounds} to {@link GDimension}.
    *
    * @param bounds The bounds
    * @return Converted dimension
    */
   public static GDimension asDimension(final GBounds bounds) {
      GDimension dimension = GraphFactory.eINSTANCE.createGDimension();
      dimension.setHeight(bounds.getHeight());
      dimension.setWidth(bounds.getWidth());
      return dimension;
   }

   /**
    * Copy the layout of one model instance to another. Model elements are matched by their id.
    *
    * @param fromRoot Source model from which the layout should be copied
    * @param toRoot   Target model to which the layout should be copied
    */
   public static void copyLayoutData(final GModelRoot fromRoot, final GModelRoot toRoot) {
      GModelIndex oldIndex = GModelIndex.get(fromRoot);
      copyLayoutDataRecursively(toRoot, oldIndex);
   }

   @SuppressWarnings("checkstyle:CyclomaticComplexity")
   private static void copyLayoutDataRecursively(final GModelElement element, final GModelIndex oldIndex) {
      if (element instanceof GBoundsAware) {
         Optional<GModelElement> oldElement = oldIndex.get(element.getId());
         if (oldElement.isPresent() && oldElement.get() instanceof GBoundsAware) {
            GBoundsAware newBae = (GBoundsAware) element;
            GBoundsAware oldBae = (GBoundsAware) oldElement.get();
            if (oldBae.getPosition() != null) {
               newBae.setPosition(EcoreUtil.copy(oldBae.getPosition()));
            }
            if (oldBae.getSize() != null) {
               newBae.setSize(EcoreUtil.copy(oldBae.getSize()));
            }
         }
      } else if (element instanceof GEdge) {
         Optional<GModelElement> oldElement = oldIndex.get(element.getId());
         if (oldElement.isPresent() && oldElement.get() instanceof GEdge
            && ((GEdge) oldElement.get()).getRoutingPoints() != null) {
            GEdge gEdge = (GEdge) element;
            gEdge.getRoutingPoints().clear();
            gEdge.getRoutingPoints().addAll(EcoreUtil.copyAll(((GEdge) oldElement.get()).getRoutingPoints()));
         }
      }
      if (element.getChildren() != null) {
         for (GModelElement child : element.getChildren()) {
            copyLayoutDataRecursively(child, oldIndex);
         }
      }
   }
}
