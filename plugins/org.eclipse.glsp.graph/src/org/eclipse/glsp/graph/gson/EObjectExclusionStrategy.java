/********************************************************************************
 * Copyright (c) 2019-2024 EclipseSource and others.
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
package org.eclipse.glsp.graph.gson;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class EObjectExclusionStrategy implements ExclusionStrategy {

   private static final String EPACKAGE_NS = "org.eclipse.emf.ecore";
   private static final int EXCLUDED_MODIFIERS = Modifier.TRANSIENT | Modifier.STATIC;

   @Override
   public boolean shouldSkipField(final FieldAttributes f) {
      return f.getDeclaringClass().getPackage().getName().startsWith(EPACKAGE_NS);
   }

   @Override
   public boolean shouldSkipClass(final Class<?> clazz) {
      return false;
   }

   public static boolean excludeField(final Field field) {
      if ((EXCLUDED_MODIFIERS & field.getModifiers()) != 0) {
         return true;
      }

      if (field.isSynthetic()) {
         return true;
      }

      if (isInnerClass(field.getType())) {
         return true;
      }

      if (isAnonymousOrNonStaticLocal(field.getType())) {
         return true;
      }

      FieldAttributes attributes = new FieldAttributes(field);
      return new EObjectExclusionStrategy().shouldSkipField(attributes);
   }

   protected static boolean isInnerClass(final Class<?> clazz) {
      return clazz.isMemberClass() && !isStatic(clazz);
   }

   protected static boolean isStatic(final Class<?> clazz) {
      return (clazz.getModifiers() & Modifier.STATIC) != 0;
   }

   protected static boolean isAnonymousOrNonStaticLocal(final Class<?> clazz) {
      return !Enum.class.isAssignableFrom(clazz) && !isStatic(clazz)
         && (clazz.isAnonymousClass() || clazz.isLocalClass());
   }

}
