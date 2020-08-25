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
package org.eclipse.glsp.server.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import org.eclipse.glsp.api.action.kind.RequestModelAction;
import org.eclipse.glsp.api.factory.GraphGsonConfiguratorFactory;
import org.eclipse.glsp.api.factory.ModelFactory;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.protocol.GLSPServerException;
import org.eclipse.glsp.api.utils.ClientOptions;
import org.eclipse.glsp.graph.GGraph;
import org.eclipse.glsp.graph.GModelRoot;

import com.google.gson.Gson;
import com.google.inject.Inject;

/**
 * A base class which can be used for all model factories that load an SModel
 * from a json file.
 *
 */
public class JsonFileModelFactory implements ModelFactory {
   private static final String FILE_PREFIX = "file://";

   @Inject
   private GraphGsonConfiguratorFactory gsonConfigurationFactory;
   private GModelRoot modelRoot;

   @Override
   public GModelRoot loadModel(final RequestModelAction action, final GraphicalModelState modelState) {
      String sourceURI = action.getOptions().get(ClientOptions.SOURCE_URI);
      File modelFile = convertToFile(sourceURI);
      if (modelFile != null && modelFile.exists()) {
         try (Reader reader = new InputStreamReader(new FileInputStream(modelFile), StandardCharsets.UTF_8)) {
            Gson gson = gsonConfigurationFactory.configureGson().create();
            modelRoot = gson.fromJson(reader, GGraph.class);
         } catch (IOException e) {
            throw new GLSPServerException("Could not load model from file: " + sourceURI, e);
         }
      }
      return modelRoot;
   }

   protected File convertToFile(final String sourceURI) {
      if (sourceURI != null) {
         return new File(sourceURI.replace(FILE_PREFIX, ""));
      }
      return null;
   }

}
