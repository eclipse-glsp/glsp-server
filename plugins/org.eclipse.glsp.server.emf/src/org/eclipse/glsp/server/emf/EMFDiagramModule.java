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

import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.di.DiagramModule;
import org.eclipse.glsp.server.di.MultiBinding;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.operations.CompoundOperationHandler;
import org.eclipse.glsp.server.operations.OperationActionHandler;
import org.eclipse.glsp.server.operations.OperationHandler;

import com.google.inject.Singleton;

/**
 * Base module for diagram implementations that operate on EMF resources.
 */
public abstract class EMFDiagramModule extends DiagramModule {

   @Override
   protected void configureBase() {
      super.configureBase();
      configureEMFEditingDomainFactory(bindEMFEditingDomainFactory());
      configureEMFIdGenerator(bindEMFIdGenerator());
   }

   protected Class<? extends EMFEditingDomainFactory> bindEMFEditingDomainFactory() {
      return DefaultEditingDomainFactory.class;
   }

   protected void configureEMFEditingDomainFactory(
      final Class<? extends EMFEditingDomainFactory> editingDomainFactoryClass) {
      bind(editingDomainFactoryClass).in(Singleton.class);
      bind(EMFEditingDomainFactory.class).to(editingDomainFactoryClass);
   }

   protected abstract Class<? extends EMFIdGenerator> bindEMFIdGenerator();

   protected void configureEMFIdGenerator(final Class<? extends EMFIdGenerator> generatorClass) {
      bind(generatorClass).in(Singleton.class);
      bind(EMFIdGenerator.class).to(generatorClass);
   }

   @Override
   protected Class<? extends EMFModelState> bindGModelState() {
      return EMFModelState.class;
   }

   @Override
   protected Class<? extends EMFSourceModelStorage> bindSourceModelStorage() {
      return EMFSourceModelStorage.class;
   }

   @Override
   @SuppressWarnings("unchecked")
   protected void configureGModelState(final Class<? extends GModelState> gmodelStateClass) {
      super.configureGModelState(gmodelStateClass);
      bind(EMFModelState.class).to((Class<? extends EMFModelState>) gmodelStateClass);
   }

   @Override
   protected void configureActionHandlers(final MultiBinding<ActionHandler> binding) {
      super.configureActionHandlers(binding);
      binding.rebind(OperationActionHandler.class, EMFOperationActionHandler.class);
   }

   @Override
   protected void configureOperationHandlers(final MultiBinding<OperationHandler> binding) {
      super.configureOperationHandlers(binding);
      binding.rebind(CompoundOperationHandler.class, EMFCompoundOperationHandler.class);
   }
}
