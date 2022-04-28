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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.glsp.server.emf.model.notation.NotationElement;
import org.eclipse.glsp.server.emf.model.notation.NotationPackage;
import org.eclipse.glsp.server.emf.model.notation.SemanticElementReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.glsp.server.emf.model.notation.impl.NotationElementImpl#getSemanticElement <em>Semantic Element</em>}</li>
 *   <li>{@link org.eclipse.glsp.server.emf.model.notation.impl.NotationElementImpl#getType <em>Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class NotationElementImpl extends MinimalEObjectImpl.Container implements NotationElement {
   /**
    * The cached value of the '{@link #getSemanticElement() <em>Semantic Element</em>}' containment reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getSemanticElement()
    * @generated
    * @ordered
    */
   protected SemanticElementReference semanticElement;

   /**
    * The default value of the '{@link #getType() <em>Type</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getType()
    * @generated
    * @ordered
    */
   protected static final String TYPE_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getType()
    * @generated
    * @ordered
    */
   protected String type = TYPE_EDEFAULT;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected NotationElementImpl() {
      super();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   protected EClass eStaticClass() {
      return NotationPackage.Literals.NOTATION_ELEMENT;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public SemanticElementReference getSemanticElement() {
      return semanticElement;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public NotificationChain basicSetSemanticElement(SemanticElementReference newSemanticElement, NotificationChain msgs) {
      SemanticElementReference oldSemanticElement = semanticElement;
      semanticElement = newSemanticElement;
      if (eNotificationRequired()) {
         ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, NotationPackage.NOTATION_ELEMENT__SEMANTIC_ELEMENT, oldSemanticElement, newSemanticElement);
         if (msgs == null) msgs = notification; else msgs.add(notification);
      }
      return msgs;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setSemanticElement(SemanticElementReference newSemanticElement) {
      if (newSemanticElement != semanticElement) {
         NotificationChain msgs = null;
         if (semanticElement != null)
            msgs = ((InternalEObject)semanticElement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - NotationPackage.NOTATION_ELEMENT__SEMANTIC_ELEMENT, null, msgs);
         if (newSemanticElement != null)
            msgs = ((InternalEObject)newSemanticElement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - NotationPackage.NOTATION_ELEMENT__SEMANTIC_ELEMENT, null, msgs);
         msgs = basicSetSemanticElement(newSemanticElement, msgs);
         if (msgs != null) msgs.dispatch();
      }
      else if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, NotationPackage.NOTATION_ELEMENT__SEMANTIC_ELEMENT, newSemanticElement, newSemanticElement));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public String getType() {
      return type;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setType(String newType) {
      String oldType = type;
      type = newType;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, NotationPackage.NOTATION_ELEMENT__TYPE, oldType, type));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
      switch (featureID) {
         case NotationPackage.NOTATION_ELEMENT__SEMANTIC_ELEMENT:
            return basicSetSemanticElement(null, msgs);
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
         case NotationPackage.NOTATION_ELEMENT__SEMANTIC_ELEMENT:
            return getSemanticElement();
         case NotationPackage.NOTATION_ELEMENT__TYPE:
            return getType();
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
         case NotationPackage.NOTATION_ELEMENT__SEMANTIC_ELEMENT:
            setSemanticElement((SemanticElementReference)newValue);
            return;
         case NotationPackage.NOTATION_ELEMENT__TYPE:
            setType((String)newValue);
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
         case NotationPackage.NOTATION_ELEMENT__SEMANTIC_ELEMENT:
            setSemanticElement((SemanticElementReference)null);
            return;
         case NotationPackage.NOTATION_ELEMENT__TYPE:
            setType(TYPE_EDEFAULT);
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
         case NotationPackage.NOTATION_ELEMENT__SEMANTIC_ELEMENT:
            return semanticElement != null;
         case NotationPackage.NOTATION_ELEMENT__TYPE:
            return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
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
      if (eIsProxy()) return super.toString();

      StringBuilder result = new StringBuilder(super.toString());
      result.append(" (type: ");
      result.append(type);
      result.append(')');
      return result.toString();
   }

} //NotationElementImpl
