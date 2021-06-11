/**
 * Copyright (c) 2019-2021 EclipseSource and others.
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
package org.eclipse.glsp.graph.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.glsp.graph.GDimension;
import org.eclipse.glsp.graph.GLayoutData;
import org.eclipse.glsp.graph.GraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>GLayout Data</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.glsp.graph.impl.GLayoutDataImpl#getPrefSize <em>Pref Size</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GLayoutDataImpl extends MinimalEObjectImpl.Container implements GLayoutData {
   /**
    * The cached value of the '{@link #getPrefSize() <em>Pref Size</em>}' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getPrefSize()
    * @generated
    * @ordered
    */
   protected GDimension prefSize;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public GLayoutDataImpl() {
      super();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   protected EClass eStaticClass() {
      return GraphPackage.Literals.GLAYOUT_DATA;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GDimension getPrefSize() {
      if (prefSize != null && prefSize.eIsProxy()) {
         InternalEObject oldPrefSize = (InternalEObject) prefSize;
         prefSize = (GDimension) eResolveProxy(oldPrefSize);
         if (prefSize != oldPrefSize) {
            if (eNotificationRequired())
               eNotify(new ENotificationImpl(this, Notification.RESOLVE, GraphPackage.GLAYOUT_DATA__PREF_SIZE,
                  oldPrefSize, prefSize));
         }
      }
      return prefSize;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public GDimension basicGetPrefSize() {
      return prefSize;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setPrefSize(GDimension newPrefSize) {
      GDimension oldPrefSize = prefSize;
      prefSize = newPrefSize;
      if (eNotificationRequired())
         eNotify(
            new ENotificationImpl(this, Notification.SET, GraphPackage.GLAYOUT_DATA__PREF_SIZE, oldPrefSize, prefSize));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Object eGet(int featureID, boolean resolve, boolean coreType) {
      switch (featureID) {
         case GraphPackage.GLAYOUT_DATA__PREF_SIZE:
            if (resolve)
               return getPrefSize();
            return basicGetPrefSize();
      }
      return super.eGet(featureID, resolve, coreType);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void eSet(int featureID, Object newValue) {
      switch (featureID) {
         case GraphPackage.GLAYOUT_DATA__PREF_SIZE:
            setPrefSize((GDimension) newValue);
            return;
      }
      super.eSet(featureID, newValue);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void eUnset(int featureID) {
      switch (featureID) {
         case GraphPackage.GLAYOUT_DATA__PREF_SIZE:
            setPrefSize((GDimension) null);
            return;
      }
      super.eUnset(featureID);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public boolean eIsSet(int featureID) {
      switch (featureID) {
         case GraphPackage.GLAYOUT_DATA__PREF_SIZE:
            return prefSize != null;
      }
      return super.eIsSet(featureID);
   }

} //GLayoutDataImpl
