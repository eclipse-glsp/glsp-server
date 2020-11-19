/********************************************************************************
 * Copyright (c) 2020 EclipseSource and others.
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
package org.eclipse.glsp.server.operations.gmodel;

import static org.eclipse.glsp.server.utils.GModelUtil.filterByType;
import static org.eclipse.glsp.server.utils.GModelUtil.shift;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.glsp.graph.GBoundsAware;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.impl.GPointImpl;
import org.eclipse.glsp.server.jsonrpc.GraphGsonConfiguratorFactory;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.operations.BasicOperationHandler;
import org.eclipse.glsp.server.operations.PasteOperation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;

public class PasteOperationHandler extends BasicOperationHandler<PasteOperation> {

   private static final int DEFAULT_OFFSET = 20;

   protected final Gson gson;

   @Inject
   public PasteOperationHandler(final GraphGsonConfiguratorFactory gsonFactory) {
      GsonBuilder builder = gsonFactory.configureGson();
      gson = builder.create();
   }

   @Override
   public void executeOperation(final PasteOperation operation, final GModelState modelState) {
      List<GModelElement> elements = getCopiedElements(operation.getClipboardData().get("application/json"));

      shift(elements, computeOffset(elements, operation.getEditorContext().getLastMousePosition()));

      Map<String, String> idMap = reassignIds(elements);
      filterElements(elements, idMap);
      rewireEdges(elements, idMap);

      modelState.getRoot().getChildren().addAll(elements);
   }

   protected ArrayList<GModelElement> getCopiedElements(final String jsonString) {
      return new ArrayList<>(Arrays.asList(gson.fromJson(jsonString, GModelElement[].class)));
   }

   protected GPoint computeOffset(final List<GModelElement> elements, final Optional<GPoint> lastMousePosition) {
      GPoint offset = new GPointImpl();
      offset.setX(DEFAULT_OFFSET);
      offset.setY(DEFAULT_OFFSET);
      if (lastMousePosition.isPresent()) {
         Optional<GBoundsAware> referenceElement = getReferenceElementForPasteOffset(elements);
         if (referenceElement.isPresent()) {
            offset.setX(lastMousePosition.get().getX() - referenceElement.get().getPosition().getX());
            offset.setY(lastMousePosition.get().getY() - referenceElement.get().getPosition().getY());
         }
      }
      return offset;
   }

   protected Optional<GBoundsAware> getReferenceElementForPasteOffset(final List<GModelElement> elements) {
      double minY = Double.MAX_VALUE;
      // use top most element as a reference for the offset by default
      for (GModelElement element : elements) {
         if (element instanceof GBoundsAware) {
            GBoundsAware boundsAware = (GBoundsAware) element;
            if (minY > boundsAware.getPosition().getY()) {
               minY = boundsAware.getPosition().getY();
               return Optional.of(boundsAware);
            }
         }
      }
      return Optional.empty();
   }

   protected Map<String, String> reassignIds(final List<GModelElement> elements) {
      Map<String, String> idMap = new HashMap<>();
      for (GModelElement element : elements) {
         String newId = UUID.randomUUID().toString();
         idMap.put(element.getId(), newId);
         element.setId(newId);
         idMap.putAll(reassignIds(element.getChildren()));
      }
      return idMap;
   }

   protected void filterElements(final List<GModelElement> elements,
      final Map<String, String> idMap) {
      List<GModelElement> toFilter = elements.stream().filter(e -> this.shouldExcludeElement(e, idMap))
         .collect(Collectors.toList());
      elements.removeAll(toFilter);
   }

   protected boolean shouldExcludeElement(final GModelElement element, final Map<String, String> idMap) {
      return false;
   }

   protected void rewireEdges(final List<GModelElement> elements, final Map<String, String> idMap) {
      filterByType(elements, GEdge.class).forEach(edge -> {
         if (idMap.containsKey(edge.getSourceId())) {
            edge.setSourceId(idMap.get(edge.getSourceId()));
         }
         if (idMap.containsKey(edge.getTargetId())) {
            edge.setTargetId(idMap.get(edge.getTargetId()));
         }
      });
   }
}
