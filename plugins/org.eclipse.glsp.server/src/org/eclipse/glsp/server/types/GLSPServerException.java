/*******************************************************************************
 * Copyright (c) 2019-2021 EclipseSource and others.
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
 ******************************************************************************/
package org.eclipse.glsp.server.types;

import java.util.NoSuchElementException;
import java.util.Optional;

public class GLSPServerException extends RuntimeException {
   private static final long serialVersionUID = 1L;

   /**
    * Constructs a {@link GLSPServerException} with the specified message.
    *
    * @param message the error message
    */
   public GLSPServerException(final String message) {
      super(message);
   }

   /**
    * Constructs a {@link GLSPServerException} with the specified message and an
    * additional exception.
    *
    * @param message   the error message
    * @param throwable a throwable as hint to the original cause of the error
    */
   public GLSPServerException(final String message, final Throwable throwable) {
      super(message, throwable);
   }

   /**
    * Tries to retrieve the value from the specified {@link Optional}. Throws a
    * {@link GLSPServerException} with the specified message if no value is present
    *
    * @param <T>              type of the optional value
    * @param optional         the optional
    * @param exceptionMessage the exepctionMessage
    * @return the value
    * @throws GLSPServerException if value not present
    */
   public static <T> T getOrThrow(final Optional<T> optional, final String exceptionMessage) {
      try {
         return optional.get();
      } catch (NoSuchElementException ex) {
         throw new GLSPServerException(exceptionMessage, ex);
      }
   }

   /**
    * Tries to retrieve and cast the value from the specified optional. Throws a
    * {@link GLSPServerException} with the specified message if no value is present
    *
    * @param <T>              type of the optional value after casting
    * @param optional         the optional
    * @param clazz            class of T
    * @param exceptionMessage the exepctionMessage
    * @return the value as T
    * @throws GLSPServerException if value not present or could not an instance of
    *                                T
    */
   public static <T> T getOrThrow(final Optional<?> optional, final Class<T> clazz, final String exceptionMessage) {
      try {
         Object toCast = optional.get();
         return clazz.cast(toCast);
      } catch (NoSuchElementException | ClassCastException ex) {
         throw new GLSPServerException(exceptionMessage, ex);
      }
   }
}
