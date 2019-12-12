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
package org.eclipse.glsp.graph.gson;

import static org.eclipse.glsp.graph.DefaultTypes.COMPARTMENT;
import static org.eclipse.glsp.graph.DefaultTypes.EDGE;
import static org.eclipse.glsp.graph.DefaultTypes.GRAPH;
import static org.eclipse.glsp.graph.DefaultTypes.LABEL;
import static org.eclipse.glsp.graph.DefaultTypes.NODE;
import static org.eclipse.glsp.graph.GraphPackage.Literals.GCOMPARTMENT;
import static org.eclipse.glsp.graph.GraphPackage.Literals.GEDGE;
import static org.eclipse.glsp.graph.GraphPackage.Literals.GGRAPH;
import static org.eclipse.glsp.graph.GraphPackage.Literals.GLABEL;
import static org.eclipse.glsp.graph.GraphPackage.Literals.GNODE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.glsp.graph.GraphPackage;

import com.google.gson.GsonBuilder;

public class GGraphGsonConfigurator {

   public static final String DEFAULT_TYPE_ATT = "type";

   private final Map<String, EClass> typeMap = new HashMap<>();
   private final List<EPackage> ePackages = new ArrayList<>();

   public GGraphGsonConfigurator() {
      withEPackages(GraphPackage.eINSTANCE);
   }

   public GGraphGsonConfigurator withDefaultTypes() {
      Map<String, EClass> defaultTypes = new HashMap<>();
      defaultTypes.put(GRAPH, GGRAPH);
      defaultTypes.put(NODE, GNODE);
      defaultTypes.put(EDGE, GEDGE);
      defaultTypes.put(COMPARTMENT, GCOMPARTMENT);
      defaultTypes.put(LABEL, GLABEL);
      return withTypes(defaultTypes);
   }

   public GGraphGsonConfigurator withTypes(final Map<String, EClass> types) {
      typeMap.putAll(types);
      return this;
   }

   public GGraphGsonConfigurator withEPackages(final EPackage... packages) {
      ePackages.addAll(Arrays.asList(packages));
      return this;
   }

   public GsonBuilder configureGsonBuilder(final GsonBuilder gsonBuilder) {
      gsonBuilder.registerTypeAdapterFactory(new EMapTypeAdapter.Factory());
      gsonBuilder.registerTypeAdapterFactory(new GModelElementTypeAdapter.Factory(DEFAULT_TYPE_ATT, typeMap));
      configureClassesOfPackages(gsonBuilder);
      gsonBuilder.addSerializationExclusionStrategy(new EObjectExclusionStrategy());
      return gsonBuilder;
   }

   protected void configureClassesOfPackages(final GsonBuilder gsonBuilder) {
      for (EPackage pkg : ePackages) {
         for (EClassifier classifier : pkg.getEClassifiers()) {
            if (classifier instanceof EClass && !((EClass) classifier).isAbstract()) {
               Class<? extends EObject> implClass = getImplementationClass((EClass) classifier, pkg);
               gsonBuilder.registerTypeAdapter(classifier.getInstanceClass(),
                  new ClassBasedDeserializer(implClass));
            }
         }
      }
   }

   private Class<? extends EObject> getImplementationClass(final EClass eClass, final EPackage pkg) {
      return pkg.getEFactoryInstance().create(eClass).getClass();
   }

}
