/********************************************************************************
 * Copyright (c) 2020 EclipseSource and others.
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
package org.eclipse.glsp.server.features.toolpalette;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.glsp.server.actions.TriggerElementCreationAction;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.operations.CreateEdgeOperation;
import org.eclipse.glsp.server.operations.CreateNodeOperation;
import org.eclipse.glsp.server.operations.CreateOperation;
import org.eclipse.glsp.server.operations.CreateOperationHandler;
import org.eclipse.glsp.server.operations.OperationHandlerRegistry;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class DefaultToolPaletteItemProvider implements ToolPaletteItemProvider {

   @Inject
   protected OperationHandlerRegistry operationHandlerRegistry;
   private int counter;

   @Override
   public List<PaletteItem> getItems(final Map<String, String> args, final GModelState modelState) {
      List<CreateOperationHandler> handlers = operationHandlerRegistry.getAll().stream()
         .filter(CreateOperationHandler.class::isInstance)
         .map(CreateOperationHandler.class::cast)
         .collect(Collectors.toList());
      counter = 0;
      List<PaletteItem> nodes = createPaletteItems(handlers, CreateNodeOperation.class);
      List<PaletteItem> edges = createPaletteItems(handlers, CreateEdgeOperation.class);
      return Lists.newArrayList(PaletteItem.createPaletteGroup("node-group", "Nodes", nodes, "fa-hammer", "A"),
         PaletteItem.createPaletteGroup("edge-group", "Edges", edges, "fa-hammer", "B"));

   }

   protected List<PaletteItem> createPaletteItems(final List<CreateOperationHandler> handlers,
      final Class<? extends CreateOperation> operationClass) {
      return handlers.stream()
         .filter(h -> operationClass.isAssignableFrom(h.getHandledOperationType()))
         .flatMap(handler -> handler.getTriggerActions()
            .stream()
            .map(action -> create(action, handler.getLabel())))
         .sorted(Comparator.comparing(PaletteItem::getLabel))
         .collect(Collectors.toList());
   }

   protected PaletteItem create(final TriggerElementCreationAction action, final String label) {
      return new PaletteItem("palette-item" + counter++, label, action);
   }
}
