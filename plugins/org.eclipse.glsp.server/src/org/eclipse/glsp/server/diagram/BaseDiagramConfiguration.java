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
package org.eclipse.glsp.server.diagram;

import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.glsp.graph.DefaultTypes;
import org.eclipse.glsp.graph.GraphExtension;
import org.eclipse.glsp.server.di.DiagramType;

import com.google.inject.Inject;

/**
 * A reusable base implementation of {@link DiagramConfiguration} that used DI to provide
 * the diagram type and the graph extension. In addition, default type mappings are configured.
 */
public abstract class BaseDiagramConfiguration implements DiagramConfiguration {
   @Inject()
   @DiagramType()
   protected String diagramType;

   @Inject
   protected Optional<GraphExtension> graphExtension;

   @Override
   public String getDiagramType() { return diagramType; }

   @Override
   public Map<String, EClass> getTypeMappings() { return DefaultTypes.getDefaultTypeMappings(); }

   @Override
   public Optional<GraphExtension> getGraphExtension() { return graphExtension; }

}
