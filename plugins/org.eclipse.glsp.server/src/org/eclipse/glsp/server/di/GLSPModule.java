/*******************************************************************************
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
 ******************************************************************************/
package org.eclipse.glsp.server.di;

import java.util.Optional;

import org.eclipse.glsp.api.action.ActionDispatcher;
import org.eclipse.glsp.api.configuration.ServerConfiguration;
import org.eclipse.glsp.api.factory.GraphGsonConfiguratorFactory;
import org.eclipse.glsp.api.factory.ModelFactory;
import org.eclipse.glsp.api.factory.PopupModelFactory;
import org.eclipse.glsp.api.labeledit.LabelEditValidator;
import org.eclipse.glsp.api.layout.ILayoutEngine;
import org.eclipse.glsp.api.markers.ModelValidator;
import org.eclipse.glsp.api.model.ModelStateProvider;
import org.eclipse.glsp.api.model.NavigationTargetResolver;
import org.eclipse.glsp.api.protocol.ClientSessionManager;
import org.eclipse.glsp.api.protocol.GLSPClient;
import org.eclipse.glsp.api.protocol.GLSPServer;
import org.eclipse.glsp.api.provider.CommandPaletteActionProvider;
import org.eclipse.glsp.api.provider.ContextMenuItemProvider;
import org.eclipse.glsp.api.provider.ToolPaletteItemProvider;
import org.eclipse.glsp.api.registry.ActionHandlerRegistry;
import org.eclipse.glsp.api.registry.ActionRegistry;
import org.eclipse.glsp.api.registry.ContextActionsProviderRegistry;
import org.eclipse.glsp.api.registry.ContextEditValidatorRegistry;
import org.eclipse.glsp.api.registry.DiagramConfigurationRegistry;
import org.eclipse.glsp.api.registry.NavigationTargetProviderRegistry;
import org.eclipse.glsp.api.registry.OperationHandlerRegistry;
import org.eclipse.glsp.api.registry.ServerCommandHandlerRegistry;
import org.eclipse.glsp.graph.GraphExtension;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public abstract class GLSPModule extends AbstractModule {

   @Override
   protected void configure() {
      // Configure default bindings
      bind(GLSPServer.class).to(bindGLSPServer()).in(Singleton.class);
      bind(PopupModelFactory.class).to(bindPopupModelFactory());
      bind(ModelFactory.class).to(bindModelFactory());
      bind(ILayoutEngine.class).to(bindLayoutEngine());
      bind(ModelValidator.class).to(bindModelValidator());
      bind(ActionDispatcher.class).to(bindActionDispatcher()).in(Singleton.class);
      bind(LabelEditValidator.class).to(bindLabelEditValidator());
      bind(ModelStateProvider.class).to(bindModelStateProvider());
      bind(GraphGsonConfiguratorFactory.class).to(bindGraphGsonConfiguratorFactory());
      bind(ServerConfiguration.class).to(bindServerConfiguration()).in(Singleton.class);
      bind(ToolPaletteItemProvider.class).to(bindToolPaletteItemProvider());
      bind(CommandPaletteActionProvider.class).to(bindCommandPaletteActionProvider());
      bind(ContextMenuItemProvider.class).to(bindContextMenuItemProvider());
      bind(NavigationTargetResolver.class).to(bindNavigationTargetResolver());
      bind(ClientSessionManager.class).toInstance(getClientSessionManager());
      // Configure set suppliers
      bind(ActionRegistry.class).to(bindActionRegistry()).in(Singleton.class);
      bind(ActionHandlerRegistry.class).to(bindActionHandlerRegistry()).in(Singleton.class);
      bind(OperationHandlerRegistry.class).to(bindOperationHandlerRegistry()).in(Singleton.class);
      bind(DiagramConfigurationRegistry.class).to(bindDiagramConfigurationRegistry()).in(Singleton.class);
      bind(ContextActionsProviderRegistry.class).to(bindContextActionsProviderRegistry()).in(Singleton.class);
      bind(NavigationTargetProviderRegistry.class).to(bindNavigationTargetProviderRegistry()).in(Singleton.class);
      bind(ContextEditValidatorRegistry.class).to(bindContextEditValidatorRegistry()).in(Singleton.class);
      bind(ServerCommandHandlerRegistry.class).to(bindServerCommandHandlerRegistry()).in(Singleton.class);

      // Configure Optional Bindings (Bindings that cannot be bound to a NullImpl)
      Optional.ofNullable(bindGraphExtension()).ifPresent(ext -> bind(GraphExtension.class).to(ext));
   }

   protected abstract ClientSessionManager getClientSessionManager();

   protected abstract Class<? extends ModelStateProvider> bindModelStateProvider();

   protected abstract Class<? extends GLSPServer> bindGLSPServer();

   protected abstract Class<? extends GraphGsonConfiguratorFactory> bindGraphGsonConfiguratorFactory();

   protected abstract Class<? extends ModelFactory> bindModelFactory();

   protected Class<? extends PopupModelFactory> bindPopupModelFactory() {
      return PopupModelFactory.NullImpl.class;
   }

   protected Class<? extends ILayoutEngine> bindLayoutEngine() {
      return ILayoutEngine.NullImpl.class;
   }

   protected Class<? extends ModelValidator> bindModelValidator() {
      return ModelValidator.NullImpl.class;
   }

   protected Class<? extends ActionDispatcher> bindActionDispatcher() {
      return ActionDispatcher.NullImpl.class;
   }

   protected Class<? extends LabelEditValidator> bindLabelEditValidator() {
      return LabelEditValidator.NullImpl.class;
   }

   protected Class<? extends ServerConfiguration> bindServerConfiguration() {
      return ServerConfiguration.NullImpl.class;
   }

   protected Class<? extends CommandPaletteActionProvider> bindCommandPaletteActionProvider() {
      return CommandPaletteActionProvider.NullImpl.class;
   }

   protected Class<? extends ContextMenuItemProvider> bindContextMenuItemProvider() {
      return ContextMenuItemProvider.NullImpl.class;
   }

   protected Class<? extends ToolPaletteItemProvider> bindToolPaletteItemProvider() {
      return ToolPaletteItemProvider.NullImpl.class;
   }

   protected Class<? extends NavigationTargetResolver> bindNavigationTargetResolver() {
      return NavigationTargetResolver.NullImpl.class;
   }

   protected abstract Class<? extends ActionRegistry> bindActionRegistry();

   protected abstract Class<? extends ActionHandlerRegistry> bindActionHandlerRegistry();

   protected abstract Class<? extends OperationHandlerRegistry> bindOperationHandlerRegistry();

   protected abstract Class<? extends DiagramConfigurationRegistry> bindDiagramConfigurationRegistry();

   protected abstract Class<? extends ContextActionsProviderRegistry> bindContextActionsProviderRegistry();

   protected abstract Class<? extends NavigationTargetProviderRegistry> bindNavigationTargetProviderRegistry();

   protected abstract Class<? extends ContextEditValidatorRegistry> bindContextEditValidatorRegistry();

   protected abstract Class<? extends ServerCommandHandlerRegistry> bindServerCommandHandlerRegistry();

   protected Class<? extends GraphExtension> bindGraphExtension() {
      return null;
   }

   @Provides
   private GLSPClient getGLSPClient(final GLSPServer glspServer) {
      return glspServer.getClient();
   }
}
