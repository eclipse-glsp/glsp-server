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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.glsp.server.emf.EMFSourceModelStorage;
import org.eclipse.glsp.server.emf.model.notation.Diagram;
import org.eclipse.glsp.server.emf.model.notation.NotationPackage;
import org.eclipse.glsp.server.emf.notation.util.NotationUtil;
import org.eclipse.glsp.server.features.core.model.RequestModelAction;

import com.google.inject.Inject;

public class EMFNotationSourceModelStorage extends EMFSourceModelStorage {
   @Inject
   protected EMFNotationModelState modelState;

   @Override
   protected ResourceSet setupResourceSet(final ResourceSet resourceSet) {
      resourceSet.getPackageRegistry().put(NotationPackage.eINSTANCE.getNsURI(), NotationPackage.eINSTANCE);
      return super.setupResourceSet(resourceSet);
   }

   @Override
   protected void doLoadSourceModel(final ResourceSet resourceSet, final URI sourceURI,
      final RequestModelAction action) {
      loadSemanticModel(resourceSet, sourceURI, action);
      loadNotationModel(resourceSet, sourceURI, action);
   }

   protected void loadNotationModel(final ResourceSet resourceSet, final URI sourceURI,
      final RequestModelAction action) {
      super.loadResource(resourceSet, deriveNotationModelURI(sourceURI), Diagram.class)
         .ifPresent(modelState::setNotationModel);
   }

   protected URI deriveNotationModelURI(final URI sourceURI) {
      return sourceURI.trimFileExtension().appendFileExtension(NotationUtil.DEFAULT_EXTENSION);
   }

   protected void loadSemanticModel(final ResourceSet resourceSet, final URI sourceURI,
      final RequestModelAction action) {
      loadResource(resourceSet, deriveSemanticModelURI(sourceURI)).ifPresent(modelState::setSemanticModel);
   }

   protected URI deriveSemanticModelURI(final URI sourceURI) {
      return sourceURI;
   }
}
