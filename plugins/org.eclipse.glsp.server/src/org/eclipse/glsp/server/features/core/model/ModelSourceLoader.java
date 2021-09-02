/********************************************************************************
 * Copyright (c) 2020-2021 EclipseSource and others.
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

import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.utils.ClientOptionsUtil;

/**
 * A source model loader loads models into the model state.
 * <p>
 * A <i>source model</i> is an arbitrary model from which the graph model of the diagram is to be created.
 * Implementations of source model loaders are specific to the type of source model or persistence format that is used
 * for a type of source model. A source model loader obtains the information on which source model shall loaded from a
 * {@link RequestModelAction}; typically its client options. Once the source model is loaded, a model loader is expected
 * to put the loaded source model into the model state for further processing, such as transforming the loaded model
 * into a graph model (see {@link GModelFactory}).
 * </p>
 *
 * @see ClientOptionsUtil
 * @see GModelFactory
 */
public interface ModelSourceLoader {
   /**
    * Loads a source model into the <code>modelState</code>.
    *
    * @param action     Action sent by the client to specifying the information needed to load the source model.
    * @param modelState The model state into which the source model shall be put.
    */
   void loadSourceModel(RequestModelAction action, GModelState modelState);
}
