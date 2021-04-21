/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
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

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.glsp.graph.GArgumentable;

public abstract class GArgumentableBuilder<T extends GArgumentable, E extends GArgumentableBuilder<T, E>>
   extends GBuilder<T> {

   protected Map<String, Object> arguments = new LinkedHashMap<>();

   public E addArgument(final String key, final Object value) {
      this.arguments.put(key, value);
      return self();
   }

   public E addArguments(final Map<String, Object> arguments) {
      this.arguments.putAll(arguments);
      return self();
   }

   public E addArgument(final Map.Entry<String, Object> argument) {
      return addArgument(argument.getKey(), argument.getValue());
   }

   protected abstract E self();

   @Override
   protected void setProperties(final T element) {
      if (arguments != null) {
         element.getArgs().putAll(arguments);
      }
   }
}
