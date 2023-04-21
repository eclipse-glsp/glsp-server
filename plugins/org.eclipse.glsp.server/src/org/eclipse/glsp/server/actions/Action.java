/********************************************************************************
 * Copyright (c) 2019-2021 EclipseSource and others.
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

import org.eclipse.glsp.server.internal.util.GenericsUtil;

/**
 * Java-implementation of the `Action` interface. An action is a declarative description of a behavior that
 * shall be
 * invoked by the receiver upon receipt of the action. It is a plain data structure, and as such transferable between
 * server and client. Actions contained in {@link ActionMessage}s are identified by their `kind` attribute. This
 * attribute is required for all actions. Certain actions are meant to be sent from the client to the server or vice
 * versa, while other actions can be sent both ways, by the client or the server. All actions must extend the default
 * action base class.
 *
 */
public abstract class Action {
   /**
    * Unique identifier specifying the kind of action to process.
    */
   private final String kind;

   private String subclientId;

   public Action(final String kind, final String subclientId) {
      super();
      this.kind = kind;
      this.subclientId = subclientId;
   }

   public Action(final String kind) {
      this(kind, null);
   }

   public String getKind() { return kind; }

   public String getSubclientId() { return subclientId; }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Action [kind=");
      builder.append(kind);
      builder.append("]");
      return builder.toString();
   }

   public static Action addSubclientId(final Action initialAction, final Action extendedAction) {
      if (initialAction.getSubclientId() != null) {
         extendedAction.subclientId = initialAction.subclientId;
      }
      return extendedAction;
   }

}
