/********************************************************************************
 * Copyright (c) 2020-2021 EclipseSource and others.
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
package org.eclipse.glsp.server.registry;

import java.util.List;
import java.util.Map;

import org.eclipse.glsp.server.internal.registry.MapMultiRegistry;

/**
 * Multi registry used to manage a set of key-value pairs. The main difference to {@link Registry} is that
 * a multi registry doesn't enforce a 1-1 relation between key and value(s).
 * One key can be associated with multiple values. The default implementation uses a {@link Map} to manage the key value
 * pairs.
 *
 * @see MapMultiRegistry
 *
 * @param <K> Type of the key
 * @param <V> Type of the values
 */
public interface MultiRegistry<K, V> {
   boolean register(K key, V element);

   boolean deregister(K key, V element);

   boolean deregisterAll(K key);

   boolean hasKey(K key);

   List<V> get(K key);

   List<V> getAll();
}
