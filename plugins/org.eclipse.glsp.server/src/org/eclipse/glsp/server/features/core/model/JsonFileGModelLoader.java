/********************************************************************************
 * Copyright (c) 2019-2021 EclipseSource and others.
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

import static org.eclipse.glsp.server.types.GLSPServerException.getOrThrow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.glsp.graph.DefaultTypes;
import org.eclipse.glsp.graph.GGraph;
import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.graph.GraphFactory;
import org.eclipse.glsp.server.gson.GraphGsonConfigurationFactory;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.types.GLSPServerException;
import org.eclipse.glsp.server.utils.ClientOptionsUtil;

import com.google.gson.Gson;
import com.google.inject.Inject;

/**
 * A source model loader that reads the graph model directly from a JSON file.
 */
public class JsonFileGModelLoader implements ModelSourceLoader {

   private static Logger LOG = Logger.getLogger(JsonFileGModelLoader.class);
   private static String EMPTY_ROOT_ID = "glsp-graph";

   @Inject
   private GraphGsonConfigurationFactory gsonConfiguratior;

   @Inject
   protected GModelState modelState;

   @Override
   public void loadSourceModel(final RequestModelAction action) {
      final File file = convertToFile(modelState);
      loadSourceModel(file, modelState).ifPresent(root -> {
         modelState.setRoot(root);
         modelState.getRoot().setRevision(-1);
      });
   }

   protected File convertToFile(final GModelState modelState) {
      return getOrThrow(ClientOptionsUtil.getSourceUriAsFile(modelState.getClientOptions()),
         "Invalid file URI:" + ClientOptionsUtil.getSourceUri(modelState.getClientOptions()));
   }

   protected Optional<GModelRoot> loadSourceModel(final File file, final GModelState modelState) {
      try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
         Gson gson = gsonConfiguratior.configureGson().create();
         GGraph root = gson.fromJson(reader, GGraph.class);
         if (root == null) {
            boolean isEmpty = file.length() == 0;
            if (isEmpty) {
               return Optional.of(createNewEmptyRoot(modelState));
            }
            throw new IOException("Could not deserialize file contents of: " + file.toURI().toString());
         }
         return Optional.ofNullable(root);
      } catch (IOException e) {
         LOG.error(e);
         throw new GLSPServerException("Could not load model from file: " + file.toURI().toString(), e);
      }
   }

   protected GModelRoot createNewEmptyRoot(final GModelState modelState) {
      GModelRoot root = GraphFactory.eINSTANCE.createGModelRoot();
      root.setId(EMPTY_ROOT_ID);
      root.setType(DefaultTypes.GRAPH);
      return root;
   }

}
