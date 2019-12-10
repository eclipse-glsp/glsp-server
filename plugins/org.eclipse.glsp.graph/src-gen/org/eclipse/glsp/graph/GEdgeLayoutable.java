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
 * A representation of the model object '<em><b>GEdge Layoutable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.glsp.graph.GEdgeLayoutable#getEdgePlacement <em>Edge Placement</em>}</li>
 * </ul>
 *
 * @see org.eclipse.glsp.graph.GraphPackage#getGEdgeLayoutable()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface GEdgeLayoutable extends EObject {
   /**
    * Returns the value of the '<em><b>Edge Placement</b></em>' containment reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Edge Placement</em>' containment reference.
    * @see #setEdgePlacement(GEdgePlacement)
    * @see org.eclipse.glsp.graph.GraphPackage#getGEdgeLayoutable_EdgePlacement()
    * @model containment="true"
    * @generated
    */
   GEdgePlacement getEdgePlacement();

   /**
    * Sets the value of the '{@link org.eclipse.glsp.graph.GEdgeLayoutable#getEdgePlacement <em>Edge Placement</em>}' containment reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param value the new value of the '<em>Edge Placement</em>' containment reference.
    * @see #getEdgePlacement()
    * @generated
    */
   void setEdgePlacement(GEdgePlacement value);

} // GEdgeLayoutable
