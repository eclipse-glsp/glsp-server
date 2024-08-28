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
package org.eclipse.glsp.graph.builder.impl;

import org.eclipse.glsp.graph.DefaultTypes;
import org.eclipse.glsp.graph.GForeignObjectElement;
import org.eclipse.glsp.graph.GraphFactory;
import org.eclipse.glsp.graph.builder.AbstractGForeignObjectBuilder;

public class GForeignObjectBuilder extends AbstractGForeignObjectBuilder<GForeignObjectElement, GForeignObjectBuilder> {

   public GForeignObjectBuilder() {
      this(DefaultTypes.FOREIGN_OBJECT);
   }

   public GForeignObjectBuilder(final String type) {
      super(type);
   }

   @Override
   protected GForeignObjectElement instantiate() {
      return GraphFactory.eINSTANCE.createGForeignObjectElement();
   }

   @Override
   protected GForeignObjectBuilder self() {
      return this;
   }

}
