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
package org.eclipse.glsp.server.emf.notation;

import org.eclipse.glsp.server.di.MultiBinding;
import org.eclipse.glsp.server.emf.EMFDiagramModule;
import org.eclipse.glsp.server.emf.EMFSourceModelStorage;
import org.eclipse.glsp.server.operations.OperationHandler;

import com.google.inject.Singleton;

/**
 * Base module for diagram implementations that operate on EMF resources with a notation model.
 */
public abstract class EMFNotationDiagramModule extends EMFDiagramModule {

   @Override
   protected void configureBase() {
      super.configureBase();
      configureEMFSemanticIdConverter(bindEMFSemanticIdConverter());
   }

   protected Class<? extends EMFSemanticIdConverter> bindEMFSemanticIdConverter() {
      return EMFSemanticIdConverter.Default.class;
   }

   protected void configureEMFSemanticIdConverter(
      final Class<? extends EMFSemanticIdConverter> converterClass) {
      bind(converterClass).in(Singleton.class);
      bind(EMFSemanticIdConverter.class).to(converterClass);
   }

   @Override
   protected Class<? extends EMFNotationModelState> bindGModelState() {
      return EMFNotationModelState.class;
   }

   @Override
   protected Class<? extends EMFSourceModelStorage> bindSourceModelStorage() {
      return EMFNotationSourceModelStorage.class;
   }

   @Override
   protected void configureOperationHandlers(final MultiBinding<OperationHandler> binding) {
      super.configureOperationHandlers(binding);
      binding.add(EMFChangeBoundsOperationHandler.class);
   }

}
