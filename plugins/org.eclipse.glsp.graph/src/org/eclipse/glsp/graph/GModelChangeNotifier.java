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

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.glsp.graph.impl.GModelChangeNotifierImpl;

/**
 * A notifier interface to notify registered {@link GModelListener}s if a {@link GModelElement} changes.
 * Mostly, changes of the {@link GModelRoot} will be tracked and broadcasted.
 */
public interface GModelChangeNotifier {

   /**
    * Get the GModelChangeNotifier for a given GModelElement.
    *
    * @param element The GModelElement to observe for changes.
    * @return The {@link GModelChangeNotifier} instance that observers the given element.
    */
   static GModelChangeNotifier get(final GModelElement element) {
      EObject root = EcoreUtil.getRootContainer(element);
      GModelChangeNotifier existingNotifier = (GModelChangeNotifierImpl) EcoreUtil.getExistingAdapter(root,
         GModelChangeNotifierImpl.class);
      return Optional.ofNullable(existingNotifier).orElseGet(() -> create(element));
   }

   /**
    * Create a new GModelChangeNotifier instance to notify about changes of the given {@link GModelElement}.
    *
    * @param element The GModelElement to observe for changes.
    * @return A new instance of {@link GModelChangeNotifier} that observers the given element.
    */
   static GModelChangeNotifier create(final GModelElement element) {
      return new GModelChangeNotifierImpl(EcoreUtil.getRootContainer(element));
   }

   /**
    * Remove an observed {@link GModelElement} from the notifier instance.
    *
    * @param element The GModelElement to remove from the observed notifier target.
    */
   static void remove(final GModelElement element) {
      EObject root = EcoreUtil.getRootContainer(element);
      GModelChangeNotifierImpl existingNotifier = (GModelChangeNotifierImpl) EcoreUtil.getExistingAdapter(root,
         GModelChangeNotifierImpl.class);
      if (existingNotifier == null) {
         return;
      }
      existingNotifier.unsetTarget(root);
   }

   /**
    * Add a {@link GModelListener} to the {@link GModelChangeNotifier} to observe GModelElement changes.
    *
    * @param listener The listener to add.
    */
   void addListener(GModelListener listener);

   /**
    * Remove a {@link GModelListener} from the {@link GModelChangeNotifier} to stop observing GModelElement changes.
    *
    * @param listener The listener to remove.
    */
   void removeListener(GModelListener listener);

}
