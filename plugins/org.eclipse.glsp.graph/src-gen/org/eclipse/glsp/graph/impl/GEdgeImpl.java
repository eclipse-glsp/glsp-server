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
import java.util.Optional;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GModelIndex;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.GraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>GEdge</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.glsp.graph.impl.GEdgeImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GEdgeImpl#getCssClasses <em>Css Classes</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GEdgeImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GEdgeImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GEdgeImpl#getTrace <em>Trace</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GEdgeImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GEdgeImpl#getRoutingPoints <em>Routing Points</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GEdgeImpl#getSourceId <em>Source Id</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GEdgeImpl#getTargetId <em>Target Id</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GEdgeImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GEdgeImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GEdgeImpl#getRouterKind <em>Router Kind</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GEdgeImpl extends MinimalEObjectImpl.Container implements GEdge {
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
    * The cached value of the '{@link #getRoutingPoints() <em>Routing Points</em>}' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getRoutingPoints()
    * @generated
    * @ordered
    */
   protected EList<GPoint> routingPoints;

   /**
    * The default value of the '{@link #getSourceId() <em>Source Id</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getSourceId()
    * @generated
    * @ordered
    */
   protected static final String SOURCE_ID_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getSourceId() <em>Source Id</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getSourceId()
    * @generated
    * @ordered
    */
   protected String sourceId = SOURCE_ID_EDEFAULT;

   /**
    * The default value of the '{@link #getTargetId() <em>Target Id</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getTargetId()
    * @generated
    * @ordered
    */
   protected static final String TARGET_ID_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getTargetId() <em>Target Id</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getTargetId()
    * @generated
    * @ordered
    */
   protected String targetId = TARGET_ID_EDEFAULT;

   /**
    * The default value of the '{@link #getRouterKind() <em>Router Kind</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getRouterKind()
    * @generated
    * @ordered
    */
   protected static final String ROUTER_KIND_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getRouterKind() <em>Router Kind</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getRouterKind()
    * @generated
    * @ordered
    */
   protected String routerKind = ROUTER_KIND_EDEFAULT;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public GEdgeImpl() {
      super();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   protected EClass eStaticClass() {
      return GraphPackage.Literals.GEDGE;
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
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GEDGE__ID, oldId, id));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EList<String> getCssClasses() {
      if (cssClasses == null) {
         cssClasses = new EDataTypeUniqueEList<String>(String.class, this, GraphPackage.GEDGE__CSS_CLASSES);
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
            GraphPackage.GEDGE__CHILDREN, GraphPackage.GMODEL_ELEMENT__PARENT);
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
      if (eContainerFeatureID() != GraphPackage.GEDGE__PARENT)
         return null;
      return (GModelElement) eInternalContainer();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public NotificationChain basicSetParent(GModelElement newParent, NotificationChain msgs) {
      msgs = eBasicSetContainer((InternalEObject) newParent, GraphPackage.GEDGE__PARENT, msgs);
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
         || (eContainerFeatureID() != GraphPackage.GEDGE__PARENT && newParent != null)) {
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
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GEDGE__PARENT, newParent, newParent));
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
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GEDGE__TRACE, oldTrace, trace));
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
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GEDGE__TYPE, oldType, type));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EList<GPoint> getRoutingPoints() {
      if (routingPoints == null) {
         routingPoints = new EObjectContainmentEList<GPoint>(GPoint.class, this, GraphPackage.GEDGE__ROUTING_POINTS);
      }
      return routingPoints;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public String getSourceId() { return sourceId; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setSourceId(String newSourceId) {
      String oldSourceId = sourceId;
      sourceId = newSourceId;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GEDGE__SOURCE_ID, oldSourceId, sourceId));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public String getTargetId() { return targetId; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setTargetId(String newTargetId) {
      String oldTargetId = targetId;
      targetId = newTargetId;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GEDGE__TARGET_ID, oldTargetId, targetId));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GModelElement getSource() {
      GModelElement source = basicGetSource();
      return source != null && source.eIsProxy() ? (GModelElement) eResolveProxy((InternalEObject) source) : source;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    *
    * @generated NOT
    */
   public GModelElement basicGetSource() {
      return findElement(this.getSourceId()).orElse(null);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    *
    * @generated NOT
    */
   @Override
   public void setSource(final GModelElement newSource) {
      if (newSource == null) {
         this.sourceId = null;
         return;
      }
      this.sourceId = newSource.getId();
   }

   private Optional<GModelElement> findElement(final String elementId) {
      return GModelIndex.get(this).get(elementId);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GModelElement getTarget() {
      GModelElement target = basicGetTarget();
      return target != null && target.eIsProxy() ? (GModelElement) eResolveProxy((InternalEObject) target) : target;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    *
    * @generated NOT
    */
   public GModelElement basicGetTarget() {
      return findElement(this.getTargetId()).orElse(null);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    *
    * @generated NOT
    */
   @Override
   public void setTarget(final GModelElement newTarget) {
      if (newTarget == null) {
         this.targetId = null;
         return;
      }
      this.targetId = newTarget.getId();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public String getRouterKind() { return routerKind; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setRouterKind(String newRouterKind) {
      String oldRouterKind = routerKind;
      routerKind = newRouterKind;
      if (eNotificationRequired())
         eNotify(
            new ENotificationImpl(this, Notification.SET, GraphPackage.GEDGE__ROUTER_KIND, oldRouterKind, routerKind));
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
         case GraphPackage.GEDGE__CHILDREN:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getChildren()).basicAdd(otherEnd, msgs);
         case GraphPackage.GEDGE__PARENT:
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
         case GraphPackage.GEDGE__CHILDREN:
            return ((InternalEList<?>) getChildren()).basicRemove(otherEnd, msgs);
         case GraphPackage.GEDGE__PARENT:
            return basicSetParent(null, msgs);
         case GraphPackage.GEDGE__ROUTING_POINTS:
            return ((InternalEList<?>) getRoutingPoints()).basicRemove(otherEnd, msgs);
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
         case GraphPackage.GEDGE__PARENT:
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
         case GraphPackage.GEDGE__ID:
            return getId();
         case GraphPackage.GEDGE__CSS_CLASSES:
            return getCssClasses();
         case GraphPackage.GEDGE__CHILDREN:
            return getChildren();
         case GraphPackage.GEDGE__PARENT:
            return getParent();
         case GraphPackage.GEDGE__TRACE:
            return getTrace();
         case GraphPackage.GEDGE__TYPE:
            return getType();
         case GraphPackage.GEDGE__ROUTING_POINTS:
            return getRoutingPoints();
         case GraphPackage.GEDGE__SOURCE_ID:
            return getSourceId();
         case GraphPackage.GEDGE__TARGET_ID:
            return getTargetId();
         case GraphPackage.GEDGE__SOURCE:
            if (resolve)
               return getSource();
            return basicGetSource();
         case GraphPackage.GEDGE__TARGET:
            if (resolve)
               return getTarget();
            return basicGetTarget();
         case GraphPackage.GEDGE__ROUTER_KIND:
            return getRouterKind();
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
         case GraphPackage.GEDGE__ID:
            setId((String) newValue);
            return;
         case GraphPackage.GEDGE__CSS_CLASSES:
            getCssClasses().clear();
            getCssClasses().addAll((Collection<? extends String>) newValue);
            return;
         case GraphPackage.GEDGE__CHILDREN:
            getChildren().clear();
            getChildren().addAll((Collection<? extends GModelElement>) newValue);
            return;
         case GraphPackage.GEDGE__PARENT:
            setParent((GModelElement) newValue);
            return;
         case GraphPackage.GEDGE__TRACE:
            setTrace((String) newValue);
            return;
         case GraphPackage.GEDGE__TYPE:
            setType((String) newValue);
            return;
         case GraphPackage.GEDGE__ROUTING_POINTS:
            getRoutingPoints().clear();
            getRoutingPoints().addAll((Collection<? extends GPoint>) newValue);
            return;
         case GraphPackage.GEDGE__SOURCE_ID:
            setSourceId((String) newValue);
            return;
         case GraphPackage.GEDGE__TARGET_ID:
            setTargetId((String) newValue);
            return;
         case GraphPackage.GEDGE__SOURCE:
            setSource((GModelElement) newValue);
            return;
         case GraphPackage.GEDGE__TARGET:
            setTarget((GModelElement) newValue);
            return;
         case GraphPackage.GEDGE__ROUTER_KIND:
            setRouterKind((String) newValue);
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
         case GraphPackage.GEDGE__ID:
            setId(ID_EDEFAULT);
            return;
         case GraphPackage.GEDGE__CSS_CLASSES:
            getCssClasses().clear();
            return;
         case GraphPackage.GEDGE__CHILDREN:
            getChildren().clear();
            return;
         case GraphPackage.GEDGE__PARENT:
            setParent((GModelElement) null);
            return;
         case GraphPackage.GEDGE__TRACE:
            setTrace(TRACE_EDEFAULT);
            return;
         case GraphPackage.GEDGE__TYPE:
            setType(TYPE_EDEFAULT);
            return;
         case GraphPackage.GEDGE__ROUTING_POINTS:
            getRoutingPoints().clear();
            return;
         case GraphPackage.GEDGE__SOURCE_ID:
            setSourceId(SOURCE_ID_EDEFAULT);
            return;
         case GraphPackage.GEDGE__TARGET_ID:
            setTargetId(TARGET_ID_EDEFAULT);
            return;
         case GraphPackage.GEDGE__SOURCE:
            setSource((GModelElement) null);
            return;
         case GraphPackage.GEDGE__TARGET:
            setTarget((GModelElement) null);
            return;
         case GraphPackage.GEDGE__ROUTER_KIND:
            setRouterKind(ROUTER_KIND_EDEFAULT);
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
         case GraphPackage.GEDGE__ID:
            return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
         case GraphPackage.GEDGE__CSS_CLASSES:
            return cssClasses != null && !cssClasses.isEmpty();
         case GraphPackage.GEDGE__CHILDREN:
            return children != null && !children.isEmpty();
         case GraphPackage.GEDGE__PARENT:
            return getParent() != null;
         case GraphPackage.GEDGE__TRACE:
            return TRACE_EDEFAULT == null ? trace != null : !TRACE_EDEFAULT.equals(trace);
         case GraphPackage.GEDGE__TYPE:
            return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
         case GraphPackage.GEDGE__ROUTING_POINTS:
            return routingPoints != null && !routingPoints.isEmpty();
         case GraphPackage.GEDGE__SOURCE_ID:
            return SOURCE_ID_EDEFAULT == null ? sourceId != null : !SOURCE_ID_EDEFAULT.equals(sourceId);
         case GraphPackage.GEDGE__TARGET_ID:
            return TARGET_ID_EDEFAULT == null ? targetId != null : !TARGET_ID_EDEFAULT.equals(targetId);
         case GraphPackage.GEDGE__SOURCE:
            return basicGetSource() != null;
         case GraphPackage.GEDGE__TARGET:
            return basicGetTarget() != null;
         case GraphPackage.GEDGE__ROUTER_KIND:
            return ROUTER_KIND_EDEFAULT == null ? routerKind != null : !ROUTER_KIND_EDEFAULT.equals(routerKind);
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
      result.append(", sourceId: ");
      result.append(sourceId);
      result.append(", targetId: ");
      result.append(targetId);
      result.append(", routerKind: ");
      result.append(routerKind);
      result.append(')');
      return result.toString();
   }

} // GEdgeImpl
