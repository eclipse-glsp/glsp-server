/********************************************************************************
 * Copyright (c) 2018 TypeFox and others.
 * (c) 2019 EclipseSource (adaptation for GModel)
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
package org.eclipse.glsp.layout;

import static org.eclipse.glsp.graph.util.GraphUtil.dimension;
import static org.eclipse.glsp.graph.util.GraphUtil.point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.elk.core.IGraphLayoutEngine;
import org.eclipse.elk.core.RecursiveGraphLayoutEngine;
import org.eclipse.elk.core.data.ILayoutMetaDataProvider;
import org.eclipse.elk.core.data.LayoutMetaDataService;
import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.util.BasicProgressMonitor;
import org.eclipse.elk.core.util.ElkUtil;
import org.eclipse.elk.graph.ElkBendPoint;
import org.eclipse.elk.graph.ElkConnectableShape;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkEdgeSection;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.ElkGraphFactory;
import org.eclipse.elk.graph.ElkLabel;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.elk.graph.ElkShape;
import org.eclipse.elk.graph.properties.IProperty;
import org.eclipse.elk.graph.properties.Property;
import org.eclipse.elk.graph.util.ElkGraphUtil;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.graph.GBoundsAware;
import org.eclipse.glsp.graph.GDimension;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GEdgeLayoutable;
import org.eclipse.glsp.graph.GGraph;
import org.eclipse.glsp.graph.GLabel;
import org.eclipse.glsp.graph.GLayouting;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.GPort;
import org.eclipse.glsp.graph.GraphFactory;
import org.eclipse.glsp.server.layout.ILayoutEngine;
import org.eclipse.glsp.server.model.GModelState;

import com.google.common.collect.Maps;

/**
 * Layout engine that uses the <a href="https://www.eclipse.org/elk/">Eclipse
 * Layout Kernel (ELK)</a>.
 *
 * <p>
 * The layout engine must be initialized once during the lifecycle of the
 * application by calling {@link #initialize(ILayoutMetaDataProvider...)}. The
 * arguments of that method should be all meta data providers of the layout
 * algorithms that should be used by this layout engine.
 * </p>
 */
public class ElkLayoutEngine implements ILayoutEngine {

   public static final IProperty<String> P_TYPE = new Property<>("org.eclipse.sprotty.layout.type");

   public static void initialize(final ILayoutMetaDataProvider... providers) {
      LayoutMetaDataService.getInstance().registerLayoutMetaDataProviders(providers);
   }

   private IGraphLayoutEngine engine = new RecursiveGraphLayoutEngine();

   protected final ElkGraphFactory factory = ElkGraphFactory.eINSTANCE;

   /*
    * Compute a layout for a graph. The default implementation uses only default
    * settings for all layout options (see
    * <a href="https://www.eclipse.org/elk/reference.html">layout options
    * reference</a>). Override this in a subclass in order to customize the layout
    * for your model using a {@link SprottyLayoutConfigurator}.
    */
   @Override
   public void layout(final GModelState modelState) {
      if (modelState.getRoot() instanceof GGraph) {
         layout((GGraph) modelState.getRoot(), null);
      }
   }

   /*
    * Compute a layout for a graph with the given configurator (or {@code null} to
    * use only default settings).
    */
   public void layout(final GGraph ggraph, final GLSPLayoutConfigurator configurator) {
      LayoutContext context = transformGraph(ggraph);
      if (configurator != null) {
         ElkUtil.applyVisitors(context.elkGraph, configurator);
      }
      applyEngine(context.elkGraph);
      transferLayout(context);
   }

   /*
    * Transform a sprotty graph to an ELK graph, including all contents.
    */
   protected LayoutContext transformGraph(final GGraph ggraph) {
      LayoutContext context = new LayoutContext();
      context.ggraph = ggraph;
      ElkNode rootNode = createGraph(ggraph);
      context.elkGraph = rootNode;
      context.shapeMap.put(ggraph, rootNode);
      processChildren(ggraph, rootNode, context);
      resolveReferences(context);
      return context;
   }

   /*
    * Create a root ELK node for the given sprotty graph.
    */
   protected ElkNode createGraph(final GGraph ggraph) {
      ElkNode elkGraph = factory.createElkNode();
      elkGraph.setIdentifier(GLSPLayoutConfigurator.toElkId(ggraph.getId()));
      elkGraph.setProperty(P_TYPE, ggraph.getType());
      return elkGraph;
   }

