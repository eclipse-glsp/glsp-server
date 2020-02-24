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
package org.eclipse.glsp.server.di;

import java.util.function.Consumer;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.ActionProcessor;
import org.eclipse.glsp.api.di.GLSPModule;
import org.eclipse.glsp.api.diagram.DiagramConfiguration;
import org.eclipse.glsp.api.factory.GraphGsonConfiguratorFactory;
import org.eclipse.glsp.api.factory.ModelFactory;
import org.eclipse.glsp.api.handler.ActionHandler;
import org.eclipse.glsp.api.handler.OperationHandler;
import org.eclipse.glsp.api.handler.ServerCommandHandler;
import org.eclipse.glsp.api.jsonrpc.GLSPClientProvider;
import org.eclipse.glsp.api.jsonrpc.GLSPServer;
import org.eclipse.glsp.api.model.ModelStateProvider;
import org.eclipse.glsp.api.provider.ContextActionsProvider;
import org.eclipse.glsp.api.provider.ToolPaletteItemProvider;
import org.eclipse.glsp.api.registry.ActionHandlerRegistry;
import org.eclipse.glsp.api.registry.ActionRegistry;
import org.eclipse.glsp.api.registry.ContextActionsProviderRegistry;
import org.eclipse.glsp.api.registry.DiagramConfigurationRegistry;
import org.eclipse.glsp.api.registry.OperationHandlerRegistry;
import org.eclipse.glsp.api.registry.ServerCommandHandlerRegistry;
import org.eclipse.glsp.server.action.DefaultActionProcessor;
import org.eclipse.glsp.server.factory.DefaultGraphGsonConfiguratorFactory;
import org.eclipse.glsp.server.jsonrpc.DefaultGLSPClientProvider;
import org.eclipse.glsp.server.jsonrpc.DefaultGLSPServer;
import org.eclipse.glsp.server.model.DefaultModelStateProvider;
import org.eclipse.glsp.server.model.FileBasedModelFactory;
import org.eclipse.glsp.server.provider.DefaultToolPaletteItemProvider;
import org.eclipse.glsp.server.registry.DIActionHandlerRegistry;
import org.eclipse.glsp.server.registry.DIActionRegistry;
import org.eclipse.glsp.server.registry.DIContextActionsProviderRegistry;
import org.eclipse.glsp.server.registry.DIDiagramConfigurationRegistry;
import org.eclipse.glsp.server.registry.DIOperationHandlerRegistry;
import org.eclipse.glsp.server.registry.DIServerCommandHandlerRegistry;

public abstract class DefaultGLSPModule extends GLSPModule {

   @Override
   public void configure() {
      super.configure();
      // Configure MultiBindings
      configureMultiBindConfigs();
   }

   protected void configureMultiBindConfigs() {
      configure(MultiBindConfig.create(Action.class), this::configureActions);
      configure(MultiBindConfig.create(ActionHandler.class), this::configureActionHandlers);
      configure(MultiBindConfig.create(ServerCommandHandler.class), this::configureServerCommandHandlers);
      configure(MultiBindConfig.create(OperationHandler.class), this::configureOperationHandlers);
      configure(MultiBindConfig.create(DiagramConfiguration.class), this::configureDiagramConfigurations);
      configure(MultiBindConfig.create(ContextActionsProvider.class), this::configureContextActionsProviders);
   }

   protected <T> void configure(final MultiBindConfig<T> multiBindings,
      final Consumer<MultiBindConfig<T>> configurator) {
      configurator.accept(multiBindings);
      multiBindings.applyBinding(binder());
   }

   protected abstract void configureDiagramConfigurations(MultiBindConfig<DiagramConfiguration> config);

   protected void configureServerCommandHandlers(final MultiBindConfig<ServerCommandHandler> bindings) {}

   protected void configureActions(final MultiBindConfig<Action> bindings) {
      bindings.addAll(MultiBindingDefaults.DEFAULT_ACTIONS);
   }

   protected void configureActionHandlers(final MultiBindConfig<ActionHandler> bindings) {
      bindings.addAll(MultiBindingDefaults.DEFAULT_ACTION_HANDLERS);
   }

   protected void configureOperationHandlers(
      final MultiBindConfig<OperationHandler> bindings) {
      bindings.addAll(MultiBindingDefaults.DEFAULT_OPERATION_HANDLERS);
   }

   protected void configureContextActionsProviders(final MultiBindConfig<ContextActionsProvider> config) {}

   @Override
   protected Class<? extends GLSPServer> bindGLSPServer() {
      return DefaultGLSPServer.class;
   }

   @Override
   protected Class<? extends GraphGsonConfiguratorFactory> bindGraphGsonConfiguratorFactory() {
      return DefaultGraphGsonConfiguratorFactory.class;
   }

   @Override
   protected Class<? extends ModelFactory> bindModelFactory() {
      return FileBasedModelFactory.class;
   }

   @Override
   protected Class<? extends ModelStateProvider> bindModelStateProvider() {
      return DefaultModelStateProvider.class;
   }

   @Override
   protected Class<? extends ActionProcessor> bindActionProcessor() {
      return DefaultActionProcessor.class;
   }

   @Override
   protected Class<? extends GLSPClientProvider> bindGSLPClientProvider() {
      return DefaultGLSPClientProvider.class;
   }

   @Override
   protected Class<? extends ToolPaletteItemProvider> bindToolPaletteItemProvider() {
      return DefaultToolPaletteItemProvider.class;
   }

   @Override
   protected Class<? extends ActionRegistry> bindActionRegistry() {
      return DIActionRegistry.class;
   }

   @Override
   protected Class<? extends DiagramConfigurationRegistry> bindDiagramConfigurationRegistry() {
      return DIDiagramConfigurationRegistry.class;
   }

   @Override
   protected Class<? extends ActionHandlerRegistry> bindActionHandlerRegistry() {
      return DIActionHandlerRegistry.class;
   }

   @Override
   protected Class<? extends OperationHandlerRegistry> bindOperationHandlerRegistry() {
      return DIOperationHandlerRegistry.class;
   }

   @Override
   protected Class<? extends ContextActionsProviderRegistry> bindContextActionsProviderRegistry() {
      return DIContextActionsProviderRegistry.class;
   }

   @Override
   protected Class<? extends ServerCommandHandlerRegistry> bindServerCommandHandlerRegistry() {
      return DIServerCommandHandlerRegistry.class;
   }

}
