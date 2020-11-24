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
package org.eclipse.glsp.server.diagram;

import static org.eclipse.glsp.server.protocol.GLSPServerException.getOrThrow;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.protocol.GLSPServerException;
import org.eclipse.glsp.server.utils.ClientOptions;
import org.eclipse.glsp.server.utils.Registry;

public interface DiagramConfigurationRegistry extends Registry<String, DiagramConfiguration> {

   default Map<String, EClass> getCollectiveTypeMappings() {
      Map<String, EClass> collectiveTypeMappings = new HashMap<>();
      getAll().stream().map(DiagramConfiguration::getTypeMappings).forEach(collectiveTypeMappings::putAll);
      return collectiveTypeMappings;
   }

   /**
    * Return the {@link DiagramConfiguration} from this registry, corresponding to the specified
    * modelState. The modelState is expected to have the {@link ClientOptions#DIAGRAM_TYPE} option,
    * and this DiagramType should exist in this registry. Otherwise, a {@link GLSPServerException}
    * will be thrown.
    *
    * @param modelState
    *                      the {@link GModelState}
    * @return
    *         the {@link DiagramConfiguration} corresponding to the specified modelState
    * @throws GLSPServerException if the {@link ClientOptions#DIAGRAM_TYPE} associated to the modelState
    *                                doesn't exist in this registry
    */
   default DiagramConfiguration get(final GModelState modelState) {
      return getOrThrow(ClientOptions.getValue(modelState.getClientOptions(), ClientOptions.DIAGRAM_TYPE)
         .flatMap(this::get), "Unsupported diagram kind");
   }
}
