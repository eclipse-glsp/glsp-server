/********************************************************************************
 * Copyright (c) 2020 EclipseSource and others.
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
package org.eclipse.glsp.server.internal.labeledit;

import java.util.Optional;

import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.server.features.directediting.ContextEditValidator;
import org.eclipse.glsp.server.features.directediting.LabelEditValidator;
import org.eclipse.glsp.server.features.directediting.RequestEditValidationAction;
import org.eclipse.glsp.server.features.directediting.ValidationStatus;
import org.eclipse.glsp.server.model.GModelState;

public class ValidateLabelEditAdapter implements ContextEditValidator {

   private final LabelEditValidator editLabelValidator;

   public ValidateLabelEditAdapter(final LabelEditValidator editLabelValidator) {
      super();
      this.editLabelValidator = editLabelValidator;
   }

   @Override
   public String getContextId() { return LabelEditValidator.CONTEXT_ID; }

   @Override
   public ValidationStatus validate(final RequestEditValidationAction action, final GModelState modelState) {
      Optional<GModelElement> element = modelState.getIndex().get(action.getModelElementId());
      if (element.isPresent()) {
         return editLabelValidator.validate(modelState, action.getText(), element.get());
      }
      return ValidationStatus.ok();
   }

}
