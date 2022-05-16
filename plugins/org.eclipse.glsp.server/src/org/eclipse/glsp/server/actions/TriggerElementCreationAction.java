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
package org.eclipse.glsp.server.actions;

import java.util.Map;

/**
 * Abstract base class for triggering the element creation tool on the client.
 */
public abstract class TriggerElementCreationAction extends Action {

   private String elementTypeId;
   private Map<String, String> args;

   public TriggerElementCreationAction(final String kind, final String elementTypeId) {
      this(kind, elementTypeId, null);
   }

   public TriggerElementCreationAction(final String kind, final String elementTypeId,
      final Map<String, String> args) {
      super(kind);
      this.elementTypeId = elementTypeId;
      this.args = args;
   }

   public String getElementTypeId() { return elementTypeId; }

   public void setElementTypeId(final String elementTypeId) { this.elementTypeId = elementTypeId; }

   public Map<String, String> getArgs() { return args; }

   public void setArgs(final Map<String, String> args) { this.args = args; }

}
