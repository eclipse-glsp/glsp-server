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

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.glsp.graph.GModelElement;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class GModelElementTypeAdapter extends PropertyBasedTypeAdapter<GModelElement> {

   private static final Logger LOG = Logger.getLogger(GModelElementTypeAdapter.class);

   private final Gson gson;
   private final Map<String, EClass> typeMap;
   private String delimiter = ":";

   public static class Factory implements TypeAdapterFactory {

      private final String typeAttribute;
      private final Map<String, EClass> typeMap;

      public Factory(final String typeAttribute, final Map<String, EClass> typeMap) {
         this.typeAttribute = typeAttribute;
         this.typeMap = typeMap;
      }

      @Override
      @SuppressWarnings("unchecked")
      public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
         if (!GModelElement.class.isAssignableFrom(type.getRawType())) {
            return null;
         }
         return (TypeAdapter<T>) new GModelElementTypeAdapter(gson, typeAttribute, typeMap);
      }

   }

   public GModelElementTypeAdapter(final Gson gson, final String typeAttribute, final Map<String, EClass> typeMap) {
      super(gson, typeAttribute);
      this.gson = gson;
      this.typeMap = typeMap;
   }

   @Override
   protected GModelElement createInstance(final String type) {
      Optional<EClass> eClass = findEClassForType(type);
      if (eClass.isPresent()) {
         GModelElement element = (GModelElement) eClass.get().getEPackage().getEFactoryInstance().create(eClass.get());
         element.setType(type);
         return element;
      }
      LOG.error("Cannot find class for type " + type);
      return null;
   }

   protected Optional<EClass> findEClassForType(final String type) {
      EClass eClass = typeMap.get(type);
      if (eClass == null) {
         List<String> subtypes = Lists.newArrayList(type.split(delimiter));
         while (eClass == null && !subtypes.isEmpty()) {
            subtypes.remove(subtypes.size() - 1);
            eClass = typeMap.get(String.join(delimiter, subtypes));
         }
      }
      return Optional.ofNullable(eClass);
   }

   @Override
   public void write(final JsonWriter out, final GModelElement value) throws IOException {
      if (value == null) {
         out.nullValue();
      } else {
         try {
            out.beginObject();
            Set<String> written = new HashSet<>();
            writeProperties(out, value, value.getClass(), written);
            out.endObject();
         } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
         }
      }
   }

   @Override
   protected void assignProperty(final GModelElement instance, final String propertyName, final JsonReader in)
      throws IllegalAccessException {
      try {
         Field field = findField(instance.getClass(), propertyName);
         Object value = gson.fromJson(in, field.getGenericType());
         if (EList.class.isAssignableFrom(field.getType()) && value instanceof Collection) {
            assignEListProperty(instance, propertyName, (Collection<?>) value);
         } else {
            field.set(instance, value);
         }
      } catch (NoSuchFieldException e) {
         // Ignore this property
      }
   }

   @Override
   protected void assignProperty(final GModelElement instance, final String propertyName, final JsonElement element)
      throws IllegalAccessException {
      try {
         Field field = findField(instance.getClass(), propertyName);
         Object value = gson.fromJson(element, field.getGenericType());
         if (EList.class.isAssignableFrom(field.getType()) && value instanceof Collection) {
            assignEListProperty(instance, propertyName, (Collection<?>) value);
         } else {
            field.set(instance, value);
         }
      } catch (NoSuchFieldException e) {
         // Ignore this property
      }
   }

   @SuppressWarnings("unchecked")
   protected void assignEListProperty(final GModelElement instance, final String propertyName,
      final Collection<?> values) {
      // add data to the correctly initialized fields instead of overwriting the fields
      EStructuralFeature feature = instance.eClass().getEStructuralFeature(propertyName);
      Object list = instance.eGet(feature);
      if (list instanceof EMap<?, ?> && values instanceof EMap<?, ?>) {
         // use putAll to ensure the correct Map.Entry type is used
         ((EMap<Object, Object>) list).putAll((EMap<?, ?>) values);
      } else if (list instanceof List<?>) {
         ((List<Object>) list).addAll(values);
      }
   }

   @Override
   @SuppressWarnings({ "checkstyle:CyclomaticComplexity", "restriction" })
   protected void writeProperties(final JsonWriter out, final GModelElement instance, final Class<?> type,
      final Set<String> written)
      throws IOException, IllegalAccessException {
      for (Field field : type.getDeclaredFields()) {
         if (!gson.excluder().excludeField(field, true) && isSet(instance, field)) {
            int modifiers = field.getModifiers();
            if (!Modifier.isTransient(modifiers) && !Modifier.isStatic(modifiers) && written.add(field.getName())) {
               writeProperty(out, instance, field);
            }
         }
      }
      Class<?> superType = type.getSuperclass();
      if (superType != null) {
         writeProperties(out, instance, superType, written);
      }
   }

   protected boolean isSet(final GModelElement instance, final Field field) {
      return instance.eIsSet(instance.eClass().getEStructuralFeature(field.getName()));
   }

   public String getDelimiter() { return delimiter; }

   public void setDelimiter(final String delimiter) { this.delimiter = delimiter; }

}
