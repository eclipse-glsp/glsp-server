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

import org.eclipse.glsp.graph.gson.GraphGsonConfigurator;
import org.eclipse.glsp.server.diagram.DiagramConfiguration;
import org.eclipse.glsp.server.gson.GraphGsonConfigurationFactory;

import com.google.inject.Inject;

public class DefaultGraphGsonConfigurationFactory implements GraphGsonConfigurationFactory {

   @Inject()
   protected DiagramConfiguration diagramConfiguration;

   @Override
   public GraphGsonConfigurator configure(final GraphGsonConfigurator graphConfigurator) {
      graphConfigurator.withTypes(diagramConfiguration.getTypeMappings());
      diagramConfiguration.getGraphExtension()
         .ifPresent(extension -> graphConfigurator.withEPackages(extension.getEPackage()));
      return graphConfigurator;
   }
}
