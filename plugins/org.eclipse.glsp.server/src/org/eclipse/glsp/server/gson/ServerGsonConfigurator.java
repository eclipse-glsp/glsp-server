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
import org.eclipse.glsp.server.actions.Action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;

/**
 * Configurator of the {@link Gson} object used by LSP4J for (de)serialization of json-rpc messages. The server
 * gson configurator configures a given {@link GsonBuilder} to be able to properly handle {@link Action}
 * objects and {@link GModelElement}s.
 */
public interface ServerGsonConfigurator {

   /**
    * Configure a given {@link GsonBuilder} and set up the necessary {@link TypeAdapter}s to properly handle
    * {@link Action}s and {@link GModelElement}s.
    *
    * @param gsonBuilder The gson builder that should be configured.
    * @return The configured gson builder.
    */
   GsonBuilder configureGsonBuilder(GsonBuilder gsonBuilder);
}
