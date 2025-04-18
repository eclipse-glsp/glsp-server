/********************************************************************************
 * Copyright (c) 2019-2021 EclipseSource and others.
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.glsp.graph.DefaultTypes;
import org.eclipse.glsp.graph.GraphPackage;

import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;

public class GraphGsonConfigurator {

   public static final String DEFAULT_TYPE_ATT = "type";

   private final Map<String, EClass> typeMap = new HashMap<>();
   private final List<EPackage> ePackages = new ArrayList<>();

   public GraphGsonConfigurator() {
      withEPackages(GraphPackage.eINSTANCE);
   }

   public GraphGsonConfigurator withDefaultTypes() {
      return withTypes(DefaultTypes.getDefaultTypeMappings());
   }

   public GraphGsonConfigurator withTypes(final Map<String, EClass> types) {
      typeMap.putAll(types);
      return this;
   }

   public GraphGsonConfigurator withEPackages(final EPackage... packages) {
      ePackages.addAll(Arrays.asList(packages));
      return this;
   }

   public GsonBuilder configureGsonBuilder(final GsonBuilder gsonBuilder) {
      gsonBuilder.registerTypeAdapterFactory(new EMapTypeAdapter.Factory());
      gsonBuilder.registerTypeAdapterFactory(new GModelElementTypeAdapter.Factory(DEFAULT_TYPE_ATT, typeMap));
      gsonBuilder.registerTypeAdapter(EList.class, (InstanceCreator<EList<?>>) type -> new BasicEList<>());
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
