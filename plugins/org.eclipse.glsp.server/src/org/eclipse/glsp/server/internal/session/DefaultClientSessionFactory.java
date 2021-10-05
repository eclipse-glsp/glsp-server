/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
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
package org.eclipse.glsp.server.internal.session;

import static org.eclipse.glsp.server.types.GLSPServerException.getOrThrow;

import java.util.Map;
import java.util.Optional;

import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.di.ClientIdModule;
import org.eclipse.glsp.server.di.ServerModule;
import org.eclipse.glsp.server.di.scope.DiagramGlobalScope;
import org.eclipse.glsp.server.di.scope.DiagramGlobalScopeModule;
import org.eclipse.glsp.server.session.ClientSession;
import org.eclipse.glsp.server.session.ClientSessionFactory;
import org.eclipse.glsp.server.utils.ModuleUtil;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.name.Named;

public class DefaultClientSessionFactory implements ClientSessionFactory {

   @Inject()
   protected Injector serverInjector;

   @Inject()
   protected DiagramGlobalScope diagramGlobalScope;

   @Inject()
   @Named(ServerModule.DIAGRAM_MODULES)
   protected Map<String, Module> diagramModules;

   @Override
   public ClientSession create(final String clientSessionId, final String diagramType) {
      Module diagramModule = getOrThrow(Optional.of(diagramModules.get(diagramType)),
         "Could not retrieve module configuration for diagram type: " + diagramType);
      Module clientIdModule = new ClientIdModule(clientSessionId);
      Module diagramScopeModule = new DiagramGlobalScopeModule(diagramGlobalScope);

      Module clientSessionModule = ModuleUtil.mixin(diagramModule, clientIdModule, diagramScopeModule);

      Injector sessionInjector = serverInjector.createChildInjector(clientSessionModule);
      return new DefaultClientSession(clientSessionId, diagramType,
         sessionInjector.getInstance(ActionDispatcher.class), sessionInjector);
   }

}
