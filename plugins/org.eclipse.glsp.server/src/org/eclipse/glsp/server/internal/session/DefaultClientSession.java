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

import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.disposable.Disposable;
import org.eclipse.glsp.server.session.ClientSession;

import com.google.inject.Injector;

public class DefaultClientSession extends Disposable implements ClientSession {

   protected final String id;
   protected final String diagramType;
   protected final ActionDispatcher actionDispatcher;
   protected final Injector injector;

   public DefaultClientSession(final String id, final String diagramType, final ActionDispatcher actionDispatcher,
      final Injector injector) {
      super();
      this.id = id;
      this.diagramType = diagramType;
      this.actionDispatcher = actionDispatcher;
      this.injector = injector;
   }

   @Override
   public String getId() { return id; }

   @Override
   public String getDiagramType() { return diagramType; }

   @Override
   public ActionDispatcher getActionDispatcher() { return actionDispatcher; }

   @Override
   public Injector getInjector() { return injector; }

}
