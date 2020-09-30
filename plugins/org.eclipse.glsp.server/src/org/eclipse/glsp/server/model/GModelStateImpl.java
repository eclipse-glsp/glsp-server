/********************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
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
package org.eclipse.glsp.server.model;

import java.util.Map;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.glsp.graph.GModelIndex;
import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.server.internal.gmodel.commandstack.GModelCommandStack;

public class GModelStateImpl implements GModelState {

   private Map<String, String> options;
   private String clientId;
   private GModelRoot currentModel;
   private BasicCommandStack commandStack;
   private String editMode;

   @Override
   public Map<String, String> getClientOptions() { return options; }

   @Override
   public void setClientId(final String clientId) { this.clientId = clientId; }

   @Override
   public String getClientId() { return clientId; }

   @Override
   public GModelRoot getRoot() { return currentModel; }

   @Override
   public void setRoot(final GModelRoot newRoot) {
      this.currentModel = newRoot;
      initializeCommandStack();
   }

   protected void initializeCommandStack() {
      if (commandStack != null) {
         commandStack.flush();
      }
      commandStack = new GModelCommandStack();
   }

   public CommandStack getCommandStack() { return commandStack; }

   protected void setCommandStack(final BasicCommandStack commandStack) {
      if (this.commandStack != null) {
         this.commandStack.flush();
      }

      this.commandStack = commandStack;
   }

   @Override
   public void setClientOptions(final Map<String, String> options) { this.options = options; }

   @Override
   public GModelIndex getIndex() { return GModelIndex.get(currentModel); }

   @Override
   public void execute(final Command command) {
      if (commandStack == null) {
         return;
      }
      commandStack.execute(command);
   }

   @Override
   public boolean canUndo() {
      if (commandStack == null) {
         return false;
      }
      return commandStack.canUndo();
   }

   @Override
   public boolean canRedo() {
      if (commandStack == null) {
         return false;
      }
      return commandStack.canRedo();
   }

   @Override
   public void undo() {
      if (commandStack == null) {
         return;
      }
      commandStack.undo();
   }

   @Override
   public void redo() {
      if (commandStack == null) {
         return;
      }
      commandStack.redo();
   }

   @Override
   public boolean isDirty() { return commandStack.isSaveNeeded(); }

   @Override
   public void saveIsDone() {
      commandStack.saveIsDone();
   }

   @Override
   public String getEditMode() { return this.editMode; }

   @Override
   public void setEditMode(final String editMode) { this.editMode = editMode; }
}
