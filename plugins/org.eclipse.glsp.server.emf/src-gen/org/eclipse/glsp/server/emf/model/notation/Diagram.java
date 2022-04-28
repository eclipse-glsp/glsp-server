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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Diagram</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.glsp.server.emf.model.notation.Diagram#getElements <em>Elements</em>}</li>
 *   <li>{@link org.eclipse.glsp.server.emf.model.notation.Diagram#getDiagramType <em>Diagram Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.glsp.server.emf.model.notation.NotationPackage#getDiagram()
 * @model
 * @generated
 */
public interface Diagram extends NotationElement {
   /**
    * Returns the value of the '<em><b>Elements</b></em>' containment reference list.
    * The list contents are of type {@link org.eclipse.glsp.server.emf.model.notation.NotationElement}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Elements</em>' containment reference list.
    * @see org.eclipse.glsp.server.emf.model.notation.NotationPackage#getDiagram_Elements()
    * @model containment="true"
    * @generated
    */
   EList<NotationElement> getElements();

   /**
    * Returns the value of the '<em><b>Diagram Type</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Diagram Type</em>' attribute.
    * @see #setDiagramType(String)
    * @see org.eclipse.glsp.server.emf.model.notation.NotationPackage#getDiagram_DiagramType()
    * @model
    * @generated
    */
   String getDiagramType();

   /**
    * Sets the value of the '{@link org.eclipse.glsp.server.emf.model.notation.Diagram#getDiagramType <em>Diagram Type</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param value the new value of the '<em>Diagram Type</em>' attribute.
    * @see #getDiagramType()
    * @generated
    */
   void setDiagramType(String value);

} // Diagram
