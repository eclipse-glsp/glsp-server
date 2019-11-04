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
package org.eclipse.glsp.graph;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.glsp.graph.impl.GModelChangeNotifierImpl;

public interface GModelChangeNotifier {

   static GModelChangeNotifier get(final GModelElement element) {
      EObject root = EcoreUtil.getRootContainer(element);
      GModelChangeNotifier existingNotifier = (GModelChangeNotifierImpl) EcoreUtil.getExistingAdapter(root,
         GModelChangeNotifierImpl.class);
      return Optional.ofNullable(existingNotifier).orElseGet(() -> create(element));
   }

   static GModelChangeNotifier create(final GModelElement element) {
      return new GModelChangeNotifierImpl(EcoreUtil.getRootContainer(element));
   }

   static void remove(final GModelElement element) {
      EObject root = EcoreUtil.getRootContainer(element);
      GModelChangeNotifierImpl existingNotifier = (GModelChangeNotifierImpl) EcoreUtil.getExistingAdapter(root,
         GModelChangeNotifierImpl.class);
      if (existingNotifier == null) {
         return;
      }
      existingNotifier.unsetTarget(root);
   }

   void addListener(GModelListener listener);

   void removeListener(GModelListener listener);

}
