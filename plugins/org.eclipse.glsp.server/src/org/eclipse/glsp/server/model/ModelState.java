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
package org.eclipse.glsp.server.model;

import java.util.Map;
import java.util.Optional;

import org.eclipse.glsp.server.actions.SetEditModeAction;

interface ModelState<T> {

   String getClientId();

   void setClientId(String clientId);

   Map<String, String> getClientOptions();

   void setClientOptions(Map<String, String> options);

   T getRoot();

   void updateRoot(T newRoot);

   boolean canUndo(String subclientId);

   boolean canRedo(String subclientId);

   void undo(String subclientId);

   void redo(String subclientId);

   boolean isDirty(String subclientId);

   String getEditMode();

   void setEditMode(String editMode);

   <P> P setProperty(String key, P property);

   <P> Optional<P> getProperty(String key, Class<P> type);

   void clearProperty(String key);

   default boolean isReadonly() { return SetEditModeAction.EDIT_MODE_READONLY.equals(getEditMode()); }
}
