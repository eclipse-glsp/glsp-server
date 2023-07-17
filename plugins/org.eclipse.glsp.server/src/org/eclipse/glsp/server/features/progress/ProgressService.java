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

/**
 * Service for starting and monitoring progress reporting to the client.
 */
public interface ProgressService {
   /**
    * Start a progress reporting.
    *
    * @param title      The title shown in the UI for the progress reporting.
    * @param message    Optional message to be shown in addition to the title. Can be <code>null</code>.
    * @param percentage Optional initial percentage (value range: 0 to 100) of the progress reporting. <code>-1</code>
    *                      if none.
    * @return a monitor to update and end the progress reporting later.
    */
   ProgressMonitor start(String title, String message, int percentage);

   /**
    * Start a progress reporting.
    *
    * @param title The title shown in the UI for the progress reporting.
    * @return a monitor to update and end the progress reporting later.
    */
   default ProgressMonitor start(final String title) {
      return this.start(title, null, -1);
   }

   /**
    * Start a progress reporting.
    *
    * @param title   The title shown in the UI for the progress reporting.
    * @param message Message to be shown in addition to the title.
    * @return a monitor to update and end the progress reporting later.
    */
   default ProgressMonitor start(final String title, final String message) {
      return this.start(title, message, -1);
   }

   /**
    * Start a progress reporting.
    *
    * @param title      The title shown in the UI for the progress reporting.
    * @param percentage Initial percentage (value range: 0 to 100) of the progress reporting.
    * @return a monitor to update and end the progress reporting later.
    */
   default ProgressMonitor start(final String title, final int percentage) {
      return this.start(title, null, percentage);
   }
}
