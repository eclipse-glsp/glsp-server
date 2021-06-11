/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
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
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.glsp.graph.GLayoutData;
import org.eclipse.glsp.graph.GraphFactory;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Custom TypeAdapter for {@link GLayoutData}. Workaround for Issue 393
 * (https://github.com/eclipse-glsp/glsp/issues/393)
 */
public class LayoutDataTypeAdapter extends TypeAdapter<GLayoutData> {

   private static final Logger LOG = Logger.getLogger(LayoutDataTypeAdapter.class);

   private final Gson gson;

   @SuppressWarnings("unchecked")
   public static class Factory implements TypeAdapterFactory {

      @Override
      public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
         if (!GLayoutData.class.isAssignableFrom(type.getRawType())) {
            return null;
         }
         return (TypeAdapter<T>) new LayoutDataTypeAdapter(gson);
      }

   }

   public LayoutDataTypeAdapter(final Gson gson) {
      this.gson = gson;
   }

   @Override
   @SuppressWarnings("checkstyle:CyclomaticComplexity")
   public GLayoutData read(final JsonReader in) throws IOException {
      try {
         in.beginObject();
         GLayoutData result = GraphFactory.eINSTANCE.createGLayoutData();
         while (in.hasNext()) {
            String propertyName = in.nextName();
            assignProperty(result, propertyName, in);
         }
         in.endObject();
         return result;
      } catch (IllegalAccessException e) {
         throw new RuntimeException(e);
      }
   }

   protected void assignProperty(final GLayoutData instance, final String propertyName, final JsonReader in)
      throws IllegalAccessException, IOException {
      try {
         Field field = findField(instance.getClass(), propertyName);
         Object value = gson.fromJson(in, field.getGenericType());
         field.set(instance, value);
      } catch (NoSuchFieldException e) {
         // Ignore this property
         in.skipValue();
         LOG.error(e);
      }
   }

   protected Field findField(final Class<?> type, final String propertyName) throws NoSuchFieldException {
      try {
         Field field = type.getDeclaredField(propertyName);
         field.setAccessible(true);
         return field;
      } catch (NoSuchFieldException e) {
         Class<?> superType = type.getSuperclass();
         if (superType != null) {
            return findField(superType, propertyName);
         }
         throw e;
      }
   }

   @Override
   public void write(final JsonWriter out, final GLayoutData value) throws IOException {
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

   @SuppressWarnings({ "checkstyle:CyclomaticComplexity", "restriction" })
   protected void writeProperties(final JsonWriter out, final GLayoutData instance, final Class<?> type,
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

   protected void writeProperty(final JsonWriter out, final GLayoutData instance, final Field field)
      throws IOException, IllegalAccessException {
      field.setAccessible(true);
      out.name(field.getName());
      Object value = field.get(instance);
      if (value == null) {
         out.nullValue();
      } else if (value == instance) {
         throw new RuntimeException("Object has a reference to itself.");
      } else {
         gson.toJson(value, value.getClass(), out);
      }
   }

   protected boolean isSet(final GLayoutData instance, final Field field) {
      return instance.eIsSet(instance.eClass().getEStructuralFeature(field.getName()));
   }
}
