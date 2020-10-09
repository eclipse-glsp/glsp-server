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

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

public final class ReflectionUtil {
   private static Logger LOG = Logger.getLogger(ReflectionUtil.class);

   private ReflectionUtil() {}

   public static <T> Optional<T> construct(final Class<? extends T> clazz) {
      try {
         return Optional.of(clazz.getConstructor().newInstance());
      } catch (ReflectiveOperationException | SecurityException e) {
         LOG.error("Could not construct instance of class: " + clazz, e);
         return Optional.empty();
      }
   }

   public static <T> List<? extends T> construct(final Collection<Class<? extends T>> classes) {
      return classes.stream()
         .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
         .map(ReflectionUtil::construct)
         .filter(Optional::isPresent)
         .map(Optional::get)
         .collect(Collectors.toList());
   }
}
