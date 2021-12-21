/********************************************************************************
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
 ********************************************************************************/
package org.eclipse.glsp.server.features.contextactions;

import java.util.List;

import org.eclipse.glsp.server.features.directediting.LabeledAction;
import org.eclipse.glsp.server.types.EditorContext;

/**
 * A provider for a certain contextId that provides {@link LabeledAction}s.
 */
public interface ContextActionsProvider {

   /**
    * Returns the context id of the {@link ContextActionsProvider}.
    */
   String getContextId();

   /**
    * Returns a list of {@link LabeledAction}s for a given {@link EditorContext}.
    *
    * @param editorContext The editorContext for which the actions are returned.
    * @return A list of {@link LabeledAction}s for a given {@link EditorContext}.
    */
   List<? extends LabeledAction> getActions(EditorContext editorContext);

   /**
    * Validates if a ContextActionsProvider can handle the given contextId.
    *
    * @param contextId The contextIf that to be checked.
    * @return `true` if a ContextActionsProvider can handle a given contextId.
    */
   default boolean handles(final String contextId) {
      return getContextId().equals(contextId);
   }

}
