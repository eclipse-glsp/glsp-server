/********************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
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
package org.eclipse.glsp.graph.impl;

import static org.eclipse.glsp.graph.GraphPackage.Literals.GEDGE__SOURCE;
import static org.eclipse.glsp.graph.GraphPackage.Literals.GEDGE__TARGET;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GModelIndex;

import com.google.common.base.Preconditions;

public class GModelIndexImpl extends ECrossReferenceAdapter implements GModelIndex {

   private final Map<String, GModelElement> idToElement = new HashMap<>();
   private final Map<EClass, Set<GModelElement>> typeToElements = new HashMap<>();
   private final GModelElement root;

   public GModelIndexImpl(final EObject target) {
      Preconditions.checkArgument(target instanceof GModelElement);
      this.root = (GModelElement) target;
      target.eAdapters().add(this);
      addIfGModelElement(target);
   }

   @Override
   protected void addAdapter(final Notifier notifier) {
      super.addAdapter(notifier);
      addIfGModelElement(notifier);
   }

   protected void addIfGModelElement(final Notifier notifier) {
      if (notifier instanceof GModelElement) {
         notifyAdd((GModelElement) notifier);
      }
   }

   @Override
   protected void removeAdapter(final Notifier notifier) {
      super.removeAdapter(notifier);
      if (notifier instanceof GModelElement) {
         notifyRemove((GModelElement) notifier);
      }
   }

   protected void notifyAdd(final GModelElement element) {
      if (idToElement.put(element.getId(), element) == null) {
         getTypeSet(element.eClass()).add(element);
         for (GModelElement child : element.getChildren()) {
            notifyAdd(child);
         }
      }
   }

   protected void notifyRemove(final GModelElement element) {
      if (idToElement.remove(element.getId()) != null) {
         getTypeSet(element.eClass()).remove(element);
         for (GModelElement child : element.getChildren()) {
            notifyRemove(child);
         }
      }
   }

   @Override
   public boolean isAdapterForType(final Object type) {
      return GModelIndexImpl.class.equals(type);
   }

   @Override
   protected void handleContainment(final Notification notification) {
      super.handleContainment(notification);
      switch (notification.getEventType()) {
         case Notification.REMOVE: {
            Notifier oldValue = (Notifier) notification.getOldValue();
            removeAdapter(oldValue);
            break;
         }
         case Notification.REMOVE_MANY: {
            for (Object oldValue : (Collection<?>) notification.getOldValue()) {
               removeAdapter((Notifier) oldValue);
            }
            break;
         }
         default:
            break;
      }
   }

   @Override
   public Optional<GModelElement> get(final String elementId) {
      Optional<GModelElement> indexMatch = Optional.ofNullable(idToElement.get(elementId));
      if (!indexMatch.isPresent() && isCurrentlyBuildingIndex()) {
         return searchElementInModel(elementId);
      }
      return indexMatch;
   }

   @Override
   public Set<GModelElement> getAll(final Collection<String> elementIds) {
      return elementIds.stream().map(this::get).map(Optional::get).collect(Collectors.toSet());
   }

   @Override
   public Collection<GEdge> getIncomingEdges(final GModelElement node) {
      return getEdgesWithIncomingReference(node, GEDGE__TARGET);
   }

   @Override
   public Collection<GEdge> getOutgoingEdges(final GModelElement node) {
      return getEdgesWithIncomingReference(node, GEDGE__SOURCE);
   }

   protected List<GEdge> getEdgesWithIncomingReference(final GModelElement node, final EReference feature) {
      return this.getNonNavigableInverseReferences(node).stream()
         .filter(s -> feature.equals(s.getEStructuralFeature())).filter(s -> s.getEObject() instanceof GEdge)
         .map(s -> (GEdge) s.getEObject()).collect(Collectors.toList());
   }

   @Override
   public Set<String> allIds() {
      return idToElement.keySet();
   }

   @Override
   public int getCounter(final EClass eClass, final Function<Integer, String> idProvider) {
      int i = getTypeCount(eClass);
      while (true) {
         String id = idProvider.apply(i);
         if (!get(id).isPresent()) {
            break;
         }
         i++;
      }
      return i;
   }

   private Set<GModelElement> getTypeSet(final EClass eClass) {
      return typeToElements.computeIfAbsent(eClass, t -> new HashSet<>());
   }

   @Override
   public int getTypeCount(final EClass eClass) {
      return getTypeSet(eClass).size();
   }

   @Override
   public GModelElement getRoot() { return root; }

   /**
    * Indicates whether this indexer is currently setting targets and thus not done indexing.
    *
    * @return <code>true</code> if currently setting targets, <code>false</code> otherwise.
    */
   public boolean isCurrentlyBuildingIndex() { return settingTargets; }

   /**
    * Searches the element by iterating through the model and not based on a built up index.
    *
    * @param elementId The element id to search for.
    * @return The element with the <code>elementId</code> or {@link Optional#empty()}.
    */
   public Optional<GModelElement> searchElementInModel(final String elementId) {
      if (elementId.equals(root.getId())) {
         return Optional.of(root);
      }
      for (TreeIterator<EObject> eAllContents = root.eAllContents(); eAllContents.hasNext();) {
         EObject next = eAllContents.next();
         if (next instanceof GModelElement) {
            GModelElement gModelElement = (GModelElement) next;
            if (elementId.equals(gModelElement.getId())) {
               return Optional.of(gModelElement);
            }
         }
      }
      return Optional.empty();
   }

}
