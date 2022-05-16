/********************************************************************************
 * Copyright (c) 2021-2022 EclipseSource and others.
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
package org.eclipse.glsp.server.gmodel;

import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.di.DiagramModule;
import org.eclipse.glsp.server.di.MultiBinding;
import org.eclipse.glsp.server.features.core.model.GModelFactory;
import org.eclipse.glsp.server.features.core.model.SourceModelStorage;
import org.eclipse.glsp.server.features.toolpalette.ToolPaletteItemProvider;
import org.eclipse.glsp.server.operations.OperationHandler;

/**
 * Base class for diagram implementations where the GModel is used as source model directly.
 * <p>
 * Thus, diagram implementations that use this module operate on the plain GModel directly and use JSON for
 * serialization. They don't use any other information source to produce the GModel (see {@link GModelFactory}) and all
 * operation handlers directly manipulate the GModel (as this is the source model in this scenario).
 * </p>
 * <p>
 * This module is <b>not</b> intended to be used for editors that operate on another source model, such as an EMF model,
 * etc. In those scenarios, a dedicated {@link SourceModelStorage}, {@link GModelFactory}, and set of operation
 * handlers, etc. are required that load, transform (into a GModel) and manipulate that particular source model
 * directly and re-create the GModel after each manipulation of the source model.
 * For those scenarios please extend the {@link DiagramModule} directly or use a dedicated base module, such as
 * the `org.eclipse.glsp.server.emf.EMFDiagramModule`.
 * </p>
 **/
public abstract class GModelDiagramModule extends DiagramModule {

   @Override
   protected Class<? extends SourceModelStorage> bindSourceModelStorage() {
      return GModelStorage.class;
   }

   @Override
   protected Class<? extends GModelFactory> bindGModelFactory() {
      return GModelFactory.NullImpl.class;
   }

   @Override
   protected void configureActionHandlers(final MultiBinding<ActionHandler> binding) {
      super.configureActionHandlers(binding);
      binding.add(GModelRequestClipboardDataActionHandler.class);
   }

   @Override
   protected void configureOperationHandlers(final MultiBinding<OperationHandler> binding) {
      super.configureOperationHandlers(binding);
      binding.add(GModelApplyLabelEditOperationHandler.class);
      binding.add(GModelChangeBoundsOperationHandler.class);
      binding.add(GModelChangeRoutingPointsHandler.class);
      binding.add(GModelDeleteOperationHandler.class);
      binding.add(GModelReconnectEdgeOperationHandler.class);
      binding.add(GModelPasteOperationHandler.class);
   }

   @Override
   protected Class<? extends ToolPaletteItemProvider> bindToolPaletteItemProvider() {
      return super.bindToolPaletteItemProvider();
   }

}
