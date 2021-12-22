/*******************************************************************************
 * Copyright (c) 2019-2021 EclipseSource and others.
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
 ******************************************************************************/
package org.eclipse.glsp.server.model;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.glsp.graph.GModelIndex;
import org.eclipse.glsp.graph.GModelRoot;

/**
 * This state represents the status of the diagram based on the {@link GModelRoot}, contains the @link GModelIndex and
 * is able to execute commands via its {@link BasicCommandStack}.
 */
public interface GModelState extends ModelState<GModelRoot> {

   /**
    * Returns the {@link GModelIndex} of the current diagram.
    *
    * @return The {@link GModelIndex} of the current diagram.
    */
   GModelIndex getIndex();

   /**
    * Called after a save has been successfully performed.
    */
   void saveIsDone();

   /**
    * Uses its {@link BasicCommandStack} to execute a given {@link Command}.
    *
    * @param command The {@link Command} to be executed.
    */
   void execute(Command command);
}
