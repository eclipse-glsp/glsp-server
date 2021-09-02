/*******************************************************************************
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
 ******************************************************************************/
package org.eclipse.glsp.server.utils;

import static org.eclipse.glsp.server.types.GLSPServerException.getOrThrow;

import java.util.Optional;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.glsp.graph.GAlignable;
import org.eclipse.glsp.graph.GBounds;
import org.eclipse.glsp.graph.GBoundsAware;
import org.eclipse.glsp.graph.GDimension;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GModelIndex;
import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.GraphFactory;
import org.eclipse.glsp.graph.util.GraphUtil;
import org.eclipse.glsp.server.features.core.model.ComputedBoundsAction;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.types.ElementAndAlignment;
import org.eclipse.glsp.server.types.ElementAndBounds;

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
      for (ElementAndBounds b : action.getBounds()) {
         GModelElement element = getOrThrow(index.get(b.getElementId()),
            "Model element not found! ID: " + b.getElementId());
         if (element instanceof GBoundsAware) {
            GBoundsAware bae = (GBoundsAware) element;
            if (b.getNewPosition() != null) {
               bae.setPosition(GraphUtil.copy(b.getNewPosition()));
            }
            if (b.getNewSize() != null) {
               bae.setSize(GraphUtil.copy(b.getNewSize()));
            }
         }
      }
      for (ElementAndAlignment a : action.getAlignments()) {
         GModelElement element = getOrThrow(index.get(a.getElementId()),
            "Model element not found! ID: " + a.getElementId());
         if (element instanceof GAlignable) {
            GAlignable alignable = (GAlignable) element;
            alignable.setAlignment(a.getNewAlignment());
         }
      }
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
