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

import org.eclipse.glsp.api.action.Action;
import org.eclipse.glsp.api.action.ActionProcessor;
import org.eclipse.glsp.api.di.GLSPModule;
import org.eclipse.glsp.api.di.MultiBindings;
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
import org.eclipse.glsp.api.provider.OperationHandlerProvider;
import org.eclipse.glsp.api.provider.ServerCommandHandlerProvider;
import org.eclipse.glsp.server.actionhandler.DIActionProcessor;
import org.eclipse.glsp.server.diagram.DIDiagramConfigurationProvider;
import org.eclipse.glsp.server.factory.DefaultGraphGsonConfiguratorFactory;
import org.eclipse.glsp.server.jsonrpc.DefaultGLSPClientProvider;
import org.eclipse.glsp.server.jsonrpc.DefaultGLSPServer;
import org.eclipse.glsp.server.model.DefaultModelStateProvider;
import org.eclipse.glsp.server.model.FileBasedModelFactory;
import org.eclipse.glsp.server.provider.DIActionHandlerProvider;
import org.eclipse.glsp.server.provider.DIOperationHandlerProvider;
import org.eclipse.glsp.server.provider.DIServerCommandHandlerProvider;

public abstract class DefaultGLSPModule extends GLSPModule {

   @Override
   protected void configureActions(final MultiBindings<Action> bindings) {
      bindings.addAll(MultiBindingDefaults.DEFAULT_ACTIONS);
   }

   @Override
   protected void configureActionHandlers(
      final MultiBindings<ActionHandler> bindings) {
      bindings.addAll(MultiBindingDefaults.DEFAULT_ACTION_HANDLERS);
   }

   @Override
   protected void configureServerCommandHandlers(
      final MultiBindings<ServerCommandHandler> bindings) {}

   @Override
   protected void configureOperationHandlers(
      final MultiBindings<OperationHandler> bindings) {
      bindings.addAll(MultiBindingDefaults.DEFAULT_OPERATION_HANDLERS);
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

   @Override
   protected Class<? extends ActionProcessor> bindActionProcessor() {
      return DIActionProcessor.class;
   }

   @Override
   protected Class<? extends GLSPClientProvider> bindGSLPClientProvider() {
      return DefaultGLSPClientProvider.class;
   }
}
