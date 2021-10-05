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
package org.eclipse.glsp.server.session;

/**
 * A listener that gets notified on certain client session lifecycle events.<br>
 * Life cycle events:
 * <ul>
 * <li>Session creation</li>
 * <li>Session disposal</li>
 * </ul>
 *
 * The scope of relevant client session ids can be restricted when registering the listener with
 * {@link ClientSessionManager#addListener(ClientSessionListener, String...)}
 */
public interface ClientSessionListener {

   /**
    * Is invoked after a new {@link ClientSession} has been created by the {@link ClientSessionManager}.
    *
    * @param clientSession The newly created client session.
    */
   default void sessionCreated(final ClientSession clientSession) {
      // No-op as default. This enables partial interface implementation.
   }

   /**
    * Is invoked after a {@link ClientSession} has been disposed by the {@link ClientSessionManager}.
    *
    * @param clientSession The client session that was disposed.
    */
   default void sessionDisposed(final ClientSession clientSession) {
      // No-op as default. This enables partial interface implementation.
   }

}
