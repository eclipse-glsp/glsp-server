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

import org.eclipse.glsp.graph.GraphExtension;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.actions.ActionHandlerRegistry;
import org.eclipse.glsp.server.actions.ActionRegistry;
import org.eclipse.glsp.server.diagram.DiagramConfigurationRegistry;
import org.eclipse.glsp.server.features.commandpalette.CommandPaletteActionProvider;
import org.eclipse.glsp.server.features.contextactions.ContextActionsProviderRegistry;
import org.eclipse.glsp.server.features.contextmenu.ContextMenuItemProvider;
import org.eclipse.glsp.server.features.core.model.ModelFactory;
import org.eclipse.glsp.server.features.directediting.ContextEditValidatorRegistry;
import org.eclipse.glsp.server.features.directediting.LabelEditValidator;
import org.eclipse.glsp.server.features.modelsourcewatcher.ModelSourceWatcher;
import org.eclipse.glsp.server.features.navigation.NavigationTargetProviderRegistry;
import org.eclipse.glsp.server.features.navigation.NavigationTargetResolver;
import org.eclipse.glsp.server.features.popup.PopupModelFactory;
import org.eclipse.glsp.server.features.toolpalette.ToolPaletteItemProvider;
import org.eclipse.glsp.server.features.validation.ModelValidator;
import org.eclipse.glsp.server.jsonrpc.GraphGsonConfiguratorFactory;
import org.eclipse.glsp.server.layout.ILayoutEngine;
import org.eclipse.glsp.server.model.ModelStateProvider;
import org.eclipse.glsp.server.operations.OperationHandlerRegistry;
import org.eclipse.glsp.server.protocol.ClientSessionManager;
import org.eclipse.glsp.server.protocol.GLSPClient;
import org.eclipse.glsp.server.protocol.GLSPServer;

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
      bind(ToolPaletteItemProvider.class).to(bindToolPaletteItemProvider());
      bind(CommandPaletteActionProvider.class).to(bindCommandPaletteActionProvider());
      bind(ContextMenuItemProvider.class).to(bindContextMenuItemProvider());
      bind(NavigationTargetResolver.class).to(bindNavigationTargetResolver());
      bind(ModelSourceWatcher.class).to(bindModelSourceWatcher()).in(Singleton.class);
      bind(ClientSessionManager.class).toInstance(getClientSessionManager());
      // Configure set suppliers
      bind(ActionRegistry.class).to(bindActionRegistry()).in(Singleton.class);
      bind(ActionHandlerRegistry.class).to(bindActionHandlerRegistry()).in(Singleton.class);
      bind(OperationHandlerRegistry.class).to(bindOperationHandlerRegistry()).in(Singleton.class);
      bind(DiagramConfigurationRegistry.class).to(bindDiagramConfigurationRegistry()).in(Singleton.class);
      bind(ContextActionsProviderRegistry.class).to(bindContextActionsProviderRegistry()).in(Singleton.class);
      bind(NavigationTargetProviderRegistry.class).to(bindNavigationTargetProviderRegistry()).in(Singleton.class);
      bind(ContextEditValidatorRegistry.class).to(bindContextEditValidatorRegistry()).in(Singleton.class);

      // Configure Optional Bindings (Bindings that cannot be bound to a NullImpl)
      Optional.ofNullable(bindGraphExtension()).ifPresent(ext -> bind(GraphExtension.class).to(ext));
   }

   protected abstract ClientSessionManager getClientSessionManager();

   protected abstract Class<? extends ModelStateProvider> bindModelStateProvider();

   @SuppressWarnings("rawtypes")
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

   protected Class<? extends ModelSourceWatcher> bindModelSourceWatcher() {
      return ModelSourceWatcher.NullImpl.class;
   }

   protected abstract Class<? extends ActionRegistry> bindActionRegistry();

   protected abstract Class<? extends ActionHandlerRegistry> bindActionHandlerRegistry();

   protected abstract Class<? extends OperationHandlerRegistry> bindOperationHandlerRegistry();

   protected abstract Class<? extends DiagramConfigurationRegistry> bindDiagramConfigurationRegistry();

   protected abstract Class<? extends ContextActionsProviderRegistry> bindContextActionsProviderRegistry();

   protected abstract Class<? extends NavigationTargetProviderRegistry> bindNavigationTargetProviderRegistry();

   protected abstract Class<? extends ContextEditValidatorRegistry> bindContextEditValidatorRegistry();

   protected Class<? extends GraphExtension> bindGraphExtension() {
      return null;
   }

   @Provides
   @SuppressWarnings("rawtypes")
   private GLSPClient getGLSPClient(final GLSPServer glspServer) {
      return glspServer.getClient();
   }
}
