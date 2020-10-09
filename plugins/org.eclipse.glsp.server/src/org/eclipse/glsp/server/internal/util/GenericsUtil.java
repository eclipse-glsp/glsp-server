/********************************************************************************
 * Copyright (c) 2020 EclipseSource and others.
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
package org.eclipse.glsp.server.internal.util;

import java.lang.reflect.ParameterizedType;

public final class GenericsUtil {
   private GenericsUtil() {}

   public static ParameterizedType getParametrizedType(final Class<?> clazz, final Class<?> genericBaseclass) {
      if (clazz.getSuperclass().equals(genericBaseclass)) { // check that we are at the top of the hierarchy
         return (ParameterizedType) clazz.getGenericSuperclass();
      }
      return getParametrizedType(clazz.getSuperclass(), genericBaseclass);
   }

   public static Class<?> getGenericTypeParameterClass(final Class<?> clazz, final Class<?> genericBaseclass) {
      return (Class<?>) (GenericsUtil.getParametrizedType(clazz, genericBaseclass))
         .getActualTypeArguments()[0];
   }
}
