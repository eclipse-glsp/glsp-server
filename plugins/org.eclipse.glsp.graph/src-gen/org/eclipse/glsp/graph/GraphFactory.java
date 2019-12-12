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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.glsp.graph.GraphPackage
 * @generated
 */
public interface GraphFactory extends EFactory {
   /**
    * The singleton instance of the factory.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   GraphFactory eINSTANCE = org.eclipse.glsp.graph.impl.GraphFactoryImpl.init();

   /**
    * Returns a new object of class '<em>GGraph</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return a new object of class '<em>GGraph</em>'.
    * @generated
    */
   GGraph createGGraph();

   /**
    * Returns a new object of class '<em>GModel Root</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return a new object of class '<em>GModel Root</em>'.
    * @generated
    */
   GModelRoot createGModelRoot();

   /**
    * Returns a new object of class '<em>GNode</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return a new object of class '<em>GNode</em>'.
    * @generated
    */
   GNode createGNode();

   /**
    * Returns a new object of class '<em>GEdge</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return a new object of class '<em>GEdge</em>'.
    * @generated
    */
   GEdge createGEdge();

   /**
    * Returns a new object of class '<em>GCompartment</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return a new object of class '<em>GCompartment</em>'.
    * @generated
    */
   GCompartment createGCompartment();

   /**
    * Returns a new object of class '<em>GLabel</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return a new object of class '<em>GLabel</em>'.
    * @generated
    */
   GLabel createGLabel();

   /**
    * Returns a new object of class '<em>GIssue Marker</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return a new object of class '<em>GIssue Marker</em>'.
    * @generated
    */
   GIssueMarker createGIssueMarker();

   /**
    * Returns a new object of class '<em>GPort</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return a new object of class '<em>GPort</em>'.
    * @generated
    */
   GPort createGPort();

   /**
    * Returns a new object of class '<em>GButton</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return a new object of class '<em>GButton</em>'.
    * @generated
    */
   GButton createGButton();

   /**
    * Returns a new object of class '<em>GPoint</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return a new object of class '<em>GPoint</em>'.
    * @generated
    */
   GPoint createGPoint();

   /**
    * Returns a new object of class '<em>GDimension</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return a new object of class '<em>GDimension</em>'.
    * @generated
    */
   GDimension createGDimension();

   /**
    * Returns a new object of class '<em>GEdge Placement</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return a new object of class '<em>GEdge Placement</em>'.
    * @generated
    */
   GEdgePlacement createGEdgePlacement();

   /**
    * Returns a new object of class '<em>GBounds</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return a new object of class '<em>GBounds</em>'.
    * @generated
    */
   GBounds createGBounds();

   /**
    * Returns a new object of class '<em>GAlignable</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return a new object of class '<em>GAlignable</em>'.
    * @generated
    */
   GAlignable createGAlignable();

   /**
    * Returns a new object of class '<em>GIssue</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return a new object of class '<em>GIssue</em>'.
    * @generated
    */
   GIssue createGIssue();

   /**
    * Returns a new object of class '<em>GHtml Root</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return a new object of class '<em>GHtml Root</em>'.
    * @generated
    */
   GHtmlRoot createGHtmlRoot();

   /**
    * Returns a new object of class '<em>GPre Rendered Element</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return a new object of class '<em>GPre Rendered Element</em>'.
    * @generated
    */
   GPreRenderedElement createGPreRenderedElement();

   /**
    * Returns the package supported by this factory.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the package supported by this factory.
    * @generated
    */
   GraphPackage getGraphPackage();

} //GraphFactory
