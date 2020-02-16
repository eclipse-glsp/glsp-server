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
package org.eclipse.glsp.server.provider;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.glsp.api.action.kind.InitCreateOperationAction;
import org.eclipse.glsp.api.handler.CreateOperationHandler;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.operation.Operation;
import org.eclipse.glsp.api.provider.ToolPaletteItemProvider;
import org.eclipse.glsp.api.supplier.OperationHandlerSupplier;
import org.eclipse.glsp.api.types.PaletteItem;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class DefaultToolPaletteItemProvider implements ToolPaletteItemProvider {

   @Inject
   protected OperationHandlerSupplier operationHandlerProvider;
   private int counter;

   @Override
   public List<PaletteItem> getItems(final Map<String, String> args, final GraphicalModelState modelState) {
      List<CreateOperationHandler> createOperationHandlers = operationHandlerProvider.get().stream()
         .filter(CreateOperationHandler.class::isInstance)
         .map(CreateOperationHandler.class::cast)
         .collect(Collectors.toList());
      counter = 0;

      List<PaletteItem> nodes = createOperationHandlers.stream()
         .filter(h -> h.getOperationKind().equals(Operation.Kind.CREATE_NODE))
         .flatMap(handler -> handler.getInitActions()
            .stream()
            .map(action -> create(action, handler.getLabel())))
         .collect(Collectors.toList());

      List<PaletteItem> edges = createOperationHandlers.stream()
         .filter(h -> h.getOperationKind().equals(Operation.Kind.CREATE_CONNECTION))
         .flatMap(handler -> handler.getInitActions()
            .stream()
            .map(action -> create(action, handler.getLabel())))
         .collect(Collectors.toList());

      return Lists.newArrayList(PaletteItem.createPaletteGroup("node-group", "Nodes", nodes, "fa-hammer", "A"),
         PaletteItem.createPaletteGroup("edge-group", "Edges", edges, "fa-hammer", "B"));

   }

   protected PaletteItem create(final InitCreateOperationAction action, final String label) {
      PaletteItem item = new PaletteItem("palette-item" + counter++, label, action);
      item.setSortString("" + counter);
      return item;
   }
}
