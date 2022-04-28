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

import org.eclipse.emf.ecore.EObject;

/**
 * A generator class that creates a unique identifier for a given EObject.
 * Ideally, the generated IDs should be considered stable during resource close/load and across model modifications.
 * The ids are used when indexing the element and may be given to the GModel element.
 */
public interface EMFIdGenerator {
   /**
    * Returns a unique identifier for the given element. The same element must always return the same id and should not
    * conflict with other elements.
    *
    * @param element source element
    * @return unique ID
    */
   String getOrCreateId(EObject element);
}
