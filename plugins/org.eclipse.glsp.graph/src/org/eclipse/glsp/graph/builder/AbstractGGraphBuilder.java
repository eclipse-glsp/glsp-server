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

import org.eclipse.glsp.graph.GGraph;
import org.eclipse.glsp.graph.GLayoutOptions;

public abstract class AbstractGGraphBuilder<T extends GGraph, E extends AbstractGGraphBuilder<T, E>>
   extends GModelRootBuilder<T, E> {

   protected GLayoutOptions gLayoutOptions;

   public AbstractGGraphBuilder(String type) {
      super(type);
   }

   public E layoutOptions(GLayoutOptions gLayoutOptions) {
      this.gLayoutOptions = gLayoutOptions;
      return self();
   }

   @Override
   protected void setProperties(T element) {
      super.setProperties(element);
      element.setLayoutOptions(gLayoutOptions);
   }

}
