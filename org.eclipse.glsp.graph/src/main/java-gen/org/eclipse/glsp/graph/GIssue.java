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
 * A representation of the model object '<em><b>GIssue</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.glsp.graph.GIssue#getSeverity <em>Severity</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.GIssue#getMessage <em>Message</em>}</li>
 * </ul>
 *
 * @see org.eclipse.glsp.graph.GraphPackage#getGIssue()
 * @model
 * @generated
 */
public interface GIssue extends EObject {
   /**
    * Returns the value of the '<em><b>Severity</b></em>' attribute.
    * The default value is <code>"info"</code>.
    * The literals are from the enumeration {@link org.eclipse.glsp.graph.GSeverity}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Severity</em>' attribute.
    * @see org.eclipse.glsp.graph.GSeverity
    * @see #setSeverity(GSeverity)
    * @see org.eclipse.glsp.graph.GraphPackage#getGIssue_Severity()
    * @model default="info" required="true"
    * @generated
    */
   GSeverity getSeverity();

   /**
    * Sets the value of the '{@link org.eclipse.glsp.graph.GIssue#getSeverity <em>Severity</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param value the new value of the '<em>Severity</em>' attribute.
    * @see org.eclipse.glsp.graph.GSeverity
    * @see #getSeverity()
    * @generated
    */
   void setSeverity(GSeverity value);

   /**
    * Returns the value of the '<em><b>Message</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Message</em>' attribute.
    * @see #setMessage(String)
    * @see org.eclipse.glsp.graph.GraphPackage#getGIssue_Message()
    * @model
    * @generated
    */
   String getMessage();

   /**
    * Sets the value of the '{@link org.eclipse.glsp.graph.GIssue#getMessage <em>Message</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param value the new value of the '<em>Message</em>' attribute.
    * @see #getMessage()
    * @generated
    */
   void setMessage(String value);

} // GIssue
