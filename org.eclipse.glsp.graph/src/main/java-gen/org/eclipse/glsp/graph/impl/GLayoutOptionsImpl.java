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

import org.eclipse.glsp.graph.GLayoutOptions;
import org.eclipse.glsp.graph.GraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>GLayout Options</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.glsp.graph.impl.GLayoutOptionsImpl#getPaddingLeft <em>Padding Left</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GLayoutOptionsImpl#getPaddingRight <em>Padding Right</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GLayoutOptionsImpl#getPaddingTop <em>Padding Top</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GLayoutOptionsImpl#getPaddingBottom <em>Padding Bottom</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GLayoutOptionsImpl#getPaddingFactor <em>Padding Factor</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GLayoutOptionsImpl#isResizeContainer <em>Resize Container</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GLayoutOptionsImpl#getVGap <em>VGap</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GLayoutOptionsImpl#getHGap <em>HGap</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GLayoutOptionsImpl#getVAlign <em>VAlign</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GLayoutOptionsImpl#getHAlign <em>HAlign</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GLayoutOptionsImpl#getMinWidth <em>Min Width</em>}</li>
 *   <li>{@link org.eclipse.glsp.graph.impl.GLayoutOptionsImpl#getMinHeight <em>Min Height</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GLayoutOptionsImpl extends MinimalEObjectImpl.Container implements GLayoutOptions {
   /**
    * The default value of the '{@link #getPaddingLeft() <em>Padding Left</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getPaddingLeft()
    * @generated
    * @ordered
    */
   protected static final Double PADDING_LEFT_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getPaddingLeft() <em>Padding Left</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getPaddingLeft()
    * @generated
    * @ordered
    */
   protected Double paddingLeft = PADDING_LEFT_EDEFAULT;

   /**
    * The default value of the '{@link #getPaddingRight() <em>Padding Right</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getPaddingRight()
    * @generated
    * @ordered
    */
   protected static final Double PADDING_RIGHT_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getPaddingRight() <em>Padding Right</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getPaddingRight()
    * @generated
    * @ordered
    */
   protected Double paddingRight = PADDING_RIGHT_EDEFAULT;

   /**
    * The default value of the '{@link #getPaddingTop() <em>Padding Top</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getPaddingTop()
    * @generated
    * @ordered
    */
   protected static final Double PADDING_TOP_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getPaddingTop() <em>Padding Top</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getPaddingTop()
    * @generated
    * @ordered
    */
   protected Double paddingTop = PADDING_TOP_EDEFAULT;

   /**
    * The default value of the '{@link #getPaddingBottom() <em>Padding Bottom</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getPaddingBottom()
    * @generated
    * @ordered
    */
   protected static final Double PADDING_BOTTOM_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getPaddingBottom() <em>Padding Bottom</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getPaddingBottom()
    * @generated
    * @ordered
    */
   protected Double paddingBottom = PADDING_BOTTOM_EDEFAULT;

   /**
    * The default value of the '{@link #getPaddingFactor() <em>Padding Factor</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getPaddingFactor()
    * @generated
    * @ordered
    */
   protected static final Double PADDING_FACTOR_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getPaddingFactor() <em>Padding Factor</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getPaddingFactor()
    * @generated
    * @ordered
    */
   protected Double paddingFactor = PADDING_FACTOR_EDEFAULT;

   /**
    * The default value of the '{@link #isResizeContainer() <em>Resize Container</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #isResizeContainer()
    * @generated
    * @ordered
    */
   protected static final boolean RESIZE_CONTAINER_EDEFAULT = false;

   /**
    * The cached value of the '{@link #isResizeContainer() <em>Resize Container</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #isResizeContainer()
    * @generated
    * @ordered
    */
   protected boolean resizeContainer = RESIZE_CONTAINER_EDEFAULT;

   /**
    * The default value of the '{@link #getVGap() <em>VGap</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getVGap()
    * @generated
    * @ordered
    */
   protected static final Double VGAP_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getVGap() <em>VGap</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getVGap()
    * @generated
    * @ordered
    */
   protected Double vGap = VGAP_EDEFAULT;

   /**
    * The default value of the '{@link #getHGap() <em>HGap</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getHGap()
    * @generated
    * @ordered
    */
   protected static final Double HGAP_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getHGap() <em>HGap</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getHGap()
    * @generated
    * @ordered
    */
   protected Double hGap = HGAP_EDEFAULT;

   /**
    * The default value of the '{@link #getVAlign() <em>VAlign</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getVAlign()
    * @generated
    * @ordered
    */
   protected static final String VALIGN_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getVAlign() <em>VAlign</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getVAlign()
    * @generated
    * @ordered
    */
   protected String vAlign = VALIGN_EDEFAULT;

   /**
    * The default value of the '{@link #getHAlign() <em>HAlign</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getHAlign()
    * @generated
    * @ordered
    */
   protected static final String HALIGN_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getHAlign() <em>HAlign</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getHAlign()
    * @generated
    * @ordered
    */
   protected String hAlign = HALIGN_EDEFAULT;

   /**
    * The default value of the '{@link #getMinWidth() <em>Min Width</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getMinWidth()
    * @generated
    * @ordered
    */
   protected static final Double MIN_WIDTH_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getMinWidth() <em>Min Width</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getMinWidth()
    * @generated
    * @ordered
    */
   protected Double minWidth = MIN_WIDTH_EDEFAULT;

   /**
    * The default value of the '{@link #getMinHeight() <em>Min Height</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getMinHeight()
    * @generated
    * @ordered
    */
   protected static final Double MIN_HEIGHT_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getMinHeight() <em>Min Height</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getMinHeight()
    * @generated
    * @ordered
    */
   protected Double minHeight = MIN_HEIGHT_EDEFAULT;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public GLayoutOptionsImpl() {
      super();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   protected EClass eStaticClass() {
      return GraphPackage.Literals.GLAYOUT_OPTIONS;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Double getPaddingLeft() { return paddingLeft; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setPaddingLeft(Double newPaddingLeft) {
      Double oldPaddingLeft = paddingLeft;
      paddingLeft = newPaddingLeft;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GLAYOUT_OPTIONS__PADDING_LEFT,
            oldPaddingLeft, paddingLeft));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Double getPaddingRight() { return paddingRight; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setPaddingRight(Double newPaddingRight) {
      Double oldPaddingRight = paddingRight;
      paddingRight = newPaddingRight;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GLAYOUT_OPTIONS__PADDING_RIGHT,
            oldPaddingRight, paddingRight));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Double getPaddingTop() { return paddingTop; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setPaddingTop(Double newPaddingTop) {
      Double oldPaddingTop = paddingTop;
      paddingTop = newPaddingTop;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GLAYOUT_OPTIONS__PADDING_TOP, oldPaddingTop,
            paddingTop));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Double getPaddingBottom() { return paddingBottom; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setPaddingBottom(Double newPaddingBottom) {
      Double oldPaddingBottom = paddingBottom;
      paddingBottom = newPaddingBottom;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GLAYOUT_OPTIONS__PADDING_BOTTOM,
            oldPaddingBottom, paddingBottom));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Double getPaddingFactor() { return paddingFactor; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setPaddingFactor(Double newPaddingFactor) {
      Double oldPaddingFactor = paddingFactor;
      paddingFactor = newPaddingFactor;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GLAYOUT_OPTIONS__PADDING_FACTOR,
            oldPaddingFactor, paddingFactor));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public boolean isResizeContainer() { return resizeContainer; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setResizeContainer(boolean newResizeContainer) {
      boolean oldResizeContainer = resizeContainer;
      resizeContainer = newResizeContainer;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GLAYOUT_OPTIONS__RESIZE_CONTAINER,
            oldResizeContainer, resizeContainer));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Double getVGap() { return vGap; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setVGap(Double newVGap) {
      Double oldVGap = vGap;
      vGap = newVGap;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GLAYOUT_OPTIONS__VGAP, oldVGap, vGap));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Double getHGap() { return hGap; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setHGap(Double newHGap) {
      Double oldHGap = hGap;
      hGap = newHGap;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GLAYOUT_OPTIONS__HGAP, oldHGap, hGap));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public String getVAlign() { return vAlign; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setVAlign(String newVAlign) {
      String oldVAlign = vAlign;
      vAlign = newVAlign;
      if (eNotificationRequired())
         eNotify(
            new ENotificationImpl(this, Notification.SET, GraphPackage.GLAYOUT_OPTIONS__VALIGN, oldVAlign, vAlign));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public String getHAlign() { return hAlign; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setHAlign(String newHAlign) {
      String oldHAlign = hAlign;
      hAlign = newHAlign;
      if (eNotificationRequired())
         eNotify(
            new ENotificationImpl(this, Notification.SET, GraphPackage.GLAYOUT_OPTIONS__HALIGN, oldHAlign, hAlign));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Double getMinWidth() { return minWidth; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setMinWidth(Double newMinWidth) {
      Double oldMinWidth = minWidth;
      minWidth = newMinWidth;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GLAYOUT_OPTIONS__MIN_WIDTH, oldMinWidth,
            minWidth));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Double getMinHeight() { return minHeight; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setMinHeight(Double newMinHeight) {
      Double oldMinHeight = minHeight;
      minHeight = newMinHeight;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GLAYOUT_OPTIONS__MIN_HEIGHT, oldMinHeight,
            minHeight));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Object eGet(int featureID, boolean resolve, boolean coreType) {
      switch (featureID) {
         case GraphPackage.GLAYOUT_OPTIONS__PADDING_LEFT:
            return getPaddingLeft();
         case GraphPackage.GLAYOUT_OPTIONS__PADDING_RIGHT:
            return getPaddingRight();
         case GraphPackage.GLAYOUT_OPTIONS__PADDING_TOP:
            return getPaddingTop();
         case GraphPackage.GLAYOUT_OPTIONS__PADDING_BOTTOM:
            return getPaddingBottom();
         case GraphPackage.GLAYOUT_OPTIONS__PADDING_FACTOR:
            return getPaddingFactor();
         case GraphPackage.GLAYOUT_OPTIONS__RESIZE_CONTAINER:
            return isResizeContainer();
         case GraphPackage.GLAYOUT_OPTIONS__VGAP:
            return getVGap();
         case GraphPackage.GLAYOUT_OPTIONS__HGAP:
            return getHGap();
         case GraphPackage.GLAYOUT_OPTIONS__VALIGN:
            return getVAlign();
         case GraphPackage.GLAYOUT_OPTIONS__HALIGN:
            return getHAlign();
         case GraphPackage.GLAYOUT_OPTIONS__MIN_WIDTH:
            return getMinWidth();
         case GraphPackage.GLAYOUT_OPTIONS__MIN_HEIGHT:
            return getMinHeight();
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
         case GraphPackage.GLAYOUT_OPTIONS__PADDING_LEFT:
            setPaddingLeft((Double) newValue);
            return;
         case GraphPackage.GLAYOUT_OPTIONS__PADDING_RIGHT:
            setPaddingRight((Double) newValue);
            return;
         case GraphPackage.GLAYOUT_OPTIONS__PADDING_TOP:
            setPaddingTop((Double) newValue);
            return;
         case GraphPackage.GLAYOUT_OPTIONS__PADDING_BOTTOM:
            setPaddingBottom((Double) newValue);
            return;
         case GraphPackage.GLAYOUT_OPTIONS__PADDING_FACTOR:
            setPaddingFactor((Double) newValue);
            return;
         case GraphPackage.GLAYOUT_OPTIONS__RESIZE_CONTAINER:
            setResizeContainer((Boolean) newValue);
            return;
         case GraphPackage.GLAYOUT_OPTIONS__VGAP:
            setVGap((Double) newValue);
            return;
         case GraphPackage.GLAYOUT_OPTIONS__HGAP:
            setHGap((Double) newValue);
            return;
         case GraphPackage.GLAYOUT_OPTIONS__VALIGN:
            setVAlign((String) newValue);
            return;
         case GraphPackage.GLAYOUT_OPTIONS__HALIGN:
            setHAlign((String) newValue);
            return;
         case GraphPackage.GLAYOUT_OPTIONS__MIN_WIDTH:
            setMinWidth((Double) newValue);
            return;
         case GraphPackage.GLAYOUT_OPTIONS__MIN_HEIGHT:
            setMinHeight((Double) newValue);
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
         case GraphPackage.GLAYOUT_OPTIONS__PADDING_LEFT:
            setPaddingLeft(PADDING_LEFT_EDEFAULT);
            return;
         case GraphPackage.GLAYOUT_OPTIONS__PADDING_RIGHT:
            setPaddingRight(PADDING_RIGHT_EDEFAULT);
            return;
         case GraphPackage.GLAYOUT_OPTIONS__PADDING_TOP:
            setPaddingTop(PADDING_TOP_EDEFAULT);
            return;
         case GraphPackage.GLAYOUT_OPTIONS__PADDING_BOTTOM:
            setPaddingBottom(PADDING_BOTTOM_EDEFAULT);
            return;
         case GraphPackage.GLAYOUT_OPTIONS__PADDING_FACTOR:
            setPaddingFactor(PADDING_FACTOR_EDEFAULT);
            return;
         case GraphPackage.GLAYOUT_OPTIONS__RESIZE_CONTAINER:
            setResizeContainer(RESIZE_CONTAINER_EDEFAULT);
            return;
         case GraphPackage.GLAYOUT_OPTIONS__VGAP:
            setVGap(VGAP_EDEFAULT);
            return;
         case GraphPackage.GLAYOUT_OPTIONS__HGAP:
            setHGap(HGAP_EDEFAULT);
            return;
         case GraphPackage.GLAYOUT_OPTIONS__VALIGN:
            setVAlign(VALIGN_EDEFAULT);
            return;
         case GraphPackage.GLAYOUT_OPTIONS__HALIGN:
            setHAlign(HALIGN_EDEFAULT);
            return;
         case GraphPackage.GLAYOUT_OPTIONS__MIN_WIDTH:
            setMinWidth(MIN_WIDTH_EDEFAULT);
            return;
         case GraphPackage.GLAYOUT_OPTIONS__MIN_HEIGHT:
            setMinHeight(MIN_HEIGHT_EDEFAULT);
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
         case GraphPackage.GLAYOUT_OPTIONS__PADDING_LEFT:
            return PADDING_LEFT_EDEFAULT == null ? paddingLeft != null : !PADDING_LEFT_EDEFAULT.equals(paddingLeft);
         case GraphPackage.GLAYOUT_OPTIONS__PADDING_RIGHT:
            return PADDING_RIGHT_EDEFAULT == null ? paddingRight != null : !PADDING_RIGHT_EDEFAULT.equals(paddingRight);
         case GraphPackage.GLAYOUT_OPTIONS__PADDING_TOP:
            return PADDING_TOP_EDEFAULT == null ? paddingTop != null : !PADDING_TOP_EDEFAULT.equals(paddingTop);
         case GraphPackage.GLAYOUT_OPTIONS__PADDING_BOTTOM:
            return PADDING_BOTTOM_EDEFAULT == null ? paddingBottom != null
               : !PADDING_BOTTOM_EDEFAULT.equals(paddingBottom);
         case GraphPackage.GLAYOUT_OPTIONS__PADDING_FACTOR:
            return PADDING_FACTOR_EDEFAULT == null ? paddingFactor != null
               : !PADDING_FACTOR_EDEFAULT.equals(paddingFactor);
         case GraphPackage.GLAYOUT_OPTIONS__RESIZE_CONTAINER:
            return resizeContainer != RESIZE_CONTAINER_EDEFAULT;
         case GraphPackage.GLAYOUT_OPTIONS__VGAP:
            return VGAP_EDEFAULT == null ? vGap != null : !VGAP_EDEFAULT.equals(vGap);
         case GraphPackage.GLAYOUT_OPTIONS__HGAP:
            return HGAP_EDEFAULT == null ? hGap != null : !HGAP_EDEFAULT.equals(hGap);
         case GraphPackage.GLAYOUT_OPTIONS__VALIGN:
            return VALIGN_EDEFAULT == null ? vAlign != null : !VALIGN_EDEFAULT.equals(vAlign);
         case GraphPackage.GLAYOUT_OPTIONS__HALIGN:
            return HALIGN_EDEFAULT == null ? hAlign != null : !HALIGN_EDEFAULT.equals(hAlign);
         case GraphPackage.GLAYOUT_OPTIONS__MIN_WIDTH:
            return MIN_WIDTH_EDEFAULT == null ? minWidth != null : !MIN_WIDTH_EDEFAULT.equals(minWidth);
         case GraphPackage.GLAYOUT_OPTIONS__MIN_HEIGHT:
            return MIN_HEIGHT_EDEFAULT == null ? minHeight != null : !MIN_HEIGHT_EDEFAULT.equals(minHeight);
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
      result.append(" (paddingLeft: ");
      result.append(paddingLeft);
      result.append(", paddingRight: ");
      result.append(paddingRight);
      result.append(", paddingTop: ");
      result.append(paddingTop);
      result.append(", paddingBottom: ");
      result.append(paddingBottom);
      result.append(", paddingFactor: ");
      result.append(paddingFactor);
      result.append(", resizeContainer: ");
      result.append(resizeContainer);
      result.append(", vGap: ");
      result.append(vGap);
      result.append(", hGap: ");
      result.append(hGap);
      result.append(", vAlign: ");
      result.append(vAlign);
      result.append(", hAlign: ");
      result.append(hAlign);
      result.append(", minWidth: ");
      result.append(minWidth);
      result.append(", minHeight: ");
      result.append(minHeight);
      result.append(')');
      return result.toString();
   }

} //GLayoutOptionsImpl