   /*
    * Transform the children of a sprotty model element to their ELK graph
    * counterparts.
    */
   @SuppressWarnings({ "checkstyle:CyclomaticComplexity", "checkstyle:NestedIfDepth" })
   protected int processChildren(final GModelElement sParent, final ElkGraphElement elkParent,
      final LayoutContext context) {
      int childrenCount = 0;
      if (sParent.getChildren() != null) {
         for (GModelElement schild : sParent.getChildren()) {
            context.parentMap.put(schild, sParent);
            ElkGraphElement elkChild = null;
            if (shouldInclude(schild, sParent, elkParent, context)) {
               if (schild instanceof GNode) {
                  GNode snode = (GNode) schild;
                  ElkNode elkNode = createNode(snode);
                  if (elkParent instanceof ElkNode) {
                     elkNode.setParent((ElkNode) elkParent);
                     childrenCount++;
                  }
                  context.shapeMap.put(snode, elkNode);
                  elkChild = elkNode;
               } else if (schild instanceof GPort) {
                  GPort gport = (GPort) schild;
                  ElkPort elkPort = createPort(gport);
                  if (elkParent instanceof ElkNode) {
                     elkPort.setParent((ElkNode) elkParent);
                     childrenCount++;
                  }
                  context.shapeMap.put(gport, elkPort);
                  elkChild = elkPort;
               } else if (schild instanceof GEdge) {
                  GEdge gedge = (GEdge) schild;
                  ElkEdge elkEdge = createEdge(gedge);
                  // The most suitable container for the edge is determined later
                  childrenCount++;
                  context.edgeMap.put(gedge, elkEdge);
                  elkChild = elkEdge;
               } else if (schild instanceof GLabel) {
                  GLabel glabel = (GLabel) schild;
                  ElkLabel elkLabel = createLabel(glabel);
                  elkLabel.setParent(elkParent);
                  childrenCount++;
                  context.shapeMap.put(glabel, elkLabel);
                  elkChild = elkLabel;
               }
            }
            int grandChildrenCount = processChildren(schild, elkChild != null ? elkChild : elkParent, context);
            childrenCount += grandChildrenCount;
            if (grandChildrenCount > 0 && sParent instanceof GLayouting && schild instanceof GBoundsAware) {
               handleClientLayout((GBoundsAware) schild, (GLayouting) sParent, elkParent, context);
            }
         }
      }
      return childrenCount;
   }

   /*
    * Return true if the given model element should be included in the layout
    * computation.
    */
   @SuppressWarnings("checkstyle:CyclomaticComplexity")
   protected boolean shouldInclude(final GModelElement element, final GModelElement sParent,
      final ElkGraphElement elkParent,
      final LayoutContext context) {
      if (element instanceof GNode || element instanceof GPort) {
         // Nodes and ports can only be contained in a node
         return elkParent instanceof ElkNode;
      } else if (element instanceof GEdge) {
         // Edges are automatically put into their most suitable container
         return true;
      } else if (sParent instanceof GLayouting) {
         // If the parent has configured a client layout, we ignore its direct children
         // in the server layout
         String layout = ((GLayouting) sParent).getLayout();
         if (layout != null && !layout.isEmpty()) {
            return false;
         }
      } else if (element instanceof GEdgeLayoutable && ((GEdgeLayoutable) element).getEdgePlacement() != null) {
         return false;
      }
      return true;
   }

   /*
    * Consider the layout computed by the client by configuring appropriate ELK
    * layout options.
    */
   @SuppressWarnings("checkstyle:CyclomaticComplexity")
   protected void handleClientLayout(final GBoundsAware element, final GLayouting sParent,
      final ElkGraphElement elkParent,
      final LayoutContext context) {
      String layout = sParent.getLayout();
      if (layout != null && !layout.isEmpty()) {
         GPoint position = element.getPosition();
         if (position == null) {
            position = GraphFactory.eINSTANCE.createGPoint();
         }
         GDimension size = element.getSize();
         if (size == null) {
            size = GraphFactory.eINSTANCE.createGDimension();
         }
         ElkPadding padding = new ElkPadding();
         padding.setLeft(position.getX());
         padding.setTop(position.getY());
         if (sParent instanceof GBoundsAware) {
            GDimension parentSize = ((GBoundsAware) sParent).getSize();
            if (parentSize != null) {
               padding.setRight(parentSize.getWidth() - position.getX() - size.getWidth());
               padding.setBottom(parentSize.getHeight() - position.getY() - size.getHeight());
            }
         }
         if (elkParent.hasProperty(CoreOptions.PADDING)) {
            // Add the previously computed padding to the current one.
            // NOTE: This makes sense only if there are multiple _nested_ layouting
            // containers of which the deepest
            // one contains actual graph elements. Multiple compartments that contain graph
            // elements but are
            // not nested into each other cannot be mapped properly to the ELK graph format.
            padding.add(elkParent.getProperty(CoreOptions.PADDING));
         }
         elkParent.setProperty(CoreOptions.PADDING, padding);
      }
   }

