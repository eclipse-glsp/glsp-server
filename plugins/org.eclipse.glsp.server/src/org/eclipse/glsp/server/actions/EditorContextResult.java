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

import org.eclipse.glsp.server.types.EditorContext;

/**
 * Response to a {@link GetEditorContextAction} containing a snapshot of the client-side editor state.
 *
 * All fields in the {@link EditorContext} reflect the state at the time of response generation.
 * The server should not assume that these values are still current when processing the response,
 * as the client state may have changed in the meantime.
 */
public class EditorContextResult extends ResponseAction {

   public static final String KIND = "editorContextResult";

   /** Snapshot of the client-side editor state at the time the response was generated. */
   private EditorContext editorContext;

   public EditorContextResult() {
      super(KIND);
   }

   public EditorContextResult(final EditorContext editorContext) {
      super(KIND);
      this.editorContext = editorContext;
   }

   public EditorContext getEditorContext() { return editorContext; }

   public void setEditorContext(final EditorContext editorContext) { this.editorContext = editorContext; }

}
