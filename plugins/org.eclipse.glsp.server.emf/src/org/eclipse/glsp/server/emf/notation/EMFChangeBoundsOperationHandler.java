/********************************************************************************
 * Copyright (c) 2022 EclipseSource and others.
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
package org.eclipse.glsp.server.emf.notation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.glsp.server.emf.AbstractEMFOperationHandler;
import org.eclipse.glsp.server.emf.model.notation.NotationPackage;
import org.eclipse.glsp.server.emf.model.notation.Shape;
import org.eclipse.glsp.server.operations.ChangeBoundsOperation;
import org.eclipse.glsp.server.types.ElementAndBounds;

import com.google.inject.Inject;

/**
 * A custom change bounds operation handler that updates the notation model for the moved shape elements.
 */
public class EMFChangeBoundsOperationHandler extends AbstractEMFOperationHandler<ChangeBoundsOperation> {

   @Inject
   protected EMFNotationModelState modelState;

   @Override
   public Optional<Command> createCommand(final ChangeBoundsOperation operation) {
      EditingDomain editingDomain = modelState.getEditingDomain();

      CompoundCommand compoundCommand = new CompoundCommand();
      for (ElementAndBounds element : operation.getNewBounds()) {
         modelState.getIndex().getNotation(element.getElementId(), Shape.class)
            .map(shape -> updateShape(editingDomain, shape, element))
            .ifPresent(commands -> commands.forEach(compoundCommand::append));
      }

      return compoundCommand.getCommandList().isEmpty() ? Optional.empty() : Optional.of(compoundCommand);
   }

   private List<Command> updateShape(final EditingDomain editingDomain, final Shape shape,
      final ElementAndBounds elementAndBounds) {
      List<Command> commands = new ArrayList<>();
      if (elementAndBounds.getNewPosition() != null) {
         Command setPosCommand = SetCommand.create(editingDomain, shape,
            NotationPackage.Literals.SHAPE__POSITION, EcoreUtil.copy(elementAndBounds.getNewPosition()));
         commands.add(setPosCommand);
      }
      if (elementAndBounds.getNewSize() != null) {
         Command setSizeCommand = SetCommand.create(editingDomain, shape,
            NotationPackage.Literals.SHAPE__SIZE, EcoreUtil.copy(elementAndBounds.getNewSize()));
         commands.add(setSizeCommand);
      }
      return commands;
   }
}
