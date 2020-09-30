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
package org.eclipse.glsp.api.action;

public abstract class Action {
   private final String kind;

   public Action(final String kind) {
      super();
      this.kind = kind;
   }

   public String getKind() { return kind; }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Action [kind=");
      builder.append(kind);
      builder.append("]");
      return builder.toString();
   }

   public static class Kind {
      public static final String CENTER = "center";
      public static final String CLEAR_MARKERS = "clearMarkers";
      public static final String COMPUTED_BOUNDS = "computedBounds";
      public static final String CUT_ACTION = "cut";
      public static final String DISPOSE_CLIENT_SESSION = "disposeClientSession";
      public static final String EXECUTE_OPERATION = "executeOperation";
      public static final String EXECUTE_SERVER_COMMAND = "executeServerCommand";
      public static final String EXPORT_SVG = "exportSvg";
      public static final String FIT_TO_SCREEN = "fit";
      public static final String INITIALIZE_CLIENT_SESSION = "initializeClientSession";
      public static final String NAVIGATE_TO_TARGET = "navigateToTarget";
      public static final String REDO = "glspRedo";
      public static final String REQUEST_BOUNDS = "requestBounds";
      public static final String REQUEST_CLIPBOARD_DATA = "requestClipboardData";
      public static final String REQUEST_CONTEXT_ACTIONS = "requestContextActions";
      public static final String REQUEST_EDIT_VALIDATION = "requestEditValidation";
      public static final String REQUEST_MARKERS = "requestMarkers";
      public static final String REQUEST_MODEL = "requestModel";
      public static final String REQUEST_NAVIGATION_TARGETS = "requestNavigationTargets";
      public static final String REQUEST_OPERATIONS = "requestOperations";
      public static final String REQUEST_POPUP_MODEL = "requestPopupModel";
      public static final String REQUEST_TYPE_HINTS = "requestTypeHints";
      public static final String RESOLVE_NAVIGATION_TARGET = "resolveNavigationTarget";
      public static final String SAVE_MODEL = "saveModel";
      public static final String SELECT = "selectElement";
      public static final String SELECT_ALL = "allSelected";
      public static final String SERVER_MESSAGE = "serverMessage";
      public static final String SERVER_STATUS = "serverStatus";
      public static final String SET_BOUNDS = "setBounds";
      public static final String SET_CLIPBOARD_DATA = "setClipboardData";
      public static final String SET_CONTEXT_ACTIONS = "setContextActions";
      public static final String SET_DIRTY_STATE = "setDirtyState";
      public static final String SET_EDIT_MODE = "setEditMode";
      public static final String SET_EDIT_VALIDATION_RESULT = "setEditValidationResult";
      public static final String SET_LABEL_EDIT_VALIDATION_RESULT = "setLabelEditValidationResult";
      public static final String SET_LAYERS = "setLayers";
      public static final String SET_MARKERS = "setMarkers";
      public static final String SET_MODEL = "setModel";
      public static final String SET_NAVIGATION_TARGETS = "setNavigationTargets";
      public static final String SET_POPUP_MODEL = "setPopupModel";
      public static final String SET_RESOLVED_NAVIGATION_TARGET = "setResolvedNavigationTarget";
      public static final String SET_TYPE_HINTS = "setTypeHints";
      public static final String TRIGGER_EDGE_CREATION = "triggerEdgeCreation";
      public static final String TRIGGER_NODE_CREATION = "triggerNodeCreation";
      public static final String UNDO = "glspUndo";
      public static final String UPDATE_MODEL = "updateModel";
      public static final String VALIDATE_LABEL_EDIT_ACTION = "validateLabelEdit";
      public static final String CONFIGURE_SERVER_HANDLERS = "configureServerHandlers";
   }
}
