/*******************************************************************************
 * Copyright (c) 2019-2020 EclipseSource and others.
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
package org.eclipse.glsp.server.protocol;

import org.eclipse.lsp4j.jsonrpc.json.adapters.JsonElementTypeAdapter;

import com.google.gson.annotations.JsonAdapter;

public class InitializeParameters {
   private String applicationId;
   @JsonAdapter(JsonElementTypeAdapter.Factory.class)
   private Object options;

   public Object getOptions() { return options; }

   public void setOptions(final Object options) { this.options = options; }

   public String getApplicationId() { return applicationId; }

   public void setApplicationId(final String applicationId) { this.applicationId = applicationId; }

   @Override
   public String toString() {
      return "InitializeParameters[options = " + options + "]";
   }
}
