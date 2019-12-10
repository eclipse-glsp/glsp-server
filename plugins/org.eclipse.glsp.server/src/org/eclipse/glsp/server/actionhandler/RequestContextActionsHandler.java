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
package org.eclipse.glsp.server.actionhandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.kind.RequestContextActions;
import org.eclipse.glsp.api.action.kind.SetContextActions;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.provider.CommandPaletteActionProvider;
import org.eclipse.glsp.api.provider.ContextMenuItemProvider;
import org.eclipse.glsp.api.types.LabeledAction;

import com.google.inject.Inject;

public class RequestContextActionsHandler extends AbstractActionHandler {

   public static final String UI_CONTROL_KEY = "ui-control";

   @Inject
   protected CommandPaletteActionProvider commandPaletteActionProvider;

   @Inject
   protected ContextMenuItemProvider contextMenuItemProvider;

   @Override
   public boolean handles(final Action action) {
      return action instanceof RequestContextActions;
   }

   @Override
   public Optional<Action> execute(final Action action, final GraphicalModelState modelState) {
      if (action instanceof RequestContextActions) {
         RequestContextActions requestContextAction = (RequestContextActions) action;
         List<String> selectedElementIds = requestContextAction.getSelectedElementIds();
         Map<String, String> args = requestContextAction.getArgs();
         List<LabeledAction> items = new ArrayList<>();
         if (equalsUiControl(args, CommandPaletteActionProvider.KEY)) {
            items.addAll(commandPaletteActionProvider.getActions(modelState, selectedElementIds,
               requestContextAction.getLastMousePosition(), args));
         } else if (equalsUiControl(args, ContextMenuItemProvider.KEY)) {
            items.addAll(contextMenuItemProvider.getItems(modelState, selectedElementIds,
               requestContextAction.getLastMousePosition(), args));

         }
         return Optional.of(new SetContextActions(items, requestContextAction.getArgs()));
      }
      return Optional.empty();
   }

   protected boolean equalsUiControl(final Map<String, String> args, final String uiControlKey) {
      return args.containsKey(UI_CONTROL_KEY) && args.get(UI_CONTROL_KEY).equals(uiControlKey);
   }
}
