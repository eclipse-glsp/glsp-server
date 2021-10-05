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
package org.eclipse.glsp.server.di.scope;

import org.eclipse.glsp.server.di.DiagramModule;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;

/**
 * A {@link Scope} implementation that is used in GLSP for sharing instances across multiple diagram injectors of the
 * same diagram type. A diagram injector is an injector with an installed {@link DiagramModule}. The
 * {@link DiagramGlobalSingleton} annotation is used to annotated bindings that should be made available to all injectors with
 * the same diagram type as Singleton.
 *
 * Use this scope with care. If its used on classes that inject session specific values (e.g. the clientId) it can cause
 * unintended side effects.
 *
 */
public interface DiagramGlobalScope extends Scope {

   /**
    * A no-op implementation that is used as default/fallback binding for {@link DiagramGlobalScope} until the correct
    * {@link DiagramGlobalScope} is installed with the help of a{@link DiagramGlobalScopeModule}.
    * While this scope implementation is configured bindings that are bound to the {@link DiagramGlobalScope}
    * essentially
    * behave the same as bindings that are unscoped (i.e. a new instance will be created on every injection call).
    *
    * This class is intended as a fallback and should the {@link NullImpl#scope(Key, Provider)} method should not be
    * called if all methods have been configured correctly. Hence a warning will be logged everytime this method is
    * invoked:
    */
   final class NullImpl implements DiagramGlobalScope {

      @Override
      /**
       * No-op implementation, provides the same behaviour as unscoped bindings. Should not be called if the module
       * setup
       * has been configured correctly.
       */
      public <T> Provider<T> scope(final Key<T> key, final Provider<T> unscoped) {
         return unscoped;
      }

   }
}
