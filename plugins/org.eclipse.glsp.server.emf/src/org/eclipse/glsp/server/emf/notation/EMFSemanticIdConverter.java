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

import java.util.Objects;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.glsp.server.emf.EMFIdGenerator;

import com.google.inject.Inject;

/**
 * A converter that can generate creates a unique identifier for a given semantic EObject but is also able to do the
 * inverse operation, i.e., map from an identifier to the EObject. The ID converter is used to resolve the semantic
 * elements referenced in notation elements during index creation.
 */
public interface EMFSemanticIdConverter extends EMFIdGenerator {
   /**
    * Resolves a semantic element based on the given id and the semantic root.
    *
    * @param semanticId semantic id generated through {@link #getOrCreateId(EObject)}.
    * @param root       semantic root element
    * @return the semantic element identified by the given semantic id
    */
   EObject resolve(String semanticId, EObject root);

   class Default implements EMFSemanticIdConverter {
      @Inject
      protected EMFIdGenerator generator;

      @Override
      public String getOrCreateId(final EObject semanticElement) {
         return this.generator.getOrCreateId(semanticElement);
      }

      @Override
      public EObject resolve(final String semanticId, final EObject root) {
         // this is a generic but not very efficient solution, custom implementations may be able to provider more
         // efficient retrieval based on ID-specific knowledge
         if (Objects.equals(semanticId, getOrCreateId(root))) {
            return root;
         }
         TreeIterator<EObject> iterator = EcoreUtil.getAllContents(root, false);
         Iterable<EObject> iterable = () -> iterator;
         return StreamSupport.stream(iterable.spliterator(), true)
            .filter(eObject -> Objects.equals(semanticId, getOrCreateId(eObject)))
            .findAny().orElse(null);
      }
   }
}
