/********************************************************************************
 * Copyright (c) 2020-2024 EclipseSource and others.
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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.eclipse.glsp.server.disposable.IDisposable;

public class Debouncer<T> implements Consumer<T>, IDisposable {
   protected final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
   protected final Map<T, ScheduledFuture<?>> scheduledExecutions = new ConcurrentHashMap<>();
   protected final Consumer<T> consumer;
   protected final long delay;
   protected final TimeUnit timeUnit;

   public Debouncer(final Consumer<T> consumer, final long delay, final TimeUnit timeUnit) {
      this.consumer = consumer;
      this.delay = delay;
      this.timeUnit = timeUnit;
   }

   @Override
   public void accept(final T argument) {
      scheduledExecutions.compute(argument, this::scheduleExecution);
   }

   protected ScheduledFuture<?> scheduleExecution(final T argument, final ScheduledFuture<?> previousExecution) {
      if (previousExecution != null) {
         previousExecution.cancel(true);
      }
      return !isDisposed()
         ? executorService.schedule(() -> execute(argument), delay, timeUnit)
         : null;
   }

   protected void execute(final T argument) {
      scheduledExecutions.remove(argument);
      consumer.accept(argument);
   }

   @Override
   public void dispose() {
      executorService.shutdownNow();
   }

   @Override
   public boolean isDisposed() { return executorService.isShutdown(); }
}
