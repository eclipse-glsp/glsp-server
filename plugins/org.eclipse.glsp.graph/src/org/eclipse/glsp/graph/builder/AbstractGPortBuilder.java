/********************************************************************************
 * Copyright (c) 2026 EclipseSource and others.
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
package org.eclipse.glsp.graph.builder;

import org.eclipse.glsp.graph.GPort;

/**
 * Abstract builder for {@link GPort} elements.
 * <p>
 * This builder exists for subclassing consistency with other element builders.
 * GPort has no additional properties beyond GShapeElement.
 *
 * @param <T> the type of GPort being built
 * @param <E> the type of the builder itself (for fluent API)
 */
public abstract class AbstractGPortBuilder<T extends GPort, E extends AbstractGPortBuilder<T, E>>
   extends GShapeElementBuilder<T, E> {

   public AbstractGPortBuilder(final String type) {
      super(type);
   }

}
