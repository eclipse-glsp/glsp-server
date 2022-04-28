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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.server.emf.idgen.FragmentIdGenerator;

/**
 * A specialization of the default ID converter to optimize the retrieval of EObjects based on fragments.
 * This converter should only be used in combination with the {@link FragmentIdGenerator}.
 */
public class SemanticFragmentIdConverter extends EMFSemanticIdConverter.Default {
   @Override
   public EObject resolve(final String uriFragment, final EObject root) {
      return root.eResource().getEObject(uriFragment);
   }
}