   /*
    * Resolve cross-references in the ELK graph.
    */
   protected void resolveReferences(final LayoutContext context) {
      Map<String, ElkConnectableShape> id2NodeMap = Maps.newHashMapWithExpectedSize(context.shapeMap.size());
      for (Map.Entry<GModelElement, ElkShape> entry : context.shapeMap.entrySet()) {
         String id = entry.getKey().getId();
         if (id != null && entry.getValue() instanceof ElkConnectableShape) {
            id2NodeMap.put(id, (ElkConnectableShape) entry.getValue());
         }
      }
      for (Map.Entry<GEdge, ElkEdge> entry : context.edgeMap.entrySet()) {
         resolveReferences(entry.getValue(), entry.getKey(), id2NodeMap, context);
      }
   }

   /*
    * Resolve the source and target cross-references for the given ELK edge.
    */
   protected void resolveReferences(final ElkEdge elkEdge, final GEdge gedge,
      final Map<String, ElkConnectableShape> id2NodeMap,
      final LayoutContext context) {
      ElkConnectableShape source = id2NodeMap.get(gedge.getSourceId());
      ElkConnectableShape target = id2NodeMap.get(gedge.getTargetId());
      if (source != null && target != null) {
         elkEdge.getSources().add(source);
         elkEdge.getTargets().add(target);
         ElkNode container = ElkGraphUtil.findBestEdgeContainment(elkEdge);
         if (container != null) {
            elkEdge.setContainingNode(container);
         } else {
            elkEdge.setContainingNode(context.elkGraph);
         }
      }
   }

   /*
    * Create an ELK node for the given sprotty node.
    */
   protected ElkNode createNode(final GNode snode) {
      ElkNode elkNode = factory.createElkNode();
      elkNode.setIdentifier(GLSPLayoutConfigurator.toElkId(snode.getId()));
      elkNode.setProperty(P_TYPE, snode.getType());
      applyBounds(snode, elkNode);
      return elkNode;
   }

   /*
    * Create an ELK port for the given sprotty port.
    */
   protected ElkPort createPort(final GPort gport) {
      ElkPort elkPort = factory.createElkPort();
      elkPort.setIdentifier(GLSPLayoutConfigurator.toElkId(gport.getId()));
      elkPort.setProperty(P_TYPE, gport.getType());
      applyBounds(gport, elkPort);
      return elkPort;
   }

   /*
    * Create an ELK edge for the given sprotty edge.
    */
   protected ElkEdge createEdge(final GEdge gedge) {
      ElkEdge elkEdge = factory.createElkEdge();
      elkEdge.setIdentifier(GLSPLayoutConfigurator.toElkId(gedge.getId()));
      elkEdge.setProperty(P_TYPE, gedge.getType());
      // The source and target of the edge are resolved later
      return elkEdge;
   }

   /*
    * Create an ELK label for the given sprotty label.
    */
   protected ElkLabel createLabel(final GLabel glabel) {
      ElkLabel elkLabel = factory.createElkLabel();
      elkLabel.setIdentifier(GLSPLayoutConfigurator.toElkId(glabel.getId()));
      elkLabel.setProperty(P_TYPE, glabel.getType());
      elkLabel.setText(glabel.getText());
      applyBounds(glabel, elkLabel);
      return elkLabel;
   }

   /*
    * Apply the bounds of the given bounds-aware element to an ELK shape (node,
    * port, or label).
    */
   protected void applyBounds(final GBoundsAware bounds, final ElkShape elkShape) {
      GPoint position = bounds.getPosition();
      if (position != null) {
         elkShape.setX(position.getX());
         elkShape.setY(position.getY());
      }
      GDimension size = bounds.getSize();
      if (size != null) {
         if (size.getWidth() >= 0) {
            elkShape.setWidth(size.getWidth());
         }
         if (size.getHeight() >= 0) {
            elkShape.setHeight(size.getHeight());
         }
      }
   }

   /*
    * Set the graph layout engine to invoke in {@link #applyEngine(ElkNode)}. The
    * default is the {@link RecursiveGraphLayoutEngine}, which determines the
    * layout algorithm to apply to each composite node based on the
    * {@link org.eclipse.elk.core.options.CoreOptions#ALGORITHM} option. This
    * requires the meta data providers of the referenced algorithms to be
    * registered using {@link #initialize(ILayoutMetaDataProvider...)} before any
    * layout is performed, e.g. on application start. Alternatively, you can use a
    * specific layout algorithm directly, e.g.
    * {@link org.eclipse.elk.alg.layered.LayeredLayoutProvider}.
    */
   public void setEngine(final IGraphLayoutEngine engine) {
      if (engine == null) {
         throw new NullPointerException();
      }
      this.engine = engine;
   }

