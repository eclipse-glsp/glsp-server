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
package org.eclipse.glsp.graph.util;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.glsp.graph.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.glsp.graph.GraphPackage
 * @generated
 */
public class GraphSwitch<T> extends Switch<T> {
   /**
    * The cached model package
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected static GraphPackage modelPackage;

   /**
    * Creates an instance of the switch.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public GraphSwitch() {
      if (modelPackage == null) {
         modelPackage = GraphPackage.eINSTANCE;
      }
   }

   /**
    * Checks whether this is a switch for the given package.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param ePackage the package in question.
    * @return whether this is a switch for the given package.
    * @generated
    */
   @Override
   protected boolean isSwitchFor(EPackage ePackage) {
      return ePackage == modelPackage;
   }

   /**
    * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the first non-null result returned by a <code>caseXXX</code> call.
    * @generated
    */
   @Override
   protected T doSwitch(int classifierID, EObject theEObject) {
      switch (classifierID) {
         case GraphPackage.GMODEL_ELEMENT: {
            GModelElement gModelElement = (GModelElement) theEObject;
            T result = caseGModelElement(gModelElement);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case GraphPackage.GSHAPE_ELEMENT: {
            GShapeElement gShapeElement = (GShapeElement) theEObject;
            T result = caseGShapeElement(gShapeElement);
            if (result == null)
               result = caseGModelElement(gShapeElement);
            if (result == null)
               result = caseGBoundsAware(gShapeElement);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case GraphPackage.GGRAPH: {
            GGraph gGraph = (GGraph) theEObject;
            T result = caseGGraph(gGraph);
            if (result == null)
               result = caseGModelRoot(gGraph);
            if (result == null)
               result = caseGBoundsAware(gGraph);
            if (result == null)
               result = caseGModelElement(gGraph);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case GraphPackage.GMODEL_ROOT: {
            GModelRoot gModelRoot = (GModelRoot) theEObject;
            T result = caseGModelRoot(gModelRoot);
            if (result == null)
               result = caseGModelElement(gModelRoot);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case GraphPackage.GNODE: {
            GNode gNode = (GNode) theEObject;
            T result = caseGNode(gNode);
            if (result == null)
               result = caseGShapeElement(gNode);
            if (result == null)
               result = caseGEdgeLayoutable(gNode);
            if (result == null)
               result = caseGLayouting(gNode);
            if (result == null)
               result = caseGModelElement(gNode);
            if (result == null)
               result = caseGBoundsAware(gNode);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case GraphPackage.GEDGE: {
            GEdge gEdge = (GEdge) theEObject;
            T result = caseGEdge(gEdge);
            if (result == null)
               result = caseGModelElement(gEdge);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case GraphPackage.GCOMPARTMENT: {
            GCompartment gCompartment = (GCompartment) theEObject;
            T result = caseGCompartment(gCompartment);
            if (result == null)
               result = caseGShapeElement(gCompartment);
            if (result == null)
               result = caseGLayouting(gCompartment);
            if (result == null)
               result = caseGModelElement(gCompartment);
            if (result == null)
               result = caseGBoundsAware(gCompartment);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case GraphPackage.GLABEL: {
            GLabel gLabel = (GLabel) theEObject;
            T result = caseGLabel(gLabel);
            if (result == null)
               result = caseGAlignable(gLabel);
            if (result == null)
               result = caseGEdgeLayoutable(gLabel);
            if (result == null)
               result = caseGShapeElement(gLabel);
            if (result == null)
               result = caseGModelElement(gLabel);
            if (result == null)
               result = caseGBoundsAware(gLabel);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case GraphPackage.GISSUE_MARKER: {
            GIssueMarker gIssueMarker = (GIssueMarker) theEObject;
            T result = caseGIssueMarker(gIssueMarker);
            if (result == null)
               result = caseGShapeElement(gIssueMarker);
            if (result == null)
               result = caseGModelElement(gIssueMarker);
            if (result == null)
               result = caseGBoundsAware(gIssueMarker);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case GraphPackage.GPORT: {
            GPort gPort = (GPort) theEObject;
            T result = caseGPort(gPort);
            if (result == null)
               result = caseGShapeElement(gPort);
            if (result == null)
               result = caseGModelElement(gPort);
            if (result == null)
               result = caseGBoundsAware(gPort);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case GraphPackage.GBUTTON: {
            GButton gButton = (GButton) theEObject;
            T result = caseGButton(gButton);
            if (result == null)
               result = caseGShapeElement(gButton);
            if (result == null)
               result = caseGModelElement(gButton);
            if (result == null)
               result = caseGBoundsAware(gButton);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case GraphPackage.GBOUNDS_AWARE: {
            GBoundsAware gBoundsAware = (GBoundsAware) theEObject;
            T result = caseGBoundsAware(gBoundsAware);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case GraphPackage.GPOINT: {
            GPoint gPoint = (GPoint) theEObject;
            T result = caseGPoint(gPoint);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case GraphPackage.GDIMENSION: {
            GDimension gDimension = (GDimension) theEObject;
            T result = caseGDimension(gDimension);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case GraphPackage.GEDGE_LAYOUTABLE: {
            GEdgeLayoutable gEdgeLayoutable = (GEdgeLayoutable) theEObject;
            T result = caseGEdgeLayoutable(gEdgeLayoutable);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case GraphPackage.GEDGE_PLACEMENT: {
            GEdgePlacement gEdgePlacement = (GEdgePlacement) theEObject;
            T result = caseGEdgePlacement(gEdgePlacement);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case GraphPackage.GLAYOUTING: {
            GLayouting gLayouting = (GLayouting) theEObject;
            T result = caseGLayouting(gLayouting);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case GraphPackage.GBOUNDS: {
            GBounds gBounds = (GBounds) theEObject;
            T result = caseGBounds(gBounds);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case GraphPackage.GALIGNABLE: {
            GAlignable gAlignable = (GAlignable) theEObject;
            T result = caseGAlignable(gAlignable);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case GraphPackage.GISSUE: {
            GIssue gIssue = (GIssue) theEObject;
            T result = caseGIssue(gIssue);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case GraphPackage.GHTML_ROOT: {
            GHtmlRoot gHtmlRoot = (GHtmlRoot) theEObject;
            T result = caseGHtmlRoot(gHtmlRoot);
            if (result == null)
               result = caseGModelRoot(gHtmlRoot);
            if (result == null)
               result = caseGModelElement(gHtmlRoot);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case GraphPackage.GPRE_RENDERED_ELEMENT: {
            GPreRenderedElement gPreRenderedElement = (GPreRenderedElement) theEObject;
            T result = caseGPreRenderedElement(gPreRenderedElement);
            if (result == null)
               result = caseGModelElement(gPreRenderedElement);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         case GraphPackage.STRING_TO_OBJECT_MAP_ENTRY: {
            @SuppressWarnings("unchecked")
            Map.Entry<String, Object> stringToObjectMapEntry = (Map.Entry<String, Object>) theEObject;
            T result = caseStringToObjectMapEntry(stringToObjectMapEntry);
            if (result == null)
               result = defaultCase(theEObject);
            return result;
         }
         default:
            return defaultCase(theEObject);
      }
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GModel Element</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GModel Element</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGModelElement(GModelElement object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GShape Element</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GShape Element</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGShapeElement(GShapeElement object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GGraph</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GGraph</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGGraph(GGraph object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GModel Root</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GModel Root</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGModelRoot(GModelRoot object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GNode</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GNode</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGNode(GNode object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GEdge</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GEdge</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGEdge(GEdge object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GCompartment</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GCompartment</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGCompartment(GCompartment object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GLabel</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GLabel</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGLabel(GLabel object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GIssue Marker</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GIssue Marker</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGIssueMarker(GIssueMarker object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GPort</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GPort</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGPort(GPort object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GButton</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GButton</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGButton(GButton object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GBounds Aware</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GBounds Aware</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGBoundsAware(GBoundsAware object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GPoint</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GPoint</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGPoint(GPoint object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GDimension</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GDimension</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGDimension(GDimension object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GEdge Layoutable</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GEdge Layoutable</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGEdgeLayoutable(GEdgeLayoutable object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GEdge Placement</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GEdge Placement</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGEdgePlacement(GEdgePlacement object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GLayouting</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GLayouting</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGLayouting(GLayouting object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GBounds</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GBounds</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGBounds(GBounds object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GAlignable</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GAlignable</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGAlignable(GAlignable object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GIssue</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GIssue</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGIssue(GIssue object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GHtml Root</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GHtml Root</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGHtmlRoot(GHtmlRoot object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>GPre Rendered Element</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>GPre Rendered Element</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseGPreRenderedElement(GPreRenderedElement object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>String To Object Map Entry</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>String To Object Map Entry</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
    * @generated
    */
   public T caseStringToObjectMapEntry(Map.Entry<String, Object> object) {
      return null;
   }

   /**
    * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
    * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch, but this is the last case anyway.
    * <!-- end-user-doc -->
    * @param object the target of the switch.
    * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
    * @see #doSwitch(org.eclipse.emf.ecore.EObject)
    * @generated
    */
   @Override
   public T defaultCase(EObject object) {
      return null;
   }

} //GraphSwitch
