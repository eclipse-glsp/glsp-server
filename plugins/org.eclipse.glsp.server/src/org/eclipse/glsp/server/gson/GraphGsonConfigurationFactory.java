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
package org.eclipse.glsp.server.gson;

import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.gson.GraphGsonConfigurator;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;

/**
 * A factory used in the client-session-specific injectors to create
 * {@link GsonBuilder}s that can properly (de)serialize all graph elements (i.e. {@link GModelElement}s
 * that are used in the diagram model.
 */
public interface GraphGsonConfigurationFactory {

   /**
    * Create a new {@link GraphGsonConfigurator}. Can be overwritten to provide custom implementations of
    * {@link GraphGsonConfigurator}.
    *
    * @return a new instance of the graph gson configurator.
    */
   default GraphGsonConfigurator create() {
      return configure(new GraphGsonConfigurator());
   }

   /**
    * Configures the graph elements (i.e. {@link TypeAdapter}s) for diagram implementation.
    *
    * @param graphGsonConfigurator The {@link GraphGsonConfigurator} that should be configured.
    * @return The configured graph gson configurator.
    */
   GraphGsonConfigurator configure(GraphGsonConfigurator graphGsonConfigurator);

   /**
    * Creates and configures a new {@link GsonBuilder}.
    *
    * @return The configured gson builder.
    */
   default GsonBuilder configureGson() {
      return configureGsonBuilder(new GsonBuilder());
   }

   /**
    * Create a new {@link GraphGsonConfigurator} and use it to configured the given {@link GsonBuilder}.
    *
    * @param builder The gson builder that should be configured.
    * @return The configured gson builder.
    */
   default GsonBuilder configureGsonBuilder(final GsonBuilder builder) {
      return create().configureGsonBuilder(builder);
   }

}
