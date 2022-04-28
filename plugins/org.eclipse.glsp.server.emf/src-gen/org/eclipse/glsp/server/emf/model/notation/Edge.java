/**
 * Copyright (c) 2022 EclipseSource and others.
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
 */
package org.eclipse.glsp.server.emf.model.notation;

import org.eclipse.emf.common.util.EList;

import org.eclipse.glsp.graph.GPoint;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Edge</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.glsp.server.emf.model.notation.Edge#getBendPoints <em>Bend Points</em>}</li>
 *   <li>{@link org.eclipse.glsp.server.emf.model.notation.Edge#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.glsp.server.emf.model.notation.Edge#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see org.eclipse.glsp.server.emf.model.notation.NotationPackage#getEdge()
 * @model
 * @generated
 */
public interface Edge extends NotationElement {
   /**
    * Returns the value of the '<em><b>Bend Points</b></em>' containment reference list.
    * The list contents are of type {@link org.eclipse.glsp.graph.GPoint}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Bend Points</em>' containment reference list.
    * @see org.eclipse.glsp.server.emf.model.notation.NotationPackage#getEdge_BendPoints()
    * @model containment="true"
    * @generated
    */
   EList<GPoint> getBendPoints();

   /**
    * Returns the value of the '<em><b>Source</b></em>' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Source</em>' reference.
    * @see #setSource(NotationElement)
    * @see org.eclipse.glsp.server.emf.model.notation.NotationPackage#getEdge_Source()
    * @model
    * @generated
    */
   NotationElement getSource();

   /**
    * Sets the value of the '{@link org.eclipse.glsp.server.emf.model.notation.Edge#getSource <em>Source</em>}' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param value the new value of the '<em>Source</em>' reference.
    * @see #getSource()
    * @generated
    */
   void setSource(NotationElement value);

   /**
    * Returns the value of the '<em><b>Target</b></em>' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Target</em>' reference.
    * @see #setTarget(NotationElement)
    * @see org.eclipse.glsp.server.emf.model.notation.NotationPackage#getEdge_Target()
    * @model
    * @generated
    */
   NotationElement getTarget();

   /**
    * Sets the value of the '{@link org.eclipse.glsp.server.emf.model.notation.Edge#getTarget <em>Target</em>}' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param value the new value of the '<em>Target</em>' reference.
    * @see #getTarget()
    * @generated
    */
   void setTarget(NotationElement value);

} // Edge
