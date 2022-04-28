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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.glsp.server.emf.model.notation.NotationPackage
 * @generated
 */
public interface NotationFactory extends EFactory {
   /**
    * The singleton instance of the factory.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   NotationFactory eINSTANCE = org.eclipse.glsp.server.emf.model.notation.impl.NotationFactoryImpl.init();

   /**
    * Returns a new object of class '<em>Shape</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return a new object of class '<em>Shape</em>'.
    * @generated
    */
   Shape createShape();

   /**
    * Returns a new object of class '<em>Edge</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return a new object of class '<em>Edge</em>'.
    * @generated
    */
   Edge createEdge();

   /**
    * Returns a new object of class '<em>Diagram</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return a new object of class '<em>Diagram</em>'.
    * @generated
    */
   Diagram createDiagram();

   /**
    * Returns a new object of class '<em>Semantic Element Reference</em>'.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return a new object of class '<em>Semantic Element Reference</em>'.
    * @generated
    */
   SemanticElementReference createSemanticElementReference();

   /**
    * Returns the package supported by this factory.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the package supported by this factory.
    * @generated
    */
   NotationPackage getNotationPackage();

} //NotationFactory
