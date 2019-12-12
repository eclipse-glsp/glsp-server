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

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.glsp.graph.GAlignable;
import org.eclipse.glsp.graph.GBounds;
import org.eclipse.glsp.graph.GBoundsAware;
import org.eclipse.glsp.graph.GButton;
import org.eclipse.glsp.graph.GCompartment;
import org.eclipse.glsp.graph.GDimension;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GEdgeLayoutable;
import org.eclipse.glsp.graph.GEdgePlacement;
import org.eclipse.glsp.graph.GGraph;
import org.eclipse.glsp.graph.GHtmlRoot;
import org.eclipse.glsp.graph.GIssue;
import org.eclipse.glsp.graph.GIssueMarker;
import org.eclipse.glsp.graph.GLabel;
import org.eclipse.glsp.graph.GLayouting;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.GPort;
import org.eclipse.glsp.graph.GPreRenderedElement;
import org.eclipse.glsp.graph.GSeverity;
import org.eclipse.glsp.graph.GShapeElement;
import org.eclipse.glsp.graph.GraphFactory;
import org.eclipse.glsp.graph.GraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class GraphPackageImpl extends EPackageImpl implements GraphPackage {
   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass gModelElementEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass gShapeElementEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass gGraphEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass gModelRootEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass gNodeEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass gEdgeEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass gCompartmentEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass gLabelEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass gIssueMarkerEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass gPortEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass gButtonEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass gBoundsAwareEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass gPointEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass gDimensionEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass gEdgeLayoutableEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass gEdgePlacementEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass gLayoutingEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass gBoundsEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass gAlignableEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass gIssueEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass gHtmlRootEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass gPreRenderedElementEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EClass stringToObjectMapEntryEClass = null;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private EEnum gSeverityEEnum = null;

   /**
    * Creates an instance of the model <b>Package</b>, registered with
    * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
    * package URI value.
    * <p>Note: the correct way to create the package is via the static
    * factory method {@link #init init()}, which also performs
    * initialization of the package, or returns the registered package,
    * if one already exists.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see org.eclipse.emf.ecore.EPackage.Registry
    * @see org.eclipse.glsp.graph.GraphPackage#eNS_URI
    * @see #init()
    * @generated
    */
   private GraphPackageImpl() {
      super(eNS_URI, GraphFactory.eINSTANCE);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private static boolean isInited = false;

   /**
    * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
    *
    * <p>This method is used to initialize {@link GraphPackage#eINSTANCE} when that field is accessed.
    * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #eNS_URI
    * @see #createPackageContents()
    * @see #initializePackageContents()
    * @generated
    */
   public static GraphPackage init() {
      if (isInited)
         return (GraphPackage) EPackage.Registry.INSTANCE.getEPackage(GraphPackage.eNS_URI);

      // Obtain or create and register package
      Object registeredGraphPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
      GraphPackageImpl theGraphPackage = registeredGraphPackage instanceof GraphPackageImpl
         ? (GraphPackageImpl) registeredGraphPackage
         : new GraphPackageImpl();

      isInited = true;

      // Create package meta-data objects
      theGraphPackage.createPackageContents();

      // Initialize created meta-data
      theGraphPackage.initializePackageContents();

      // Mark meta-data to indicate it can't be changed
      theGraphPackage.freeze();

      // Update the registry and return the package
      EPackage.Registry.INSTANCE.put(GraphPackage.eNS_URI, theGraphPackage);
      return theGraphPackage;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EClass getGModelElement() { return gModelElementEClass; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGModelElement_Id() { return (EAttribute) gModelElementEClass.getEStructuralFeatures().get(0); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGModelElement_CssClasses() {
      return (EAttribute) gModelElementEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EReference getGModelElement_Children() {
      return (EReference) gModelElementEClass.getEStructuralFeatures().get(2);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EReference getGModelElement_Parent() {
      return (EReference) gModelElementEClass.getEStructuralFeatures().get(3);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGModelElement_Trace() {
      return (EAttribute) gModelElementEClass.getEStructuralFeatures().get(4);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGModelElement_Type() {
      return (EAttribute) gModelElementEClass.getEStructuralFeatures().get(5);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EClass getGShapeElement() { return gShapeElementEClass; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EClass getGGraph() { return gGraphEClass; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EReference getGGraph_LayoutOptions() { return (EReference) gGraphEClass.getEStructuralFeatures().get(0); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EClass getGModelRoot() { return gModelRootEClass; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EReference getGModelRoot_CanvasBounds() {
      return (EReference) gModelRootEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGModelRoot_Revision() { return (EAttribute) gModelRootEClass.getEStructuralFeatures().get(1); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EClass getGNode() { return gNodeEClass; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EClass getGEdge() { return gEdgeEClass; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EReference getGEdge_RoutingPoints() { return (EReference) gEdgeEClass.getEStructuralFeatures().get(0); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGEdge_SourceId() { return (EAttribute) gEdgeEClass.getEStructuralFeatures().get(1); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGEdge_TargetId() { return (EAttribute) gEdgeEClass.getEStructuralFeatures().get(2); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EReference getGEdge_Source() { return (EReference) gEdgeEClass.getEStructuralFeatures().get(3); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EReference getGEdge_Target() { return (EReference) gEdgeEClass.getEStructuralFeatures().get(4); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGEdge_RouterKind() { return (EAttribute) gEdgeEClass.getEStructuralFeatures().get(5); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EClass getGCompartment() { return gCompartmentEClass; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EClass getGLabel() { return gLabelEClass; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGLabel_Text() { return (EAttribute) gLabelEClass.getEStructuralFeatures().get(0); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EClass getGIssueMarker() { return gIssueMarkerEClass; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EReference getGIssueMarker_Issues() {
      return (EReference) gIssueMarkerEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EClass getGPort() { return gPortEClass; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EClass getGButton() { return gButtonEClass; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGButton_Enabled() { return (EAttribute) gButtonEClass.getEStructuralFeatures().get(0); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EClass getGBoundsAware() { return gBoundsAwareEClass; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EReference getGBoundsAware_Position() {
      return (EReference) gBoundsAwareEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EReference getGBoundsAware_Size() { return (EReference) gBoundsAwareEClass.getEStructuralFeatures().get(1); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EClass getGPoint() { return gPointEClass; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGPoint_X() { return (EAttribute) gPointEClass.getEStructuralFeatures().get(0); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGPoint_Y() { return (EAttribute) gPointEClass.getEStructuralFeatures().get(1); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EClass getGDimension() { return gDimensionEClass; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGDimension_Width() { return (EAttribute) gDimensionEClass.getEStructuralFeatures().get(0); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGDimension_Height() { return (EAttribute) gDimensionEClass.getEStructuralFeatures().get(1); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EClass getGEdgeLayoutable() { return gEdgeLayoutableEClass; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EReference getGEdgeLayoutable_EdgePlacement() {
      return (EReference) gEdgeLayoutableEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EClass getGEdgePlacement() { return gEdgePlacementEClass; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGEdgePlacement_Position() {
      return (EAttribute) gEdgePlacementEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGEdgePlacement_Offset() {
      return (EAttribute) gEdgePlacementEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGEdgePlacement_Side() {
      return (EAttribute) gEdgePlacementEClass.getEStructuralFeatures().get(2);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGEdgePlacement_Rotate() {
      return (EAttribute) gEdgePlacementEClass.getEStructuralFeatures().get(3);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EClass getGLayouting() { return gLayoutingEClass; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGLayouting_Layout() { return (EAttribute) gLayoutingEClass.getEStructuralFeatures().get(0); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EReference getGLayouting_LayoutOptions() {
      return (EReference) gLayoutingEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EClass getGBounds() { return gBoundsEClass; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGBounds_X() { return (EAttribute) gBoundsEClass.getEStructuralFeatures().get(0); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGBounds_Y() { return (EAttribute) gBoundsEClass.getEStructuralFeatures().get(1); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGBounds_Width() { return (EAttribute) gBoundsEClass.getEStructuralFeatures().get(2); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGBounds_Height() { return (EAttribute) gBoundsEClass.getEStructuralFeatures().get(3); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EClass getGAlignable() { return gAlignableEClass; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EReference getGAlignable_Alignment() { return (EReference) gAlignableEClass.getEStructuralFeatures().get(0); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EClass getGIssue() { return gIssueEClass; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGIssue_Severity() { return (EAttribute) gIssueEClass.getEStructuralFeatures().get(0); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGIssue_Message() { return (EAttribute) gIssueEClass.getEStructuralFeatures().get(1); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EClass getGHtmlRoot() { return gHtmlRootEClass; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGHtmlRoot_Classes() { return (EAttribute) gHtmlRootEClass.getEStructuralFeatures().get(0); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EClass getGPreRenderedElement() { return gPreRenderedElementEClass; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getGPreRenderedElement_Code() {
      return (EAttribute) gPreRenderedElementEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EClass getStringToObjectMapEntry() { return stringToObjectMapEntryEClass; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getStringToObjectMapEntry_Key() {
      return (EAttribute) stringToObjectMapEntryEClass.getEStructuralFeatures().get(0);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EAttribute getStringToObjectMapEntry_Value() {
      return (EAttribute) stringToObjectMapEntryEClass.getEStructuralFeatures().get(1);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EEnum getGSeverity() { return gSeverityEEnum; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GraphFactory getGraphFactory() { return (GraphFactory) getEFactoryInstance(); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private boolean isCreated = false;

   /**
    * Creates the meta-model objects for the package.  This method is
    * guarded to have no affect on any invocation but its first.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void createPackageContents() {
      if (isCreated)
         return;
      isCreated = true;

      // Create classes and their features
      gModelElementEClass = createEClass(GMODEL_ELEMENT);
      createEAttribute(gModelElementEClass, GMODEL_ELEMENT__ID);
      createEAttribute(gModelElementEClass, GMODEL_ELEMENT__CSS_CLASSES);
      createEReference(gModelElementEClass, GMODEL_ELEMENT__CHILDREN);
      createEReference(gModelElementEClass, GMODEL_ELEMENT__PARENT);
      createEAttribute(gModelElementEClass, GMODEL_ELEMENT__TRACE);
      createEAttribute(gModelElementEClass, GMODEL_ELEMENT__TYPE);

      gShapeElementEClass = createEClass(GSHAPE_ELEMENT);

      gGraphEClass = createEClass(GGRAPH);
      createEReference(gGraphEClass, GGRAPH__LAYOUT_OPTIONS);

      gModelRootEClass = createEClass(GMODEL_ROOT);
      createEReference(gModelRootEClass, GMODEL_ROOT__CANVAS_BOUNDS);
      createEAttribute(gModelRootEClass, GMODEL_ROOT__REVISION);

      gNodeEClass = createEClass(GNODE);

      gEdgeEClass = createEClass(GEDGE);
      createEReference(gEdgeEClass, GEDGE__ROUTING_POINTS);
      createEAttribute(gEdgeEClass, GEDGE__SOURCE_ID);
      createEAttribute(gEdgeEClass, GEDGE__TARGET_ID);
      createEReference(gEdgeEClass, GEDGE__SOURCE);
      createEReference(gEdgeEClass, GEDGE__TARGET);
      createEAttribute(gEdgeEClass, GEDGE__ROUTER_KIND);

      gCompartmentEClass = createEClass(GCOMPARTMENT);

      gLabelEClass = createEClass(GLABEL);
      createEAttribute(gLabelEClass, GLABEL__TEXT);

      gIssueMarkerEClass = createEClass(GISSUE_MARKER);
      createEReference(gIssueMarkerEClass, GISSUE_MARKER__ISSUES);

      gPortEClass = createEClass(GPORT);

      gButtonEClass = createEClass(GBUTTON);
      createEAttribute(gButtonEClass, GBUTTON__ENABLED);

      gBoundsAwareEClass = createEClass(GBOUNDS_AWARE);
      createEReference(gBoundsAwareEClass, GBOUNDS_AWARE__POSITION);
      createEReference(gBoundsAwareEClass, GBOUNDS_AWARE__SIZE);

      gPointEClass = createEClass(GPOINT);
      createEAttribute(gPointEClass, GPOINT__X);
      createEAttribute(gPointEClass, GPOINT__Y);

      gDimensionEClass = createEClass(GDIMENSION);
      createEAttribute(gDimensionEClass, GDIMENSION__WIDTH);
      createEAttribute(gDimensionEClass, GDIMENSION__HEIGHT);

      gEdgeLayoutableEClass = createEClass(GEDGE_LAYOUTABLE);
      createEReference(gEdgeLayoutableEClass, GEDGE_LAYOUTABLE__EDGE_PLACEMENT);

      gEdgePlacementEClass = createEClass(GEDGE_PLACEMENT);
      createEAttribute(gEdgePlacementEClass, GEDGE_PLACEMENT__POSITION);
      createEAttribute(gEdgePlacementEClass, GEDGE_PLACEMENT__OFFSET);
      createEAttribute(gEdgePlacementEClass, GEDGE_PLACEMENT__SIDE);
      createEAttribute(gEdgePlacementEClass, GEDGE_PLACEMENT__ROTATE);

      gLayoutingEClass = createEClass(GLAYOUTING);
      createEAttribute(gLayoutingEClass, GLAYOUTING__LAYOUT);
      createEReference(gLayoutingEClass, GLAYOUTING__LAYOUT_OPTIONS);

      gBoundsEClass = createEClass(GBOUNDS);
      createEAttribute(gBoundsEClass, GBOUNDS__X);
      createEAttribute(gBoundsEClass, GBOUNDS__Y);
      createEAttribute(gBoundsEClass, GBOUNDS__WIDTH);
      createEAttribute(gBoundsEClass, GBOUNDS__HEIGHT);

      gAlignableEClass = createEClass(GALIGNABLE);
      createEReference(gAlignableEClass, GALIGNABLE__ALIGNMENT);

      gIssueEClass = createEClass(GISSUE);
      createEAttribute(gIssueEClass, GISSUE__SEVERITY);
      createEAttribute(gIssueEClass, GISSUE__MESSAGE);

      gHtmlRootEClass = createEClass(GHTML_ROOT);
      createEAttribute(gHtmlRootEClass, GHTML_ROOT__CLASSES);

      gPreRenderedElementEClass = createEClass(GPRE_RENDERED_ELEMENT);
      createEAttribute(gPreRenderedElementEClass, GPRE_RENDERED_ELEMENT__CODE);

      stringToObjectMapEntryEClass = createEClass(STRING_TO_OBJECT_MAP_ENTRY);
      createEAttribute(stringToObjectMapEntryEClass, STRING_TO_OBJECT_MAP_ENTRY__KEY);
      createEAttribute(stringToObjectMapEntryEClass, STRING_TO_OBJECT_MAP_ENTRY__VALUE);

      // Create enums
      gSeverityEEnum = createEEnum(GSEVERITY);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private boolean isInitialized = false;

   /**
    * Complete the initialization of the package and its meta-model.  This
    * method is guarded to have no affect on any invocation but its first.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void initializePackageContents() {
      if (isInitialized)
         return;
      isInitialized = true;

      // Initialize package
      setName(eNAME);
      setNsPrefix(eNS_PREFIX);
      setNsURI(eNS_URI);

      // Create type parameters

      // Set bounds for type parameters

      // Add supertypes to classes
      gShapeElementEClass.getESuperTypes().add(this.getGModelElement());
      gShapeElementEClass.getESuperTypes().add(this.getGBoundsAware());
      gGraphEClass.getESuperTypes().add(this.getGModelRoot());
      gGraphEClass.getESuperTypes().add(this.getGBoundsAware());
      gModelRootEClass.getESuperTypes().add(this.getGModelElement());
      gNodeEClass.getESuperTypes().add(this.getGShapeElement());
      gNodeEClass.getESuperTypes().add(this.getGEdgeLayoutable());
      gNodeEClass.getESuperTypes().add(this.getGLayouting());
      gEdgeEClass.getESuperTypes().add(this.getGModelElement());
      gCompartmentEClass.getESuperTypes().add(this.getGShapeElement());
      gCompartmentEClass.getESuperTypes().add(this.getGLayouting());
      gLabelEClass.getESuperTypes().add(this.getGAlignable());
      gLabelEClass.getESuperTypes().add(this.getGEdgeLayoutable());
      gLabelEClass.getESuperTypes().add(this.getGShapeElement());
      gIssueMarkerEClass.getESuperTypes().add(this.getGShapeElement());
      gPortEClass.getESuperTypes().add(this.getGShapeElement());
      gButtonEClass.getESuperTypes().add(this.getGShapeElement());
      gHtmlRootEClass.getESuperTypes().add(this.getGModelRoot());
      gPreRenderedElementEClass.getESuperTypes().add(this.getGModelElement());

      // Initialize classes, features, and operations; add parameters
      initEClass(gModelElementEClass, GModelElement.class, "GModelElement", IS_ABSTRACT, IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getGModelElement_Id(), ecorePackage.getEString(), "id", null, 0, 1, GModelElement.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getGModelElement_CssClasses(), ecorePackage.getEString(), "cssClasses", null, 0, -1,
         GModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);
      initEReference(getGModelElement_Children(), this.getGModelElement(), this.getGModelElement_Parent(), "children",
         null, 0, -1, GModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
         !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEReference(getGModelElement_Parent(), this.getGModelElement(), this.getGModelElement_Children(), "parent",
         null, 0, 1, GModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
         !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getGModelElement_Trace(), ecorePackage.getEString(), "trace", null, 0, 1, GModelElement.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getGModelElement_Type(), ecorePackage.getEString(), "type", null, 0, 1, GModelElement.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(gShapeElementEClass, GShapeElement.class, "GShapeElement", IS_ABSTRACT, IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);

      initEClass(gGraphEClass, GGraph.class, "GGraph", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
      initEReference(getGGraph_LayoutOptions(), this.getStringToObjectMapEntry(), null, "layoutOptions", null, 0, -1,
         GGraph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
         IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(gModelRootEClass, GModelRoot.class, "GModelRoot", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getGModelRoot_CanvasBounds(), this.getGBounds(), null, "canvasBounds", null, 0, 1,
         GModelRoot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
         !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getGModelRoot_Revision(), ecorePackage.getEInt(), "revision", null, 0, 1, GModelRoot.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(gNodeEClass, GNode.class, "GNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

      initEClass(gEdgeEClass, GEdge.class, "GEdge", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
      initEReference(getGEdge_RoutingPoints(), this.getGPoint(), null, "routingPoints", null, 0, -1, GEdge.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);
      initEAttribute(getGEdge_SourceId(), ecorePackage.getEString(), "sourceId", null, 0, 1, GEdge.class, !IS_TRANSIENT,
         !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getGEdge_TargetId(), ecorePackage.getEString(), "targetId", null, 0, 1, GEdge.class, !IS_TRANSIENT,
         !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEReference(getGEdge_Source(), this.getGModelElement(), null, "source", null, 0, 1, GEdge.class, IS_TRANSIENT,
         IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
         IS_ORDERED);
      initEReference(getGEdge_Target(), this.getGModelElement(), null, "target", null, 0, 1, GEdge.class, IS_TRANSIENT,
         IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
         IS_ORDERED);
      initEAttribute(getGEdge_RouterKind(), ecorePackage.getEString(), "routerKind", null, 0, 1, GEdge.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(gCompartmentEClass, GCompartment.class, "GCompartment", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);

      initEClass(gLabelEClass, GLabel.class, "GLabel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getGLabel_Text(), ecorePackage.getEString(), "text", null, 1, 1, GLabel.class, !IS_TRANSIENT,
         !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(gIssueMarkerEClass, GIssueMarker.class, "GIssueMarker", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getGIssueMarker_Issues(), this.getGIssue(), null, "issues", null, 0, -1, GIssueMarker.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);

      initEClass(gPortEClass, GPort.class, "GPort", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

      initEClass(gButtonEClass, GButton.class, "GButton", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getGButton_Enabled(), ecorePackage.getEBoolean(), "enabled", "true", 1, 1, GButton.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(gBoundsAwareEClass, GBoundsAware.class, "GBoundsAware", IS_ABSTRACT, IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getGBoundsAware_Position(), this.getGPoint(), null, "position", null, 0, 1, GBoundsAware.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);
      initEReference(getGBoundsAware_Size(), this.getGDimension(), null, "size", null, 0, 1, GBoundsAware.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);

      initEClass(gPointEClass, GPoint.class, "GPoint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getGPoint_X(), ecorePackage.getEDouble(), "x", "0", 1, 1, GPoint.class, !IS_TRANSIENT,
         !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getGPoint_Y(), ecorePackage.getEDouble(), "y", "0", 1, 1, GPoint.class, !IS_TRANSIENT,
         !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(gDimensionEClass, GDimension.class, "GDimension", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getGDimension_Width(), ecorePackage.getEDouble(), "width", "0", 1, 1, GDimension.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getGDimension_Height(), ecorePackage.getEDouble(), "height", "0", 1, 1, GDimension.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(gEdgeLayoutableEClass, GEdgeLayoutable.class, "GEdgeLayoutable", IS_ABSTRACT, IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getGEdgeLayoutable_EdgePlacement(), this.getGEdgePlacement(), null, "edgePlacement", null, 0, 1,
         GEdgeLayoutable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
         !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(gEdgePlacementEClass, GEdgePlacement.class, "GEdgePlacement", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getGEdgePlacement_Position(), ecorePackage.getEDoubleObject(), "position", "0", 1, 1,
         GEdgePlacement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);
      initEAttribute(getGEdgePlacement_Offset(), ecorePackage.getEDoubleObject(), "offset", "0", 1, 1,
         GEdgePlacement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);
      initEAttribute(getGEdgePlacement_Side(), ecorePackage.getEString(), "side", "left", 0, 1, GEdgePlacement.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getGEdgePlacement_Rotate(), ecorePackage.getEBoolean(), "rotate", null, 0, 1, GEdgePlacement.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(gLayoutingEClass, GLayouting.class, "GLayouting", IS_ABSTRACT, IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getGLayouting_Layout(), ecorePackage.getEString(), "layout", null, 0, 1, GLayouting.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEReference(getGLayouting_LayoutOptions(), this.getStringToObjectMapEntry(), null, "layoutOptions", null, 0,
         -1, GLayouting.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
         !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(gBoundsEClass, GBounds.class, "GBounds", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getGBounds_X(), ecorePackage.getEDouble(), "x", "0", 1, 1, GBounds.class, !IS_TRANSIENT,
         !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getGBounds_Y(), ecorePackage.getEDouble(), "y", "0", 1, 1, GBounds.class, !IS_TRANSIENT,
         !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getGBounds_Width(), ecorePackage.getEDouble(), "width", "0", 1, 1, GBounds.class, !IS_TRANSIENT,
         !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getGBounds_Height(), ecorePackage.getEDouble(), "height", "0", 1, 1, GBounds.class, !IS_TRANSIENT,
         !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(gAlignableEClass, GAlignable.class, "GAlignable", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEReference(getGAlignable_Alignment(), this.getGPoint(), null, "alignment", null, 0, 1, GAlignable.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);

      initEClass(gIssueEClass, GIssue.class, "GIssue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getGIssue_Severity(), this.getGSeverity(), "severity", "info", 1, 1, GIssue.class, !IS_TRANSIENT,
         !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getGIssue_Message(), ecorePackage.getEString(), "message", null, 0, 1, GIssue.class, !IS_TRANSIENT,
         !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(gHtmlRootEClass, GHtmlRoot.class, "GHtmlRoot", !IS_ABSTRACT, !IS_INTERFACE,
         IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getGHtmlRoot_Classes(), ecorePackage.getEString(), "classes", null, 0, -1, GHtmlRoot.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

      initEClass(gPreRenderedElementEClass, GPreRenderedElement.class, "GPreRenderedElement", !IS_ABSTRACT,
         !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getGPreRenderedElement_Code(), ecorePackage.getEString(), "code", null, 0, 1,
         GPreRenderedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
         !IS_DERIVED, IS_ORDERED);

      initEClass(stringToObjectMapEntryEClass, Map.Entry.class, "StringToObjectMapEntry", !IS_ABSTRACT, !IS_INTERFACE,
         !IS_GENERATED_INSTANCE_CLASS);
      initEAttribute(getStringToObjectMapEntry_Key(), ecorePackage.getEString(), "key", null, 1, 1, Map.Entry.class,
         !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
      initEAttribute(getStringToObjectMapEntry_Value(), ecorePackage.getEJavaObject(), "value", null, 0, 1,
         Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
         IS_ORDERED);

      // Initialize enums and add enum literals
      initEEnum(gSeverityEEnum, GSeverity.class, "GSeverity");
      addEEnumLiteral(gSeverityEEnum, GSeverity.ERROR);
      addEEnumLiteral(gSeverityEEnum, GSeverity.WARNING);
      addEEnumLiteral(gSeverityEEnum, GSeverity.INFO);

      // Create resource
      createResource(eNS_URI);
   }

} //GraphPackageImpl
