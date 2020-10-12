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

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.glsp.graph.GEdgePlacement;
import org.eclipse.glsp.graph.GraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>GEdge Placement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.glsp.graph.impl.GEdgePlacementImpl#getPosition <em>Position</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GEdgePlacementImpl#getOffset <em>Offset</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GEdgePlacementImpl#getSide <em>Side</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GEdgePlacementImpl#isRotate <em>Rotate</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GEdgePlacementImpl extends MinimalEObjectImpl.Container implements GEdgePlacement {
   /**
    * The default value of the '{@link #getPosition() <em>Position</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getPosition()
    * @generated
    * @ordered
    */
   protected static final Double POSITION_EDEFAULT = Double.valueOf(0.0);

   /**
    * The cached value of the '{@link #getPosition() <em>Position</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getPosition()
    * @generated
    * @ordered
    */
   protected Double position = POSITION_EDEFAULT;

   /**
    * The default value of the '{@link #getOffset() <em>Offset</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getOffset()
    * @generated
    * @ordered
    */
   protected static final Double OFFSET_EDEFAULT = Double.valueOf(0.0);

   /**
    * The cached value of the '{@link #getOffset() <em>Offset</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getOffset()
    * @generated
    * @ordered
    */
   protected Double offset = OFFSET_EDEFAULT;

   /**
    * The default value of the '{@link #getSide() <em>Side</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getSide()
    * @generated
    * @ordered
    */
   protected static final String SIDE_EDEFAULT = "left";

   /**
    * The cached value of the '{@link #getSide() <em>Side</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getSide()
    * @generated
    * @ordered
    */
   protected String side = SIDE_EDEFAULT;

   /**
    * The default value of the '{@link #isRotate() <em>Rotate</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #isRotate()
    * @generated
    * @ordered
    */
   protected static final boolean ROTATE_EDEFAULT = false;

   /**
    * The cached value of the '{@link #isRotate() <em>Rotate</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #isRotate()
    * @generated
    * @ordered
    */
   protected boolean rotate = ROTATE_EDEFAULT;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public GEdgePlacementImpl() {
      super();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   protected EClass eStaticClass() {
      return GraphPackage.Literals.GEDGE_PLACEMENT;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Double getPosition() { return position; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setPosition(Double newPosition) {
      Double oldPosition = position;
      position = newPosition;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GEDGE_PLACEMENT__POSITION, oldPosition,
            position));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Double getOffset() { return offset; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setOffset(Double newOffset) {
      Double oldOffset = offset;
      offset = newOffset;
      if (eNotificationRequired())
         eNotify(
            new ENotificationImpl(this, Notification.SET, GraphPackage.GEDGE_PLACEMENT__OFFSET, oldOffset, offset));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public String getSide() { return side; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setSide(String newSide) {
      String oldSide = side;
      side = newSide;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GEDGE_PLACEMENT__SIDE, oldSide, side));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public boolean isRotate() { return rotate; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setRotate(boolean newRotate) {
      boolean oldRotate = rotate;
      rotate = newRotate;
      if (eNotificationRequired())
         eNotify(
            new ENotificationImpl(this, Notification.SET, GraphPackage.GEDGE_PLACEMENT__ROTATE, oldRotate, rotate));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Object eGet(int featureID, boolean resolve, boolean coreType) {
      switch (featureID) {
         case GraphPackage.GEDGE_PLACEMENT__POSITION:
            return getPosition();
         case GraphPackage.GEDGE_PLACEMENT__OFFSET:
            return getOffset();
         case GraphPackage.GEDGE_PLACEMENT__SIDE:
            return getSide();
         case GraphPackage.GEDGE_PLACEMENT__ROTATE:
            return isRotate();
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
         case GraphPackage.GEDGE_PLACEMENT__POSITION:
            setPosition((Double) newValue);
            return;
         case GraphPackage.GEDGE_PLACEMENT__OFFSET:
            setOffset((Double) newValue);
            return;
         case GraphPackage.GEDGE_PLACEMENT__SIDE:
            setSide((String) newValue);
            return;
         case GraphPackage.GEDGE_PLACEMENT__ROTATE:
            setRotate((Boolean) newValue);
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
         case GraphPackage.GEDGE_PLACEMENT__POSITION:
            setPosition(POSITION_EDEFAULT);
            return;
         case GraphPackage.GEDGE_PLACEMENT__OFFSET:
            setOffset(OFFSET_EDEFAULT);
            return;
         case GraphPackage.GEDGE_PLACEMENT__SIDE:
            setSide(SIDE_EDEFAULT);
            return;
         case GraphPackage.GEDGE_PLACEMENT__ROTATE:
            setRotate(ROTATE_EDEFAULT);
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
         case GraphPackage.GEDGE_PLACEMENT__POSITION:
            return POSITION_EDEFAULT == null ? position != null : !POSITION_EDEFAULT.equals(position);
         case GraphPackage.GEDGE_PLACEMENT__OFFSET:
            return OFFSET_EDEFAULT == null ? offset != null : !OFFSET_EDEFAULT.equals(offset);
         case GraphPackage.GEDGE_PLACEMENT__SIDE:
            return SIDE_EDEFAULT == null ? side != null : !SIDE_EDEFAULT.equals(side);
         case GraphPackage.GEDGE_PLACEMENT__ROTATE:
            return rotate != ROTATE_EDEFAULT;
      }
      return super.eIsSet(featureID);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public String toString() {
      if (eIsProxy())
         return super.toString();

      StringBuilder result = new StringBuilder(super.toString());
      result.append(" (position: ");
      result.append(position);
      result.append(", offset: ");
      result.append(offset);
      result.append(", side: ");
      result.append(side);
      result.append(", rotate: ");
      result.append(rotate);
      result.append(')');
      return result.toString();
   }

} //GEdgePlacementImpl