   public IGraphLayoutEngine getEngine() { return engine; }

   /*
    * Apply the layout engine that has been configured with
    * {@link #setEngine(IGraphLayoutEngine)}.
    */
   protected void applyEngine(final ElkNode elkGraph) {
      getEngine().layout(elkGraph, new BasicProgressMonitor());
   }

   /*
    * Transfer the computed ELK layout back to the original sprotty graph.
    */
   protected void transferLayout(final LayoutContext context) {
      transferLayout(context.ggraph, context);
   }

   /*
    * Apply the computed ELK layout to the given model element.
    */
   @SuppressWarnings("checkstyle:CyclomaticComplexity")
   protected void transferLayout(final GModelElement element, final LayoutContext context) {
      if (element instanceof GGraph) {
         transferGraphLayout((GGraph) element, context.elkGraph, context);
      } else if (element instanceof GNode) {
         GNode snode = (GNode) element;
         ElkNode elkNode = (ElkNode) context.shapeMap.get(snode);
         if (elkNode != null) {
            transferNodeLayout(snode, elkNode, context);
         }
      } else if (element instanceof GPort) {
         GPort gport = (GPort) element;
         ElkPort elkPort = (ElkPort) context.shapeMap.get(gport);
         if (elkPort != null) {
            transferPortLayout(gport, elkPort, context);
         }
      } else if (element instanceof GEdge) {
         GEdge gedge = (GEdge) element;
         ElkEdge elkEdge = context.edgeMap.get(gedge);
         if (elkEdge != null) {
            transferEdgeLayout(gedge, elkEdge, context);
         }
      } else if (element instanceof GLabel) {
         GLabel glabel = (GLabel) element;
         ElkLabel elkLabel = (ElkLabel) context.shapeMap.get(glabel);
         if (elkLabel != null) {
            transferLabelLayout(glabel, elkLabel, context);
         }
      }
      if (element.getChildren() != null) {
         for (GModelElement child : element.getChildren()) {
            transferLayout(child, context);
         }
      }
   }

   /*
    * Apply the computed ELK layout to the given sprotty graph.
    */
   protected void transferGraphLayout(final GGraph ggraph, final ElkNode elkGraph, final LayoutContext context) {
      ggraph.setPosition(point(elkGraph.getX(), elkGraph.getY()));
      ggraph.setSize(dimension(elkGraph.getWidth(), elkGraph.getHeight()));
   }

   /*
    * Apply the computed ELK layout to the given sprotty node.
    */
   protected void transferNodeLayout(final GNode snode, final ElkNode elkNode, final LayoutContext context) {
      GPoint offset = getOffset(snode, elkNode, context);
      snode.setPosition(point(elkNode.getX() + offset.getX(), elkNode.getY() + offset.getY()));
      snode.setSize(dimension(elkNode.getWidth(), elkNode.getHeight()));
   }

   /*
    * Apply the computed ELK layout to the given sprotty port.
    */
   protected void transferPortLayout(final GPort gport, final ElkPort elkPort, final LayoutContext context) {
      GPoint offset = getOffset(gport, elkPort, context);
      gport.setPosition(point(elkPort.getX() + offset.getX(), elkPort.getY() + offset.getY()));
      gport.setSize(dimension(elkPort.getWidth(), elkPort.getHeight()));
   }

   /*
    * Apply the computed ELK layout to the given sprotty label.
    */
   protected void transferLabelLayout(final GLabel glabel, final ElkLabel elkLabel, final LayoutContext context) {
      GPoint offset = getOffset(glabel, elkLabel, context);
      glabel.setPosition(point(elkLabel.getX() + offset.getX(), elkLabel.getY() + offset.getY()));
      glabel.setSize(dimension(elkLabel.getWidth(), elkLabel.getHeight()));
   }

