/*******************************************************************************
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
 ******************************************************************************/
package org.eclipse.glsp.api.di;

import java.util.Optional;
import java.util.function.Consumer;

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.ActionProcessor;
import org.eclipse.glsp.api.configuration.ServerConfiguration;
import org.eclipse.glsp.api.diagram.DiagramConfiguration;
import org.eclipse.glsp.api.diagram.DiagramConfigurationProvider;
import org.eclipse.glsp.api.factory.GraphGsonConfiguratorFactory;
import org.eclipse.glsp.api.factory.ModelFactory;
import org.eclipse.glsp.api.factory.PopupModelFactory;
import org.eclipse.glsp.api.handler.ActionHandler;
import org.eclipse.glsp.api.handler.OperationHandler;
import org.eclipse.glsp.api.handler.ServerCommandHandler;
import org.eclipse.glsp.api.jsonrpc.GLSPClientProvider;
import org.eclipse.glsp.api.jsonrpc.GLSPServer;
import org.eclipse.glsp.api.labeledit.LabelEditValidator;
import org.eclipse.glsp.api.layout.ILayoutEngine;
import org.eclipse.glsp.api.markers.ModelValidator;
import org.eclipse.glsp.api.model.ModelElementOpenListener;
import org.eclipse.glsp.api.model.ModelExpansionListener;
import org.eclipse.glsp.api.model.ModelSelectionListener;
import org.eclipse.glsp.api.model.ModelStateProvider;
import org.eclipse.glsp.api.provider.ActionHandlerProvider;
import org.eclipse.glsp.api.provider.CommandPaletteActionProvider;
import org.eclipse.glsp.api.provider.ContextMenuItemProvider;
import org.eclipse.glsp.api.provider.OperationHandlerProvider;
import org.eclipse.glsp.api.provider.ServerCommandHandlerProvider;
import org.eclipse.glsp.graph.GraphExtension;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public abstract class GLSPModule extends AbstractModule {

   @Override
   protected void configure() {
      bind(GLSPServer.class).to(bindGLSPServer());
      bind(PopupModelFactory.class).to(bindPopupModelFactory());
      bind(ModelFactory.class).to(bindModelFactory());
      bind(ModelSelectionListener.class).to(bindModelSelectionListener());
      bind(ModelExpansionListener.class).to(bindModelExpansionListener());
      bind(ModelElementOpenListener.class).to(bindModelElementOpenListener());
      bind(ILayoutEngine.class).to(bindLayoutEngine());
      bind(ActionHandlerProvider.class).to(bindActionHandlerProvider());
      bind(OperationHandlerProvider.class).to(bindOperatioHandlerProvider());
      bind(ServerCommandHandlerProvider.class).to(bindServerCommandHandlerProvider());
      bind(CommandPaletteActionProvider.class).to(bindCommandPaletteActionProvider());
      bind(ContextMenuItemProvider.class).to(bindContextMenuItemProvider());
      bind(ModelValidator.class).to(bindModelValidator());
      bind(ActionProcessor.class).to(bindActionProcessor()).in(Singleton.class);
      bind(DiagramConfigurationProvider.class).to(bindDiagramConfigurationProvider());
      bind(LabelEditValidator.class).to(bindLabelEditValidator());
      bind(ModelStateProvider.class).to(bindModelStateProvider());
      bind(GraphGsonConfiguratorFactory.class).to(bindGraphGsonConfiguratorFactory());
      bind(GLSPClientProvider.class).to(bindGSLPClientProvider());
      bind(ServerConfiguration.class).to(bindServerConfiguration()).in(Singleton.class);

      configureMultibindings();

      // Optional Bindings
      Optional.ofNullable(bindGraphExtension()).ifPresent(ext -> bind(GraphExtension.class).to(ext));

   }

   protected void configureMultibindings() {

      configure(MultiBindings.create(Action.class), this::configureActions);
      configure(MultiBindings
         .create(ActionHandler.class), this::configureActionHandlers);
      configure(MultiBindings
         .create(ServerCommandHandler.class), this::configureServerCommandHandlers);
      configure(MultiBindings
         .create(OperationHandler.class), this::configureOperationHandlers);
      configure(MultiBindings
         .create(DiagramConfiguration.class), this::configureDiagramConfigurations);

   }

   private <T> void configure(final MultiBindings<T> multiBindings,
      final Consumer<MultiBindings<T>> configurator) {
      configurator.accept(multiBindings);
      multiBindings.applyBindings(binder());
   }

   protected abstract void configureActions(
      MultiBindings<Action> bindings);

   protected abstract void configureActionHandlers(
      MultiBindings<ActionHandler> bindings);

   protected abstract void configureServerCommandHandlers(
      MultiBindings<ServerCommandHandler> bindings);

   protected abstract void configureOperationHandlers(
      MultiBindings<OperationHandler> bindings);

   protected abstract void configureDiagramConfigurations(
      MultiBindings<DiagramConfiguration> bindings);

   protected abstract Class<? extends GLSPClientProvider> bindGSLPClientProvider();

   protected abstract Class<? extends ModelStateProvider> bindModelStateProvider();

   protected abstract Class<? extends DiagramConfigurationProvider> bindDiagramConfigurationProvider();

   protected abstract Class<? extends GLSPServer> bindGLSPServer();

   protected abstract Class<? extends GraphGsonConfiguratorFactory> bindGraphGsonConfiguratorFactory();

   protected Class<? extends CommandPaletteActionProvider> bindCommandPaletteActionProvider() {
      return CommandPaletteActionProvider.NullImpl.class;
   }

   protected Class<? extends ContextMenuItemProvider> bindContextMenuItemProvider() {
      return ContextMenuItemProvider.NullImpl.class;
   }

   protected Class<? extends ActionHandlerProvider> bindActionHandlerProvider() {
      return ActionHandlerProvider.NullImpl.class;
   }

   protected Class<? extends OperationHandlerProvider> bindOperatioHandlerProvider() {
      return OperationHandlerProvider.NullImpl.class;
   }

   protected Class<? extends ModelExpansionListener> bindModelExpansionListener() {
      return ModelExpansionListener.NullImpl.class;
   }

   protected Class<? extends ModelFactory> bindModelFactory() {
      return ModelFactory.NullImpl.class;
   }

   protected Class<? extends ModelSelectionListener> bindModelSelectionListener() {
      return ModelSelectionListener.NullImpl.class;
   }

   protected Class<? extends ModelElementOpenListener> bindModelElementOpenListener() {
      return ModelElementOpenListener.NullImpl.class;
   }

   protected Class<? extends PopupModelFactory> bindPopupModelFactory() {
      return PopupModelFactory.NullImpl.class;
   }

   protected Class<? extends ILayoutEngine> bindLayoutEngine() {
      return ILayoutEngine.NullImpl.class;
   }

   protected Class<? extends ServerCommandHandlerProvider> bindServerCommandHandlerProvider() {
      return ServerCommandHandlerProvider.NullImpl.class;
   }

   protected Class<? extends ModelValidator> bindModelValidator() {
      return ModelValidator.NullImpl.class;
   }

   protected Class<? extends ActionProcessor> bindActionProcessor() {
      return ActionProcessor.NullImpl.class;
   }

   protected Class<? extends LabelEditValidator> bindLabelEditValidator() {
      return LabelEditValidator.NullImpl.class;
   }

   protected Class<? extends GraphExtension> bindGraphExtension() {
      return null;
   }

   protected Class<? extends ServerConfiguration> bindServerConfiguration() {
      return ServerConfiguration.NullImpl.class;
   }
}
