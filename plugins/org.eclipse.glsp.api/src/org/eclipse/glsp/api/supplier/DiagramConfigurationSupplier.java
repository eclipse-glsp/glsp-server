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
package org.eclipse.glsp.api.supplier;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.glsp.api.diagram.DiagramConfiguration;

public interface DiagramConfigurationSupplier extends Supplier<Set<DiagramConfiguration>> {

   Collection<String> getDiagramTypes();

   default Optional<DiagramConfiguration> get(final String diagramType) {
      return get().stream().filter(h -> h.getDiagramType().equals(diagramType)).findFirst();
   }

   default boolean provides(final String diagramType) {
      return getDiagramTypes().contains(diagramType);
   }

   default Optional<DiagramConfiguration> createDefault() {
      if (getDiagramTypes().size() == 1) {
         String diagramType = getDiagramTypes().iterator().next();
         return get(diagramType);
      }
      return Optional.empty();
   }

   default Map<String, EClass> getCollectiveTypeMappings() {
      Map<String, EClass> collectiveTypeMappings = new HashMap<>();
      get().stream().map(DiagramConfiguration::getTypeMappings).forEach(collectiveTypeMappings::putAll);
      return collectiveTypeMappings;
   }
}
