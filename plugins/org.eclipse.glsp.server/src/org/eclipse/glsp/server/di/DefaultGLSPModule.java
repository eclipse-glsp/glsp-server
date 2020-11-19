/********************************************************************************
 * Copyright (c) 2019-2020 EclipseSource and others.
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

import static org.eclipse.glsp.server.actions.ClientActionHandler.CLIENT_ACTIONS;

import java.util.function.Consumer;

import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.actions.ActionHandlerRegistry;
import org.eclipse.glsp.server.actions.ActionRegistry;
import org.eclipse.glsp.server.actions.ClientActionHandler;
import org.eclipse.glsp.server.diagram.DiagramConfiguration;
import org.eclipse.glsp.server.diagram.DiagramConfigurationRegistry;
import org.eclipse.glsp.server.features.contextactions.ContextActionsProvider;
import org.eclipse.glsp.server.features.contextactions.ContextActionsProviderRegistry;
import org.eclipse.glsp.server.features.directediting.ContextEditValidator;
import org.eclipse.glsp.server.features.directediting.ContextEditValidatorRegistry;
import org.eclipse.glsp.server.features.navigation.NavigationTargetProvider;
import org.eclipse.glsp.server.features.navigation.NavigationTargetProviderRegistry;
import org.eclipse.glsp.server.features.toolpalette.DefaultToolPaletteItemProvider;
import org.eclipse.glsp.server.features.toolpalette.ToolPaletteItemProvider;
import org.eclipse.glsp.server.internal.action.DefaultActionDispatcher;
import org.eclipse.glsp.server.internal.di.DIActionHandlerRegistry;
import org.eclipse.glsp.server.internal.di.DIActionRegistry;
import org.eclipse.glsp.server.internal.di.DIContextActionsProviderRegistry;
import org.eclipse.glsp.server.internal.di.DIContextEditValidatorRegistry;
import org.eclipse.glsp.server.internal.di.DIDiagramConfigurationRegistry;
import org.eclipse.glsp.server.internal.di.DINavigationTargetProviderRegistry;
import org.eclipse.glsp.server.internal.di.DIOperationHandlerRegistry;
import org.eclipse.glsp.server.internal.di.MultiBindingDefaults;
import org.eclipse.glsp.server.internal.json.DefaultGraphGsonConfiguratorFactory;
import org.eclipse.glsp.server.jsonrpc.DefaultClientSessionManager;
import org.eclipse.glsp.server.jsonrpc.DefaultGLSPServer;
import org.eclipse.glsp.server.jsonrpc.GraphGsonConfiguratorFactory;
import org.eclipse.glsp.server.model.DefaultModelStateProvider;
import org.eclipse.glsp.server.model.ModelStateProvider;
import org.eclipse.glsp.server.operations.OperationHandler;
import org.eclipse.glsp.server.operations.OperationHandlerRegistry;
import org.eclipse.glsp.server.protocol.ClientSessionManager;
import org.eclipse.glsp.server.protocol.GLSPServer;
import org.eclipse.glsp.server.utils.MultiBinding;

public abstract class DefaultGLSPModule extends GLSPModule {

   @Override
   public void configure() {
      super.configure();
      configureMultiBinding();
   }

   protected void configureMultiBinding() {
      configure(MultiBinding.create(Action.class).setAnnotationName(CLIENT_ACTIONS), this::configureClientActions);
      configure(MultiBinding.create(ActionHandler.class), this::configureActionHandlers);
      configure(MultiBinding.create(OperationHandler.class), this::configureOperationHandlers);
      configure(MultiBinding.create(DiagramConfiguration.class), this::configureDiagramConfigurations);
      configure(MultiBinding.create(ContextActionsProvider.class), this::configureContextActionsProviders);
      configure(MultiBinding.create(ContextEditValidator.class), this::configureContextEditValidators);
      configure(MultiBinding.create(NavigationTargetProvider.class), this::configureNavigationTargetProviders);
   }

   protected <T> void configure(final MultiBinding<T> binding, final Consumer<MultiBinding<T>> configurator) {
      configurator.accept(binding);
      binding.applyBinding(binder());
   }

   protected abstract void configureDiagramConfigurations(MultiBinding<DiagramConfiguration> binding);

   /**
    * Actions that will be handled by delegation to the client, e.g. via {@link ClientActionHandler}
    *
    * @param binding action bindings
    */
   protected void configureClientActions(final MultiBinding<Action> binding) {
      binding.addAll(MultiBindingDefaults.DEFAULT_CLIENT_ACTIONS);
   }

   /**
    * Actions that can be handled by the server.
    *
    * @param binding action handler bindings
    */
   protected void configureActionHandlers(final MultiBinding<ActionHandler> binding) {
      binding.addAll(MultiBindingDefaults.DEFAULT_ACTION_HANDLERS);
   }

   /***
    * Operations that can be handled by the server.
    *
    * @param binding operation handler bindings
    */
   protected void configureOperationHandlers(final MultiBinding<OperationHandler> binding) {
      binding.addAll(MultiBindingDefaults.DEFAULT_OPERATION_HANDLERS);
   }

   protected void configureContextActionsProviders(final MultiBinding<ContextActionsProvider> binding) {}

   protected void configureContextEditValidators(final MultiBinding<ContextEditValidator> binding) {}

   protected void configureNavigationTargetProviders(final MultiBinding<NavigationTargetProvider> binding) {}

   @Override
   @SuppressWarnings("rawtypes")
   protected Class<? extends GLSPServer> bindGLSPServer() {
      return DefaultGLSPServer.class;
   }

   @Override
   protected Class<? extends GraphGsonConfiguratorFactory> bindGraphGsonConfiguratorFactory() {
      return DefaultGraphGsonConfiguratorFactory.class;
   }

   @Override
   protected Class<? extends ModelStateProvider> bindModelStateProvider() {
      return DefaultModelStateProvider.class;
   }

   @Override
   protected Class<? extends ActionDispatcher> bindActionDispatcher() {
      return DefaultActionDispatcher.class;
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
   protected Class<? extends NavigationTargetProviderRegistry> bindNavigationTargetProviderRegistry() {
      return DINavigationTargetProviderRegistry.class;
   }

   @Override
   protected Class<? extends ContextEditValidatorRegistry> bindContextEditValidatorRegistry() {
      return DIContextEditValidatorRegistry.class;
   }

   @Override
   protected ClientSessionManager getClientSessionManager() { return DefaultClientSessionManager.INSTANCE; }

}
