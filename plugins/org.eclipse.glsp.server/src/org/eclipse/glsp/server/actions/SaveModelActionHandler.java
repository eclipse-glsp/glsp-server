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
package org.eclipse.glsp.server.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.glsp.graph.GGraph;
import org.eclipse.glsp.server.features.modelsourcewatcher.ModelSourceWatcher;
import org.eclipse.glsp.server.jsonrpc.GraphGsonConfiguratorFactory;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.utils.ClientOptions;

import com.google.gson.Gson;
import com.google.inject.Inject;

public class SaveModelActionHandler extends BasicActionHandler<SaveModelAction> {
   private static final Logger LOG = Logger.getLogger(SaveModelActionHandler.class);

   @Inject
   protected GraphGsonConfiguratorFactory gsonConfigurationFactory;

   @Inject
   private ModelSourceWatcher modelSourceWatcher;

   @Override
   public List<Action> executeAction(final SaveModelAction action, final GModelState modelState) {
      modelSourceWatcher.pauseWatching(modelState);
      try {
         saveModelState(modelState);
      } finally {
         modelSourceWatcher.continueWatching(modelState);
      }
      return listOf(new SetDirtyStateAction(modelState.isDirty()));
   }

   protected void saveModelState(final GModelState modelState) {
      convertToFile(modelState).ifPresent(file -> {
         try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            Gson gson = gsonConfigurationFactory.configureGson().setPrettyPrinting().create();
            gson.toJson(modelState.getRoot(), GGraph.class, writer);
            modelState.saveIsDone();
         } catch (IOException e) {
            LOG.error(e);
         }
      });
   }

   protected Optional<File> convertToFile(final GModelState modelState) {
      Optional<String> sourceUriOpt = ClientOptions.getValue(modelState.getClientOptions(), ClientOptions.SOURCE_URI);
      if (sourceUriOpt.isPresent()) {
         return Optional.of(new File(sourceUriOpt.get()));
      }
      return Optional.empty();
   }
}
