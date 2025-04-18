/*******************************************************************************
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
 ******************************************************************************/
package org.eclipse.glsp.server.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.glsp.server.utils.CollaborationUtil;

import com.google.inject.Inject;

public class DefaultCommandStackManager implements CommandStackManager {

   @Inject
   CommandStackFactory factory;

   // subclientId, CommandStack
   protected Map<String, CommandStack> commandStackMap = new HashMap<>();

   @Override
   public CommandStack getOrCreateCommandStack(final String subclientId) {
      String subclientIdOrFallback = getSubclientIdOrFallback(subclientId);
      if (commandStackMap.containsKey(subclientIdOrFallback)) {
         return commandStackMap.get(subclientIdOrFallback);
      }

      CommandStack commandStack = factory.createCommandStack();
      commandStackMap.put(subclientIdOrFallback, commandStack);
      return commandStack;
   }

   @Override
   public List<CommandStack> getAllCommandStacks() { return new ArrayList<>(commandStackMap.values()); }

   @Override
   public void setCommandStack(final CommandStack commandStack, final String subclientId) {
      String subclientIdOrFallback = getSubclientIdOrFallback(subclientId);
      if (commandStackMap.containsKey(subclientIdOrFallback)) {
         commandStackMap.get(subclientIdOrFallback).flush();
      }
      commandStackMap.put(subclientIdOrFallback, commandStack);
   }

   private String getSubclientIdOrFallback(final String subclientId) {
      if (subclientId != null) {
         return subclientId;
      }
      return CollaborationUtil.FALLBACK_SUBCLIENT_ID;
   }

}
