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
package org.eclipse.glsp.server.actionhandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.kind.SaveModelAction;
import org.eclipse.glsp.api.action.kind.SetDirtyStateAction;
import org.eclipse.glsp.api.factory.GraphGsonConfiguratorFactory;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.utils.ClientOptions;
import org.eclipse.glsp.graph.GGraph;

import com.google.gson.Gson;
import com.google.inject.Inject;

public class SaveModelActionHandler extends BasicActionHandler<SaveModelAction> {
   private static final Logger LOG = Logger.getLogger(SaveModelActionHandler.class);

   @Inject
   protected GraphGsonConfiguratorFactory gsonConfigurationFactory;

   @Override
   public List<Action> executeAction(final SaveModelAction action, final GraphicalModelState modelState) {
      saveModelState(modelState);

      return listOf(new SetDirtyStateAction(modelState.isDirty()));
   }

   private void saveModelState(final GraphicalModelState modelState) {
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

   private Optional<File> convertToFile(final GraphicalModelState modelState) {
      Optional<String> sourceUriOpt = ClientOptions.getValue(modelState.getClientOptions(), ClientOptions.SOURCE_URI);
      if (sourceUriOpt.isPresent()) {
         return Optional.of(new File(sourceUriOpt.get()));
      }
      return Optional.empty();
   }
}
