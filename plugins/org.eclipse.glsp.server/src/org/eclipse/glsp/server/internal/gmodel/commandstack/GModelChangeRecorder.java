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
package org.eclipse.glsp.server.internal.gmodel.commandstack;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.change.FeatureChange;
import org.eclipse.emf.ecore.change.util.BasicChangeRecorder;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.glsp.graph.GModelChangeNotifier;
import org.eclipse.glsp.graph.GModelListener;
import org.eclipse.glsp.graph.GModelRoot;

public class GModelChangeRecorder extends BasicChangeRecorder implements GModelListener {

   private final GModelRoot modelRoot;

   public GModelChangeRecorder(final GModelRoot root) {
      this.modelRoot = root;
      GModelChangeNotifier.get(modelRoot).addListener(this);
   }

   @Override
   public void dispose() {
      GModelChangeNotifier.get(modelRoot).removeListener(this);
   }

   public GModelChangeRecorder beginRecording() {
      if (changeDescription == null) {
         changeDescription = createChangeDescription();
      }
      setChangeDescription(changeDescription);
      setRecording(true);
      return this;
   }

   /*
    * This method is based on EMF's ChangeRecorder.notifyChanged(Notification)
    */
   @Override
   public void notifyChanged(final Notification notification) {
      Object notifier = notification.getNotifier();
      if (notifier instanceof EObject) {
         Object feature = notification.getFeature();
         if (feature instanceof EReference) {
            EReference eReference = (EReference) feature;
            handleFeature(eReference, eReference.isContainment() ? eReference : null, notification,
               (EObject) notifier);
         } else if (feature != null) {
            handleFeature((EStructuralFeature) feature, null, notification, (EObject) notifier);
         }
      }
   }

   /*
    * This method is based on EMF's
    * ChangeRecorder.handleFeature(EStructuralFeature, Reference, Notification,
    * EObject). In contrast to the implementation in EMF, we don't add an adapter
    * for each new object, as this is done already in GModelChangeNotifier. Thus,
    * we avoid having to create a new EContentAdapter (expensive!) for each
    * recording.
    */
   @SuppressWarnings({ "checkstyle:CyclomaticComplexity", "checkstyle:JavaNCSS" })
   protected void handleFeature(final EStructuralFeature feature, final EReference containment,
      final Notification notification,
      final EObject eObject) {
      List<FeatureChange> changes = null;
      FeatureChange change = null;
      if (shouldRecord(feature, eObject)) {
         changes = getFeatureChanges(eObject);
         change = getFeatureChange(changes, feature);
      }

      switch (notification.getEventType()) {
         case Notification.RESOLVE:
         case Notification.SET:
         case Notification.UNSET: {
            if (change == null && changes != null) {
               if (feature.isMany()) {
                  List<Object> oldValue = new BasicEList<>((Collection<?>) eObject.eGet(feature));
                  int index = notification.getPosition();
                  if (index != Notification.NO_INDEX) {
                     oldValue.set(index, notification.getOldValue());
                  }
                  change = createFeatureChange(eObject, feature, oldValue, notification.wasSet());
               } else {
                  Object oldValue = notification.getOldValue();
                  change = createFeatureChange(eObject, feature, oldValue, notification.wasSet());
               }
               ((InternalEList<FeatureChange>) changes).addUnique(change);
            }
            break;
         }
         case Notification.ADD: {
            if (change == null && changes != null) {
               List<Object> oldValue = new BasicEList<>((Collection<?>) eObject.eGet(feature));
               oldValue.remove(notification.getPosition());
               change = createFeatureChange(eObject, feature, oldValue, notification.wasSet());
               ((InternalEList<FeatureChange>) changes).addUnique(change);
            }
            break;
         }
         case Notification.ADD_MANY: {
            if (change == null && changes != null) {
               List<Object> oldValue = new BasicEList<>((Collection<?>) eObject.eGet(feature));
               int position = notification.getPosition();
               for (int i = ((Collection<?>) notification.getNewValue()).size(); --i >= 0;) {
                  oldValue.remove(position);
               }
               change = createFeatureChange(eObject, feature, oldValue, notification.wasSet());
               ((InternalEList<FeatureChange>) changes).addUnique(change);
            }
            break;
         }
         case Notification.REMOVE: {
            if (change == null && changes != null) {
               List<Object> oldValue = new BasicEList<>((Collection<?>) eObject.eGet(feature));
               int position = notification.getPosition();
               if (position == Notification.NO_INDEX) {
                  position = 0;
               }
               oldValue.add(position, notification.getOldValue());
               change = createFeatureChange(eObject, feature, oldValue, notification.wasSet());
               ((InternalEList<FeatureChange>) changes).addUnique(change);
            }
            break;
         }
         case Notification.REMOVE_MANY: {
            if (change == null && changes != null) {
               @SuppressWarnings("unchecked")
               List<Object> removedValues = (List<Object>) notification.getOldValue();
               List<Object> oldValue = new BasicEList<>((Collection<?>) eObject.eGet(feature));
               int[] positions = (int[]) notification.getNewValue();
               if (positions == null) {
                  oldValue.addAll(removedValues);
               } else {
                  for (int i = 0; i < positions.length; ++i) {
                     oldValue.add(positions[i], removedValues.get(i));
                  }
               }
               change = createFeatureChange(eObject, feature, oldValue, notification.wasSet());
               ((InternalEList<FeatureChange>) changes).addUnique(change);
            }
            break;
         }
         case Notification.MOVE: {
            if (change == null && changes != null) {
               EList<Object> oldValue = new BasicEList<>((Collection<?>) eObject.eGet(feature));
               int position = notification.getPosition();
               int oldPosition = (Integer) notification.getOldValue();
               oldValue.move(oldPosition, position);
               change = createFeatureChange(eObject, feature, oldValue, notification.wasSet());
               ((InternalEList<FeatureChange>) changes).addUnique(change);
            }
            break;
         }
         default:
            break;
      }
   }

}
