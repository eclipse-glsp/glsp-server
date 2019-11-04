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

import org.eclipse.glsp.graph.GIssue;
import org.eclipse.glsp.graph.GSeverity;
import org.eclipse.glsp.graph.GraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>GIssue</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.glsp.graph.impl.GIssueImpl#getSeverity <em>Severity</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GIssueImpl#getMessage <em>Message</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GIssueImpl extends MinimalEObjectImpl.Container implements GIssue {
   /**
    * The default value of the '{@link #getSeverity() <em>Severity</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getSeverity()
    * @generated
    * @ordered
    */
   protected static final GSeverity SEVERITY_EDEFAULT = GSeverity.INFO;

   /**
    * The cached value of the '{@link #getSeverity() <em>Severity</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getSeverity()
    * @generated
    * @ordered
    */
   protected GSeverity severity = SEVERITY_EDEFAULT;

   /**
    * The default value of the '{@link #getMessage() <em>Message</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getMessage()
    * @generated
    * @ordered
    */
   protected static final String MESSAGE_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getMessage() <em>Message</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getMessage()
    * @generated
    * @ordered
    */
   protected String message = MESSAGE_EDEFAULT;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public GIssueImpl() {
      super();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   protected EClass eStaticClass() {
      return GraphPackage.Literals.GISSUE;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GSeverity getSeverity() { return severity; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setSeverity(GSeverity newSeverity) {
      GSeverity oldSeverity = severity;
      severity = newSeverity == null ? SEVERITY_EDEFAULT : newSeverity;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GISSUE__SEVERITY, oldSeverity, severity));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public String getMessage() { return message; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setMessage(String newMessage) {
      String oldMessage = message;
      message = newMessage;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GISSUE__MESSAGE, oldMessage, message));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Object eGet(int featureID, boolean resolve, boolean coreType) {
      switch (featureID) {
         case GraphPackage.GISSUE__SEVERITY:
            return getSeverity();
         case GraphPackage.GISSUE__MESSAGE:
            return getMessage();
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
         case GraphPackage.GISSUE__SEVERITY:
            setSeverity((GSeverity) newValue);
            return;
         case GraphPackage.GISSUE__MESSAGE:
            setMessage((String) newValue);
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
         case GraphPackage.GISSUE__SEVERITY:
            setSeverity(SEVERITY_EDEFAULT);
            return;
         case GraphPackage.GISSUE__MESSAGE:
            setMessage(MESSAGE_EDEFAULT);
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
         case GraphPackage.GISSUE__SEVERITY:
            return severity != SEVERITY_EDEFAULT;
         case GraphPackage.GISSUE__MESSAGE:
            return MESSAGE_EDEFAULT == null ? message != null : !MESSAGE_EDEFAULT.equals(message);
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
      result.append(" (severity: ");
      result.append(severity);
      result.append(", message: ");
      result.append(message);
      result.append(')');
      return result.toString();
   }

} //GIssueImpl
