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
package org.eclipse.glsp.server.internal.di;

import java.util.List;

import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.actions.CenterAction;
import org.eclipse.glsp.server.actions.ClientActionHandler;
import org.eclipse.glsp.server.actions.ConfigureServerHandlersAction;
import org.eclipse.glsp.server.actions.DisposeClientSessionActionHandler;
import org.eclipse.glsp.server.actions.ExportSVGAction;
import org.eclipse.glsp.server.actions.FitToScreenAction;
import org.eclipse.glsp.server.actions.GLSPServerStatusAction;
import org.eclipse.glsp.server.actions.InitializeClientSessionActionHandler;
import org.eclipse.glsp.server.actions.SaveModelActionHandler;
import org.eclipse.glsp.server.actions.SelectAction;
import org.eclipse.glsp.server.actions.SelectAllAction;
import org.eclipse.glsp.server.actions.ServerMessageAction;
import org.eclipse.glsp.server.actions.ServerStatusAction;
import org.eclipse.glsp.server.actions.SetDirtyStateAction;
import org.eclipse.glsp.server.actions.SetEditModeAction;
import org.eclipse.glsp.server.actions.SetEditModeActionHandler;
import org.eclipse.glsp.server.actions.TriggerEdgeCreationAction;
import org.eclipse.glsp.server.actions.TriggerNodeCreationAction;
import org.eclipse.glsp.server.diagram.RequestTypeHintsActionHandler;
import org.eclipse.glsp.server.diagram.SetTypeHintsAction;
import org.eclipse.glsp.server.features.clipboard.RequestClipboardDataActionHandler;
import org.eclipse.glsp.server.features.clipboard.SetClipboardDataAction;
import org.eclipse.glsp.server.features.contextactions.RequestContextActionsHandler;
import org.eclipse.glsp.server.features.contextactions.SetContextActions;
import org.eclipse.glsp.server.features.core.model.ComputedBoundsActionHandler;
import org.eclipse.glsp.server.features.core.model.RequestBoundsAction;
import org.eclipse.glsp.server.features.core.model.RequestModelActionHandler;
import org.eclipse.glsp.server.features.core.model.SetBoundsAction;
import org.eclipse.glsp.server.features.core.model.SetModelAction;
import org.eclipse.glsp.server.features.core.model.UpdateModelAction;
import org.eclipse.glsp.server.features.directediting.ApplyLabelEditOperationHandler;
import org.eclipse.glsp.server.features.directediting.RequestEditValidationHandler;
import org.eclipse.glsp.server.features.directediting.SetEditValidationResultAction;
import org.eclipse.glsp.server.features.modelsourcewatcher.ModelSourceChangedAction;
import org.eclipse.glsp.server.features.navigation.NavigateToExternalTargetAction;
import org.eclipse.glsp.server.features.navigation.NavigateToTargetAction;
import org.eclipse.glsp.server.features.navigation.RequestNavigationTargetsActionHandler;
import org.eclipse.glsp.server.features.navigation.ResolveNavigationTargetActionHandler;
import org.eclipse.glsp.server.features.navigation.SetNavigationTargetsAction;
import org.eclipse.glsp.server.features.navigation.SetResolvedNavigationTargetAction;
import org.eclipse.glsp.server.features.popup.RequestPopupModelActionHandler;
import org.eclipse.glsp.server.features.popup.SetPopupModelAction;
import org.eclipse.glsp.server.features.undoredo.UndoRedoActionHandler;
import org.eclipse.glsp.server.features.validation.DeleteMarkersAction;
import org.eclipse.glsp.server.features.validation.RequestMarkersHandler;
import org.eclipse.glsp.server.features.validation.SetMarkersAction;
import org.eclipse.glsp.server.operations.OperationActionHandler;
import org.eclipse.glsp.server.operations.OperationHandler;
import org.eclipse.glsp.server.operations.gmodel.ChangeBoundsOperationHandler;
import org.eclipse.glsp.server.operations.gmodel.ChangeRoutingPointsHandler;
import org.eclipse.glsp.server.operations.gmodel.CompoundOperationHandler;
import org.eclipse.glsp.server.operations.gmodel.CutOperationHandler;
import org.eclipse.glsp.server.operations.gmodel.DeleteOperationHandler;
import org.eclipse.glsp.server.operations.gmodel.LayoutOperationHandler;
import org.eclipse.glsp.server.operations.gmodel.PasteOperationHandler;
import org.eclipse.glsp.server.operations.gmodel.ReconnectEdgeOperationHandler;

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
      ExportSVGAction.class,
      DeleteMarkersAction.class,
      FitToScreenAction.class,
      GLSPServerStatusAction.class,
      ModelSourceChangedAction.class,
      NavigateToTargetAction.class,
      NavigateToExternalTargetAction.class,
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
      ReconnectEdgeOperationHandler.class,
      CompoundOperationHandler.class);
}
