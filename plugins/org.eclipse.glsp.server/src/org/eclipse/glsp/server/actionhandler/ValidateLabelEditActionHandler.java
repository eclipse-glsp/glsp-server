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
package org.eclipse.glsp.server.actionhandler;

import java.util.List;
import java.util.Optional;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.kind.SetEditLabelValidationResultAction;
import org.eclipse.glsp.api.action.kind.ValidateLabelEditAction;
import org.eclipse.glsp.api.labeledit.EditLabelValidationResult;
import org.eclipse.glsp.api.labeledit.LabelEditValidator;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.graph.GModelElement;

import com.google.inject.Inject;

public class ValidateLabelEditActionHandler extends BasicActionHandler<ValidateLabelEditAction> {

   @Inject
   protected LabelEditValidator editLabelValidator;

   @Override
   protected List<Action> executeAction(final ValidateLabelEditAction action, final GraphicalModelState modelState) {
      Optional<GModelElement> element = modelState.getIndex().get(action.getLabelId());
      if (element.isPresent()) {
         return listOf(new SetEditLabelValidationResultAction(
            editLabelValidator.validate(modelState, action.getValue(), element.get())));
      }
      return listOf(new SetEditLabelValidationResultAction(EditLabelValidationResult.OK_RESULT));
   }

}
