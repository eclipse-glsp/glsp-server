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
package org.eclipse.glsp.server.features.validation;

/**
 * The default reasons for a request for markers.
 */
public final class MarkersReason {

   /**
    * Batch validation executed on demand by the client.
    */
   public static final String BATCH = "batch";
   /**
    * Live validation executed on start and after model update.
    */
   public static final String LIVE = "live";

   private MarkersReason() {
      // prevent instantiation for class only holding constants.
   }

}
