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
package org.eclipse.glsp.server.features.core.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.eclipse.glsp.graph.GGraph;
import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.server.jsonrpc.GraphGsonConfiguratorFactory;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.protocol.GLSPServerException;
import org.eclipse.glsp.server.utils.ClientOptions;

import com.google.gson.Gson;
import com.google.inject.Inject;

/**
 * A base class which can be used for all model factories that load an SModel
 * from a json file.
 *
 */
public class JsonFileModelFactory implements ModelFactory {

   @Inject
   private GraphGsonConfiguratorFactory gsonConfigurationFactory;

   private GModelRoot modelRoot;

   @Override
   public GModelRoot loadModel(final RequestModelAction action, final GModelState modelState) {
      final Optional<File> file = ClientOptions.getSourceUriAsFile(action.getOptions());
      if (file.isPresent() && file.get().exists()) {
         try (Reader reader = new InputStreamReader(new FileInputStream(file.get()), StandardCharsets.UTF_8)) {
            Gson gson = gsonConfigurationFactory.configureGson().create();
            modelRoot = gson.fromJson(reader, GGraph.class);
         } catch (IOException e) {
            throw new GLSPServerException("Could not load model from file: " + file.get().toURI().toString(), e);
         }
      }
      return modelRoot;
   }

}
