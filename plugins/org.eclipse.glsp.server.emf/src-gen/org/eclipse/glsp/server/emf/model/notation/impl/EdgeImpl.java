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
package org.eclipse.glsp.server.emf.model.notation.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.glsp.graph.GPoint;

import org.eclipse.glsp.server.emf.model.notation.Edge;
import org.eclipse.glsp.server.emf.model.notation.NotationElement;
import org.eclipse.glsp.server.emf.model.notation.NotationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Edge</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.glsp.server.emf.model.notation.impl.EdgeImpl#getBendPoints <em>Bend Points</em>}</li>
 *   <li>{@link org.eclipse.glsp.server.emf.model.notation.impl.EdgeImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.glsp.server.emf.model.notation.impl.EdgeImpl#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EdgeImpl extends NotationElementImpl implements Edge {
   /**
    * The cached value of the '{@link #getBendPoints() <em>Bend Points</em>}' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getBendPoints()
    * @generated
    * @ordered
    */
   protected EList<GPoint> bendPoints;

   /**
    * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getSource()
    * @generated
    * @ordered
    */
   protected NotationElement source;

   /**
    * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getTarget()
    * @generated
    * @ordered
    */
   protected NotationElement target;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected EdgeImpl() {
      super();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   protected EClass eStaticClass() {
      return NotationPackage.Literals.EDGE;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EList<GPoint> getBendPoints() {
      if (bendPoints == null) {
         bendPoints = new EObjectContainmentEList<GPoint>(GPoint.class, this, NotationPackage.EDGE__BEND_POINTS);
      }
      return bendPoints;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public NotationElement getSource() {
      if (source != null && source.eIsProxy()) {
         InternalEObject oldSource = (InternalEObject)source;
         source = (NotationElement)eResolveProxy(oldSource);
         if (source != oldSource) {
            if (eNotificationRequired())
               eNotify(new ENotificationImpl(this, Notification.RESOLVE, NotationPackage.EDGE__SOURCE, oldSource, source));
         }
      }
      return source;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public NotationElement basicGetSource() {
      return source;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setSource(NotationElement newSource) {
      NotationElement oldSource = source;
      source = newSource;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, NotationPackage.EDGE__SOURCE, oldSource, source));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public NotationElement getTarget() {
      if (target != null && target.eIsProxy()) {
         InternalEObject oldTarget = (InternalEObject)target;
         target = (NotationElement)eResolveProxy(oldTarget);
         if (target != oldTarget) {
            if (eNotificationRequired())
               eNotify(new ENotificationImpl(this, Notification.RESOLVE, NotationPackage.EDGE__TARGET, oldTarget, target));
         }
      }
      return target;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public NotationElement basicGetTarget() {
      return target;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setTarget(NotationElement newTarget) {
      NotationElement oldTarget = target;
      target = newTarget;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, NotationPackage.EDGE__TARGET, oldTarget, target));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
      switch (featureID) {
         case NotationPackage.EDGE__BEND_POINTS:
            return ((InternalEList<?>)getBendPoints()).basicRemove(otherEnd, msgs);
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
         case NotationPackage.EDGE__BEND_POINTS:
            return getBendPoints();
         case NotationPackage.EDGE__SOURCE:
            if (resolve) return getSource();
            return basicGetSource();
         case NotationPackage.EDGE__TARGET:
            if (resolve) return getTarget();
            return basicGetTarget();
      }
      return super.eGet(featureID, resolve, coreType);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @SuppressWarnings("unchecked")
   @Override
   public void eSet(int featureID, Object newValue) {
      switch (featureID) {
         case NotationPackage.EDGE__BEND_POINTS:
            getBendPoints().clear();
            getBendPoints().addAll((Collection<? extends GPoint>)newValue);
            return;
         case NotationPackage.EDGE__SOURCE:
            setSource((NotationElement)newValue);
            return;
         case NotationPackage.EDGE__TARGET:
            setTarget((NotationElement)newValue);
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
         case NotationPackage.EDGE__BEND_POINTS:
            getBendPoints().clear();
            return;
         case NotationPackage.EDGE__SOURCE:
            setSource((NotationElement)null);
            return;
         case NotationPackage.EDGE__TARGET:
            setTarget((NotationElement)null);
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
         case NotationPackage.EDGE__BEND_POINTS:
            return bendPoints != null && !bendPoints.isEmpty();
         case NotationPackage.EDGE__SOURCE:
            return source != null;
         case NotationPackage.EDGE__TARGET:
            return target != null;
      }
      return super.eIsSet(featureID);
   }

} //EdgeImpl
