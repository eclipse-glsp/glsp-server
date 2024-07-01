/********************************************************************************
 * Copyright (c) 2019-2023 EclipseSource and others.
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
package org.eclipse.glsp.example.workflow;

import com.google.gson.Gson;
import com.google.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import org.eclipse.glsp.graph.GGraph;
import org.eclipse.glsp.server.actions.SaveModelAction;
import org.eclipse.glsp.server.features.core.model.RequestModelAction;
import org.eclipse.glsp.server.features.core.model.SourceModelStorage;
import org.eclipse.glsp.server.gson.GraphGsonConfigurationFactory;
import org.eclipse.glsp.server.types.GLSPServerException;
import org.eclipse.glsp.server.utils.ClientOptionsUtil;
import org.eclipse.glsp.server.gmodel.GModelStorage;

/**
 * This {@link SourceModelStorage} serves as a naive implementation similar to the default {@link GModelStorage}.
 * The main difference being that the source model is not directly instantiated as GModel, which works
 * in the Workflow example (since its data format is already a valid GModel), but not generally. Therefore,
 * this example is more easily applicable to custom model formats.
 *
 * The model saved here is later on transformed in {@link WorkflowModelFactory} and has to be updated in the handlers,
 *  if source model and GModel are different.
 */
public class WorkflowModelStorage implements SourceModelStorage {

    @Inject
    protected WorkflowModelState modelState;

    protected Gson gson;

    @Inject
    public void configureGson(final GraphGsonConfigurationFactory gsonConfigurator) {
        gson = gsonConfigurator.configureGson().setPrettyPrinting().create();
    }

    @Override
    public void loadSourceModel(final RequestModelAction action) {
        final File file = ClientOptionsUtil.getSourceUriAsFile(action.getOptions()).orElseThrow();
        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            var model = gson.fromJson(reader, GGraph.class);
            modelState.setModel(model);
            modelState.setProperty(ClientOptionsUtil.SOURCE_URI, action.getOptions().get(ClientOptionsUtil.SOURCE_URI));
        } catch (IOException e) {
            throw new GLSPServerException("Could not load model from file: " + file.toURI().toString(), e);
        }
    }

    @Override
    public void saveSourceModel(final SaveModelAction action) {
        File file = ClientOptionsUtil.getAsFile(modelState.getProperty(ClientOptionsUtil.SOURCE_URI, String.class).orElseThrow());
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            gson.toJson(modelState.getRoot(), GGraph.class, writer);
        } catch (IOException e) {
            throw new GLSPServerException("An error occured during save process.", e);
        }
    }

}
