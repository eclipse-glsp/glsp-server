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
package org.eclipse.glsp.server.utils;

import com.google.inject.Module;
import com.google.inject.util.Modules;

public final class ModuleUtil {

   private ModuleUtil() {}

   /**
    * Mix in additional modules into a base modules. Bindings that are already present in the base module will be
    * overwritten with the binding of the additional module.
    *
    * @param baseModule        the base module.
    * @param additionalModules the additional modules
    * @return A merged (mixedin) module
    */
   public static Module mixin(Module baseModule, final Module... additionalModules) {
      for (Module additionalModule : additionalModules) {
         baseModule = Modules.override(baseModule).with(additionalModule);
      }
      return baseModule;
   }
}
