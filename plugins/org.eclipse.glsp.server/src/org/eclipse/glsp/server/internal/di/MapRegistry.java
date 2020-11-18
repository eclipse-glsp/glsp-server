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
package org.eclipse.glsp.server.internal.di;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.eclipse.glsp.server.protocol.GLSPServerException;
import org.eclipse.glsp.server.utils.Registry;

public abstract class MapRegistry<K, V> implements Registry<K, V> {
   protected Map<K, V> elements;

   public MapRegistry() {
      this.elements = new HashMap<>();
   }

   @Override
   public boolean register(final K key, final V instance) {
      return this.elements.putIfAbsent(key, instance) != null;
   }

   @Override
   public boolean deregister(final K key) {
      return elements.remove(key) != null;
   }

   @Override
   public boolean hasKey(final K key) {
      return this.elements.containsKey(key);
   }

   @Override
   public Optional<V> get(final K key) {
      return Optional.ofNullable(elements.get(key));
   }

   @Override
   public Set<V> getAll() { return new HashSet<>(elements.values()); }

   @Override
   public Set<K> keys() {
      return new HashSet<>(elements.keySet());
   }

   protected GLSPServerException missing(final K key) {
      return new GLSPServerException("Unknown registry key: " + key);
   }
}
