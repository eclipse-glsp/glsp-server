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
package org.eclipse.glsp.server.factory;

import org.eclipse.glsp.api.factory.GraphGsonConfiguratorFactory;
import org.eclipse.glsp.api.supplier.DiagramConfigurationSupplier;
import org.eclipse.glsp.graph.GraphExtension;
import org.eclipse.glsp.graph.gson.GGraphGsonConfigurator;

import com.google.inject.Inject;

public class DefaultGraphGsonConfiguratorFactory implements GraphGsonConfiguratorFactory {
   @Inject
   private DiagramConfigurationSupplier diagramConfigurationProvider;
   @Inject(optional = true)
   private GraphExtension graphExtension;

   @Override
   public GGraphGsonConfigurator create() {
      GGraphGsonConfigurator configurator = new GGraphGsonConfigurator()
         .withTypes(diagramConfigurationProvider.getCollectiveTypeMappings());
      if (graphExtension != null) {
         configurator = configurator.withEPackages(graphExtension.getEPackage());
      }
      return configurator;
   }
}
