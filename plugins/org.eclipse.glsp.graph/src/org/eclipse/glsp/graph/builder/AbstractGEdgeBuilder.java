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
package org.eclipse.glsp.graph.builder;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.util.GraphUtil;

public abstract class AbstractGEdgeBuilder<T extends GEdge, E extends AbstractGEdgeBuilder<T, E>>
   extends GModelElementBuilder<T, E> {

   protected GModelElement source;
   protected String sourceId;
   protected GModelElement target;
   protected String targetId;
   protected List<GPoint> routingPoints = new ArrayList<>();
   protected String routerKind;

   public AbstractGEdgeBuilder(String type) {
      super(type);
   }

   public E source(GModelElement source) {
      this.source = source;
      this.sourceId = source.getId();
      return self();
   }

   public E sourceId(String sourceId) {
      this.sourceId = sourceId;
      return self();
   }

   public E target(GModelElement target) {
      this.target = target;
      this.targetId = target.getId();
      return self();
   }

   public E targetId(String targetId) {
      this.targetId = targetId;
      return self();
   }

   public E addRoutingPoint(GPoint point) {
      this.routingPoints.add(point);
      return self();
   }

   public E addRoutingPoint(double x, double y) {
      return addRoutingPoint(GraphUtil.point(x, y));
   }

   public E addRoutingPoints(List<GPoint> routingPoints) {
      this.routingPoints.addAll(routingPoints);
      return self();
   }

   public E routerKind(String routerKind) {
      this.routerKind = routerKind;
      return self();
   }

   @Override
   protected void setProperties(T edge) {
      super.setProperties(edge);
      edge.setSourceId(sourceId);
      edge.setTargetId(targetId);
      edge.setRouterKind(routerKind);
      if (source != null) {
         edge.setSource(source);
      }
      if (target != null) {
         edge.setTarget(target);

      }
      edge.getRoutingPoints().addAll(routingPoints);
   }

}
