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
 * The monitor of a progress reporting, which can be used to update and end the reporting.
 */
public interface ProgressMonitor {
   /**
    * Updates an ongoing progress reporting.
    *
    * @param message    Optional message to be shown in addition to the title. Can be <code>null</code>.
    * @param percentage Optional percentage (value range: 0 to 100) of the progress reporting. <code>-1</code>
    *                      if none.
    */
   void update(String message, int percentage);

   /**
    * Updates an ongoing progress reporting.
    *
    * @param percentage Percentage (value range: 0 to 100) of the progress reporting.
    */
   default void update(final int percentage) {
      this.update(null, percentage);
   }

   /**
    * Updates an ongoing progress reporting.
    *
    * @param message Message to be shown in addition to the title.
    */
   default void update(final String message) {
      this.update(message, -1);
   }

   /**
    * Ends an ongoing progress reporting.
    */
   void end();
}
