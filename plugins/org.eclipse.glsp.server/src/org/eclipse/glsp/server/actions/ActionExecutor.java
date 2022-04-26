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
package org.eclipse.glsp.server.actions;

import java.util.concurrent.Executor;

import org.eclipse.glsp.server.disposable.IDisposable;

/**
 * FIXME add doc if approach is approved
 */
public interface ActionExecutor extends IDisposable, Executor {

   /**
    * Returns {@code true} if the given thread is an action executor thread.
    *
    * @return {@code true} if the given thread is an action executor thread
    */
   boolean isExecutorThread(Thread thread);

   /**
    * Returns {@code true} if this executor has been shut down.
    *
    * @return {@code true} if this executor has been shut down
    */
   boolean isShutdown();
}
