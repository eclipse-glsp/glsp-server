/********************************************************************************
 * Copyright (c) 2020-2023 EclipseSource and others.
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
package org.eclipse.glsp.server.operations;

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.glsp.server.actions.ActionDispatcher;

import com.google.inject.Inject;

/**
 * Performs the cut operation by dispatching a {@link DeleteOperation} for the elements to be cut.
 */
public class CutOperationHandler extends GModelOperationHandler<CutOperation> {

   @Inject
   protected ActionDispatcher actionDispatcher;

   protected List<String> getElementsToCut(final CutOperation cutAction) {
      return cutAction.getEditorContext().getSelectedElementIds();
   }

   @Override
   public Optional<Command> createCommand(final CutOperation operation) {
      List<String> elementsToCut = getElementsToCut(operation);
      return elementsToCut.isEmpty()
         ? doNothing()
         : commandOf(() -> actionDispatcher.dispatch(new DeleteOperation(elementsToCut)));
   }
}
