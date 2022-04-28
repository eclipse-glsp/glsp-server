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
package org.eclipse.glsp.server.emf.idgen;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.glsp.server.emf.EMFIdGenerator;

/**
 * An ID generator that uses the fragment as a unique identifier for an element. If this strategy is used, the
 * fragment should be considered unique across all operations. For instance, if an element is removed within a list, all
 * subsequent elements are moved ahead. If the index position is considered in the fragment, objects may be identified
 * incorrectly.
 */
public class FragmentIdGenerator implements EMFIdGenerator {
   @Override
   public String getOrCreateId(final EObject element) {
      return EcoreUtil.getURI(element).fragment();
   }
}
