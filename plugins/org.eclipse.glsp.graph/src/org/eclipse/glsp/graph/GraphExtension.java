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
package org.eclipse.glsp.graph;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EPackage;

/**
 * Is used to configure a graph extension for a diagram language. A graph extension
 * is a custom Ecore model that extends the default GLSP Graph ecore model, e.g by adding new GModelElement types.
 */
public interface GraphExtension {
   /**
    * Unique identifier for this graph extension (Default: NSURI of the EPackage).
    *
    * @return Id as String
    */
   default String getId() { return getEPackage().getNsURI(); }

   /**
    * Returns the EPackage for this {@link GraphExtension}.
    *
    * @return the EPackage
    */

   EPackage getEPackage();

   /**
    * Returns the EFactory for this {@link GraphExtension}.
    *
    * @return the EFactory
    */
   EFactory getEFactory();
}
