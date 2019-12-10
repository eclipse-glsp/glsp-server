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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GAlignable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.glsp.graph.GAlignable#getAlignment <em>Alignment</em>}</li>
 * </ul>
 *
 * @see org.eclipse.glsp.graph.GraphPackage#getGAlignable()
 * @model
 * @generated
 */
public interface GAlignable extends EObject {
   /**
    * Returns the value of the '<em><b>Alignment</b></em>' containment reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Alignment</em>' containment reference.
    * @see #setAlignment(GPoint)
    * @see org.eclipse.glsp.graph.GraphPackage#getGAlignable_Alignment()
    * @model containment="true"
    * @generated
    */
   GPoint getAlignment();

   /**
    * Sets the value of the '{@link org.eclipse.glsp.graph.GAlignable#getAlignment <em>Alignment</em>}' containment reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param value the new value of the '<em>Alignment</em>' containment reference.
    * @see #getAlignment()
    * @generated
    */
   void setAlignment(GPoint value);

} // GAlignable
