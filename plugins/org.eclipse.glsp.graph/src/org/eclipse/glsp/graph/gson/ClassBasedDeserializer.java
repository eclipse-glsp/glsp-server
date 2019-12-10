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

import java.lang.reflect.Type;

import org.eclipse.emf.ecore.EObject;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class ClassBasedDeserializer implements JsonDeserializer<EObject> {

   private Class<? extends EObject> clazz;

   public ClassBasedDeserializer(Class<? extends EObject> clazz) {
      this.clazz = clazz;
   }

   @Override
   public EObject deserialize(JsonElement json, Type type, JsonDeserializationContext context)
      throws JsonParseException {
      return context.deserialize(json, clazz);
   }

}
