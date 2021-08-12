/********************************************************************************
 * Copyright (c) 2020-2021 EclipseSource and others.
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
package org.eclipse.glsp.server.disposable;

/**
 * A simple implementation of {@link IDisposable} that ensures that we only dispose an element once and track the
 * disposed state correctly.
 */
public class Disposable implements IDisposable {
   private boolean disposed;

   @Override
   public void dispose() {
      if (!disposed) {
         doDispose();
         disposed = true;
      }
   }

   protected void doDispose() {
      // do nothing
   }

   @Override
   public boolean isDisposed() { return disposed; }
}
