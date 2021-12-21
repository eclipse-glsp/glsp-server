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
 * Interface for objects that can or need to be disposed properly.
 */
public interface IDisposable {
   /**
    * Disposes the current object by releasing any underlying resources or cleaning up any registrations.
    */
   void dispose();

   /**
    * Returns true if this object is considered disposed.
    *
    * @return true if this object is disposed false otherwise
    */
   boolean isDisposed();

   /**
    * Disposes the given object if it is not null.
    *
    * @param disposable disposable object
    */
   static void disposeIfExists(final IDisposable disposable) {
      if (disposable != null) {
         disposable.dispose();
      }
   }

   /**
    * Creates a new IDisposable for a given runnable.
    *
    * @param runnable The runnable for which a new IDisposable instance is created.
    * @return A new instance of an IDisposable.
    */
   static IDisposable create(final Runnable runnable) {
      return new Disposable() {
         @Override
         protected void doDispose() {
            runnable.run();
         }
      };
   }
}
