/*******************************************************************************
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
 ******************************************************************************/
package org.eclipse.glsp.server.features.core.model;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.glsp.server.actions.RequestAction;
import org.eclipse.glsp.server.di.DiagramModule;

/**
 * Sent from the client to the server in order to request a GModel.
 *
 * <p>
 * This is the first request sent by the client in order to obtain a GModel for rendering from the server.
 * The server then uses the {@link SourceModelStorage} configured in the {@link DiagramModule} to load the source model
 * and transforms it with the configured {@link GModelFactory} into a GModel and send this GModel to the client via the
 * {@link ModelSubmissionHandler}.
 * </p>
 *
 * @see RequestModelActionHandler
 */
public class RequestModelAction extends RequestAction<SetModelAction> {

   public static final String KIND = "requestModel";

   private final Map<String, String> options;

   public RequestModelAction() {
      super(KIND);
      options = new HashMap<>();
   }

   public RequestModelAction(final Map<String, String> options) {
      super(KIND);
      this.options = options;
   }

   public Map<String, String> getOptions() { return options; }
}
