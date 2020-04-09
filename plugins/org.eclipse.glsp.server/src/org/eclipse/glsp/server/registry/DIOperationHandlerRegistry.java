/********************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
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

import java.util.Optional;
import java.util.Set;

import org.eclipse.glsp.api.handler.CreateOperationHandler;
import org.eclipse.glsp.api.handler.OperationHandler;
import org.eclipse.glsp.api.operation.CreateOperation;
import org.eclipse.glsp.api.operation.Operation;
import org.eclipse.glsp.api.registry.MapRegistry;
import org.eclipse.glsp.api.registry.OperationHandlerRegistry;
import org.eclipse.glsp.api.utils.ReflectionUtil;

import com.google.inject.Inject;

public class DIOperationHandlerRegistry
   implements OperationHandlerRegistry {

   private final MapRegistry<String, OperationHandler> internalRegistry;

   @Inject
   public DIOperationHandlerRegistry(final Set<OperationHandler> handlers) {
      internalRegistry = new MapRegistry<String, OperationHandler>() {};
      handlers.forEach(handler -> {
         ReflectionUtil.construct(handler.getHandledOperationType())
            .ifPresent(operation -> register(operation, handler));
      });
   }

   protected String deriveKey(final Operation key) {
      String elementTypeId = key instanceof CreateOperation ? ((CreateOperation) key).getElementTypeId() : null;
      return deriveKey(key, elementTypeId);
   }

   protected String deriveKey(final Operation key, final String elementTypeId) {
      String derivedKey = key.getClass().getName();
      if (elementTypeId != null) {
         return derivedKey + "_" + elementTypeId;
      }
      return derivedKey;
   }

   @Override
   public boolean register(final Operation key, final OperationHandler handler) {
      if (handler instanceof CreateOperationHandler) {
         return ((CreateOperationHandler) handler).getHandledElementTypeIds().stream()
            .allMatch(typeId -> internalRegistry.register(deriveKey(key, typeId), handler));

      }
      return internalRegistry.register(deriveKey(key, null), handler);
   }

   @Override
   public boolean deregister(final Operation key) {
      return internalRegistry.deregister(deriveKey(key));
   }

   @Override
   public boolean hasKey(final Operation key) {
      return internalRegistry.hasKey(deriveKey(key));
   }

   @Override
   public Optional<OperationHandler> get(final Operation key) {
      return internalRegistry.get(deriveKey(key));
   }

   @Override
   public Set<OperationHandler> getAll() { return internalRegistry.getAll(); }
}
