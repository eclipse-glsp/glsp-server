/********************************************************************************
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
 ********************************************************************************/
package org.eclipse.glsp.graph;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.glsp.graph.impl.GModelIndexImpl;

/**
 * Is used to index all child elements of a {@link GModelRoot} by their id. Offers a set
 * of query methods to retrieve indexed elements.
 */
public interface GModelIndex {

   /**
    * Returns the GModelIndex instance that contains the root of the given {@link GModelElement}.
    *
    * @param element The GModelElement for which an index instance should be returned.
    * @return The GModelIndex instance that contains the root of the given GModelElement.
    */
   static GModelIndex get(final GModelElement element) {
      EObject root = EcoreUtil.getRootContainer(element);
      GModelIndex existingIndex = (GModelIndexImpl) EcoreUtil.getExistingAdapter(root, GModelIndexImpl.class);
      return Optional.ofNullable(existingIndex).orElseGet(() -> (create(element)));
   }

   /**
    * Create a new GModelIndex instance of the root of the given {@link GModelElement}.
    *
    * @param element The GModelElement for which a new index instance should be created.
    * @return A new GModelIndex instance that contains the root of the given GModelElement.
    */
   static GModelIndex create(final GModelElement element) {
      return new GModelIndexImpl(EcoreUtil.getRootContainer(element));
   }

   /**
    * Removes the root of the given {@link GModelElement} from an existing GModelIndex instance.
    *
    * @param element The GModelElement which root should be removed from the index instance.
    */
   static void remove(final GModelElement element) {
      EObject root = EcoreUtil.getRootContainer(element);
      GModelIndexImpl existingIndex = (GModelIndexImpl) EcoreUtil.getExistingAdapter(root, GModelIndexImpl.class);
      if (existingIndex == null) {
         return;
      }
      existingIndex.unsetTarget(root);
   }

   /**
    * Returns an optional {@link GModelElement} by its elementId.
    *
    * @param elementId The id of the requested {@link GModelElement}.
    * @return An optional instance of the {@link GModelElement}.
    */
   Optional<GModelElement> get(String elementId);

   /**
    * Returns a set of {@link GModelElement} instances by a Collection of elementIds.
    *
    * @param elementIds The ids to request the {@link GModelElement} instances.
    * @return A set of {@link GModelElement}s.
    */
   Set<GModelElement> getAll(Collection<String> elementIds);

   /**
    * Returns a collection of all incoming edges for a {@link GModelElement}.
    *
    * @param node The requested {@link GModelElement}.
    * @return A collection of all incoming edges for a {@link GModelElement}.
    */
   Collection<GEdge> getIncomingEdges(GModelElement node);

   /**
    * Returns a collection of all outgoing edges for a {@link GModelElement}.
    *
    * @param node The requested {@link GModelElement}.
    * @return A collection of all outgoing edges for a {@link GModelElement}.
    */
   Collection<GEdge> getOutgoingEdges(GModelElement node);

   /**
    * Returns a set of all {@link GModelElement} ids contained in this instance of the GModelIndex.
    *
    * @return A set of elementIds.
    */
   Set<String> allIds();

   /**
    * Returns the contained {@link GModelRoot} in this instance of the GModelIndex.
    *
    * @return The contained {@link GModelRoot} instance.
    */
   GModelElement getRoot();

   /**
    * Returns the current amount of occurrences of an EClass in this instance of the GModelIndex.
    *
    * @param eClass     The EClass to be counted in this instance of the GModelIndex.
    * @param idProvider A function that provides an id based on the counter result.
    *
    * @return The amount of occurrences of an EClass.
    */
   int getCounter(EClass eClass, Function<Integer, String> idProvider);

