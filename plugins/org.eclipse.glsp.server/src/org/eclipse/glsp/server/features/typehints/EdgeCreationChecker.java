/********************************************************************************
 * Copyright (c) 2023 EclipseSource and others.
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
package org.eclipse.glsp.server.features.typehints;

import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.server.types.EdgeTypeHint;

/**
 * Optional service used to check the validity of an edge being created. Used in combination with `dynamic`
 * {@link EdgeTypeHint}s.
 * A dynamic edge type hint is used for cases where a plain list of allowed source and target element ids is not enough
 * to determine
 * wether an edge being created is valid. In this cases the client will query the server to determine wether the edge
 * is valid.
 * The `EdgeCreationChecker` then checks the given edge information and returns wether the edge being created is
 * valid.
 */
public interface EdgeCreationChecker {
   /**
    * Checks wether the given source element for an edge beeing created is valid i.e. if the
    * given source is an allowed source element for the given edge type.
    *
    * @return `true` if the edge source is valid, `false` otherwise
    */
   boolean isValidSource(String edgeType, GModelElement sourceElement);

   /**
    * Checks wether the given information for an edge beeing created is valid i.e. if the
    * given target is an allowed target element for the given source and edge type.
    *
    * @return `true` if the edge target is valid, `false` otherwise
    */
   boolean isValidTarget(String edgeType, GModelElement sourceElement, GModelElement targetElement);
}
