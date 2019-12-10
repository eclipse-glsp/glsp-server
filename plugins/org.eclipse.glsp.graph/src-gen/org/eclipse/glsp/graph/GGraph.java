/**
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
 * ********************************************************************************
 */
package org.eclipse.glsp.graph;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GGraph</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.glsp.graph.GGraph#getLayoutOptions <em>Layout Options</em>}</li>
 * </ul>
 *
 * @see org.eclipse.glsp.graph.GraphPackage#getGGraph()
 * @model
 * @generated
 */
public interface GGraph extends GModelRoot, GBoundsAware {
   /**
    * Returns the value of the '<em><b>Layout Options</b></em>' containment reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Layout Options</em>' containment reference.
    * @see #setLayoutOptions(GLayoutOptions)
    * @see org.eclipse.glsp.graph.GraphPackage#getGGraph_LayoutOptions()
    * @model containment="true"
    * @generated
    */
   GLayoutOptions getLayoutOptions();

   /**
    * Sets the value of the '{@link org.eclipse.glsp.graph.GGraph#getLayoutOptions <em>Layout Options</em>}' containment reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param value the new value of the '<em>Layout Options</em>' containment reference.
    * @see #getLayoutOptions()
    * @generated
    */
   void setLayoutOptions(GLayoutOptions value);

} // GGraph
