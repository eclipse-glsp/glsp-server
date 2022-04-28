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
package org.eclipse.glsp.server.emf.notation;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.graph.builder.impl.GEdgeBuilder;
import org.eclipse.glsp.graph.builder.impl.GNodeBuilder;
import org.eclipse.glsp.server.emf.EMFGModelFactory;
import org.eclipse.glsp.server.emf.model.notation.Diagram;
import org.eclipse.glsp.server.emf.model.notation.Edge;
import org.eclipse.glsp.server.emf.model.notation.Shape;
import org.eclipse.glsp.server.emf.notation.util.NotationUtil;

import com.google.inject.Inject;

/**
 * A graph model factory produces a graph model from the semantic model and the notation model in the model state.
 */
public abstract class EMFNotationGModelFactory extends EMFGModelFactory {

   @Inject
   protected EMFNotationModelState modelState;

   @Override
   protected void fillRootElement(final GModelRoot newRoot) {
      Diagram notationModel = modelState.getNotationModel();
      EObject semanticModel = modelState.getSemanticModel();
      modelState.getIndex().indexAll(notationModel, semanticModel);
      fillRootElement(semanticModel, notationModel, newRoot);
   }

   /**
    * Fills the new root element with a graph model drived from the semantic and the notation model.
    *
    * @param semanticModel semantic model root
    * @param notationModel notation model root
    * @param newRoot       new graph model root
    */
   protected abstract void fillRootElement(EObject semanticModel, Diagram notationModel,
      GModelRoot newRoot);

   /**
    * Applies all layout related data from the shape semantic element to the builder if possible.
    *
    * @param shapeElement element represented by a shape in the notation
    * @param builder      node builder
    * @return the given builder
    */
   protected GNodeBuilder applyShapeData(final EObject shapeElement, final GNodeBuilder builder) {
      modelState.getIndex().getNotation(shapeElement, Shape.class)
         .ifPresent(shape -> NotationUtil.applyShapeData(shape, builder));
      return builder;
   }

   /**
    * Applies all layout related data from the edge semantic element to the builder if possible.
    *
    * @param edgeElement element represented by an edge in the notation
    * @param builder     edge builder
    * @return the given builder
    */
   protected GEdgeBuilder applyEdgeData(final EObject edgeElement, final GEdgeBuilder builder) {
      modelState.getIndex().getNotation(edgeElement, Edge.class)
         .ifPresent(edge -> NotationUtil.applyEdgeData(edge, builder));
      return builder;
   }

}
