/********************************************************************************
 * Copyright (c) 2022 Logicals and others.
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
package org.eclipse.glsp.graph.util;

import java.util.UUID;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class IdKeeperAdapter extends AdapterImpl {
   private final String id;

   public IdKeeperAdapter(final Notifier target) {
      super();
      setTarget(target);
      this.id = createId();
   }

   protected String createId() {
      return UUID.randomUUID().toString();
   }

   public String getId() { return id; }

   @Override
   public boolean isAdapterForType(final Object type) {
      return type instanceof EObject;
   }

   public static IdKeeperAdapter getOrCreate(final Notifier notifier) {
      Object existingAdapter = notifier.eAdapters() != null
         ? EcoreUtil.getExistingAdapter(notifier, IdKeeperAdapter.class)
         : null;
      if (existingAdapter != null) {
         return IdKeeperAdapter.class.cast(existingAdapter);
      }
      final IdKeeperAdapter adapter = new IdKeeperAdapter(notifier);
      notifier.eAdapters().add(adapter);
      return adapter;
   }

   public static String getId(final Notifier notifier) {
      return getOrCreate(notifier).getId();
   }

}
