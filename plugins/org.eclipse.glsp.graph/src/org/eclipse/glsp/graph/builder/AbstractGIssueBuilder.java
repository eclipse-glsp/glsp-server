/********************************************************************************
 * Copyright (c) 2026 EclipseSource and others.
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
package org.eclipse.glsp.graph.builder;

import org.eclipse.glsp.graph.GIssue;
import org.eclipse.glsp.graph.GSeverity;

/**
 * Abstract builder for {@link GIssue} elements.
 * <p>
 * This builder provides common properties for building GIssue instances,
 * including severity and message.
 *
 * @param <T> the type of GIssue being built
 * @param <E> the type of the builder itself (for fluent API)
 */
public abstract class AbstractGIssueBuilder<T extends GIssue, E extends AbstractGIssueBuilder<T, E>>
   extends GBuilder<T> {

   protected String severity;
   protected String message;

   @SuppressWarnings("unchecked")
   protected E self() {
      return (E) this;
   }

   public E severity(final GSeverity severity) {
      return this.severity(severity.getLiteral());
   }

   public E severity(final String severity) {
      this.severity = severity;
      return self();
   }

   public E message(final String message) {
      this.message = message;
      return self();
   }

   @Override
   protected void setProperties(final T issue) {
      issue.setSeverity(severity);
      issue.setMessage(message);
   }

}
