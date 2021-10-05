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
import org.eclipse.glsp.server.internal.session.DefaultClientSessionFactory;

import com.google.inject.AbstractModule;

/**
 * A helper module that can be used to configure and bind a {@link DiagramGlobalScope}. Used for the creation of GLSP
 * diagram session injectors to provide the correct {@link DiagramGlobalScope} (i.e. the instance maintained by the
 * parent server injector). This module is mixed into the {@link DiagramModule} to override the
 *
 * see also {@link DefaultClientSessionFactory}
 *
 */
public class DiagramGlobalScopeModule extends AbstractModule {

   protected final DiagramGlobalScope scope;

   public DiagramGlobalScopeModule(final DiagramGlobalScope scope) {
      this.scope = scope;
   }

   @Override
   protected void configure() {
      bindScope(DiagramGlobalSingleton.class, scope);
   }
}
