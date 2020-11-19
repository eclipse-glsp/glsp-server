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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.glsp.server.protocol.GLSPServerException;
import org.eclipse.glsp.server.utils.MultiRegistry;

public abstract class MapMultiRegistry<K, V> implements MultiRegistry<K, V> {
   protected Map<K, List<V>> elements;

   public MapMultiRegistry() {
      this.elements = new LinkedHashMap<>();
   }

   @Override
   public boolean register(final K key, final V instance) {
      List<V> instances = elements.computeIfAbsent(key, k -> new ArrayList<>());
      return instances.add(instance);
   }

   @Override
   public boolean deregister(final K key, final V instance) {
      List<V> instances = elements.get(key);
      return instances != null ? instances.remove(instance) : false;
   }

   @Override
   public boolean deregisterAll(final K key) {
      return elements.remove(key) != null;
   }

   @Override
   public boolean hasKey(final K key) {
      return this.elements.containsKey(key);
   }

   @Override
   public List<V> get(final K key) {
      return elements.getOrDefault(key, new ArrayList<>());
   }

   @Override
   public List<V> getAll() {
      return elements.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
   }

   protected GLSPServerException missing(final K key) {
      return new GLSPServerException("Unknown registry key: " + key);
   }
}
