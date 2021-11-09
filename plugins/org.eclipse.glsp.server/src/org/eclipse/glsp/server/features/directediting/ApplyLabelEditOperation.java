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
package org.eclipse.glsp.server.features.directediting;

import org.eclipse.glsp.server.operations.Operation;

public class ApplyLabelEditOperation extends Operation {

   public static final String KIND = "applyLabelEdit";

   private String labelId;
   private String text;

   public ApplyLabelEditOperation() {
      super(KIND);
   }

   public ApplyLabelEditOperation(final String labelId, final String text) {
      this();
      this.labelId = labelId;
      this.text = text;
   }

   public String getLabelId() { return labelId; }

   public String getText() { return text; }

}
