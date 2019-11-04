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

import org.eclipse.glsp.graph.GBounds;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.graph.GraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>GModel Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.glsp.graph.impl.GModelRootImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GModelRootImpl#getCssClasses <em>Css Classes</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GModelRootImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GModelRootImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GModelRootImpl#getTrace <em>Trace</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GModelRootImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GModelRootImpl#getCanvasBounds <em>Canvas Bounds</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GModelRootImpl#getRevision <em>Revision</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GModelRootImpl extends MinimalEObjectImpl.Container implements GModelRoot {
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
    * The cached value of the '{@link #getCanvasBounds() <em>Canvas Bounds</em>}' containment reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getCanvasBounds()
    * @generated
    * @ordered
    */
   protected GBounds canvasBounds;

   /**
    * The default value of the '{@link #getRevision() <em>Revision</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getRevision()
    * @generated
    * @ordered
    */
   protected static final int REVISION_EDEFAULT = 0;

   /**
    * The cached value of the '{@link #getRevision() <em>Revision</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getRevision()
    * @generated
    * @ordered
    */
   protected int revision = REVISION_EDEFAULT;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public GModelRootImpl() {
      super();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   protected EClass eStaticClass() {
      return GraphPackage.Literals.GMODEL_ROOT;
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
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GMODEL_ROOT__ID, oldId, id));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EList<String> getCssClasses() {
      if (cssClasses == null) {
         cssClasses = new EDataTypeUniqueEList<String>(String.class, this, GraphPackage.GMODEL_ROOT__CSS_CLASSES);
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
            GraphPackage.GMODEL_ROOT__CHILDREN, GraphPackage.GMODEL_ELEMENT__PARENT);
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
      if (eContainerFeatureID() != GraphPackage.GMODEL_ROOT__PARENT)
         return null;
      return (GModelElement) eInternalContainer();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public NotificationChain basicSetParent(GModelElement newParent, NotificationChain msgs) {
      msgs = eBasicSetContainer((InternalEObject) newParent, GraphPackage.GMODEL_ROOT__PARENT, msgs);
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
         || (eContainerFeatureID() != GraphPackage.GMODEL_ROOT__PARENT && newParent != null)) {
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
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GMODEL_ROOT__PARENT, newParent, newParent));
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
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GMODEL_ROOT__TRACE, oldTrace, trace));
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
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GMODEL_ROOT__TYPE, oldType, type));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GBounds getCanvasBounds() { return canvasBounds; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public NotificationChain basicSetCanvasBounds(GBounds newCanvasBounds, NotificationChain msgs) {
      GBounds oldCanvasBounds = canvasBounds;
      canvasBounds = newCanvasBounds;
      if (eNotificationRequired()) {
         ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
            GraphPackage.GMODEL_ROOT__CANVAS_BOUNDS, oldCanvasBounds, newCanvasBounds);
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
   public void setCanvasBounds(GBounds newCanvasBounds) {
      if (newCanvasBounds != canvasBounds) {
         NotificationChain msgs = null;
         if (canvasBounds != null)
            msgs = ((InternalEObject) canvasBounds).eInverseRemove(this,
               EOPPOSITE_FEATURE_BASE - GraphPackage.GMODEL_ROOT__CANVAS_BOUNDS, null, msgs);
         if (newCanvasBounds != null)
            msgs = ((InternalEObject) newCanvasBounds).eInverseAdd(this,
               EOPPOSITE_FEATURE_BASE - GraphPackage.GMODEL_ROOT__CANVAS_BOUNDS, null, msgs);
         msgs = basicSetCanvasBounds(newCanvasBounds, msgs);
         if (msgs != null)
            msgs.dispatch();
      } else if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GMODEL_ROOT__CANVAS_BOUNDS, newCanvasBounds,
            newCanvasBounds));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public int getRevision() { return revision; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setRevision(int newRevision) {
      int oldRevision = revision;
      revision = newRevision;
      if (eNotificationRequired())
         eNotify(
            new ENotificationImpl(this, Notification.SET, GraphPackage.GMODEL_ROOT__REVISION, oldRevision, revision));
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
         case GraphPackage.GMODEL_ROOT__CHILDREN:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getChildren()).basicAdd(otherEnd, msgs);
         case GraphPackage.GMODEL_ROOT__PARENT:
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
         case GraphPackage.GMODEL_ROOT__CHILDREN:
            return ((InternalEList<?>) getChildren()).basicRemove(otherEnd, msgs);
         case GraphPackage.GMODEL_ROOT__PARENT:
            return basicSetParent(null, msgs);
         case GraphPackage.GMODEL_ROOT__CANVAS_BOUNDS:
            return basicSetCanvasBounds(null, msgs);
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
         case GraphPackage.GMODEL_ROOT__PARENT:
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
         case GraphPackage.GMODEL_ROOT__ID:
            return getId();
         case GraphPackage.GMODEL_ROOT__CSS_CLASSES:
            return getCssClasses();
         case GraphPackage.GMODEL_ROOT__CHILDREN:
            return getChildren();
         case GraphPackage.GMODEL_ROOT__PARENT:
            return getParent();
         case GraphPackage.GMODEL_ROOT__TRACE:
            return getTrace();
         case GraphPackage.GMODEL_ROOT__TYPE:
            return getType();
         case GraphPackage.GMODEL_ROOT__CANVAS_BOUNDS:
            return getCanvasBounds();
         case GraphPackage.GMODEL_ROOT__REVISION:
            return getRevision();
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
         case GraphPackage.GMODEL_ROOT__ID:
            setId((String) newValue);
            return;
         case GraphPackage.GMODEL_ROOT__CSS_CLASSES:
            getCssClasses().clear();
            getCssClasses().addAll((Collection<? extends String>) newValue);
            return;
         case GraphPackage.GMODEL_ROOT__CHILDREN:
            getChildren().clear();
            getChildren().addAll((Collection<? extends GModelElement>) newValue);
            return;
         case GraphPackage.GMODEL_ROOT__PARENT:
            setParent((GModelElement) newValue);
            return;
         case GraphPackage.GMODEL_ROOT__TRACE:
            setTrace((String) newValue);
            return;
         case GraphPackage.GMODEL_ROOT__TYPE:
            setType((String) newValue);
            return;
         case GraphPackage.GMODEL_ROOT__CANVAS_BOUNDS:
            setCanvasBounds((GBounds) newValue);
            return;
         case GraphPackage.GMODEL_ROOT__REVISION:
            setRevision((Integer) newValue);
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
         case GraphPackage.GMODEL_ROOT__ID:
            setId(ID_EDEFAULT);
            return;
         case GraphPackage.GMODEL_ROOT__CSS_CLASSES:
            getCssClasses().clear();
            return;
         case GraphPackage.GMODEL_ROOT__CHILDREN:
            getChildren().clear();
            return;
         case GraphPackage.GMODEL_ROOT__PARENT:
            setParent((GModelElement) null);
            return;
         case GraphPackage.GMODEL_ROOT__TRACE:
            setTrace(TRACE_EDEFAULT);
            return;
         case GraphPackage.GMODEL_ROOT__TYPE:
            setType(TYPE_EDEFAULT);
            return;
         case GraphPackage.GMODEL_ROOT__CANVAS_BOUNDS:
            setCanvasBounds((GBounds) null);
            return;
         case GraphPackage.GMODEL_ROOT__REVISION:
            setRevision(REVISION_EDEFAULT);
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
         case GraphPackage.GMODEL_ROOT__ID:
            return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
         case GraphPackage.GMODEL_ROOT__CSS_CLASSES:
            return cssClasses != null && !cssClasses.isEmpty();
         case GraphPackage.GMODEL_ROOT__CHILDREN:
            return children != null && !children.isEmpty();
         case GraphPackage.GMODEL_ROOT__PARENT:
            return getParent() != null;
         case GraphPackage.GMODEL_ROOT__TRACE:
            return TRACE_EDEFAULT == null ? trace != null : !TRACE_EDEFAULT.equals(trace);
         case GraphPackage.GMODEL_ROOT__TYPE:
            return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
         case GraphPackage.GMODEL_ROOT__CANVAS_BOUNDS:
            return canvasBounds != null;
         case GraphPackage.GMODEL_ROOT__REVISION:
            return revision != REVISION_EDEFAULT;
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
      result.append(", revision: ");
      result.append(revision);
      result.append(')');
      return result.toString();
   }

} //GModelRootImpl
