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
package org.eclipse.glsp.server.di;

import java.util.Optional;
import java.util.function.Consumer;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.binder.ScopedBindingBuilder;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.multibindings.OptionalBinder;

/**
 *
 * Common super class for GLSP guice {@link Module}s.
 */
public abstract class GLSPModule extends AbstractModule {
   public static final String CLIENT_ACTIONS = "ClientActions";

   /**
    * Splits the configure method in two phases. The "configureBase" sub method
    * provides configures the default bindings of this module. The "configureAdditionals" sub method is a no-op
    * method that can be overwritten by subclasses to configure additional bindings without having to overwrite the
    * configure() method.
    */
   @Override
   protected void configure() {
      configureBase();
      configureAdditionals();
   }

   protected abstract void configureBase();

   protected void configureAdditionals() {
      // empty as default. Can be extended in subclasses.
   }

   /**
    * Configuration method for multibinded values. The passed configurator is typically a submethod of this module. This
    * means
    * that subclasses can customize the {@link MultiBinding} object before the actual {@link Multibinder} is created.
    *
    * @param <T>          Type of the {@link MultiBinding}
    * @param binding      The multi binding configuration object
    * @param configurator The consumer that should be used to configure the given {@link Multibinder}
    */
   protected <T> void configure(final MultiBinding<T> binding, final Consumer<MultiBinding<T>> configurator) {
      configurator.accept(binding);
      binding.applyBinding(binder());
   }

   /**
    * Utility method to bind a key to an Optional of a given class. If the given class is null the Optional will be
    * empty.
    *
    * @param <T> Type of key
    * @param <S> (Sub)type to which the key should be bound.
    * @param key The key that should be used for binding
    * @param to  Subtype to which this key should be bound. Can be null
    * @return An optional of the {@link ScopedBindingBuilder}. Is empty if the 'to' type was null
    */
   protected <T, S extends T> Optional<ScopedBindingBuilder> bindOptionally(final Class<T> key, final Class<S> to) {
      OptionalBinder.newOptionalBinder(binder(), key);
      return Optional.ofNullable(to).map(toClass -> {
         return bind(key).to(toClass);
      });
   }

}
