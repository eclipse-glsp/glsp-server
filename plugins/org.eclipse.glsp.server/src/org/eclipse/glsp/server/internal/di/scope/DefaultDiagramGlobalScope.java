/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
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
package org.eclipse.glsp.server.internal.di.scope;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.glsp.server.di.DiagramModule;
import org.eclipse.glsp.server.di.DiagramType;
import org.eclipse.glsp.server.di.scope.DiagramGlobalScope;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.internal.SingletonScope;

@SuppressWarnings("restriction")
public class DefaultDiagramGlobalScope implements DiagramGlobalScope {

   private final Map<String, Map<Key<?>, Object>> diagramTypeStores = new ConcurrentHashMap<>();

   public DefaultDiagramGlobalScope() {}

   @Override
   public <T> Provider<T> scope(final Key<T> key, final Provider<T> unscoped) {
      return () -> {
         Optional<String> diagramType = getDiagramType(unscoped);
         if (diagramType.isEmpty()) {
            throw new IllegalArgumentException(
               "Could not complete scoping operation. Diagram type could not be derived."
                  + " Injector needs to be create with a 'GLSPDiagramModule'!");
         }
         return getInstanceFromScope(diagramType.get(), key, unscoped);
      };
   }

   @SuppressWarnings("unchecked")
   protected synchronized <T> T getInstanceFromScope(final String diagramType, final Key<T> key,
      final Provider<T> unscoped) {
      Map<Key<?>, Object> diagramStore = diagramTypeStores.computeIfAbsent(diagramType, k -> new HashMap<>());
      T instance = (T) diagramStore.computeIfAbsent(key, k -> unscoped.get());
      return instance;
   }

   /**
    * Retrieve the diagram type from the given {@link Provider} using the underlying injector.
    * It is expected that the injector was created with a {@link DiagramModule} otherwise the
    * diagram type cannot be retrieved.
    *
    * @param unscoped The provider whose implicit diagram type should be retrieved.
    * @return Optional of the diagram type.
    */
   protected Optional<String> getDiagramType(final Provider<?> unscoped) {
      return getInjector(unscoped)
         .map(injector -> injector.getInstance(Key.get(String.class, DiagramType.class)));
   }

   /**
    * Retrieve the injector from the provider. Looking at the implementation for {@link SingletonScope}
    * we know that this provider will always be an instance of {@code ProviderToInternalFactoryAdapter}.
    * From this we can retrieve the injector. However, {@code ProviderToInternalFactoryAdapter} is an internal class
    * which means we have to use reflection here to access the injector.
    *
    * @param unscoped The provider whose injector should be retrieved.
    * @return Optional of the injector, is empty if an exception occurred during the reflective access.
    */
   protected Optional<Injector> getInjector(final Provider<?> unscoped) {
      try {
         Method getInjector = unscoped.getClass().getDeclaredMethod("getInjector");
         getInjector.setAccessible(true);
         Injector injector = (Injector) getInjector.invoke(unscoped);
         return Optional.of(injector);
      } catch (ReflectiveOperationException e) {
         return Optional.empty();
      }
   }

   @Override
   public String toString() {
      return "DiagramGlobalScope";
   }

}
