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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.glsp.server.di.GLSPModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public abstract class GLSPServerLauncher {

   private final GLSPModule glspModule;
   private final List<Module> modules;

   public GLSPServerLauncher(final GLSPModule glspModule) {
      this.glspModule = glspModule;
      modules = new ArrayList<>();
      modules.add(glspModule);
   }

   public void addAdditionalModules(final Module... modules) {
      Arrays.stream(modules)
         .filter(module -> !this.modules.contains(module))
         .forEach(this.modules::add);
   }

   public Injector createInjector() {
      return Guice.createInjector(modules);
   }

   public abstract void start(String hostname, int port);

   public abstract void shutdown();

   public GLSPModule getGLSPModule() { return glspModule; }

}
