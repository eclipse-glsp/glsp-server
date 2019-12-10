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
package org.eclipse.glsp.server.util;

import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.graph.GPort;

public final class GModelUtil {
   private GModelUtil() {}

   public static Function<Integer, String> idAndIndex(final String id) {
      return i -> id + i;
   }

   public static int generateId(final EClass eClass, final String id, final GraphicalModelState modelState) {
      return modelState.getIndex().getCounter(eClass, idAndIndex(id));
   }

   public static int generateId(final GModelElement element, final String id, final GraphicalModelState modelState) {
      int index = generateId(element.eClass(), id, modelState);
      element.setId(idAndIndex(id).apply(index));
      return index;
   }

   @SuppressWarnings("checkstyle:VisibilityModifier")
   public static Predicate<GModelElement> IS_CONNECTABLE = modelElement -> modelElement instanceof GPort
      || modelElement instanceof GNode;
}
