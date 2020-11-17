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
package org.eclipse.glsp.server.utils;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

public final class FutureUtil {

   private FutureUtil() {}

   /**
    * <p>
    * Aggregate a collection of {@link CompletableFuture} into a single result.
    * Only supports Void futures; as this method focuses on aggregating exceptions
    * rather than aggregating results.
    * </p>
    * <p>
    * If any of the actions completes exceptionally, the result will also complete
    * exceptionally, using the first occurring exception. If multiple actions
    * complete exceptionally, additional exceptions will be registered as
    * {@link Exception#getSuppressed() suppressed exceptions}.
    * </p>
    *
    * @param actions
    *                   The actions to aggregate
    * @return
    *         A single {@link CompletableFuture}, that will be completed once all of the actions are complete.
    *         The future will be completed exceptionally if any of the actions completes exceptionally.
    */
   @SuppressWarnings("checkstyle:cyclomaticComplexity")
   public static CompletableFuture<Void> aggregateResults(final Collection<CompletableFuture<Void>> actions) {
      if (actions.isEmpty()) {
         return CompletableFuture.completedFuture(null);
      }
      if (actions.size() == 1) {
         return actions.iterator().next();
      }

      final CompletableFuture<Void> result = new CompletableFuture<>();
      final AtomicInteger remainingActions = new AtomicInteger(actions.size());
      final AtomicReference<Throwable> firstException = new AtomicReference<>();

      BiFunction<Void, Throwable, Void> onComplete = (any, ex) -> {
         if (ex != null) {
            if (!firstException.compareAndSet(null, ex)) {
               // Additional exception
               firstException.get().addSuppressed(ex);
            }
         }

         if (remainingActions.decrementAndGet() == 0) {
            Throwable t = firstException.get();
            if (t == null) {
               result.complete(null);
            } else {
               result.completeExceptionally(t);
            }
         }
         return null;
      };
      for (CompletableFuture<Void> action : actions) {
         action.handle(onComplete);
      }
      return result;
   }
}
