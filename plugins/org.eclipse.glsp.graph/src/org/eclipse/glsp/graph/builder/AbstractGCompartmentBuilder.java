/********************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
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

import org.eclipse.glsp.graph.GCompartment;
import org.eclipse.glsp.graph.GLayoutOptions;

public abstract class AbstractGCompartmentBuilder<T extends GCompartment, E extends AbstractGCompartmentBuilder<T, E>>
   extends GShapeElementBuilder<T, E> {
   protected String layout;
   protected GLayoutOptions layoutOptions;

   public AbstractGCompartmentBuilder(String type) {
      super(type);
   }

   public E layoutOptions(GLayoutOptions layoutOptions) {
      this.layoutOptions = layoutOptions;
      return self();
   }

   public E layout(String layout) {
      this.layout = layout;
      return self();
   }

   @Override
   protected void setProperties(T comp) {
      super.setProperties(comp);
      comp.setLayoutOptions(layoutOptions);
      comp.setLayout(layout);
   }
}
