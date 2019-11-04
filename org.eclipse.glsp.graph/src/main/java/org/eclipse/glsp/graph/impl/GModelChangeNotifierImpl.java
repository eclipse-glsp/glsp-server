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

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.glsp.graph.GModelChangeNotifier;
import org.eclipse.glsp.graph.GModelListener;

public class GModelChangeNotifierImpl extends EContentAdapter implements GModelChangeNotifier {

   private List<GModelListener> listeners = new CopyOnWriteArrayList<>();

   public GModelChangeNotifierImpl(EObject target) {
      target.eAdapters().add(this);
   }

   @Override
   public void notifyChanged(Notification notification) {
      super.notifyChanged(notification);
      listeners.forEach(listener -> listener.notifyChanged(notification));
   }

   @Override
   public void addListener(GModelListener listener) {
      listeners.add(listener);
   }

   @Override
   public void removeListener(GModelListener listener) {
      listeners.remove(listener);
   }

   @Override
   public boolean isAdapterForType(Object type) {
      return GModelChangeNotifierImpl.class.equals(type);
   }

}
