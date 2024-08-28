/********************************************************************************
 * Copyright (c) 2024 EclipseSource and others.
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
package org.eclipse.glsp.server.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * A BiIndex maintains a bidirectional map i.e a map that preserves the uniqueness of its values as well as
 * that of its keys. It offers a subset of the {@link Map} API to query, add and remove elements to/from the index.
 * In addition, provides immutable access to the underlying key-value map and its inverse map.
 *
 * This implementation is taylored towards the use in (source) model indexing and is not intended as a general purpose
 * bi-map.
 *
 */
public class BiIndex<K, V> {

   protected Map<K, V> map = new HashMap<>();
   protected Map<V, K> inverseMap = new HashMap<>();

   /**
    * Returns an unmodifiable map of the currently indexed key-value pairs.
    */
   public Map<K, V> map() {
      return Map.copyOf(map);
   }

   /**
    * Returns an unmodifiable inversed map (value to key) of the currently indexed key-value pairs.
    */
   public Map<V, K> inverseMap() {
      return Map.copyOf(inverseMap);
   }

   public int size() {
      return map.size();
   }

   public boolean isEmpty() { return map.isEmpty(); }

   public boolean containsKey(final Object key) {
      return map.containsKey(key);
   }

   public boolean containsValue(final Object value) {
      return inverseMap.containsKey(value);
   }

   public V get(final Object key) {
      return map.get(key);
   }

   public K getKey(final Object value) {
      return inverseMap.get(value);
   }

   /**
    * Behaves similar to {@link Map#put} but throws an expection if the given value is already associated with a
    * different key.
    *
    * @throws IllegalArgumentException if the given value is already bound to a different key in this
    *                                     index. To avoid this exception, call {@link
    *                                     #forcePut} instead.
    */
   public V put(final K key, final V value) {
      K existingKey = inverseMap.get(value);
      if (existingKey != null && existingKey != key) {
         throw new IllegalArgumentException("Value already present: " + value);
      }
      V result = map.put(key, value);
      inverseMap.put(value, key);
      return result;
   }

   /**
    * Force implementation of {@link #put}(). Removes any exsting entry for the given value
    * before continuing with the put operation i.e. it overrides any existing entries.
    */
   public V forcePut(final K key, final V value) {
      K existingKey = inverseMap.remove(value);
      if (existingKey != null) {
         map.remove(existingKey);

      }
      return put(key, value);
   }

   public void clear() {
      map.clear();
      inverseMap.clear();
   }

   public V remove(final Object key) {
      V value = map.remove(key);
      if (value != null) {
         inverseMap.remove(value);
      }
      return value;
   }

   public K removeValue(final Object value) {
      K key = inverseMap.remove(value);
      if (key != null) {
         map.remove(key);
      }
      return key;

   }

   public boolean remove(final Object key, final Object value) {
      Object currentValue = map.get(key);
      Object currentKey = inverseMap.get(value);
      if (currentValue.equals(value) && currentKey.equals(key)) {
         map.remove(key);
         inverseMap.remove(value);
         return true;
      }
      return false;
   }

   /**
    * Similar to {@link Map#putIfAbsent}. In addition to the key, it
    * also checks if the value is absent and only continues with the put operation if
    * both are absent.
    */
   public V putIfAbsent(final K key, final V value) {
      V v = get(key);

      if (v == null && !inverseMap.containsKey(value)) {
         v = put(key, value);
      }

      return v;
   }

}