   /**
    * Returns the first element of type clazz starting from the element with the
    * given id and walking up the parent hierarchy.
    *
    * @param elementId The id of the element to start the search from.
    * @param clazz     The class of which the found element should be an instance.
    * @param <T>       The type of the element to be found.
    * @return An optional with the element of type clazz or an empty optional.
    */
   default <T extends GModelElement> Optional<T> findElementByClass(final String elementId, final Class<T> clazz) {
      Optional<GModelElement> element = get(elementId);
      if (element.isPresent()) {
         return findElementByClass(element.get(), clazz);
      }
      return Optional.empty();
   }

   /**
    * Returns the first element of type clazz starting from the given element and
    * walking up the parent hierarchy.
    *
    * @param element The element to start the search from.
    * @param clazz   The class of which the found element should be an instance.
    * @param <T>     The type of the element to be found.
    * @return An optional with the element of type clazz or an empty optional.
    *
    *
    */
   default <T extends GModelElement> Optional<T> findElementByClass(final GModelElement element, final Class<T> clazz) {
      if (element == null) {
         return Optional.empty();
      }
      if (clazz.isInstance(element)) {
         return Optional.of(clazz.cast(element));
      }

      GModelElement parent = element.getParent();
      return parent != null ? findElementByClass(parent, clazz) : Optional.empty();
   }

   /**
    * Returns the first element matching the predicate starting element with the
    * given id and walking up the parent hierarchy.
    *
    * @param elementId The element to start the search from
    * @param predicate The predicate which the element should match
    * @return An optional with the first element matching the predicate or an empty
    *         optional
    */
   default Optional<GModelElement> findElement(final String elementId, final Predicate<GModelElement> predicate) {
      Optional<GModelElement> element = get(elementId);
      if (element.isPresent()) {
         return findElement(element.get(), predicate);
      }
      return Optional.empty();
   }

   /**
    * Returns the first element matching the predicate starting from the given
    * element and walking up the parent hierarchy.
    *
    * @param element   The element to start the search from.
    * @param predicate The predicate which the element should match.
    * @return An optional with the first element matching the predicate or an empty
    *         optional.
    */
   default Optional<GModelElement> findElement(final GModelElement element, final Predicate<GModelElement> predicate) {
      if (element == null) {
         return Optional.empty();
      }
      if (predicate.test(element)) {
         return Optional.of(element);
      }

      GModelElement parent = element.getParent();
      return parent != null ? findElement(parent, predicate) : Optional.empty();
   }

   /**
    * Returns all elements of the type clazz contained in the {@link GModelRoot}.
    *
    * @param <T>   Type of the elements to be returned.
    * @param clazz The class of which the returned elements should be instances.
    * @return A set containing the elements of type clazz.
    */
   default <T extends GModelElement> Set<T> getAllByClass(final Class<T> clazz) {
      return findAll(this.getRoot(), clazz);
   }

   /**
    * Returns all elements of the type clazz contained in a given parent {@link GModelElement}.
    *
    * @param <T>    Type of the elements to be returned.
    * @param parent The parent element to start the element search from.
    * @param type   The class of which the returned elements should be instances.
    * @return A set containing the elements of type clazz.
    */
   default <T extends GModelElement> Set<T> findAll(final GModelElement parent, final Class<T> type) {
      return getStream(parent).flatMap(this::getStream).filter(type::isInstance).map(type::cast)
         .collect(Collectors.toSet());
   }

   /**
    * Returns a {@link Stream} of all contained {@link GModelElement} in the given {@link GModelElement}.
    *
    * @param element The element to retrieve all contained elements as stream.
    * @return A stream of all elements.
    */
   default Stream<GModelElement> getStream(final GModelElement element) {
      if (element == null) {
         return Stream.empty();
      }
      if (element.getChildren() == null) {
         return Stream.of(element);
      }
      return Stream.concat(Stream.of(element), element.getChildren().stream());
   }

   /**
    * Returns the current amount of type occurrences in this instance of the GModelIndex.
    *
    * @param eClass The EClass to be counted in this instance of the GModelIndex.
    *
    * @return The amount of type occurrences.
    */
   int getTypeCount(EClass eClass);

}
