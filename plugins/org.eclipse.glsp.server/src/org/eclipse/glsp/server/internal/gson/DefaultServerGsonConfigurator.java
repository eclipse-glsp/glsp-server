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
package org.eclipse.glsp.server.internal.gson;

import static org.eclipse.glsp.server.di.ServerModule.DIAGRAM_MODULES;

import java.util.Map;

import org.eclipse.glsp.graph.gson.EnumTypeAdapter;
import org.eclipse.glsp.graph.gson.GraphGsonConfigurator;
import org.eclipse.glsp.server.actions.ActionRegistry;
import org.eclipse.glsp.server.diagram.ServerConfigurationContribution;
import org.eclipse.glsp.server.gson.ActionTypeAdapter;
import org.eclipse.glsp.server.gson.ServerGsonConfigurator;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.name.Named;

public class DefaultServerGsonConfigurator implements ServerGsonConfigurator {

   protected ActionRegistry actionRegistry;
   protected GraphGsonConfigurator graphGsonConfigurator;

   @Inject()
   public DefaultServerGsonConfigurator(final Injector serverInjector,
      @Named(DIAGRAM_MODULES) final Map<String, Module> diagramModules, final ActionRegistry actionRegistry) {
      this.actionRegistry = actionRegistry;
      graphGsonConfigurator = createGraphGsonConfigurator();

      diagramModules.values().stream()
         .map(module -> serverInjector.createChildInjector(module).getInstance(ServerConfigurationContribution.class))
         .forEach(contribution -> {
            contribution.configure(actionRegistry);
            contribution.configure(graphGsonConfigurator);
         });
   }

   protected TypeAdapterFactory getActionTypeAdapterFactory() {
      return new ActionTypeAdapter.Factory(actionRegistry.getAllAsMap());
   }

   protected GraphGsonConfigurator createGraphGsonConfigurator() {
      return new GraphGsonConfigurator();
   }

   @Override
   public GsonBuilder configureGsonBuilder(final GsonBuilder gsonBuilder) {
      gsonBuilder.registerTypeAdapterFactory(getActionTypeAdapterFactory());
      gsonBuilder.registerTypeAdapterFactory(new EnumTypeAdapter.Factory());
      return graphGsonConfigurator.configureGsonBuilder(gsonBuilder);
   }

}
