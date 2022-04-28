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
package org.eclipse.glsp.graph.util;

import java.util.function.Function;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

public final class RootAdapterUtil {
   private RootAdapterUtil() {}

   public static <T extends Adapter, E extends EObject> T getOrCreate(final E element,
      final Function<EObject, T> adapterCreator, final Class<T> adapterClass) {
      EObject root = EcoreUtil.getRootContainer(element);
      Object existingAdapter = root.eAdapters() != null ? EcoreUtil.getExistingAdapter(root, adapterClass) : null;
      if (existingAdapter != null) {
         return adapterClass.cast(existingAdapter);
      }
      T adapter = adapterCreator.apply(root);
      root.eAdapters().add(adapter);
      return adapter;
   }
}
