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
package org.eclipse.glsp.server.features.modelsourcewatcher;

import org.eclipse.glsp.server.model.GModelState;

/**
 * A model source watcher observes the model source and notifies the client if the model
 * source has changed.
 */
public interface ModelSourceWatcher {

   /**
    * Starts watching the model source in the specified <code>modelState</code>.
    *
    * @param modelState The model state indicating the model source.
    */
   default void startWatching(final GModelState modelState) {}

   /**
    * Stops watching the model source in the specified <code>modelState</code>.
    * <p>
    * If the watching hasn't been started before, this won't do anything.
    * </p>
    *
    * @param modelState The model state indicating the model source.
    */
   default void stopWatching(final GModelState modelState) {}

   /**
    * Pauses the client notifications of this watcher.
    * <p>
    * If the watching hasn't been started before, this won't do anything.
    * </p>
    *
    * @param modelState The model state indicating the model source.
    */
   default void pauseWatching(final GModelState modelState) {}

   /**
    * Continues the client notifications of this watcher.
    * <p>
    * If the watching hasn't been started or paused before, this won't do anything.
    * </p>
    *
    * @param modelState The model state indicating the model source.
    */
   default void continueWatching(final GModelState modelState) {}

   /** A no-op watcher that can be bound if no watching is intended or supported. */
   final class NullImpl implements ModelSourceWatcher {}

}
