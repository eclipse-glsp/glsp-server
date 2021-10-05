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
package org.eclipse.glsp.server.di;

import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.actions.SaveModelActionHandler;
import org.eclipse.glsp.server.features.clipboard.RequestClipboardDataActionHandler;
import org.eclipse.glsp.server.features.core.model.ComputedBoundsActionHandler;
import org.eclipse.glsp.server.features.core.model.GModelFactory;
import org.eclipse.glsp.server.features.core.model.JsonFileGModelLoader;
import org.eclipse.glsp.server.features.core.model.ModelSourceLoader;
import org.eclipse.glsp.server.features.directediting.ApplyLabelEditOperationHandler;
import org.eclipse.glsp.server.features.toolpalette.ToolPaletteItemProvider;
import org.eclipse.glsp.server.features.undoredo.UndoRedoActionHandler;
import org.eclipse.glsp.server.operations.OperationHandler;
import org.eclipse.glsp.server.operations.gmodel.ChangeBoundsOperationHandler;
import org.eclipse.glsp.server.operations.gmodel.ChangeRoutingPointsHandler;
import org.eclipse.glsp.server.operations.gmodel.CutOperationHandler;
import org.eclipse.glsp.server.operations.gmodel.DeleteOperationHandler;
import org.eclipse.glsp.server.operations.gmodel.LayoutOperationHandler;
import org.eclipse.glsp.server.operations.gmodel.PasteOperationHandler;
import org.eclipse.glsp.server.operations.gmodel.ReconnectEdgeOperationHandler;

/**
 * Reusable base class for diagram implementations that operate on the plain gmodel and use JSON for serialization.
 **/
public abstract class GModelJsonDiagramModule extends DiagramModule {

   @Override
   protected Class<? extends ModelSourceLoader> bindSourceModelLoader() {
      return JsonFileGModelLoader.class;
   }

   @Override
   protected Class<? extends GModelFactory> bindGModelFactory() {
      return GModelFactory.NullImpl.class;
   }

   @Override
   protected void configureActionHandlers(final MultiBinding<ActionHandler> binding) {
      super.configureActionHandlers(binding);
      binding.add(ComputedBoundsActionHandler.class);
      binding.add(SaveModelActionHandler.class);
      binding.add(UndoRedoActionHandler.class);
      binding.add(RequestClipboardDataActionHandler.class);
   }

   @Override
   protected void configureOperationHandlers(final MultiBinding<OperationHandler> binding) {
      super.configureOperationHandlers(binding);
      binding.add(ApplyLabelEditOperationHandler.class);
      binding.add(ChangeBoundsOperationHandler.class);
      binding.add(ChangeRoutingPointsHandler.class);
      binding.add(CutOperationHandler.class);
      binding.add(DeleteOperationHandler.class);
      binding.add(LayoutOperationHandler.class);
      binding.add(PasteOperationHandler.class);
      binding.add(ReconnectEdgeOperationHandler.class);
   }

   @Override
   protected Class<? extends ToolPaletteItemProvider> bindToolPaletteItemProvider() {
      return super.bindToolPaletteItemProvider();
   }

}
