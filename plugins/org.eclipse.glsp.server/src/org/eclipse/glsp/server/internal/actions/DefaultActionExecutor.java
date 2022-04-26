/********************************************************************************
 * Copyright (c) 2022 EclipseSource and others.
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
package org.eclipse.glsp.server.internal.actions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.eclipse.glsp.server.actions.ActionExecutor;
import org.eclipse.glsp.server.disposable.Disposable;

/**
 * FIXME add doc if approach is approved
 */
public class DefaultActionExecutor extends Disposable implements ActionExecutor {

   private static final Logger LOG = Logger.getLogger(DefaultActionExecutor.class);

   protected final DefaultActionExecutorThreadFactory threadFactory = new DefaultActionExecutorThreadFactory(
      DefaultActionExecutor.class.getSimpleName());
   protected final ExecutorService executor = Executors.newSingleThreadExecutor(threadFactory);

   @Override
   public void execute(final Runnable command) {
      executor.execute(command);
   }

   @Override
   public boolean isExecutorThread(final Thread thread) {
      return threadFactory.thread == thread;
   }

   @Override
   public boolean isShutdown() { return executor.isShutdown(); }

   @Override
   protected void doDispose() {
      super.doDispose();
      executor.shutdown();
      try {
         if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            executor.shutdownNow();
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
               LOG.warn("Could not terminate executor thread's");
            }
         }
      } catch (InterruptedException ie) {
         executor.shutdownNow();
         Thread.currentThread().interrupt();
      }
   }

   protected static class DefaultActionExecutorThreadFactory implements ThreadFactory {

      private static final AtomicInteger COUNT = new AtomicInteger(0);

      protected final String name;
      protected Thread thread;

      protected DefaultActionExecutorThreadFactory(final String name) {
         this.name = name;
      }

      @Override
      public Thread newThread(final Runnable r) {
         if (thread != null) {
            LOG.warn("More than one thread is created for the action executor");
         }
         thread = new Thread(r, name + " " + COUNT.incrementAndGet());
         thread.setDaemon(true);
         return thread;
      }

   }
}
