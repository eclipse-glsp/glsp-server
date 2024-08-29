/********************************************************************************
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
 ********************************************************************************/
package org.eclipse.glsp.graph;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;

public final class DefaultTypes {

   private DefaultTypes() {}

   // HTML elements
   public static final String HTML = "html";

   // Generic types
   public static final String FOREIGN_OBJECT = "foreign-object";
   public static final String PRE_RENDERED = "pre-rendered";
   public static final String SHAPE_PRE_RENDERED = "shape-pre-rendered";

   // Svg Elements
   public static final String SVG = "svg";

   // Graph Types
   public static final String GRAPH = "graph";
   public static final String NODE = "node";
   public static final String COMPARTMENT = "comp";
   public static final String COMPARTMENT_HEADER = "comp:header";
   public static final String EDGE = "edge";
   public static final String PORT = "port";
   public static final String ROUTING_POINT = "routing-point"; // client-side only type by default
   public static final String VOLATILE_ROUTING_POINT = "volatile-routing-point"; // client-side only type by default
   public static final String LABEL = "label";

   // UI elements
   public static final String BUTTON = "button";
   public static final String EXPAND_BUTTON = "button:expand";
   public static final String ISSUE_MARKER = "marker"; // client-side only type by default

   // Shapes
   public static final String NODE_CIRCLE = "node:circle";
   public static final String NODE_RECTANGLE = "node:rectangle";
   public static final String NODE_DIAMOND = "node:diamond";

   public static Map<String, EClass> getDefaultTypeMappings() {
      Map<String, EClass> mapping = new HashMap<>();
      mapping.put(GRAPH, GraphPackage.Literals.GGRAPH);
      mapping.put(NODE, GraphPackage.Literals.GNODE);
      mapping.put(EDGE, GraphPackage.Literals.GEDGE);
      mapping.put(PORT, GraphPackage.Literals.GPORT);
      mapping.put(LABEL, GraphPackage.Literals.GLABEL);
      mapping.put(COMPARTMENT, GraphPackage.Literals.GCOMPARTMENT);
      mapping.put(BUTTON, GraphPackage.Literals.GBUTTON);
      mapping.put(ISSUE_MARKER, GraphPackage.Literals.GISSUE_MARKER);

      mapping.put(HTML, GraphPackage.Literals.GHTML_ROOT);
      mapping.put(PRE_RENDERED, GraphPackage.Literals.GPRE_RENDERED_ELEMENT);
      mapping.put(SHAPE_PRE_RENDERED, GraphPackage.Literals.GSHAPE_PRE_RENDERED_ELEMENT);
      mapping.put(FOREIGN_OBJECT, GraphPackage.Literals.GFOREIGN_OBJECT_ELEMENT);
      return mapping;
   }
}
