/********************************************************************************
 * Copyright (c) 2020-2022 EclipseSource and others.
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
package org.eclipse.glsp.server.features.sourcemodelwatcher;

/**
 * A source model watcher observes the source model resource and notifies the client if the source model has changed.
 */
public interface SourceModelWatcher {

   /**
    * Starts watching the source model.
    */
   default void startWatching() {}

   /**
    * Stops watching the source model.
    * <p>
    * If the watching hasn't been started before, this won't do anything.
    * </p>
    */
   default void stopWatching() {}

   /**
    * Pauses the client notifications of this watcher.
    * <p>
    * If the watching hasn't been started before, this won't do anything.
    * </p>
    */
   default void pauseWatching() {}

   /**
    * Continues the client notifications of this watcher.
    * <p>
    * If the watching hasn't been started or paused before, this won't do anything.
    * </p>
    */
   default void continueWatching() {}
}
