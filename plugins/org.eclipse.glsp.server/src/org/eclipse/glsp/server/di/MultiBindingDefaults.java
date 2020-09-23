/********************************************************************************
 * Copyright (c) 2019-2020 EclipseSource and others.
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
import org.eclipse.glsp.api.action.kind.CenterAction;
import org.eclipse.glsp.api.action.kind.ClearMarkersAction;
import org.eclipse.glsp.api.action.kind.ConfigureServerHandlersAction;
import org.eclipse.glsp.api.action.kind.ExportSVGAction;
import org.eclipse.glsp.api.action.kind.FitToScreenAction;
import org.eclipse.glsp.api.action.kind.GLSPServerStatusAction;
import org.eclipse.glsp.api.action.kind.NavigateToTargetAction;
import org.eclipse.glsp.api.action.kind.RequestBoundsAction;
import org.eclipse.glsp.api.action.kind.SelectAction;
import org.eclipse.glsp.api.action.kind.SelectAllAction;
import org.eclipse.glsp.api.action.kind.ServerMessageAction;
import org.eclipse.glsp.api.action.kind.ServerStatusAction;
import org.eclipse.glsp.api.action.kind.SetBoundsAction;
import org.eclipse.glsp.api.action.kind.SetClipboardDataAction;
import org.eclipse.glsp.api.action.kind.SetContextActions;
import org.eclipse.glsp.api.action.kind.SetDirtyStateAction;
import org.eclipse.glsp.api.action.kind.SetEditModeAction;
import org.eclipse.glsp.api.action.kind.SetEditValidationResultAction;
import org.eclipse.glsp.api.action.kind.SetMarkersAction;
import org.eclipse.glsp.api.action.kind.SetModelAction;
import org.eclipse.glsp.api.action.kind.SetNavigationTargetsAction;
import org.eclipse.glsp.api.action.kind.SetPopupModelAction;
import org.eclipse.glsp.api.action.kind.SetResolvedNavigationTargetAction;
import org.eclipse.glsp.api.action.kind.SetTypeHintsAction;
import org.eclipse.glsp.api.action.kind.TriggerEdgeCreationAction;
import org.eclipse.glsp.api.action.kind.TriggerNodeCreationAction;
import org.eclipse.glsp.api.action.kind.UpdateModelAction;
import org.eclipse.glsp.api.handler.ActionHandler;
import org.eclipse.glsp.api.handler.OperationHandler;
import org.eclipse.glsp.server.actionhandler.ClientActionHandler;
import org.eclipse.glsp.server.actionhandler.ComputedBoundsActionHandler;
import org.eclipse.glsp.server.actionhandler.DisposeClientSessionActionHandler;
import org.eclipse.glsp.server.actionhandler.ExecuteServerCommandActionHandler;
import org.eclipse.glsp.server.actionhandler.InitializeClientSessionActionHandler;
import org.eclipse.glsp.server.actionhandler.OperationActionHandler;
import org.eclipse.glsp.server.actionhandler.RequestClipboardDataActionHandler;
import org.eclipse.glsp.server.actionhandler.RequestContextActionsHandler;
import org.eclipse.glsp.server.actionhandler.RequestEditValidationHandler;
import org.eclipse.glsp.server.actionhandler.RequestMarkersHandler;
import org.eclipse.glsp.server.actionhandler.RequestModelActionHandler;
import org.eclipse.glsp.server.actionhandler.RequestNavigationTargetsActionHandler;
import org.eclipse.glsp.server.actionhandler.RequestPopupModelActionHandler;
import org.eclipse.glsp.server.actionhandler.RequestTypeHintsActionHandler;
import org.eclipse.glsp.server.actionhandler.ResolveNavigationTargetActionHandler;
import org.eclipse.glsp.server.actionhandler.SaveModelActionHandler;
import org.eclipse.glsp.server.actionhandler.SetEditModeActionHandler;
import org.eclipse.glsp.server.actionhandler.UndoRedoActionHandler;
import org.eclipse.glsp.server.operationhandler.ApplyLabelEditOperationHandler;
import org.eclipse.glsp.server.operationhandler.ChangeBoundsOperationHandler;
import org.eclipse.glsp.server.operationhandler.ChangeRoutingPointsHandler;
import org.eclipse.glsp.server.operationhandler.CompoundOperationHandler;
import org.eclipse.glsp.server.operationhandler.CutOperationHandler;
import org.eclipse.glsp.server.operationhandler.DeleteOperationHandler;
import org.eclipse.glsp.server.operationhandler.LayoutOperationHandler;
import org.eclipse.glsp.server.operationhandler.PasteOperationHandler;
import org.eclipse.glsp.server.operationhandler.ReconnectOperationHandler;

import com.google.common.collect.Lists;

public final class MultiBindingDefaults {

   private MultiBindingDefaults() {}

   public static final List<Class<? extends ActionHandler>> DEFAULT_ACTION_HANDLERS = Lists.newArrayList(
      ClientActionHandler.class,
      ComputedBoundsActionHandler.class,
      DisposeClientSessionActionHandler.class,
      InitializeClientSessionActionHandler.class,
      OperationActionHandler.class,
      RequestModelActionHandler.class,
      RequestPopupModelActionHandler.class,
      SaveModelActionHandler.class,
      UndoRedoActionHandler.class,
      ExecuteServerCommandActionHandler.class,
      ResolveNavigationTargetActionHandler.class,
      RequestClipboardDataActionHandler.class,
      RequestNavigationTargetsActionHandler.class,
      RequestTypeHintsActionHandler.class,
      RequestContextActionsHandler.class,
      RequestEditValidationHandler.class,
      RequestMarkersHandler.class,
      SetEditModeActionHandler.class);

   public static final List<Class<? extends Action>> DEFAULT_CLIENT_ACTIONS = Lists.newArrayList(
      CenterAction.class,
      ClearMarkersAction.class,
      ExportSVGAction.class,
      FitToScreenAction.class,
      GLSPServerStatusAction.class,
      NavigateToTargetAction.class,
      RequestBoundsAction.class,
      SelectAction.class,
      SelectAllAction.class,
      ServerMessageAction.class,
      SetBoundsAction.class,
      SetClipboardDataAction.class,
      SetContextActions.class,
      SetDirtyStateAction.class,
      SetEditModeAction.class,
      SetEditValidationResultAction.class,
      SetMarkersAction.class,
      SetModelAction.class,
      SetNavigationTargetsAction.class,
      SetPopupModelAction.class,
      SetResolvedNavigationTargetAction.class,
      SetTypeHintsAction.class,
      ServerStatusAction.class,
      TriggerNodeCreationAction.class,
      TriggerEdgeCreationAction.class,
      UpdateModelAction.class,
      ConfigureServerHandlersAction.class);

   public static final List<Class<? extends OperationHandler>> DEFAULT_OPERATION_HANDLERS = Lists.newArrayList(
      ApplyLabelEditOperationHandler.class,
      ChangeBoundsOperationHandler.class,
      ChangeRoutingPointsHandler.class,
      CutOperationHandler.class,
      DeleteOperationHandler.class,
      LayoutOperationHandler.class,
      PasteOperationHandler.class,
      ReconnectOperationHandler.class,
      CompoundOperationHandler.class);
}
