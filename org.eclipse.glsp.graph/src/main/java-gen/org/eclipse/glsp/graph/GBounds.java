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
 * A representation of the model object '<em><b>GBounds</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.glsp.graph.GBounds#getX <em>X</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.GBounds#getY <em>Y</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.GBounds#getWidth <em>Width</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.GBounds#getHeight <em>Height</em>}</li>
 * </ul>
 *
 * @see org.eclipse.glsp.graph.GraphPackage#getGBounds()
 * @model
 * @generated
 */
public interface GBounds extends EObject {
   /**
    * Returns the value of the '<em><b>X</b></em>' attribute.
    * The default value is <code>"0"</code>.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>X</em>' attribute.
    * @see #setX(double)
    * @see org.eclipse.glsp.graph.GraphPackage#getGBounds_X()
    * @model default="0" required="true"
    * @generated
    */
   double getX();

   /**
    * Sets the value of the '{@link org.eclipse.glsp.graph.GBounds#getX <em>X</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param value the new value of the '<em>X</em>' attribute.
    * @see #getX()
    * @generated
    */
   void setX(double value);

   /**
    * Returns the value of the '<em><b>Y</b></em>' attribute.
    * The default value is <code>"0"</code>.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Y</em>' attribute.
    * @see #setY(double)
    * @see org.eclipse.glsp.graph.GraphPackage#getGBounds_Y()
    * @model default="0" required="true"
    * @generated
    */
   double getY();

   /**
    * Sets the value of the '{@link org.eclipse.glsp.graph.GBounds#getY <em>Y</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param value the new value of the '<em>Y</em>' attribute.
    * @see #getY()
    * @generated
    */
   void setY(double value);

   /**
    * Returns the value of the '<em><b>Width</b></em>' attribute.
    * The default value is <code>"0"</code>.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Width</em>' attribute.
    * @see #setWidth(double)
    * @see org.eclipse.glsp.graph.GraphPackage#getGBounds_Width()
    * @model default="0" required="true"
    * @generated
    */
   double getWidth();

   /**
    * Sets the value of the '{@link org.eclipse.glsp.graph.GBounds#getWidth <em>Width</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param value the new value of the '<em>Width</em>' attribute.
    * @see #getWidth()
    * @generated
    */
   void setWidth(double value);

   /**
    * Returns the value of the '<em><b>Height</b></em>' attribute.
    * The default value is <code>"0"</code>.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Height</em>' attribute.
    * @see #setHeight(double)
    * @see org.eclipse.glsp.graph.GraphPackage#getGBounds_Height()
    * @model default="0" required="true"
    * @generated
    */
   double getHeight();

   /**
    * Sets the value of the '{@link org.eclipse.glsp.graph.GBounds#getHeight <em>Height</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param value the new value of the '<em>Height</em>' attribute.
    * @see #getHeight()
    * @generated
    */
   void setHeight(double value);

} // GBounds
