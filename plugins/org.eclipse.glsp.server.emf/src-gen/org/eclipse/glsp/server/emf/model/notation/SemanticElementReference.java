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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Semantic Element Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.glsp.server.emf.model.notation.SemanticElementReference#getElementId <em>Element Id</em>}</li>
 *   <li>{@link org.eclipse.glsp.server.emf.model.notation.SemanticElementReference#getResolvedSemanticElement <em>Resolved Semantic Element</em>}</li>
 * </ul>
 *
 * @see org.eclipse.glsp.server.emf.model.notation.NotationPackage#getSemanticElementReference()
 * @model
 * @generated
 */
public interface SemanticElementReference extends EObject {
   /**
    * Returns the value of the '<em><b>Element Id</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Element Id</em>' attribute.
    * @see #setElementId(String)
    * @see org.eclipse.glsp.server.emf.model.notation.NotationPackage#getSemanticElementReference_ElementId()
    * @model
    * @generated
    */
   String getElementId();

   /**
    * Sets the value of the '{@link org.eclipse.glsp.server.emf.model.notation.SemanticElementReference#getElementId <em>Element Id</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param value the new value of the '<em>Element Id</em>' attribute.
    * @see #getElementId()
    * @generated
    */
   void setElementId(String value);

   /**
    * Returns the value of the '<em><b>Resolved Semantic Element</b></em>' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Resolved Semantic Element</em>' reference.
    * @see #setResolvedSemanticElement(EObject)
    * @see org.eclipse.glsp.server.emf.model.notation.NotationPackage#getSemanticElementReference_ResolvedSemanticElement()
    * @model transient="true"
    * @generated
    */
   EObject getResolvedSemanticElement();

   /**
    * Sets the value of the '{@link org.eclipse.glsp.server.emf.model.notation.SemanticElementReference#getResolvedSemanticElement <em>Resolved Semantic Element</em>}' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param value the new value of the '<em>Resolved Semantic Element</em>' reference.
    * @see #getResolvedSemanticElement()
    * @generated
    */
   void setResolvedSemanticElement(EObject value);

} // SemanticElementReference
