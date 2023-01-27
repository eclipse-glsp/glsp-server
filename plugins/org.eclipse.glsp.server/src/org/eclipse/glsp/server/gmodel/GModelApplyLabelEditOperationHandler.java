/********************************************************************************
 * Copyright (c) 2019-2023 EclipseSource and others.
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
package org.eclipse.glsp.server.gmodel;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.glsp.graph.GLabel;
import org.eclipse.glsp.server.features.directediting.ApplyLabelEditOperation;
import org.eclipse.glsp.server.operations.GModelOperationHandler;

/**
 * Applies label changes directly to the GModel.
 */
public class GModelApplyLabelEditOperationHandler extends GModelOperationHandler<ApplyLabelEditOperation> {

   @Override
   public Optional<Command> createCommand(final ApplyLabelEditOperation operation) {
      GLabel label = findLabel(operation).orElseThrow(
         () -> new IllegalArgumentException("Element with provided ID cannot be found or is not a GLabel"));
      return Objects.equals(label.getText(), operation.getText())
         ? doNothing()
         : commandOf(() -> label.setText(operation.getText()));
   }

   protected Optional<GLabel> findLabel(final ApplyLabelEditOperation operation) {
      return modelState.getIndex().getByClass(operation.getLabelId(), GLabel.class);
   }
}
