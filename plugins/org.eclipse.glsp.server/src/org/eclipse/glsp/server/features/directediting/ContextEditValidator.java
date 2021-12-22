/********************************************************************************
 * Copyright (c) 2020-2021 EclipseSource and others.
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
package org.eclipse.glsp.server.features.directediting;

import org.eclipse.glsp.server.features.contextactions.ContextActionsProvider;

public interface ContextEditValidator {

   /**
    * Returns the context id of the {@link ContextActionsProvider}.
    */
   String getContextId();

   /**
    * Returns the {@link ValidationStatus} for a given {@link RequestEditValidationAction}.
    *
    * @param action The RequestEditValidationAction to validate.
    * @return The {@link ValidationStatus} for a given {@link RequestEditValidationAction}.
    */
   ValidationStatus validate(RequestEditValidationAction action);

   /**
    * Validates if the validator can handle a given contextId.
    *
    * @param contextId The contextId to check.
    * @return `true` if the validator can handle a given contextId.
    */
   default boolean handles(final String contextId) {
      return getContextId().equals(contextId);
   }

}
