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

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.glsp.server.actions.SaveModelAction;
import org.eclipse.glsp.server.features.core.model.RequestModelAction;
import org.eclipse.glsp.server.features.core.model.SourceModelStorage;
import org.eclipse.glsp.server.types.GLSPServerException;
import org.eclipse.glsp.server.utils.ClientOptionsUtil;

import com.google.inject.Inject;

/**
 * This implementation of the {@link SourceModelStorage} handles the persistence of source models via
 * the {@link EMFModelState}.
 */
public class EMFSourceModelStorage implements SourceModelStorage {

   private static Logger LOGGER = LogManager.getLogger(EMFSourceModelStorage.class.getSimpleName());

   @Inject
   protected EMFModelState modelState;

   @Inject
   protected EMFEditingDomainFactory editingDomainFactory;

   @Override
   public void loadSourceModel(final RequestModelAction action) {
      String sourceURI = ClientOptionsUtil.getSourceUri(action.getOptions())
         .orElseThrow(() -> new GLSPServerException("No source URI given to load model!"));
      URI resourceURI = URI.createFileURI(sourceURI);

      EditingDomain editingDomain = getOrCreateEditingDomain(action.getSubclientId());
      doLoadSourceModel(editingDomain.getResourceSet(), resourceURI, action);
   }

   protected EditingDomain getOrCreateEditingDomain(final String subclientId) {
      if (modelState.getEditingDomain() != null) {
         return modelState.getEditingDomain();
      }
      EditingDomain editingDomain = editingDomainFactory.createEditingDomain();
      setupResourceSet(editingDomain.getResourceSet());
      modelState.setEditingDomain(editingDomain, subclientId);
      return editingDomain;
   }

   protected ResourceSet setupResourceSet(final ResourceSet resourceSet) {
      EcorePackage.eINSTANCE.eClass();
      resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
      return resourceSet;
   }

   protected void doLoadSourceModel(final ResourceSet resourceSet, final URI sourceURI,
      final RequestModelAction action) {
      loadResource(resourceSet, sourceURI);
   }

   protected Optional<EObject> loadResource(final ResourceSet resourceSet, final URI resourceURI) {
      return loadResource(resourceSet, resourceURI, EObject.class);
   }

   protected <T extends EObject> Optional<T> loadResource(final ResourceSet resourceSet, final URI resourceURI,
      final Class<T> modelClass) {
      Resource resource = resourceSet.getResource(resourceURI, true);
      if (resource == null) {
         throw new GLSPServerException("Failed to load resource: " + resourceURI);
      }

      return resource.getContents().stream().filter(modelClass::isInstance).map(modelClass::cast).findFirst();
   }

   @Override
   public void saveSourceModel(final SaveModelAction action) {
      for (Resource resource : modelState.getResourceSet().getResources()) {
         if (resource.getURI() != null) {
            try {
               resource.save(Collections.EMPTY_MAP);
            } catch (IOException e) {
               LOGGER.error("Could not save resource: " + resource.getURI(), e);
               throw new GLSPServerException("Could not save model to file: " + resource.getURI(), e);
            }
         }
      }
   }
}
