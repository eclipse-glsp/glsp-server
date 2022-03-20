/********************************************************************************
 * Copyright (c) 2020-2022 EclipseSource and others.
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
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.glsp.server.types.GLSPServerException;

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

   /**
    * This method will search for an actual type argument that matches the expected base type, starting from the given
    * clazz up the complete class hierarchy. If the given type object matches the actual type argument, the type object
    * is returns as that type.
    *
    * @param <T>        expected base type
    * @param clazz      search start
    * @param baseType   base type that matches the actual type argument
    * @param typeObject the object that may implement the given type
    * @return the given type object as the matching type
    */
   public static <T> Optional<? extends T> asActualTypeArgument(final Class<?> clazz, final Class<T> baseType,
      final Object typeObject) {
      return findActualTypeArgument(clazz, baseType)
         .filter(matchingType -> matchingType.isInstance(typeObject))
         .map(matchingType -> matchingType.cast(typeObject));
   }

   /**
    * This method will search for an actual type argument that matches the expected base type, starting from the given
    * clazz up the complete class hierarchy.
    *
    * @param <T>      expected base type
    * @param clazz    search start
    * @param baseType base type that matches the actual type argument
    * @return the type argument that is closest to the given <code>clazz</code> and matches the given base base
    */
   public static <T> Class<? extends T> getActualTypeArgument(final Class<?> clazz, final Class<T> baseType) {
      return findActualTypeArgument(clazz, baseType)
         .orElseThrow(() -> new GLSPServerException("No matching type argument for " + baseType + " in " + clazz));
   }

   /**
    * This method will search for an actual type argument that matches the expected base type, starting from the given
    * clazz up the complete class hierarchy.
    *
    * @param <T>      expected base type
    * @param clazz    search start
    * @param baseType base type that matches the actual type argument
    * @return the type argument that is closest to the given <code>clazz</code> and matches the given base base
    */
   public static <T> Optional<Class<? extends T>> findActualTypeArgument(final Class<?> clazz,
      final Class<T> baseType) {
      return findActualTypeArgument(clazz, baseType, null);
   }

   /**
    * This method will search for an actual type argument that matches the expected base type, starting from the given
    * clazz until the search stop.
    *
    * @param <T>        expected base type
    * @param clazz      search start
    * @param baseType   base type that matches the actual type argument
    * @param searchStop search stop or <code>null</code> if we should search up to Object
    * @return the type argument that is closest to the given <code>clazz</code> and matches the given base base
    */
   @SuppressWarnings({ "unchecked", "checkstyle:CyclomaticComplexity" })
   public static <T> Optional<Class<? extends T>> findActualTypeArgument(final Class<?> clazz,
      final Class<T> baseType, final Class<?> searchStop) {
      if (clazz == null || baseType == null) {
         return Optional.empty();
      }
      if (clazz.getGenericSuperclass() instanceof ParameterizedType) {
         ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
         for (Type typeArgument : parameterizedType.getActualTypeArguments()) {
            if (typeArgument instanceof Class<?> && baseType.isAssignableFrom((Class<?>) typeArgument)) {
               return Optional.of((Class<? extends T>) typeArgument);
            }
         }
      }
      return clazz.getSuperclass() == null || Objects.equals(clazz, searchStop)
         ? Optional.empty()
         : findActualTypeArgument(clazz.getSuperclass(), baseType, searchStop);
   }
}
