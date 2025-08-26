/********************************************************************************
 * Copyright (c) 2020-2024 EclipseSource and others.
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BiIndexTest {

   private final BiIndex<String, String> index = new BiIndex<>();

   @BeforeEach
   public void beforeEach() {
      index.clear();
   }

   @Test
   void putNewKeyValuePair() {
      String key = "key";
      String value = "value";
      assertEquals(0, index.size());
      assertTrue(index.isEmpty());
      assertFalse(index.containsKey(key));
      assertFalse(index.containsValue(value));
      index.put(key, value);
      assertEquals(1, index.size());
      assertFalse(index.isEmpty());
      assertTrue(index.containsKey(key));
      assertTrue(index.containsValue(value));
      assertEquals(key, index.getKey(value));
      assertEquals(value, index.get(key));

   }

   @Test
   void putConflictingKeyValuePair_shouldThrowExpection() {
      String key = "key";
      String value = "value";
      index.put(key, value);
      assertThrows(IllegalArgumentException.class, () -> index.put("AnotherKey", value));
   }

   @Test
   void putDuplicateKeyValuePair() {
      String key = "key";
      String value = "value";
      index.put(key, value);
      index.put(key, value);
      assertEquals(1, index.size());
      assertFalse(index.isEmpty());
      assertTrue(index.containsKey(key));
      assertTrue(index.containsValue(value));
   }

   @Test
   void forcePutConflictingKeyValuePair() {
      String key = "key";
      String value = "value";
      String key2 = "anotherKey";
      index.put(key, value);
      index.forcePut(key2, value);
      assertFalse(index.containsKey(key));
      assertTrue(index.containsKey(key2));
      assertTrue(index.containsValue(value));
   }

   @Test
   void clearIndex() {
      index.put("k", "v");
      index.put("s", "f");
      assertFalse(index.isEmpty());
      index.clear();
      assertTrue(index.isEmpty());
      assertTrue(index.map.isEmpty());
      assertTrue(index.inverseMap.isEmpty());
   }

   @Test
   void removeKey() {
      String key = "key";
      String value = "value";
      index.put(key, value);
      assertFalse(index.isEmpty());
      index.remove(key);
      assertTrue(index.isEmpty());
      assertFalse(index.containsKey(key));
      assertFalse(index.containsValue(value));
   }

   @Test
   void removeValue() {
      String key = "key";
      String value = "value";
      index.put(key, value);
      assertFalse(index.isEmpty());
      index.removeValue(value);
      assertTrue(index.isEmpty());
      assertFalse(index.containsKey(key));
      assertFalse(index.containsValue(value));
   }

   @Test
   void removeKeyValuePair() {
      String key = "key";
      String value = "value";
      index.put(key, value);
      assertFalse(index.isEmpty());
      index.remove(key, value);
      assertTrue(index.isEmpty());
      assertFalse(index.containsKey(key));
      assertFalse(index.containsValue(value));
   }

   @Test
   void putIfAbsentWithAbsentKeyValuePair() {
      String key = "key";
      String value = "value";
      index.putIfAbsent(key, value);
      assertFalse(index.isEmpty());
      assertTrue(index.containsKey(key));
      assertTrue(index.containsValue(value));
   }

   @Test
   void putIfAbsentWithPresentKeyAndOrValue() {
      String key = "key";
      String value = "value";
      index.put(key, value);
      index.putIfAbsent(key, "anotherValue");
      assertTrue(index.containsValue(value));
      assertEquals(index.get(key), value);
      index.putIfAbsent("anotherKey", value);
      assertTrue(index.containsKey(key));
      assertEquals(index.getKey(value), key);
   }

}
