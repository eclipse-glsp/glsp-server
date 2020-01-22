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
package org.eclipse.glsp.api.registry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.glsp.api.jsonrpc.GLSPServerException;

public abstract class InstanceRegistry<T> {
   protected Map<String, T> elements;

   public InstanceRegistry() {
      this.elements = new HashMap<>();
   }

   public boolean register(final String key, final T instance) {
      return this.elements.putIfAbsent(key, instance) != null;
   }

   public boolean deregister(final String key) {
      return elements.remove(key) != null;
   }

   public boolean hasKey(final String key) {
      return this.elements.containsKey(key);
   }

   public T get(final String key) {
      T result = elements.get(key);
      if (result == null) {
         throw missing(key);
      }
      return result;
   }

   public Set<T> getAll() { return new HashSet<>(elements.values()); }

   protected GLSPServerException missing(final String key) {
      return new GLSPServerException("Unknown registry key: " + key);
   }
}
