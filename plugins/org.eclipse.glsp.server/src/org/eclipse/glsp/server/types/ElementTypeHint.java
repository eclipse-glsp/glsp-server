/********************************************************************************
 * Copyright (c) 2019-2022 EclipseSource and others.
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
package org.eclipse.glsp.server.types;

import org.eclipse.glsp.server.diagram.RequestTypeHintsAction;

/**
 * Type hints are sent from the server to the client to provide information on which edit operations are possible for
 * elements of a certain type.
 *
 * This additional information helps the client to configure editing tools and visual feedback for elements accordingly
 * without needing to interact during a user interaction.
 *
 * @see RequestTypeHintsAction
 */
public abstract class ElementTypeHint {

   private String elementTypeId;
   private boolean repositionable;
   private boolean deletable;

   public ElementTypeHint() {}

   public ElementTypeHint(final String elementTypeId, final boolean repositionable, final boolean deletable) {
      super();
      this.elementTypeId = elementTypeId;
      this.repositionable = repositionable;
      this.deletable = deletable;
   }

   public String getElementTypeId() { return elementTypeId; }

   public void setElementTypeId(final String elementTypeId) { this.elementTypeId = elementTypeId; }

   public boolean isRepositionable() { return repositionable; }

   public void setRepositionable(final boolean repositionable) { this.repositionable = repositionable; }

   public boolean isDeletable() { return deletable; }

   public void setDeletable(final boolean deletable) { this.deletable = deletable; }

}
