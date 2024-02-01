/********************************************************************************
 * Copyright (c) 2023 EclipseSource and others.
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
package org.eclipse.glsp.server.features.progress;

import java.util.Optional;

import org.eclipse.glsp.server.actions.Action;

/**
 * Sent by the server to the client to end the reporting of a progress.
 */
public class EndProgressAction extends Action {
   public static final String KIND = "endProgress";

   private String progressId;

   private String message;

   public EndProgressAction() {
      super(KIND);
   }

   public EndProgressAction(final String progressId) {
      this();
      this.progressId = progressId;
   }

   public EndProgressAction(final String progressId, final String message) {
      this(progressId);
      this.message = message;
   }

   public void setProgressId(final String progressId) { this.progressId = progressId; }

   public String getProgressId() { return progressId; }

   public void setMessage(final String message) { this.message = message; }

   public Optional<String> getMessage() { return Optional.ofNullable(message); }

}
