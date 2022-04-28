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
package org.eclipse.glsp.server.emf;

import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * A factory to create a new editing domain.
 *
 * @see EditingDomain
 */
public interface EMFEditingDomainFactory {
   /**
    * Creates a new editing domain managing a set of interrelated EMF models in a resource set and track their
    * modifications through a command stack.
    *
    * @return editing domain
    */
   EditingDomain createEditingDomain();
}
