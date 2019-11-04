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
package org.eclipse.glsp.server.provider;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.kind.ApplyLabelEditOperationAction;
import org.eclipse.glsp.api.action.kind.CenterAction;
import org.eclipse.glsp.api.action.kind.ChangeBoundsOperationAction;
import org.eclipse.glsp.api.action.kind.ChangeContainerOperationAction;
import org.eclipse.glsp.api.action.kind.CollapseExpandAction;
import org.eclipse.glsp.api.action.kind.CollapseExpandAllAction;
import org.eclipse.glsp.api.action.kind.ComputedBoundsAction;
import org.eclipse.glsp.api.action.kind.CreateConnectionOperationAction;
import org.eclipse.glsp.api.action.kind.CreateNodeOperationAction;
import org.eclipse.glsp.api.action.kind.DeleteOperationAction;
import org.eclipse.glsp.api.action.kind.ExecuteServerCommandAction;
import org.eclipse.glsp.api.action.kind.ExportSVGAction;
import org.eclipse.glsp.api.action.kind.FitToScreenAction;
import org.eclipse.glsp.api.action.kind.IdentifiableRequestAction;
import org.eclipse.glsp.api.action.kind.LayoutAction;
import org.eclipse.glsp.api.action.kind.OpenAction;
import org.eclipse.glsp.api.action.kind.ReconnectConnectionOperationAction;
import org.eclipse.glsp.api.action.kind.RedoAction;
import org.eclipse.glsp.api.action.kind.RequestBoundsAction;
import org.eclipse.glsp.api.action.kind.RequestCommandPaletteActions;
import org.eclipse.glsp.api.action.kind.RequestExportSvgAction;
import org.eclipse.glsp.api.action.kind.RequestLayersAction;
import org.eclipse.glsp.api.action.kind.RequestMarkersAction;
import org.eclipse.glsp.api.action.kind.RequestModelAction;
import org.eclipse.glsp.api.action.kind.RequestOperationsAction;
import org.eclipse.glsp.api.action.kind.RequestPopupModelAction;
import org.eclipse.glsp.api.action.kind.RequestTypeHintsAction;
import org.eclipse.glsp.api.action.kind.RerouteConnectionOperationAction;
import org.eclipse.glsp.api.action.kind.SaveModelAction;
import org.eclipse.glsp.api.action.kind.SelectAction;
import org.eclipse.glsp.api.action.kind.SelectAllAction;
import org.eclipse.glsp.api.action.kind.ServerStatusAction;
import org.eclipse.glsp.api.action.kind.SetBoundsAction;
import org.eclipse.glsp.api.action.kind.SetEditLabelValidationResultAction;
import org.eclipse.glsp.api.action.kind.SetLayersAction;
import org.eclipse.glsp.api.action.kind.SetModelAction;
import org.eclipse.glsp.api.action.kind.SetOperationsAction;
import org.eclipse.glsp.api.action.kind.SetPopupModelAction;
import org.eclipse.glsp.api.action.kind.ToogleLayerAction;
import org.eclipse.glsp.api.action.kind.UndoAction;
import org.eclipse.glsp.api.action.kind.UpdateModelAction;
import org.eclipse.glsp.api.action.kind.ValidateLabelEditAction;
import org.eclipse.glsp.api.provider.ActionProvider;

public class DefaultActionProvider implements ActionProvider {
   protected final Set<Action> defaultActions;

   public DefaultActionProvider() {
      defaultActions = new HashSet<>();
      addDefaultActions();
   }

   private void addDefaultActions() {
      defaultActions.add(new ApplyLabelEditOperationAction());
      defaultActions.add(new CenterAction());
      defaultActions.add(new ChangeBoundsOperationAction());
      defaultActions.add(new CollapseExpandAction());
      defaultActions.add(new CollapseExpandAllAction());
      defaultActions.add(new ComputedBoundsAction());
      defaultActions.add(new CreateConnectionOperationAction());
      defaultActions.add(new CreateNodeOperationAction());
      defaultActions.add(new DeleteOperationAction());
      defaultActions.add(new ExportSVGAction());
      defaultActions.add(new FitToScreenAction());
      defaultActions.add(new ChangeContainerOperationAction());
      defaultActions.add(new OpenAction());
      defaultActions.add(new RequestBoundsAction());
      defaultActions.add(new RequestTypeHintsAction());
      defaultActions.add(new RequestExportSvgAction());
      defaultActions.add(new RequestLayersAction());
      defaultActions.add(new RequestModelAction());
      defaultActions.add(new RequestOperationsAction());
      defaultActions.add(new RequestPopupModelAction());
      defaultActions.add(new RequestMarkersAction());
      defaultActions.add(new SaveModelAction());
      defaultActions.add(new UndoAction());
      defaultActions.add(new RedoAction());
      defaultActions.add(new SelectAction());
      defaultActions.add(new SelectAllAction());
      defaultActions.add(new ServerStatusAction());
      defaultActions.add(new SetBoundsAction());
      defaultActions.add(new SetLayersAction());
      defaultActions.add(new SetModelAction());
      defaultActions.add(new SetOperationsAction());
      defaultActions.add(new SetPopupModelAction());
      defaultActions.add(new SetEditLabelValidationResultAction());
      defaultActions.add(new ToogleLayerAction());
      defaultActions.add(new UpdateModelAction());
      defaultActions.add(new ExecuteServerCommandAction());
      defaultActions.add(new RequestCommandPaletteActions());
      defaultActions.add(new IdentifiableRequestAction());
      defaultActions.add(new ReconnectConnectionOperationAction());
      defaultActions.add(new RerouteConnectionOperationAction());
      defaultActions.add(new LayoutAction());
      defaultActions.add(new ValidateLabelEditAction());
   }

   @Override
   public Set<Action> getActions() { return defaultActions; }

}
