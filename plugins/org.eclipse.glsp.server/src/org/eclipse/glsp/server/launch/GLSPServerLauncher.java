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
package org.eclipse.glsp.server.launch;

import org.eclipse.glsp.api.di.GLSPModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

public abstract class GLSPServerLauncher {

   private GLSPModule module;
   private Injector injector;

   public GLSPServerLauncher() {}

   public GLSPServerLauncher(final GLSPModule module) {
      this.module = module;
   }

   protected Injector doSetup() {
      return Guice.createInjector(module);
   }

   public void start(final String hostname, final int port) {
      if (injector == null) {
         injector = doSetup();
      }
      run(hostname, port);
   }

   protected abstract void run(String hostname, int port);

   public abstract void shutdown();

   public GLSPModule getGLSPModule() { return module; }

   public Injector getInjector() { return injector; }

   public void setModule(final GLSPModule module) { this.module = module; }
}
