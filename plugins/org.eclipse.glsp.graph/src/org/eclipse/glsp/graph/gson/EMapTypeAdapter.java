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
import java.util.Map;

import org.eclipse.emf.common.util.BasicEMap;
import org.eclipse.emf.common.util.EMap;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * Structurally an EMap is a list of map entries which is usually written as an array but we want to treat it as a
 * normal Map written as an object.
 */
public class EMapTypeAdapter<T> extends TypeAdapter<EMap<?, ?>> {

   private final Gson gson;

   public static class Factory implements TypeAdapterFactory {

      @Override
      @SuppressWarnings({ "unchecked", "rawtypes" })
      public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
         Class<?> rawType = typeToken.getRawType();
         if (!EMap.class.isAssignableFrom(rawType)) {
            return null;
         }

         return new EMapTypeAdapter(gson);
      }
   }

   public EMapTypeAdapter(final Gson gson) {
      this.gson = gson;
   }

   @Override
   public EMap<?, ?> read(final JsonReader in) throws IOException {
      JsonToken peek = in.peek();
      if (peek == JsonToken.NULL) {
         in.nextNull();
         return null;
      }

      TypeAdapter<?> mapAdapter = gson.getAdapter(TypeToken.get(Map.class));
      Map<?, ?> map = (Map<?, ?>) mapAdapter.read(in);
      return new BasicEMap<>(map);
   }

   @SuppressWarnings({ "unchecked", "rawtypes" })
   @Override
   public void write(final JsonWriter out, final EMap<?, ?> value) throws IOException {
      if (value == null) {
         out.nullValue();
         return;
      }

      Map<?, ?> map = value.map();
      TypeAdapter mapAdapter = gson.getAdapter(map.getClass());
      mapAdapter.write(out, map);
   }

}
