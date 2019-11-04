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
package org.eclipse.glsp.graph.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.glsp.graph.GAlignable;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.GraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>GAlignable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.glsp.graph.impl.GAlignableImpl#getAlignment <em>Alignment</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GAlignableImpl extends MinimalEObjectImpl.Container implements GAlignable {
   /**
    * The cached value of the '{@link #getAlignment() <em>Alignment</em>}' containment reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getAlignment()
    * @generated
    * @ordered
    */
   protected GPoint alignment;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public GAlignableImpl() {
      super();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   protected EClass eStaticClass() {
      return GraphPackage.Literals.GALIGNABLE;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GPoint getAlignment() { return alignment; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public NotificationChain basicSetAlignment(GPoint newAlignment, NotificationChain msgs) {
      GPoint oldAlignment = alignment;
      alignment = newAlignment;
      if (eNotificationRequired()) {
         ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
            GraphPackage.GALIGNABLE__ALIGNMENT, oldAlignment, newAlignment);
         if (msgs == null)
            msgs = notification;
         else
            msgs.add(notification);
      }
      return msgs;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setAlignment(GPoint newAlignment) {
      if (newAlignment != alignment) {
         NotificationChain msgs = null;
         if (alignment != null)
            msgs = ((InternalEObject) alignment).eInverseRemove(this,
               EOPPOSITE_FEATURE_BASE - GraphPackage.GALIGNABLE__ALIGNMENT, null, msgs);
         if (newAlignment != null)
            msgs = ((InternalEObject) newAlignment).eInverseAdd(this,
               EOPPOSITE_FEATURE_BASE - GraphPackage.GALIGNABLE__ALIGNMENT, null, msgs);
         msgs = basicSetAlignment(newAlignment, msgs);
         if (msgs != null)
            msgs.dispatch();
      } else if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GALIGNABLE__ALIGNMENT, newAlignment,
            newAlignment));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
      switch (featureID) {
         case GraphPackage.GALIGNABLE__ALIGNMENT:
            return basicSetAlignment(null, msgs);
      }
      return super.eInverseRemove(otherEnd, featureID, msgs);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Object eGet(int featureID, boolean resolve, boolean coreType) {
      switch (featureID) {
         case GraphPackage.GALIGNABLE__ALIGNMENT:
            return getAlignment();
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
         case GraphPackage.GALIGNABLE__ALIGNMENT:
            setAlignment((GPoint) newValue);
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
         case GraphPackage.GALIGNABLE__ALIGNMENT:
            setAlignment((GPoint) null);
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
         case GraphPackage.GALIGNABLE__ALIGNMENT:
            return alignment != null;
      }
      return super.eIsSet(featureID);
   }

} //GAlignableImpl
