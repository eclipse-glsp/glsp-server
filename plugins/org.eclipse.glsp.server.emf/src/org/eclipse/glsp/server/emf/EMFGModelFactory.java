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
package org.eclipse.glsp.server.emf;

import org.eclipse.glsp.graph.GGraph;
import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.graph.builder.impl.GGraphBuilder;
import org.eclipse.glsp.server.features.core.model.GModelFactory;
import org.eclipse.glsp.server.utils.ClientOptionsUtil;

import com.google.inject.Inject;

/**
 * A graph model factory produces a graph model from the source model in the model state.
 */
public abstract class EMFGModelFactory implements GModelFactory {

   @Inject
   protected EMFModelState modelState;

   @Inject
   protected EMFIdGenerator idGenerator;

   @Override
   public void createGModel() {
      GModelRoot newRoot = createRootElement();
      modelState.updateRoot(newRoot);
      fillRootElement(newRoot);
   }

   protected GModelRoot createRootElement() {
      GGraph graph = new GGraphBuilder().build();
      graph.setId(ClientOptionsUtil.getSourceUri(modelState.getClientOptions()).orElse("root"));
      return graph;
   }

   /**
    * Fills the new root element with a graph model derived from the source model.
    *
    * @param newRoot new graph model root
    */
   protected abstract void fillRootElement(GModelRoot newRoot);

}
