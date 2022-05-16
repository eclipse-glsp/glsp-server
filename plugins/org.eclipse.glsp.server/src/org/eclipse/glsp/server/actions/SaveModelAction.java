/*******************************************************************************
 * Copyright (c) 2019-2022 EclipseSource and others.
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
 ******************************************************************************/
package org.eclipse.glsp.server.actions;

import java.util.Optional;

/**
 * A request for saving the current model state, typically sent by the client to the server.
 */
public class SaveModelAction extends Action {

   public static final String KIND = "saveModel";
   private String fileUri;

   public SaveModelAction() {
      super(KIND);
   }

   public SaveModelAction(final String fileUri) {
      this();
      this.fileUri = fileUri;
   }

   public void setFileUri(final String fileUri) { this.fileUri = fileUri; }

   public Optional<String> getFileUri() { return Optional.ofNullable(fileUri); }
}