   /*
    * Apply the computed ELK layout to the given sprotty edge.
    */
   protected void transferEdgeLayout(final GEdge gedge, final ElkEdge elkEdge, final LayoutContext context) {
      if (!elkEdge.getSections().isEmpty()) {
         GPoint offset = getOffset(gedge, elkEdge, context);
         ElkEdgeSection section = elkEdge.getSections().get(0);
         List<GPoint> newRoutingPoints = new ArrayList<>();
         GPoint p1 = point(section.getStartX() + offset.getX(), section.getStartY() + offset.getY());
         newRoutingPoints.add(p1);
         for (ElkBendPoint bendGPoint : section.getBendPoints()) {
            GPoint p2 = point(bendGPoint.getX() + offset.getX(), bendGPoint.getY() + offset.getY());
            newRoutingPoints.add(p2);
         }
         GPoint p3 = point(section.getEndX() + offset.getX(), section.getEndY() + offset.getY());
         newRoutingPoints.add(p3);
         EList<GPoint> routingPoints = gedge.getRoutingPoints();
         routingPoints.clear();
         routingPoints.addAll(newRoutingPoints);
      }
   }

   /*
    * Compute the offset for applying a computed ELK layout to a sprotty model
    * element. Such an offset can occur when the two elements are put into
    * containers with different coordinate systems.
    */
   @SuppressWarnings({ "checkstyle:CyclomaticComplexity", "checkstyle:NestedIfDepth",
      "checkstyle:NPathComplexity", "checkstyle:JavaNCSS" })

   protected GPoint getOffset(final GModelElement gelem, final ElkGraphElement elkElem, final LayoutContext context) {
      // Build a list of parents of the sprotty model element
      LinkedList<GModelElement> sParents = null;
      GModelElement currentSParent = gelem;
      while (currentSParent != null) {
         currentSParent = context.parentMap.get(currentSParent);
         if (currentSParent != null) {
            ElkShape shapeForSParent = context.shapeMap.get(currentSParent);
            if (shapeForSParent == elkElem.eContainer()) {
               // Shortcut: the current sprotty parent matches the ELK container
               double x = 0, y = 0;
               if (sParents != null) {
                  for (GModelElement sParent : sParents) {
                     if (sParent instanceof GBoundsAware) {
                        GPoint position = ((GBoundsAware) sParent).getPosition();
                        x -= position.getX();
                        y -= position.getY();
                     }
                  }
               }
               return point(x, y);
            }
            if (sParents == null) {
               sParents = new LinkedList<>();
            }
            sParents.addFirst(currentSParent);
         }
      }

      // Build a list of parents of the ELK graph element
      LinkedList<EObject> elkParents = new LinkedList<>();
      EObject currentElkParent = elkElem;
      while (currentElkParent != null) {
         currentElkParent = currentElkParent.eContainer();
         if (currentElkParent != null) {
            elkParents.addFirst(currentElkParent);
         }
      }

      boolean foundMismatch = false;
      do {
         // Find the next sprotty parent that is connected to a shape
         ElkShape shapeForSParent = null;
         int nextSParentIndex = 0;
         while (shapeForSParent == null && nextSParentIndex < sParents.size()) {
            shapeForSParent = context.shapeMap.get(sParents.get(nextSParentIndex++));
         }
         // Find the next ELK parent that is a shape
         ElkShape elkParentShape = null;
         while (elkParentShape == null && !elkParents.isEmpty()) {
            EObject elkParent = elkParents.getFirst();
            if (elkParent instanceof ElkShape) {
               elkParentShape = (ElkShape) elkParent;
            } else {
               elkParents.removeFirst();
            }
         }
         // Remove the current parents if they match
         if (shapeForSParent != null && shapeForSParent == elkParentShape) {
            for (int i = 0; i < nextSParentIndex; i++) {
               sParents.removeFirst();
            }
            elkParents.removeFirst();
         } else {
            foundMismatch = true;
         }
      } while (!foundMismatch && !sParents.isEmpty() && !elkParents.isEmpty());

      double x = 0, y = 0;
      // Add the remaining ELK shapes to the offset
      for (EObject elkParent : elkParents) {
         if (elkParent instanceof ElkShape) {
            ElkShape elkShape = (ElkShape) elkParent;
            x += elkShape.getX();
            y += elkShape.getY();
         }
      }
      // Subtract the remaining sprotty shapes from the offset
      for (GModelElement sParent : sParents) {
         if (sParent instanceof GBoundsAware) {
            GPoint position = ((GBoundsAware) sParent).getPosition();
            x -= position.getX();
            y -= position.getY();
         }
      }
      return point(x, y);
   }

   /**
    * Data required for applying the computed ELK layout to the original sprotty
    * model.
    */
   @SuppressWarnings("checkstyle:VisibilityModifier")
   protected static class LayoutContext {
      public GGraph ggraph;
      public ElkNode elkGraph;
      public final Map<GModelElement, GModelElement> parentMap = Maps.newHashMap();
      public final Map<GModelElement, ElkShape> shapeMap = Maps.newLinkedHashMap();
      public final Map<GEdge, ElkEdge> edgeMap = Maps.newLinkedHashMap();
   }

}
