/*******************************************************************************
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
 ******************************************************************************/
package org.eclipse.glsp.server.features.navigation;

import org.eclipse.glsp.server.actions.RequestAction;

public class ResolveNavigationTargetAction extends RequestAction<SetResolvedNavigationTargetAction> {

   public static final String KIND = "resolveNavigationTarget";

   private NavigationTarget navigationTarget;

   public ResolveNavigationTargetAction() {
      super(KIND);
   }

   public ResolveNavigationTargetAction(final NavigationTarget target) {
      this();
      this.navigationTarget = target;
   }

   public NavigationTarget getNavigationTarget() { return navigationTarget; }

   public void setNavigationTarget(final NavigationTarget navigationTarget) {
      this.navigationTarget = navigationTarget;
   }

}
