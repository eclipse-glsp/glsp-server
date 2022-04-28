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

import java.util.Optional;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.graph.GModelIndex;
import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.server.emf.EMFModelIndex;
import org.eclipse.glsp.server.emf.EMFModelState;
import org.eclipse.glsp.server.emf.model.notation.Diagram;
import org.eclipse.glsp.server.session.ClientSession;
import org.eclipse.glsp.server.session.ClientSessionListener;

import com.google.inject.Inject;

/**
 * This state represents the status of the diagram based on the {@link GModelRoot}, contains the {@link EMFModelIndex},
 * is able to execute commands via its {@link CommandStack} and is registered as {@link ClientSessionListener}
 * to be able to reset the EMF resources on diagram close.
 *
 * This model state assumes that there is a single semantic root element and a single notation element.
 */
public class EMFNotationModelState extends EMFModelState {

   protected Diagram notationModel;

   protected EObject semanticModel;

   @Inject
   protected EMFSemanticIdConverter semanticIdConverter;

   @Override
   protected GModelIndex getOrUpdateIndex(final GModelRoot newRoot) {
      return EMFNotationModelIndex.getOrCreate(getRoot(), semanticIdConverter);
   }

   @Override
   public EMFNotationModelIndex getIndex() { return (EMFNotationModelIndex) super.getIndex(); }

   public void setSemanticModel(final EObject semanticModel) { this.semanticModel = semanticModel; }

   public EObject getSemanticModel() { return this.semanticModel; }

   public <T extends EObject> Optional<T> getSemanticModel(final Class<T> clazz) {
      return Optional.ofNullable(this.semanticModel).filter(clazz::isInstance).map(clazz::cast);
   }

   public void setNotationModel(final Diagram notationModel) { this.notationModel = notationModel; }

   public Diagram getNotationModel() { return this.notationModel; }

   public <T extends Diagram> Optional<T> getNotationModel(final Class<T> clazz) {
      return Optional.ofNullable(this.notationModel).filter(clazz::isInstance).map(clazz::cast);
   }

   @Override
   public void sessionDisposed(final ClientSession clientSession) {
      this.semanticModel = null;
      this.notationModel = null;
      super.sessionDisposed(clientSession);
   }
}
