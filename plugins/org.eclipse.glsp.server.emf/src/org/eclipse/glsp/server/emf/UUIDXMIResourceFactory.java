/********************************************************************************
 * Copyright (c) 2022 EclipseSource and others.
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
package org.eclipse.glsp.server.emf;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

/**
 * A resource factory that uses UUIDs to identify elements within the resources instead of the ID attribute and ensures
 * that those IDs are not overridden during resource loading.
 */
public class UUIDXMIResourceFactory extends XMIResourceFactoryImpl {
   @Override
   public Resource createResource(final URI uri) {
      return new Resource(uri);
   }

   public static class Resource extends XMIResourceImpl {
      public Resource(final URI uri) {
         super(uri);
      }

      @Override
      protected boolean useIDAttributes() {
         return false;
      }

      @Override
      protected boolean useUUIDs() {
         return true;
      }

      @Override
      protected boolean assignIDsWhileLoading() {
         return false;
      }
   }
}
