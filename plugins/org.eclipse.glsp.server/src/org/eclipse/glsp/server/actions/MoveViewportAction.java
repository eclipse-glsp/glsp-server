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
package org.eclipse.glsp.server.actions;

/**
 * Instructs the client to move the diagram canvas by the given amount.
 */
public class MoveViewportAction extends Action {

   public static final String KIND = "moveViewport";

   private double moveX;
   private double moveY;

   public MoveViewportAction() {
      super(KIND);
   }

   public MoveViewportAction(final double moveX, final double moveY) {
      super(KIND);
      this.moveX = moveX;
      this.moveY = moveY;
   }

   public double getMoveX() { return moveX; }

   public void setMoveX(final double moveX) { this.moveX = moveX; }

   public double getMoveY() { return moveY; }

   public void setMoveY(final double moveY) { this.moveY = moveY; }

}
