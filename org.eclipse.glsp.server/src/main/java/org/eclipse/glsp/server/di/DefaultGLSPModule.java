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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.glsp.api.action.ActionProcessor;
import org.eclipse.glsp.api.di.GLSPModule;
import org.eclipse.glsp.api.diagram.DiagramConfiguration;
import org.eclipse.glsp.api.diagram.DiagramConfigurationProvider;
import org.eclipse.glsp.api.factory.GraphGsonConfiguratorFactory;
import org.eclipse.glsp.api.factory.ModelFactory;
import org.eclipse.glsp.api.handler.ActionHandler;
import org.eclipse.glsp.api.handler.OperationHandler;
import org.eclipse.glsp.api.handler.ServerCommandHandler;
import org.eclipse.glsp.api.jsonrpc.GLSPClientProvider;
import org.eclipse.glsp.api.jsonrpc.GLSPServer;
import org.eclipse.glsp.api.model.ModelStateProvider;
import org.eclipse.glsp.api.provider.ActionHandlerProvider;
import org.eclipse.glsp.api.provider.ActionProvider;
import org.eclipse.glsp.api.provider.OperationHandlerProvider;
import org.eclipse.glsp.api.provider.ServerCommandHandlerProvider;
import org.eclipse.glsp.server.actionhandler.CollapseExpandActionHandler;
import org.eclipse.glsp.server.actionhandler.ComputedBoundsActionHandler;
import org.eclipse.glsp.server.actionhandler.DIActionProcessor;
import org.eclipse.glsp.server.actionhandler.ExecuteServerCommandActionHandler;
import org.eclipse.glsp.server.actionhandler.LayoutActionHandler;
import org.eclipse.glsp.server.actionhandler.OpenActionHandler;
import org.eclipse.glsp.server.actionhandler.OperationActionHandler;
import org.eclipse.glsp.server.actionhandler.RequestContextActionsHandler;
import org.eclipse.glsp.server.actionhandler.RequestMarkersHandler;
import org.eclipse.glsp.server.actionhandler.RequestModelActionHandler;
import org.eclipse.glsp.server.actionhandler.RequestOperationsActionHandler;
import org.eclipse.glsp.server.actionhandler.RequestPopupModelActionHandler;
import org.eclipse.glsp.server.actionhandler.RequestTypeHintsActionHandler;
import org.eclipse.glsp.server.actionhandler.SaveModelActionHandler;
import org.eclipse.glsp.server.actionhandler.SelectActionHandler;
import org.eclipse.glsp.server.actionhandler.UndoRedoActionHandler;
import org.eclipse.glsp.server.actionhandler.ValidateLabelEditActionHandler;
import org.eclipse.glsp.server.diagram.DIDiagramConfigurationProvider;
import org.eclipse.glsp.server.factory.DefaultGraphGsonConfiguratorFactory;
import org.eclipse.glsp.server.jsonrpc.DefaultGLSPClientProvider;
import org.eclipse.glsp.server.jsonrpc.DefaultGLSPServer;
import org.eclipse.glsp.server.model.DefaultModelStateProvider;
import org.eclipse.glsp.server.model.FileBasedModelFactory;
import org.eclipse.glsp.server.provider.DIActionHandlerProvider;
import org.eclipse.glsp.server.provider.DIOperationHandlerProvider;
import org.eclipse.glsp.server.provider.DIServerCommandHandlerProvider;
import org.eclipse.glsp.server.provider.DefaultActionProvider;

import com.google.common.collect.Lists;
import com.google.inject.multibindings.Multibinder;

public abstract class DefaultGLSPModule extends GLSPModule {

   protected Multibinder<ActionHandler> actionHandlerBinder;
   protected Multibinder<ServerCommandHandler> serverCommandHandler;
   protected Multibinder<OperationHandler> operationHandler;
   protected Multibinder<DiagramConfiguration> diagramConfiguration;

   private final List<Class<? extends ActionHandler>> defaultActionHandlers = Lists.newArrayList(
      CollapseExpandActionHandler.class, ComputedBoundsActionHandler.class, OpenActionHandler.class,
      OperationActionHandler.class, RequestModelActionHandler.class, RequestOperationsActionHandler.class,
      RequestPopupModelActionHandler.class, SaveModelActionHandler.class, UndoRedoActionHandler.class,
      SelectActionHandler.class, ExecuteServerCommandActionHandler.class, RequestTypeHintsActionHandler.class,
      RequestContextActionsHandler.class, RequestMarkersHandler.class, LayoutActionHandler.class,
      ValidateLabelEditActionHandler.class);

   @Override
   protected void configure() {
      super.configure();
      // Configure multibindings
      actionHandlerBinder = Multibinder.newSetBinder(binder(), ActionHandler.class);
      serverCommandHandler = Multibinder.newSetBinder(binder(), ServerCommandHandler.class);
      operationHandler = Multibinder.newSetBinder(binder(), OperationHandler.class);
      diagramConfiguration = Multibinder.newSetBinder(binder(), DiagramConfiguration.class);
      bindActionHandlers().forEach(h -> actionHandlerBinder.addBinding().to(h));
      bindServerCommandHandlers().forEach(h -> serverCommandHandler.addBinding().to(h));
      bindOperationHandlers().forEach(h -> operationHandler.addBinding().to(h));
      bindDiagramConfigurations().forEach(h -> diagramConfiguration.addBinding().to(h));
   }

   protected void rebind(final Class<? extends ActionHandler> defaultBinding,
      final Class<? extends ActionHandler> newBinding) {
      if (defaultActionHandlers.contains(defaultBinding)) {
         defaultActionHandlers.remove(defaultBinding);
      }
      defaultActionHandlers.add(newBinding);
   }

   protected Collection<Class<? extends ActionHandler>> bindActionHandlers() {
      return defaultActionHandlers;
   }

   @Override
   protected Class<? extends GLSPServer> bindGLSPServer() {
      return DefaultGLSPServer.class;
   }

   @Override
   protected Class<? extends GraphGsonConfiguratorFactory> bindGraphGsonConfiguratorFactory() {
      return DefaultGraphGsonConfiguratorFactory.class;
   }

   @Override
   protected Class<? extends ActionProvider> bindActionProvider() {
      return DefaultActionProvider.class;
   }

   @Override
   protected Class<? extends ActionHandlerProvider> bindActionHandlerProvider() {
      return DIActionHandlerProvider.class;
   }

   @Override
   protected Class<? extends OperationHandlerProvider> bindOperatioHandlerProvider() {
      return DIOperationHandlerProvider.class;
   }

   @Override
   protected Class<? extends ServerCommandHandlerProvider> bindServerCommandHandlerProvider() {
      return DIServerCommandHandlerProvider.class;
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
   protected Class<? extends DiagramConfigurationProvider> bindDiagramConfigurationProvider() {
      return DIDiagramConfigurationProvider.class;
   }

   protected abstract Collection<Class<? extends OperationHandler>> bindOperationHandlers();

   protected abstract Collection<Class<? extends DiagramConfiguration>> bindDiagramConfigurations();

   protected Collection<Class<? extends ServerCommandHandler>> bindServerCommandHandlers() {
      return Collections.emptySet();
   }

   @Override
   protected Class<? extends ActionProcessor> bindActionProcessor() {
      return DIActionProcessor.class;
   }

   @Override
   protected Class<? extends GLSPClientProvider> bindGSLPClientProvider() {
      return DefaultGLSPClientProvider.class;
   }
}
