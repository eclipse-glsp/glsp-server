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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GEdge</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.glsp.graph.GEdge#getRoutingPoints <em>Routing Points</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.GEdge#getSourceId <em>Source Id</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.GEdge#getTargetId <em>Target Id</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.GEdge#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.GEdge#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.GEdge#getRouterKind <em>Router Kind</em>}</li>
 * </ul>
 *
 * @see org.eclipse.glsp.graph.GraphPackage#getGEdge()
 * @model
 * @generated
 */
public interface GEdge extends GModelElement {
   /**
    * Returns the value of the '<em><b>Routing Points</b></em>' containment reference list.
    * The list contents are of type {@link org.eclipse.glsp.graph.GPoint}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Routing Points</em>' containment reference list.
    * @see org.eclipse.glsp.graph.GraphPackage#getGEdge_RoutingPoints()
    * @model containment="true"
    * @generated
    */
   EList<GPoint> getRoutingPoints();

   /**
    * Returns the value of the '<em><b>Source Id</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Source Id</em>' attribute.
    * @see #setSourceId(String)
    * @see org.eclipse.glsp.graph.GraphPackage#getGEdge_SourceId()
    * @model
    * @generated
    */
   String getSourceId();

   /**
    * Sets the value of the '{@link org.eclipse.glsp.graph.GEdge#getSourceId <em>Source Id</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param value the new value of the '<em>Source Id</em>' attribute.
    * @see #getSourceId()
    * @generated
    */
   void setSourceId(String value);

   /**
    * Returns the value of the '<em><b>Target Id</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Target Id</em>' attribute.
    * @see #setTargetId(String)
    * @see org.eclipse.glsp.graph.GraphPackage#getGEdge_TargetId()
    * @model
    * @generated
    */
   String getTargetId();

   /**
    * Sets the value of the '{@link org.eclipse.glsp.graph.GEdge#getTargetId <em>Target Id</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param value the new value of the '<em>Target Id</em>' attribute.
    * @see #getTargetId()
    * @generated
    */
   void setTargetId(String value);

   /**
    * Returns the value of the '<em><b>Source</b></em>' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Source</em>' reference.
    * @see #setSource(GModelElement)
    * @see org.eclipse.glsp.graph.GraphPackage#getGEdge_Source()
    * @model transient="true" volatile="true"
    * @generated
    */
   GModelElement getSource();

   /**
    * Sets the value of the '{@link org.eclipse.glsp.graph.GEdge#getSource <em>Source</em>}' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param value the new value of the '<em>Source</em>' reference.
    * @see #getSource()
    * @generated
    */
   void setSource(GModelElement value);

   /**
    * Returns the value of the '<em><b>Target</b></em>' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Target</em>' reference.
    * @see #setTarget(GModelElement)
    * @see org.eclipse.glsp.graph.GraphPackage#getGEdge_Target()
    * @model transient="true" volatile="true"
    * @generated
    */
   GModelElement getTarget();

   /**
    * Sets the value of the '{@link org.eclipse.glsp.graph.GEdge#getTarget <em>Target</em>}' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param value the new value of the '<em>Target</em>' reference.
    * @see #getTarget()
    * @generated
    */
   void setTarget(GModelElement value);

   /**
    * Returns the value of the '<em><b>Router Kind</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Router Kind</em>' attribute.
    * @see #setRouterKind(String)
    * @see org.eclipse.glsp.graph.GraphPackage#getGEdge_RouterKind()
    * @model
    * @generated
    */
   String getRouterKind();

   /**
    * Sets the value of the '{@link org.eclipse.glsp.graph.GEdge#getRouterKind <em>Router Kind</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param value the new value of the '<em>Router Kind</em>' attribute.
    * @see #getRouterKind()
    * @generated
    */
   void setRouterKind(String value);

} // GEdge
