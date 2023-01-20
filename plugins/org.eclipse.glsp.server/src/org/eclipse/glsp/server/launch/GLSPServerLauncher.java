/********************************************************************************
 * Copyright (c) 2019-2023 EclipseSource and others.
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
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.glsp.server.di.ServerModule;
import org.eclipse.glsp.server.utils.LaunchUtil;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public abstract class GLSPServerLauncher {

   private static Logger LOGGER = LogManager.getLogger(GLSPServerLauncher.class);
   private final List<Module> modules;

   public GLSPServerLauncher(final ServerModule serverModule, final Module... additionalModules) {
      modules = new ArrayList<>();
      modules.add(serverModule);
      Stream.of(additionalModules).forEach(modules::add);
   }

   public Injector createInjector() {
      return Guice.createInjector(modules);
   }

   public abstract void start(String hostname, int port);

   public void start(final String hostname, final int port, final boolean consoleLogging, final String logDir,
      final Level logLevel) {
      // configure logging
      LaunchUtil.configureLogger(consoleLogging, logDir, logLevel);

      start(hostname, port);
   }

   public void start(final String hostname, final int port, final DefaultCLIParser parser) {
      try {
         // configure logging
         LaunchUtil.configure(parser);
      } catch (ParseException e) {
         LOGGER.error("Error during log configuration!", e);
      }
      start(hostname, port);
   }

   public abstract void shutdown();

}
