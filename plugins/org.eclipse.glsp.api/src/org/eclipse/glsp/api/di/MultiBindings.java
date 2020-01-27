/********************************************************************************
 * Copyright (c) 2020 EclipseSource and others.
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
package org.eclipse.glsp.api.di;

import java.util.Collection;
import java.util.HashSet;

import com.google.inject.Binder;
import com.google.inject.multibindings.Multibinder;

public class MultiBindings<T> {

   public static <T> MultiBindings<T> create(final Class<T> clazz) {
      return new MultiBindings<>(clazz);
   }

   private final Collection<Class<? extends T>> bindings;

   private final Class<T> type;

   public MultiBindings(final Class<T> clazz) {
      type = clazz;
      bindings = new HashSet<>();

   }

   /**
    * Applies the stored bindings to the given binder in form of a set binding.
    *
    * @param binder binder
    */
   public void applyBindings(final Binder binder) {
      Multibinder<T> multiBinder = Multibinder.newSetBinder(binder, getType());
      bindings.forEach(b -> multiBinder.addBinding().to(b));
   }

   public boolean add(final Class<? extends T> newBinding) {
      return bindings.add(newBinding);
   }

   public boolean addAll(final Collection<Class<? extends T>> newBindings) {
      return bindings.addAll(newBindings);
   }

   public boolean remove(final Class<? extends T> toRemove) {
      return bindings.remove(toRemove);
   }

   public boolean removeAll(final Collection<Class<? extends T>> toRemove) {
      return bindings.removeAll(toRemove);
   }

   public boolean rebind(final Class<? extends T> oldBinding, final Class<? extends T> newBinding) {
      if (remove(oldBinding)) {
         add(newBinding);
         return true;
      }
      return false;
   }

   Class<T> getType() { return type; }

}
