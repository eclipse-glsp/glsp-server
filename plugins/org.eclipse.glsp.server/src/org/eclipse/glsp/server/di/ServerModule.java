/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.glsp.server.actions.ActionExecutor;
import org.eclipse.glsp.server.actions.ActionRegistry;
import org.eclipse.glsp.server.di.scope.DiagramGlobalScope;
import org.eclipse.glsp.server.gson.ServerGsonConfigurator;
import org.eclipse.glsp.server.internal.actions.DefaultActionExecutor;
import org.eclipse.glsp.server.internal.actions.DefaultActionRegistry;
import org.eclipse.glsp.server.internal.di.scope.DefaultDiagramGlobalScope;
import org.eclipse.glsp.server.internal.gson.DefaultServerGsonConfigurator;
import org.eclipse.glsp.server.internal.session.DefaultClientSessionFactory;
import org.eclipse.glsp.server.internal.session.DefaultClientSessionManager;
import org.eclipse.glsp.server.protocol.DefaultGLSPServer;
import org.eclipse.glsp.server.protocol.GLSPClient;
import org.eclipse.glsp.server.protocol.GLSPServer;
import org.eclipse.glsp.server.session.ClientSessionFactory;
import org.eclipse.glsp.server.session.ClientSessionManager;
import org.eclipse.glsp.server.utils.ModuleUtil;

import com.google.common.base.Preconditions;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

/**
 * The server module is the central configuration artifact for configuring the server injector (i.e. main injector). For
 * each application connecting to the server process a new server injector is created. The server module provides the
 * base bindings necessary for setting up the base {@link GLSPServer} infrastructure. In addition, it is used to
 * configure the set of {@link DiagramModule}s. Diagram modules are used to create the diagram-session-specific child
 * injector when the
 * {@link GLSPServer#initializeClientSession(org.eclipse.glsp.server.protocol.InitializeClientSessionParameters)}
 * method is called.
 *
 * All bindings inside of the {@link ServerModule#configure()} method are delegated to separate methods which makes it
 * easy to override a specific binding in subclasses.
 *
 * <p>
 * The following bindings are provided:
 * </p>
 *
 * <ul>
 * <li>{@link Map}&lt;String,Module&gt; annotated with <code> @named("Diagram_Modules")</code>
 * <li>{@link ServerGsonConfigurator}
 * <li>{@link GLSPServer}
 * <li>{@link ClientSessionFactory}
 * <li>{@link ClientSessionManager}
 * <li>{@link ActionRegistry}
 * <li>{@link DiagramGlobalScope}
 * </ul>
 *
 */
public class ServerModule extends GLSPModule {
   public static final String DIAGRAM_MODULES = "Diagram_Modules";
   private final Map<String, Module> diagramModules = new HashMap<>();

   /**
    * Configure a new {@link DiagramModule} for this server. A diagram module represents the base configuration artifact
    * for configuring a diagram-language-specific client session injector. The diagram type provided
    * {@link DiagramModule#getDiagramType()} is used to retrieve the correct diagram module when the {@link GLSPServer}
    * initialises a new client session.
    * The given diagram module and all (optional) additional modules will be combined to one merged module. If bindings
    * of the additional modules are conflicting with the binding in the base diagram module the original binding will be
    * overwritten. The merged module is stored in a map using its diagram type as key.
    *
    * @param diagramModule The base diagram module
    * @param mixinModules  Additional modules
    * @return The server module itself. This enables a builder-pattern like chaining of multiple diagram configuration
    *         calls.
    */
   public ServerModule configureDiagramModule(final DiagramModule diagramModule, final Module... mixinModules) {
      String diagramType = diagramModule.getDiagramType();
      Preconditions.checkState(!diagramModules.containsKey(diagramType),
         "A module configuration is already present for diagram type: " + diagramType);

      Module combinedModule = ModuleUtil.mixin(diagramModule, mixinModules);
      diagramModules.put(diagramType, combinedModule);
      return this;
   }

   @Override
   protected void configureBase() {
      bindDiagramModules();
      bind(ServerGsonConfigurator.class).to(bindGsonConfigurator()).in(Singleton.class);

      bind(GLSPServer.class).to(bindGLSPServer()).in(Singleton.class);
      bind(ClientSessionFactory.class).to(bindClientSessionFactory()).in(Singleton.class);
      bind(ClientSessionManager.class).to(bindClientSessionManager()).in(Singleton.class);

      bind(ActionRegistry.class).to(bindActionRegistry()).in(Singleton.class);
      bind(ActionExecutor.class).to(bindActionExecutor()).in(Singleton.class);

      bind(DiagramGlobalScope.class).to(bindDiagramGlobalScope()).in(Singleton.class);
   }

   /**
    * Bind the configured diagram modules map using a named annotation with the {@link ServerModule#DIAGRAM_MODULES}
    * constant.
    *
    * Example injection:
    *
    * <pre>
    * &#64;inject
    * &#64;inject(ServerModule.DIAGRAM_MODULES)
    * private Map&#60;String, Module&#62; diagramModules;
    * </pre>
    */
   protected void bindDiagramModules() {
      bind(new TypeLiteral<Map<String, Module>>() {}).annotatedWith(Names.named(DIAGRAM_MODULES))
         .toInstance(diagramModules);
   }

   protected Class<? extends ServerGsonConfigurator> bindGsonConfigurator() {
      return DefaultServerGsonConfigurator.class;
   }

   protected Class<? extends GLSPServer> bindGLSPServer() {
      return DefaultGLSPServer.class;
   }

   protected Class<? extends ClientSessionFactory> bindClientSessionFactory() {
      return DefaultClientSessionFactory.class;
   }

   protected Class<? extends ClientSessionManager> bindClientSessionManager() {
      return DefaultClientSessionManager.class;
   }

   protected Class<? extends ActionRegistry> bindActionRegistry() {
      return DefaultActionRegistry.class;
   }

   protected Class<? extends ActionExecutor> bindActionExecutor() {
      return DefaultActionExecutor.class;
   }

   protected Class<? extends DiagramGlobalScope> bindDiagramGlobalScope() {
      return DefaultDiagramGlobalScope.class;
   }

   @Provides
   protected GLSPClient getGLSPClient(final GLSPServer glspServer) {
      return glspServer.getClient();
   }
}
