/*******************************************************************************
 * Copyright (c) 2019-2026 EclipseSource and others.
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

   protected static Logger LOGGER = LogManager.getLogger(LayoutUtil.class);

   private LayoutUtil() {}

   /**
    * Apply the computed bounds from the given {@link ComputedBoundsAction} to the model.
    *
    * <p>
    * An entry that cannot be applied is skipped and logged rather than treated as an error.
    * </p>
    *
    * @param root       The model root.
    * @param action     The computed bounds action.
    * @param modelState The model state
    * @deprecated Use
    *             {@link org.eclipse.glsp.server.features.core.model.ComputedBoundsActionHandler#applyBounds(GModelRoot, ComputedBoundsAction)}
    *             instead. This method dispatches to the static per-kind methods directly and therefore bypasses the
    *             overridable ones of the handler.
    */
   @Deprecated
   public static void applyBounds(final GModelRoot root, final ComputedBoundsAction action,
      final GModelState modelState) {
      GModelIndex index = modelState.getIndex();
      applyElementBounds(action.getBounds(), index);
      applyAlignments(action.getAlignments(), index);
      applyRoutes(action.getRoutes(), index);
   }

   /**
    * Applies the computed bounds of several elements.
    *
    * @param allBounds The new bounds.
    * @param index     The model index.
    */
   public static void applyElementBounds(final List<ElementAndBounds> allBounds, final GModelIndex index) {
      allBounds.forEach(bounds -> {
         if (applyBounds(bounds, index).isEmpty()) {
            LOGGER.warn("Skipped computed bounds of element '" + bounds.getElementId() + "'");
         }
      });
   }

   /**
    * Applies the computed alignments of several elements.
    *
    * @param alignments The new alignments.
    * @param index      The model index.
    */
   public static void applyAlignments(final List<ElementAndAlignment> alignments, final GModelIndex index) {
      alignments.forEach(alignment -> {
         if (applyAlignment(alignment, index).isEmpty()) {
            LOGGER.warn("Skipped computed alignment of element '" + alignment.getElementId() + "'");
         }
      });
   }

   /**
    * Applies the computed routes.
    *
    * <p>
    * A skipped route is logged at debug level, an edge the client has not finished routing yet is expected.
    * </p>
    *
    * @param routes The new routes.
    * @param index  The model index.
    */
   public static void applyRoutes(final List<ElementAndRoutingPoints> routes, final GModelIndex index) {
      routes.forEach(route -> {
         if (applyRoute(route, index).isEmpty()) {
            LOGGER.debug("Skipped computed route of element '" + route.getElementId() + "'");
         }
      });
   }

   /**
    * Applies the new bounds to the model.
    *
    * @param bounds The new bounds.
    * @param index  The model index.
    * @return The changed element, or empty if the bounds could not be applied to any element.
    */
   public static Optional<GBoundsAware> applyBounds(final ElementAndBounds bounds, final GModelIndex index) {
      Optional<GModelElement> element = index.get(bounds.getElementId());
      if (element.isPresent() && element.get() instanceof GBoundsAware) {
         GBoundsAware bae = (GBoundsAware) element.get();
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
    * @return The changed element, or empty if the alignment could not be applied to any element.
    */
   public static Optional<GAlignable> applyAlignment(final ElementAndAlignment alignment, final GModelIndex index) {
      Optional<GModelElement> element = index.get(alignment.getElementId());
      if (element.isPresent() && element.get() instanceof GAlignable) {
         GAlignable alignable = (GAlignable) element.get();
         alignable.setAlignment(alignment.getNewAlignment());
         return Optional.of(alignable);
      }
      return Optional.empty();
   }

   /**
    * Applies the new route to the model. A route needs at least a source and a target point to describe an edge.
    *
    * @param route The new route.
    * @param index The model index.
    * @return The changed edge, or empty if the route could not be applied to any edge.
    */
   public static Optional<GEdge> applyRoute(final ElementAndRoutingPoints route, final GModelIndex index) {
      List<GPoint> routingPoints = route.getNewRoutingPoints();
      Optional<GEdge> edge = index.findElementByClass(route.getElementId(), GEdge.class);
      if (edge.isEmpty() || routingPoints == null || routingPoints.size() < 2) {
         return Optional.empty();
      }
      EList<GPoint> edgeRoutingPoints = edge.get().getRoutingPoints();
      edgeRoutingPoints.clear();
      edgeRoutingPoints.addAll(routingPoints);
      // first and last point mark the source and target point
      edge.get().getArgs().put(GArguments.KEY_EDGE_SOURCE_POINT, edgeRoutingPoints.remove(0));
      edge.get().getArgs().put(GArguments.KEY_EDGE_TARGET_POINT,
         edgeRoutingPoints.remove(edgeRoutingPoints.size() - 1));
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
