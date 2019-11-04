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
 * A representation of the model object '<em><b>GButton</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.glsp.graph.GButton#isEnabled <em>Enabled</em>}</li>
 * </ul>
 *
 * @see org.eclipse.glsp.graph.GraphPackage#getGButton()
 * @model
 * @generated
 */
public interface GButton extends GShapeElement {
   /**
    * Returns the value of the '<em><b>Enabled</b></em>' attribute.
    * The default value is <code>"true"</code>.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Enabled</em>' attribute.
    * @see #setEnabled(boolean)
    * @see org.eclipse.glsp.graph.GraphPackage#getGButton_Enabled()
    * @model default="true" required="true"
    * @generated
    */
   boolean isEnabled();

   /**
    * Sets the value of the '{@link org.eclipse.glsp.graph.GButton#isEnabled <em>Enabled</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param value the new value of the '<em>Enabled</em>' attribute.
    * @see #isEnabled()
    * @generated
    */
   void setEnabled(boolean value);

} // GButton
