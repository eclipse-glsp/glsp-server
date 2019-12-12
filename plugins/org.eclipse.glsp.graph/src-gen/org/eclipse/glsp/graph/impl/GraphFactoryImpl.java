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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.glsp.graph.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class GraphFactoryImpl extends EFactoryImpl implements GraphFactory {
   /**
    * Creates the default factory implementation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public static GraphFactory init() {
      try {
         GraphFactory theGraphFactory = (GraphFactory) EPackage.Registry.INSTANCE.getEFactory(GraphPackage.eNS_URI);
         if (theGraphFactory != null) {
            return theGraphFactory;
         }
      } catch (Exception exception) {
         EcorePlugin.INSTANCE.log(exception);
      }
      return new GraphFactoryImpl();
   }

   /**
    * Creates an instance of the factory.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public GraphFactoryImpl() {
      super();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EObject create(EClass eClass) {
      switch (eClass.getClassifierID()) {
         case GraphPackage.GGRAPH:
            return createGGraph();
         case GraphPackage.GMODEL_ROOT:
            return createGModelRoot();
         case GraphPackage.GNODE:
            return createGNode();
         case GraphPackage.GEDGE:
            return createGEdge();
         case GraphPackage.GCOMPARTMENT:
            return createGCompartment();
         case GraphPackage.GLABEL:
            return createGLabel();
         case GraphPackage.GISSUE_MARKER:
            return createGIssueMarker();
         case GraphPackage.GPORT:
            return createGPort();
         case GraphPackage.GBUTTON:
            return createGButton();
         case GraphPackage.GPOINT:
            return createGPoint();
         case GraphPackage.GDIMENSION:
            return createGDimension();
         case GraphPackage.GEDGE_PLACEMENT:
            return createGEdgePlacement();
         case GraphPackage.GBOUNDS:
            return createGBounds();
         case GraphPackage.GALIGNABLE:
            return createGAlignable();
         case GraphPackage.GISSUE:
            return createGIssue();
         case GraphPackage.GHTML_ROOT:
            return createGHtmlRoot();
         case GraphPackage.GPRE_RENDERED_ELEMENT:
            return createGPreRenderedElement();
         case GraphPackage.STRING_TO_OBJECT_MAP_ENTRY:
            return (EObject) createStringToObjectMapEntry();
         default:
            throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
      }
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Object createFromString(EDataType eDataType, String initialValue) {
      switch (eDataType.getClassifierID()) {
         case GraphPackage.GSEVERITY:
            return createGSeverityFromString(eDataType, initialValue);
         default:
            throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
      }
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public String convertToString(EDataType eDataType, Object instanceValue) {
      switch (eDataType.getClassifierID()) {
         case GraphPackage.GSEVERITY:
            return convertGSeverityToString(eDataType, instanceValue);
         default:
            throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
      }
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GGraph createGGraph() {
      GGraphImpl gGraph = new GGraphImpl();
      return gGraph;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GModelRoot createGModelRoot() {
      GModelRootImpl gModelRoot = new GModelRootImpl();
      return gModelRoot;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GNode createGNode() {
      GNodeImpl gNode = new GNodeImpl();
      return gNode;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GEdge createGEdge() {
      GEdgeImpl gEdge = new GEdgeImpl();
      return gEdge;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GCompartment createGCompartment() {
      GCompartmentImpl gCompartment = new GCompartmentImpl();
      return gCompartment;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GLabel createGLabel() {
      GLabelImpl gLabel = new GLabelImpl();
      return gLabel;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GIssueMarker createGIssueMarker() {
      GIssueMarkerImpl gIssueMarker = new GIssueMarkerImpl();
      return gIssueMarker;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GPort createGPort() {
      GPortImpl gPort = new GPortImpl();
      return gPort;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GButton createGButton() {
      GButtonImpl gButton = new GButtonImpl();
      return gButton;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GPoint createGPoint() {
      GPointImpl gPoint = new GPointImpl();
      return gPoint;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GDimension createGDimension() {
      GDimensionImpl gDimension = new GDimensionImpl();
      return gDimension;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GEdgePlacement createGEdgePlacement() {
      GEdgePlacementImpl gEdgePlacement = new GEdgePlacementImpl();
      return gEdgePlacement;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GBounds createGBounds() {
      GBoundsImpl gBounds = new GBoundsImpl();
      return gBounds;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GAlignable createGAlignable() {
      GAlignableImpl gAlignable = new GAlignableImpl();
      return gAlignable;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GIssue createGIssue() {
      GIssueImpl gIssue = new GIssueImpl();
      return gIssue;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GHtmlRoot createGHtmlRoot() {
      GHtmlRootImpl gHtmlRoot = new GHtmlRootImpl();
      return gHtmlRoot;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GPreRenderedElement createGPreRenderedElement() {
      GPreRenderedElementImpl gPreRenderedElement = new GPreRenderedElementImpl();
      return gPreRenderedElement;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public Map.Entry<String, Object> createStringToObjectMapEntry() {
      StringToObjectMapEntryImpl stringToObjectMapEntry = new StringToObjectMapEntryImpl();
      return stringToObjectMapEntry;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public GSeverity createGSeverityFromString(EDataType eDataType, String initialValue) {
      GSeverity result = GSeverity.get(initialValue);
      if (result == null)
         throw new IllegalArgumentException(
            "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
      return result;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public String convertGSeverityToString(EDataType eDataType, Object instanceValue) {
      return instanceValue == null ? null : instanceValue.toString();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public GraphPackage getGraphPackage() { return (GraphPackage) getEPackage(); }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @deprecated
    * @generated
    */
   @Deprecated
   public static GraphPackage getPackage() { return GraphPackage.eINSTANCE; }

} //GraphFactoryImpl
