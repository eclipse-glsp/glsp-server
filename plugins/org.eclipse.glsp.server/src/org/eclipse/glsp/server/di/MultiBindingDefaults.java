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
package org.eclipse.glsp.server.di;

import java.util.List;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.kind.ApplyLabelEditOperationAction;
import org.eclipse.glsp.api.action.kind.CenterAction;
import org.eclipse.glsp.api.action.kind.ChangeBoundsOperationAction;
import org.eclipse.glsp.api.action.kind.ChangeContainerOperationAction;
import org.eclipse.glsp.api.action.kind.ChangeRoutingPointsOperationAction;
import org.eclipse.glsp.api.action.kind.CollapseExpandAction;
import org.eclipse.glsp.api.action.kind.CollapseExpandAllAction;
import org.eclipse.glsp.api.action.kind.ComputedBoundsAction;
import org.eclipse.glsp.api.action.kind.CreateConnectionOperationAction;
import org.eclipse.glsp.api.action.kind.CreateNodeOperationAction;
import org.eclipse.glsp.api.action.kind.DeleteOperationAction;
import org.eclipse.glsp.api.action.kind.ExecuteServerCommandAction;
import org.eclipse.glsp.api.action.kind.ExportSVGAction;
import org.eclipse.glsp.api.action.kind.FitToScreenAction;
import org.eclipse.glsp.api.action.kind.LayoutAction;
import org.eclipse.glsp.api.action.kind.OpenAction;
import org.eclipse.glsp.api.action.kind.ReconnectConnectionOperationAction;
import org.eclipse.glsp.api.action.kind.RedoAction;
import org.eclipse.glsp.api.action.kind.RequestBoundsAction;
import org.eclipse.glsp.api.action.kind.RequestContextActions;
import org.eclipse.glsp.api.action.kind.RequestExportSvgAction;
import org.eclipse.glsp.api.action.kind.RequestLayersAction;
import org.eclipse.glsp.api.action.kind.RequestMarkersAction;
import org.eclipse.glsp.api.action.kind.RequestModelAction;
import org.eclipse.glsp.api.action.kind.RequestOperationsAction;
import org.eclipse.glsp.api.action.kind.RequestPopupModelAction;
import org.eclipse.glsp.api.action.kind.RequestTypeHintsAction;
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
import org.eclipse.glsp.api.handler.ActionHandler;
import org.eclipse.glsp.api.handler.OperationHandler;
import org.eclipse.glsp.server.actionhandler.CollapseExpandActionHandler;
import org.eclipse.glsp.server.actionhandler.ComputedBoundsActionHandler;
import org.eclipse.glsp.server.actionhandler.ExecuteServerCommandActionHandler;
import org.eclipse.glsp.server.actionhandler.LayoutActionHandler;
import org.eclipse.glsp.server.actionhandler.OpenActionHandler;
import org.eclipse.glsp.server.actionhandler.OperationActionHandler;
import org.eclipse.glsp.server.actionhandler.RequestContextActionsHandler;
import org.eclipse.glsp.server.actionhandler.RequestMarkersHandler;
import org.eclipse.glsp.server.actionhandler.RequestModelActionHandler;
import org.eclipse.glsp.server.actionhandler.RequestOperationsActionHandler;
import org.eclipse.glsp.server.actionhandler.RequestPopupModelActionHandler;
import org.eclipse.glsp.server.actionhandler.RequestTypeHintsActionHandler;
import org.eclipse.glsp.server.actionhandler.SaveModelActionHandler;
import org.eclipse.glsp.server.actionhandler.SelectActionHandler;
import org.eclipse.glsp.server.actionhandler.UndoRedoActionHandler;
import org.eclipse.glsp.server.actionhandler.ValidateLabelEditActionHandler;
import org.eclipse.glsp.server.operationhandler.ApplyLabelEditOperationHandler;
import org.eclipse.glsp.server.operationhandler.ChangeBoundsOperationHandler;
import org.eclipse.glsp.server.operationhandler.ChangeRoutingPointsHandler;
import org.eclipse.glsp.server.operationhandler.DeleteOperationHandler;
import org.eclipse.glsp.server.operationhandler.ReconnectEdgeHandler;

import com.google.common.collect.Lists;

public final class MultiBindingDefaults {
   private MultiBindingDefaults() {}

   public static final List<Class<? extends Action>> DEFAULT_ACTIONS = Lists.newArrayList(
      ApplyLabelEditOperationAction.class,
      CenterAction.class,
      ChangeBoundsOperationAction.class,
      CollapseExpandAction.class,
      CollapseExpandAllAction.class,
      ComputedBoundsAction.class,
      CreateConnectionOperationAction.class,
      CreateNodeOperationAction.class,
      DeleteOperationAction.class,
      ExportSVGAction.class,
      FitToScreenAction.class,
      ChangeContainerOperationAction.class,
      OpenAction.class,
      RequestBoundsAction.class,
      RequestTypeHintsAction.class,
      RequestExportSvgAction.class,
      RequestLayersAction.class,
      RequestModelAction.class,
      RequestOperationsAction.class,
      RequestPopupModelAction.class,
      RequestMarkersAction.class,
      SaveModelAction.class,
      UndoAction.class,
      RedoAction.class,
      SelectAction.class,
      SelectAllAction.class,
      ServerStatusAction.class,
      SetBoundsAction.class,
      SetLayersAction.class,
      SetModelAction.class,
      SetOperationsAction.class,
      SetPopupModelAction.class,
      SetEditLabelValidationResultAction.class,
      ToogleLayerAction.class,
      UpdateModelAction.class,
      ExecuteServerCommandAction.class,
      RequestContextActions.class,
      ReconnectConnectionOperationAction.class,
      ChangeRoutingPointsOperationAction.class,
      LayoutAction.class,
      ValidateLabelEditAction.class);

   public static final List<Class<? extends ActionHandler>> DEFAULT_ACTION_HANDLERS = Lists.newArrayList(
      CollapseExpandActionHandler.class,
      ComputedBoundsActionHandler.class,
      OpenActionHandler.class,
      OperationActionHandler.class,
      RequestModelActionHandler.class,
      RequestOperationsActionHandler.class,
      RequestPopupModelActionHandler.class,
      SaveModelActionHandler.class,
      UndoRedoActionHandler.class,
      SelectActionHandler.class,
      ExecuteServerCommandActionHandler.class,
      RequestTypeHintsActionHandler.class,
      RequestContextActionsHandler.class,
      RequestMarkersHandler.class,
      LayoutActionHandler.class,
      ValidateLabelEditActionHandler.class);

   public static final List<Class<? extends OperationHandler>> DEFAULT_OPERATION_HANDLERS = Lists.newArrayList(
      ChangeBoundsOperationHandler.class,
      ReconnectEdgeHandler.class,
      ChangeRoutingPointsHandler.class,
      DeleteOperationHandler.class,
      ApplyLabelEditOperationHandler.class);
}
