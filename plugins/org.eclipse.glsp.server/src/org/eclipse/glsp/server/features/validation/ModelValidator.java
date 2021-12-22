/********************************************************************************
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
 ********************************************************************************/
package org.eclipse.glsp.server.features.validation;

import java.util.List;

import org.eclipse.glsp.graph.GModelElement;

/**
 * Validates a list of {@link GModelElement}s based on a set of validation rules and returns corresponding issue
 * {@link Marker}s.
 * An issue marker is a serializable description of the validation violation that can be visualised by the GLSP client.
 */
public interface ModelValidator {

   /**
    * Validates the given list of {@link GModelElement}s and returns a list of {@link Marker}s.
    *
    * @param elements The list of {@link GModelElement} to validate.
    * @return A list of {@link Marker}s for the validated {@link GModelElement}s.
    */
   List<Marker> validate(GModelElement... elements);
}
