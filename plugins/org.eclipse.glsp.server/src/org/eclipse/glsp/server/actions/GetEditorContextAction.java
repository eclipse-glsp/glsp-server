/********************************************************************************
 * Copyright (c) 2026 EclipseSource and others.
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
package org.eclipse.glsp.server.actions;

/**
 * Sent from the server to the client to request the current editor context. This is the server-initiated
 * counterpart to the {@code editorContext} that is included in many client-to-server requests.
 *
 * All fields in the response represent a snapshot of the client state at the time the response is generated.
 * There is no guarantee that the state has not changed by the time the server processes the response.
 *
 * @see EditorContextResult
 */
public class GetEditorContextAction extends RequestAction<EditorContextResult> {

   public static final String KIND = "getEditorContext";

   public GetEditorContextAction() {
      super(KIND);
   }

}
