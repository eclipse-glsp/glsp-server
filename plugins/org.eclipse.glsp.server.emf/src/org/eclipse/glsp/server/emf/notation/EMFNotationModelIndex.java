/*******************************************************************************
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
 ******************************************************************************/
package org.eclipse.glsp.server.emf.notation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.util.RootAdapterUtil;
import org.eclipse.glsp.server.emf.EMFModelIndex;
import org.eclipse.glsp.server.emf.model.notation.Diagram;
import org.eclipse.glsp.server.emf.model.notation.NotationElement;
import org.eclipse.glsp.server.emf.model.notation.NotationFactory;
import org.eclipse.glsp.server.emf.model.notation.SemanticElementReference;
import org.eclipse.glsp.server.emf.notation.util.NotationUtil;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * Is used to index all child elements of a semantic and notation model.
 * Offers a set of query methods retrieve, initialize and resolve indexed notation elements.
 */
public class EMFNotationModelIndex extends EMFModelIndex {
   protected static Logger LOGGER = LogManager.getLogger(EMFNotationModelIndex.class);

   protected BiMap<EObject, NotationElement> notationIndex;
   protected final EMFSemanticIdConverter idConverter;

   protected EMFNotationModelIndex(final EObject target, final EMFSemanticIdConverter idConverter) {
      super(target, idConverter);
      this.notationIndex = HashBiMap.create();
      this.idConverter = idConverter;
   }

   @Override
   public boolean isAdapterForType(final Object type) {
      return super.isAdapterForType(type) || EMFNotationModelIndex.class.equals(type);
   }

   @Override
   public void clear() {
      super.clear();
      notationIndex.clear();
   }

   public Diagram indexAll(final Diagram diagram, final EObject semanticModel) {
      resolveDiagram(diagram, semanticModel);
      indexDiagram(diagram);
      return diagram;
   }

   public Optional<NotationElement> getNotation(final EObject semanticElement) {
      return Optional.ofNullable(notationIndex.get(semanticElement));
   }

   public <T extends NotationElement> Optional<T> getNotation(final EObject semanticElement, final Class<T> clazz) {
      return safeCast(getNotation(semanticElement), clazz);
   }

   public Optional<NotationElement> getNotation(final String id) {
      return getEObject(id).flatMap(this::getNotation);
   }

   public <T extends NotationElement> Optional<T> getNotation(final String id, final Class<T> clazz) {
      return safeCast(getNotation(id), clazz);
   }

   public Optional<NotationElement> getNotation(final GModelElement gModelElement) {
      return getNotation(gModelElement.getId());
   }

   public <T extends NotationElement> Optional<T> getNotation(final GModelElement element, final Class<T> clazz) {
      return safeCast(getNotation(element), clazz);
   }

   protected void indexDiagram(final Diagram diagram) {
      indexNotation(diagram);
      diagram.getElements().forEach(this::indexNotation);
   }

   protected void indexNotation(final NotationElement notationElement) {
      if (!NotationUtil.isUnresolved(notationElement)) {
         EObject semanticElement = notationElement.getSemanticElement().getResolvedSemanticElement();
         notationIndex.put(semanticElement, notationElement);
         eObjectIndex.inverse().putIfAbsent(semanticElement, getOrCreateId(semanticElement));
      }
   }

   protected void resolveDiagram(final Diagram diagram, final EObject semanticModel) {
      findUnresolvedElements(diagram, semanticModel)
         .forEach(notation -> resolveNotationElement(notation, semanticModel));
   }

   protected void resolveNotationElement(final NotationElement notation, final EObject semanticModel) {
      if (notation != null) {
         SemanticElementReference resolved = resolveSemanticReference(notation.getSemanticElement(), semanticModel);
         notation.setSemanticElement(resolved);
      }
   }

   protected SemanticElementReference resolveSemanticReference(final SemanticElementReference reference,
      final EObject semanticModel) {
      if (reference.getResolvedSemanticElement() != null) {
         // already resolved
         return reference;
      }
      return resolveSemanticElementReference(reference, semanticModel);
   }

   protected SemanticElementReference resolveSemanticElementReference(final SemanticElementReference reference,
      final EObject semanticModel) {
      if (semanticModel == null) {
         return reference;
      }
      Optional.ofNullable(idConverter.resolve(reference.getElementId(), semanticModel))
         .ifPresent(reference::setResolvedSemanticElement);
      return reference;
   }

   protected List<NotationElement> findUnresolvedElements(final Diagram diagram, final EObject semanticModel) {
      List<NotationElement> unresolved = new ArrayList<>();
      if (NotationUtil.isUnresolved(diagram)) {
         unresolved.add(diagram);
      }
      diagram.getElements().stream()
         .filter(NotationUtil::isUnresolved)
         .forEach(unresolved::add);

      return unresolved;
   }

   protected SemanticElementReference createReference(final EObject eObject) {
      SemanticElementReference reference = NotationFactory.eINSTANCE.createSemanticElementReference();
      reference.setResolvedSemanticElement(eObject);
      reference.setElementId(getOrCreateId(eObject).toString());
      return reference;
   }

   public static EMFNotationModelIndex getOrCreate(final GModelElement element,
      final EMFSemanticIdConverter idGenerator) {
      return RootAdapterUtil.getOrCreate(element, root -> new EMFNotationModelIndex(root, idGenerator),
         EMFNotationModelIndex.class);
   }
}
