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
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.glsp.server.emf.EMFIdGenerator;
import org.eclipse.glsp.server.emf.UUIDXMIResourceFactory;

/**
 * An ID generator that uses the IDs within an XMI resource to identify elements. If this strategy is used, it
 * must be ensured that the element is stored in an {@link XMIResource}. If the IDs should be kept during
 * resource loadings, a special {@link UUIDXMIResourceFactory} may be used to load and store the resource.
 *
 * Please note that with this strategy, IDs cannot be generated for detached elements that are not part of the resource,
 * e.g., newly created elements that have not been added yet.
 */
public class XMIIDGenerator implements EMFIdGenerator {
   @Override
   public String getOrCreateId(final EObject element) {
      return ((XMIResource) element.eResource()).getID(element);
   }
}
