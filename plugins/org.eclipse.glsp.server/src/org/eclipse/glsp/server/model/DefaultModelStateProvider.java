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

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.model.ModelStateProvider;

import com.google.inject.Singleton;

@Singleton
public class DefaultModelStateProvider implements ModelStateProvider {

   private final Map<String, GraphicalModelState> clientModelStates;

   public DefaultModelStateProvider() {
      clientModelStates = new ConcurrentHashMap<>();
   }

   @Override
   public Optional<GraphicalModelState> getModelState(final String clientId) {
      return Optional.ofNullable(clientModelStates.get(clientId));
   }

   @Override
   public GraphicalModelState create(final String clientId) {
      GraphicalModelState modelState = createModelState();
      modelState.setClientId(clientId);
      clientModelStates.put(clientId, modelState);
      return modelState;
   }

   protected GraphicalModelState createModelState() {
      return new ModelStateImpl();
   }

   @Override
   public void remove(final String clientId) {
      clientModelStates.remove(clientId);
   }

}
