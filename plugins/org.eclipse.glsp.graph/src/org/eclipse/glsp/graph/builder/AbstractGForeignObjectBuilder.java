/********************************************************************************
 * Copyright (c) 2024 EclipseSource and others.
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

import org.eclipse.glsp.graph.GForeignObjectElement;

public abstract class AbstractGForeignObjectBuilder<T extends GForeignObjectElement, E extends AbstractGForeignObjectBuilder<T, E>>
   extends AbstractGShapePrenderedElementBuilder<T, E> {
   protected String namespace;

   public AbstractGForeignObjectBuilder(final String type) {
      super(type);
   }

   public E namespace(final String namespace) {
      this.namespace = namespace;
      return self();
   }

   @Override
   protected void setProperties(final T element) {
      super.setProperties(element);
      element.setNamespace(namespace);
   }

}
