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

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

public class DefaultEditingDomainFactory implements EMFEditingDomainFactory {

   @Override
   public EditingDomain createEditingDomain() {
      return new AdapterFactoryEditingDomain(createAdapterFactory(), createCommandStack(), createResourceSet());
   }

   protected BasicCommandStack createCommandStack() {
      return new BasicCommandStack();
   }

   protected AdapterFactory createAdapterFactory() {
      return new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
   }

   protected ResourceSet createResourceSet() {
      return new ResourceSetImpl();
   }

}
