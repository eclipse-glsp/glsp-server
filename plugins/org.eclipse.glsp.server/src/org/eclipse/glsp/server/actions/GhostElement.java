/********************************************************************************
 * Copyright (c) 2023 EclipseSource and others.
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

import java.util.Optional;

import org.eclipse.glsp.graph.GModelElement;

/**
 * A ghost element describes an element that may be rendered as a schematic visualization of an element that may be
 * inserted during creation.
 */
public class GhostElement {
   private Object template;
   private Boolean dynamic;

   public GhostElement(final String templateElementId) {
      this.template = templateElementId;
   }

   public GhostElement(final String templateElementId, final boolean dynamic) {
      this.template = templateElementId;
      this.dynamic = dynamic;
   }

   public GhostElement(final GModelElement element) {
      this.template = element;
   }

   public GhostElement(final GModelElement element, final boolean dynamic) {
      this.template = element;
      this.dynamic = dynamic;
   }

   public Optional<String> getTemplateElementId() {
      return template instanceof String templateElementId ? Optional.of(templateElementId) : Optional.empty();
   }

   public void setTemplateElementId(final String templateElementId) { this.template = templateElementId; }

   public Optional<GModelElement> getTemplateElement() {
      return template instanceof GModelElement templateElement ? Optional.of(templateElement) : Optional.empty();
   }

   public void setTemplateElement(final GModelElement templateElement) { this.template = templateElement; }

   public Boolean getDynamic() { return dynamic; }

   public void setDynamic(final boolean dynamic) { this.dynamic = dynamic; }
}
