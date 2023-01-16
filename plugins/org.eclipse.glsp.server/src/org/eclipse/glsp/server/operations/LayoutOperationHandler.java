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
package org.eclipse.glsp.server.operations;

import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.glsp.server.diagram.DiagramConfiguration;
import org.eclipse.glsp.server.layout.LayoutEngine;
import org.eclipse.glsp.server.layout.ServerLayoutKind;

import com.google.inject.Inject;

/**
 * Delegates to the configured {@link LayoutEngine} to apply a layout.
 */
public class LayoutOperationHandler extends GModelOperationHandler<LayoutOperation> {

   @Inject
   protected Optional<LayoutEngine> layoutEngine;

   @Inject
   protected DiagramConfiguration diagramConfiguration;

   @Override
   public Optional<Command> createCommand(final LayoutOperation operation) {
      return layoutEngine.isEmpty() || diagramConfiguration.getLayoutKind() != ServerLayoutKind.MANUAL
         ? doNothing()
         : commandOf(() -> layoutEngine.get().layout());
   }

}
