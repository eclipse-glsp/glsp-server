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
package org.eclipse.glsp.server.emf;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.impl.GModelIndexImpl;
import org.eclipse.glsp.graph.util.RootAdapterUtil;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * Is used to index all child elements of an arbitrary EMF source model.
 * Offers a set of query methods retrieve indexed elements.
 */
public class EMFModelIndex extends GModelIndexImpl {

   protected EMFIdGenerator idGenerator;

   protected BiMap<String, EObject> eObjectIndex;

   protected EMFModelIndex(final EObject target, final EMFIdGenerator idGenerator) {
      super(target);
      this.eObjectIndex = HashBiMap.create();
      this.idGenerator = idGenerator;
   }

   @Override
   public boolean isAdapterForType(final Object type) {
      return super.isAdapterForType(type) || EMFModelIndex.class.equals(type);
   }

   @Override
   public void clear() {
      super.clear();
      this.eObjectIndex.clear();
   }

   public String indexEObject(final EObject element) {
      String id = getOrCreateId(element);
      eObjectIndex.putIfAbsent(id, element);
      return id;
   }

   protected String getOrCreateId(final EObject element) {
      return idGenerator.getOrCreateId(element);
   }

   public Optional<EObject> getEObject(final String id) {
      return Optional.ofNullable(eObjectIndex.get(id));
   }

   public <T extends EObject> Optional<T> getEObject(final String id, final Class<T> clazz) {
      return safeCast(Optional.ofNullable(eObjectIndex.get(id)), clazz);
   }

   public Optional<EObject> getEObject(final GModelElement gModelElement) {
      return getEObject(gModelElement.getId());
   }

   public <T extends EObject> Optional<T> getEObject(final GModelElement gModelElement, final Class<T> clazz) {
      return getEObject(gModelElement.getId(), clazz);
   }

   protected <T> Optional<T> safeCast(final Optional<?> toCast, final Class<T> clazz) {
      return toCast.filter(clazz::isInstance).map(clazz::cast);
   }

   public static EMFModelIndex getOrCreate(final GModelElement element, final EMFIdGenerator idGenerator) {
      return RootAdapterUtil.getOrCreate(element, root -> new EMFModelIndex(root, idGenerator), EMFModelIndex.class);
   }

}
