/********************************************************************************
 * Copyright (c) 2020 EclipseSource and others.
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
package org.eclipse.glsp.server.features.navigation;

import java.util.List;

import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.types.EditorContext;

public interface NavigationTargetProvider {

   String JSON_OPENER_OPTIONS = "jsonOpenerOptions";

   /**
    * Specifies the navigation targets for the given target type.
    * <p>
    * If the <code>args</code> of a returned {@link NavigationTarget} contain a
    * {@link NavigationTarget#ELEMENT_IDS}, GLSP diagram clients should navigate to
    * the model elements with the specified ID within the current diagram. Multiple element
    * IDs can be concatenated with {@link NavigationTarget#ELEMENT_IDS_SEPARATOR}.
    * <p>
    * If the <code>args</code> of a returned {@link NavigationTarget} contain a property
    * {@link #JSON_OPENER_OPTIONS} and its uri is outside of the current diagram, the string
    * value of the {@value #JSON_OPENER_OPTIONS} property will be parsed as JSON
    * and merged into the opener options by the Theia integration of the GLSP client.
    * This allows GLSP servers to pass additional opener options, such as a selection, etc.
    * Other clients (non-Theia clients) should behave the same way.
    *
    * @param editorContext The editor context
    * @param modelState    The model state
    * @return the list of navigation targets
    */
   List<? extends NavigationTarget> getTargets(EditorContext editorContext, GModelState modelState);

   String getTargetTypeId();

   default boolean handles(final String targetTypeId) {
      return getTargetTypeId().equals(targetTypeId);
   }

}
