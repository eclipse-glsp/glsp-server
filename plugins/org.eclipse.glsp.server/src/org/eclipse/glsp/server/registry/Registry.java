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

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.internal.registry.MapRegistry;
import org.eclipse.glsp.server.operations.OperationHandler;

/**
 * A registry manages a set of key-value pairs and provides query functionality. In GLSP this is typically used to
 * provide a convenience API for working with multi-injected instances e.g. {@link ActionHandler}s or
 * {@link OperationHandler}s.
 * The default implementation uses a {@link Map} to manage the key value pairs.
 *
 * @see MapRegistry
 *
 * @param <K> Key type
 * @param <V> Value type
 */
public interface Registry<K, V> {

   /**
    * Registers a new key-value pair.
    *
    * @param key     The key object.
    * @param element The value object.
    * @return 'true' if the pair was registered successfully, 'false' if another pair with the same key is already
    *         registered.
    */
   boolean register(K key, V element);

   /**
    * Removes the value with the given key from the registry.
    *
    * @param key The key of the value which should be removed
    * @return true if the value was removed successfully, 'false' if no value was registered for the given key.
    */
   boolean deregister(K key);

   /**
    * Queries the registry to check whether a value for the given key is registered.
    *
    * @param key The key which should be checked.
    * @return 'true' if a key-value pair is registered for the given key, false otherwise.
    */
   boolean hasKey(K key);

   /**
    * Retrieve the value for the given key.
    *
    * @param key The key whose value should be retrieved.
    * @return An {@link Optional} with the registered value. Is empty if no value was registered for the given key.
    */
   Optional<V> get(K key);

   /**
    * Retrieve all registered values from the registry.
    *
    * @return A set of all registered keys.
    */
   Set<V> getAll();

   /**
    * Retrieve all registered keys from the registry.
    *
    * @return A set of all registered keys.
    */
   Set<K> keys();
}
