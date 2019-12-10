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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GPreRenderedElement;
import org.eclipse.glsp.graph.GraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>GPre Rendered Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.glsp.graph.impl.GPreRenderedElementImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GPreRenderedElementImpl#getCssClasses <em>Css Classes</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GPreRenderedElementImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GPreRenderedElementImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GPreRenderedElementImpl#getTrace <em>Trace</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GPreRenderedElementImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GPreRenderedElementImpl#getCode <em>Code</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GPreRenderedElementImpl extends MinimalEObjectImpl.Container implements GPreRenderedElement {
   /**
    * The default value of the '{@link #getId() <em>Id</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getId()
    * @generated
    * @ordered
    */
   protected static final String ID_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getId()
    * @generated
    * @ordered
    */
   protected String id = ID_EDEFAULT;

   /**
    * The cached value of the '{@link #getCssClasses() <em>Css Classes</em>}' attribute list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getCssClasses()
    * @generated
    * @ordered
    */
   protected EList<String> cssClasses;

   /**
    * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getChildren()
    * @generated
    * @ordered
    */
   protected EList<GModelElement> children;

   /**
    * The default value of the '{@link #getTrace() <em>Trace</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getTrace()
    * @generated
    * @ordered
    */
   protected static final String TRACE_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getTrace() <em>Trace</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getTrace()
    * @generated
    * @ordered
    */
   protected String trace = TRACE_EDEFAULT;

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
    * The default value of the '{@link #getCode() <em>Code</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getCode()
    * @generated
    * @ordered
    */
   protected static final String CODE_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getCode() <em>Code</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getCode()
    * @generated
    * @ordered
    */
   protected String code = CODE_EDEFAULT;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public GPreRenderedElementImpl() {
      super();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   protected EClass eStaticClass() {
      return GraphPackage.Literals.GPRE_RENDERED_ELEMENT;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public String getId() { return id; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setId(String newId) {
      String oldId = id;
      id = newId;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GPRE_RENDERED_ELEMENT__ID, oldId, id));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EList<String> getCssClasses() {
      if (cssClasses == null) {
         cssClasses = new EDataTypeUniqueEList<String>(String.class, this,
            GraphPackage.GPRE_RENDERED_ELEMENT__CSS_CLASSES);
      }
      return cssClasses;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EList<GModelElement> getChildren() {
      if (children == null) {
         children = new EObjectContainmentWithInverseEList<GModelElement>(GModelElement.class, this,
            GraphPackage.GPRE_RENDERED_ELEMENT__CHILDREN, GraphPackage.GMODEL_ELEMENT__PARENT);
      }
      return children;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GModelElement getParent() {
      if (eContainerFeatureID() != GraphPackage.GPRE_RENDERED_ELEMENT__PARENT)
         return null;
      return (GModelElement) eInternalContainer();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public NotificationChain basicSetParent(GModelElement newParent, NotificationChain msgs) {
      msgs = eBasicSetContainer((InternalEObject) newParent, GraphPackage.GPRE_RENDERED_ELEMENT__PARENT, msgs);
      return msgs;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setParent(GModelElement newParent) {
      if (newParent != eInternalContainer()
         || (eContainerFeatureID() != GraphPackage.GPRE_RENDERED_ELEMENT__PARENT && newParent != null)) {
         if (EcoreUtil.isAncestor(this, newParent))
            throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
         NotificationChain msgs = null;
         if (eInternalContainer() != null)
            msgs = eBasicRemoveFromContainer(msgs);
         if (newParent != null)
            msgs = ((InternalEObject) newParent).eInverseAdd(this, GraphPackage.GMODEL_ELEMENT__CHILDREN,
               GModelElement.class, msgs);
         msgs = basicSetParent(newParent, msgs);
         if (msgs != null)
            msgs.dispatch();
      } else if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GPRE_RENDERED_ELEMENT__PARENT, newParent,
            newParent));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public String getTrace() { return trace; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setTrace(String newTrace) {
      String oldTrace = trace;
      trace = newTrace;
      if (eNotificationRequired())
         eNotify(
            new ENotificationImpl(this, Notification.SET, GraphPackage.GPRE_RENDERED_ELEMENT__TRACE, oldTrace, trace));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public String getType() { return type; }

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
         eNotify(
            new ENotificationImpl(this, Notification.SET, GraphPackage.GPRE_RENDERED_ELEMENT__TYPE, oldType, type));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public String getCode() { return code; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setCode(String newCode) {
      String oldCode = code;
      code = newCode;
      if (eNotificationRequired())
         eNotify(
            new ENotificationImpl(this, Notification.SET, GraphPackage.GPRE_RENDERED_ELEMENT__CODE, oldCode, code));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @SuppressWarnings("unchecked")
   @Override
   public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
      switch (featureID) {
         case GraphPackage.GPRE_RENDERED_ELEMENT__CHILDREN:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getChildren()).basicAdd(otherEnd, msgs);
         case GraphPackage.GPRE_RENDERED_ELEMENT__PARENT:
            if (eInternalContainer() != null)
               msgs = eBasicRemoveFromContainer(msgs);
            return basicSetParent((GModelElement) otherEnd, msgs);
      }
      return super.eInverseAdd(otherEnd, featureID, msgs);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
      switch (featureID) {
         case GraphPackage.GPRE_RENDERED_ELEMENT__CHILDREN:
            return ((InternalEList<?>) getChildren()).basicRemove(otherEnd, msgs);
         case GraphPackage.GPRE_RENDERED_ELEMENT__PARENT:
            return basicSetParent(null, msgs);
      }
      return super.eInverseRemove(otherEnd, featureID, msgs);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
      switch (eContainerFeatureID()) {
         case GraphPackage.GPRE_RENDERED_ELEMENT__PARENT:
            return eInternalContainer().eInverseRemove(this, GraphPackage.GMODEL_ELEMENT__CHILDREN, GModelElement.class,
               msgs);
      }
      return super.eBasicRemoveFromContainerFeature(msgs);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Object eGet(int featureID, boolean resolve, boolean coreType) {
      switch (featureID) {
         case GraphPackage.GPRE_RENDERED_ELEMENT__ID:
            return getId();
         case GraphPackage.GPRE_RENDERED_ELEMENT__CSS_CLASSES:
            return getCssClasses();
         case GraphPackage.GPRE_RENDERED_ELEMENT__CHILDREN:
            return getChildren();
         case GraphPackage.GPRE_RENDERED_ELEMENT__PARENT:
            return getParent();
         case GraphPackage.GPRE_RENDERED_ELEMENT__TRACE:
            return getTrace();
         case GraphPackage.GPRE_RENDERED_ELEMENT__TYPE:
            return getType();
         case GraphPackage.GPRE_RENDERED_ELEMENT__CODE:
            return getCode();
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
         case GraphPackage.GPRE_RENDERED_ELEMENT__ID:
            setId((String) newValue);
            return;
         case GraphPackage.GPRE_RENDERED_ELEMENT__CSS_CLASSES:
            getCssClasses().clear();
            getCssClasses().addAll((Collection<? extends String>) newValue);
            return;
         case GraphPackage.GPRE_RENDERED_ELEMENT__CHILDREN:
            getChildren().clear();
            getChildren().addAll((Collection<? extends GModelElement>) newValue);
            return;
         case GraphPackage.GPRE_RENDERED_ELEMENT__PARENT:
            setParent((GModelElement) newValue);
            return;
         case GraphPackage.GPRE_RENDERED_ELEMENT__TRACE:
            setTrace((String) newValue);
            return;
         case GraphPackage.GPRE_RENDERED_ELEMENT__TYPE:
            setType((String) newValue);
            return;
         case GraphPackage.GPRE_RENDERED_ELEMENT__CODE:
            setCode((String) newValue);
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
         case GraphPackage.GPRE_RENDERED_ELEMENT__ID:
            setId(ID_EDEFAULT);
            return;
         case GraphPackage.GPRE_RENDERED_ELEMENT__CSS_CLASSES:
            getCssClasses().clear();
            return;
         case GraphPackage.GPRE_RENDERED_ELEMENT__CHILDREN:
            getChildren().clear();
            return;
         case GraphPackage.GPRE_RENDERED_ELEMENT__PARENT:
            setParent((GModelElement) null);
            return;
         case GraphPackage.GPRE_RENDERED_ELEMENT__TRACE:
            setTrace(TRACE_EDEFAULT);
            return;
         case GraphPackage.GPRE_RENDERED_ELEMENT__TYPE:
            setType(TYPE_EDEFAULT);
            return;
         case GraphPackage.GPRE_RENDERED_ELEMENT__CODE:
            setCode(CODE_EDEFAULT);
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
         case GraphPackage.GPRE_RENDERED_ELEMENT__ID:
            return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
         case GraphPackage.GPRE_RENDERED_ELEMENT__CSS_CLASSES:
            return cssClasses != null && !cssClasses.isEmpty();
         case GraphPackage.GPRE_RENDERED_ELEMENT__CHILDREN:
            return children != null && !children.isEmpty();
         case GraphPackage.GPRE_RENDERED_ELEMENT__PARENT:
            return getParent() != null;
         case GraphPackage.GPRE_RENDERED_ELEMENT__TRACE:
            return TRACE_EDEFAULT == null ? trace != null : !TRACE_EDEFAULT.equals(trace);
         case GraphPackage.GPRE_RENDERED_ELEMENT__TYPE:
            return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
         case GraphPackage.GPRE_RENDERED_ELEMENT__CODE:
            return CODE_EDEFAULT == null ? code != null : !CODE_EDEFAULT.equals(code);
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
      result.append(" (id: ");
      result.append(id);
      result.append(", cssClasses: ");
      result.append(cssClasses);
      result.append(", trace: ");
      result.append(trace);
      result.append(", type: ");
      result.append(type);
      result.append(", code: ");
      result.append(code);
      result.append(')');
      return result.toString();
   }

} //GPreRenderedElementImpl
