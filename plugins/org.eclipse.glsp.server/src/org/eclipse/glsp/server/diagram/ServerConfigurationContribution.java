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
package org.eclipse.glsp.server.diagram;

import org.eclipse.glsp.graph.gson.GraphGsonConfigurator;
import org.eclipse.glsp.server.actions.ActionRegistry;
import org.eclipse.glsp.server.di.DiagramModule;
import org.eclipse.glsp.server.gson.ServerGsonConfigurator;

/**
 * In order to configure some artifacts (e.g the {@link ServerGsonConfigurator} the server needs
 * diagram language specific information. To enable proper configuration each {@link DiagramModule} provides
 * a {@link ServerConfigurationContribution} which can be queried by the server injector to delegate the configuration
 * of objects that require diagram-specific knowledge.
 */
public interface ServerConfigurationContribution {

   /**
    * Configure (i.e. register a new actions in) the global {@link ActionRegistry}
    *
    * @param registry The configured action registry
    */
   void configure(ActionRegistry registry);

   /**
    * Configure the global {@link GraphGsonConfigurator } that is used by the {@link ServerGsonConfigurator}.
    *
    * @param graphGsonConfigurator The graph gson configurator that should be configured
    */
   void configure(GraphGsonConfigurator graphGsonConfigurator);

}
