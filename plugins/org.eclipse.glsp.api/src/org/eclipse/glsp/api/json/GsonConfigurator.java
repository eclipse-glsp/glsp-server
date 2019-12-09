/*******************************************************************************
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
 ******************************************************************************/
package org.eclipse.glsp.api.json;

import org.eclipse.glsp.api.action.ActionRegistry;
import org.eclipse.glsp.api.factory.GraphGsonConfiguratorFactory;
import org.eclipse.glsp.graph.gson.EnumTypeAdapter;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import com.google.inject.Inject;

public class GsonConfigurator {

   private ActionRegistry actionRegistry;
   private GraphGsonConfiguratorFactory gsonConfigurationFactory;

   @Inject
   public GsonConfigurator(ActionRegistry actionRegistry, GraphGsonConfiguratorFactory gsonConfigurationFactory) {
      this.actionRegistry = actionRegistry;
      this.gsonConfigurationFactory = gsonConfigurationFactory;
   }

   protected TypeAdapterFactory getActionTypeAdapterFactory() {
      return new ActionTypeAdapter.Factory(actionRegistry.getAllActions());
   }

   public GsonBuilder configureGsonBuilder(GsonBuilder gsonBuilder) {
      gsonBuilder.registerTypeAdapterFactory(getActionTypeAdapterFactory());
      gsonBuilder.registerTypeAdapterFactory(new EnumTypeAdapter.Factory());
      return gsonConfigurationFactory.create().configureGsonBuilder(gsonBuilder);
   }

}
