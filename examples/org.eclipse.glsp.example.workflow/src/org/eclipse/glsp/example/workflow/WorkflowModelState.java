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

import org.eclipse.glsp.graph.GGraph;
import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.server.model.DefaultGModelState;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.features.core.model.GModelFactory;

/**
 * This model state serves to demonstrate how to extend or create a custom model state.
 *
 * While this may not be necessary when handling JSON formatted graphs that already
 * correspond to a GModel (as the Workflow example does), since {@link DefaultGModelState}
 * is sufficient, it nonetheless provides an adequte example for custom formats.
 */
public class WorkflowModelState extends DefaultGModelState {

    /**
     * The source model that needs to be transformed into a GModel and its {@link GModelRoot}.
     * It is saved in the {@link GModelState} in order to later be available in the
     * corresponding {@link GModelFactory}.
     *
     * Its type solely depends on the used source model.
     */
    private GGraph model;

    public GGraph getModel() {
        return model;
    }

    public void setModel(GGraph model) {
        this.model = model;
    }
}
