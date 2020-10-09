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
package org.eclipse.glsp.server.features.directediting;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.BasicActionHandler;
import org.eclipse.glsp.server.model.GModelState;

import com.google.inject.Inject;

public class RequestEditValidationHandler extends BasicActionHandler<RequestEditValidationAction> {

   private static Logger log = Logger.getLogger(RequestEditValidationHandler.class);

   @Inject
   protected ContextEditValidatorRegistry contextEditValidatorRegistry;

   @Override
   public List<Action> executeAction(final RequestEditValidationAction action, final GModelState modelState) {
      Optional<ValidationStatus> validationResult = contextEditValidatorRegistry.get(action.getContextId())
         .map(provider -> provider.validate(action, modelState));
      if (!validationResult.isPresent()) {
         String message = "No validator registered for the context '" + action.getContextId() + "'";
         log.warn(message);
         return listOf(new SetEditValidationResultAction(ValidationStatus.warning(message)));
      }
      return listOf(new SetEditValidationResultAction(validationResult.get()));
   }
}
