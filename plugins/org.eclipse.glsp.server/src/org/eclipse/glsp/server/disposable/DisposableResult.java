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
package org.eclipse.glsp.server.disposable;

/**
 * Simple implementation of {@link IDisposableResult} based on {@link Disposable}.
 * This call can either be instantiated directly with a result, or a result can
 * be set later on via {@link #setResult(Object)}. If the result actually needs
 * to be disposed, subclasses should override {@link #doDispose()}.
 */
public class DisposableResult<T> extends Disposable implements IDisposableResult<T> {
   private T result;

   /**
    * Instantiate this {@link DisposableResult} without an initial value. A value
    * should be provided via {@link #setResult(Object)}, or by overriding
    * {@link #getResult()}.
    */
   public DisposableResult() {
      super();
   }

   /**
    * Instantiate this {@link DisposableResult} with the specified result.
    *
    * @param result The value to be returned via {@link #getResult()}
    */
   public DisposableResult(final T result) {
      this();
      this.result = result;
   }

   public void setResult(final T result) { this.result = result; }

   @Override
   public T getResult() { return result; }
}
