/**
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
 * ********************************************************************************
 */
package org.eclipse.glsp.graph;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GLayout Data</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.glsp.graph.GLayoutData#getPrefSize <em>Pref Size</em>}</li>
 * </ul>
 *
 * @see org.eclipse.glsp.graph.GraphPackage#getGLayoutData()
 * @model
 * @generated
 */
public interface GLayoutData extends EObject {
   /**
    * Returns the value of the '<em><b>Pref Size</b></em>' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * <!-- begin-model-doc -->
    * Preferred size, used as a hint by client-side layout to compute the visual bounds. The resulting visual bounds may be larger (e.g. because the node was expanded to display child nodes) or smaller (e.g. because the parent node doesn't allow this node to grow too much)
    * <!-- end-model-doc -->
    * @return the value of the '<em>Pref Size</em>' reference.
    * @see #setPrefSize(GDimension)
    * @see org.eclipse.glsp.graph.GraphPackage#getGLayoutData_PrefSize()
    * @model
    * @generated
    */
   GDimension getPrefSize();

   /**
    * Sets the value of the '{@link org.eclipse.glsp.graph.GLayoutData#getPrefSize <em>Pref Size</em>}' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param value the new value of the '<em>Pref Size</em>' reference.
    * @see #getPrefSize()
    * @generated
    */
   void setPrefSize(GDimension value);

} // GLayoutData
