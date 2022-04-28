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
import org.eclipse.glsp.graph.util.IdKeeperAdapter;
import org.eclipse.glsp.server.emf.EMFIdGenerator;

/**
 * An ID generator that creates UUIDs for elements. It is ensured that each element only gets a single unique
 * ID during one session. However, the IDs are not persisted as part of the model by default so they may not be stable
 * across several resource loadings.
 */
public class UUIDIdGenerator implements EMFIdGenerator {
   @Override
   public String getOrCreateId(final EObject element) {
      return IdKeeperAdapter.getId(element);
   }
}
