/**
 * Copyright (c) 2019-2022 EclipseSource and others.
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

import org.eclipse.glsp.graph.GBoundsAware;
import org.eclipse.glsp.graph.GDimension;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.GShapePreRenderedElement;
import org.eclipse.glsp.graph.GraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>GShape Pre Rendered Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.glsp.graph.impl.GShapePreRenderedElementImpl#getPosition <em>Position</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GShapePreRenderedElementImpl#getSize <em>Size</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GShapePreRenderedElementImpl extends GPreRenderedElementImpl implements GShapePreRenderedElement {
   /**
    * The cached value of the '{@link #getPosition() <em>Position</em>}' containment reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getPosition()
    * @generated
    * @ordered
    */
   protected GPoint position;

   /**
    * The cached value of the '{@link #getSize() <em>Size</em>}' containment reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getSize()
    * @generated
    * @ordered
    */
   protected GDimension size;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public GShapePreRenderedElementImpl() {
      super();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   protected EClass eStaticClass() {
      return GraphPackage.Literals.GSHAPE_PRE_RENDERED_ELEMENT;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GPoint getPosition() { return position; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public NotificationChain basicSetPosition(GPoint newPosition, NotificationChain msgs) {
      GPoint oldPosition = position;
      position = newPosition;
      if (eNotificationRequired()) {
         ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
            GraphPackage.GSHAPE_PRE_RENDERED_ELEMENT__POSITION, oldPosition, newPosition);
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
   public void setPosition(GPoint newPosition) {
      if (newPosition != position) {
         NotificationChain msgs = null;
         if (position != null)
            msgs = ((InternalEObject) position).eInverseRemove(this,
               EOPPOSITE_FEATURE_BASE - GraphPackage.GSHAPE_PRE_RENDERED_ELEMENT__POSITION, null, msgs);
         if (newPosition != null)
            msgs = ((InternalEObject) newPosition).eInverseAdd(this,
               EOPPOSITE_FEATURE_BASE - GraphPackage.GSHAPE_PRE_RENDERED_ELEMENT__POSITION, null, msgs);
         msgs = basicSetPosition(newPosition, msgs);
         if (msgs != null)
            msgs.dispatch();
      } else if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GSHAPE_PRE_RENDERED_ELEMENT__POSITION,
            newPosition, newPosition));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GDimension getSize() { return size; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public NotificationChain basicSetSize(GDimension newSize, NotificationChain msgs) {
      GDimension oldSize = size;
      size = newSize;
      if (eNotificationRequired()) {
         ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
            GraphPackage.GSHAPE_PRE_RENDERED_ELEMENT__SIZE, oldSize, newSize);
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
   public void setSize(GDimension newSize) {
      if (newSize != size) {
         NotificationChain msgs = null;
         if (size != null)
            msgs = ((InternalEObject) size).eInverseRemove(this,
               EOPPOSITE_FEATURE_BASE - GraphPackage.GSHAPE_PRE_RENDERED_ELEMENT__SIZE, null, msgs);
         if (newSize != null)
            msgs = ((InternalEObject) newSize).eInverseAdd(this,
               EOPPOSITE_FEATURE_BASE - GraphPackage.GSHAPE_PRE_RENDERED_ELEMENT__SIZE, null, msgs);
         msgs = basicSetSize(newSize, msgs);
         if (msgs != null)
            msgs.dispatch();
      } else if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GSHAPE_PRE_RENDERED_ELEMENT__SIZE, newSize,
            newSize));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
      switch (featureID) {
         case GraphPackage.GSHAPE_PRE_RENDERED_ELEMENT__POSITION:
            return basicSetPosition(null, msgs);
         case GraphPackage.GSHAPE_PRE_RENDERED_ELEMENT__SIZE:
            return basicSetSize(null, msgs);
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
         case GraphPackage.GSHAPE_PRE_RENDERED_ELEMENT__POSITION:
            return getPosition();
         case GraphPackage.GSHAPE_PRE_RENDERED_ELEMENT__SIZE:
            return getSize();
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
         case GraphPackage.GSHAPE_PRE_RENDERED_ELEMENT__POSITION:
            setPosition((GPoint) newValue);
            return;
         case GraphPackage.GSHAPE_PRE_RENDERED_ELEMENT__SIZE:
            setSize((GDimension) newValue);
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
         case GraphPackage.GSHAPE_PRE_RENDERED_ELEMENT__POSITION:
            setPosition((GPoint) null);
            return;
         case GraphPackage.GSHAPE_PRE_RENDERED_ELEMENT__SIZE:
            setSize((GDimension) null);
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
         case GraphPackage.GSHAPE_PRE_RENDERED_ELEMENT__POSITION:
            return position != null;
         case GraphPackage.GSHAPE_PRE_RENDERED_ELEMENT__SIZE:
            return size != null;
      }
      return super.eIsSet(featureID);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
      if (baseClass == GBoundsAware.class) {
         switch (derivedFeatureID) {
            case GraphPackage.GSHAPE_PRE_RENDERED_ELEMENT__POSITION:
               return GraphPackage.GBOUNDS_AWARE__POSITION;
            case GraphPackage.GSHAPE_PRE_RENDERED_ELEMENT__SIZE:
               return GraphPackage.GBOUNDS_AWARE__SIZE;
            default:
               return -1;
         }
      }
      return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
      if (baseClass == GBoundsAware.class) {
         switch (baseFeatureID) {
            case GraphPackage.GBOUNDS_AWARE__POSITION:
               return GraphPackage.GSHAPE_PRE_RENDERED_ELEMENT__POSITION;
            case GraphPackage.GBOUNDS_AWARE__SIZE:
               return GraphPackage.GSHAPE_PRE_RENDERED_ELEMENT__SIZE;
            default:
               return -1;
         }
      }
      return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
   }

} //GShapePreRenderedElementImpl
