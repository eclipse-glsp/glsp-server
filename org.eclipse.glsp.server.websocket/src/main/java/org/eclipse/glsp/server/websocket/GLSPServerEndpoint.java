/*******************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *  
 *   This program and the accompanying materials are made available under the
 *   terms of the Eclipse Public License v. 2.0 which is available at
 *   https://www.eclipse.org/legal/epl-2.0.
 *  
 *   This Source Code may also be made available under the following Secondary
 *   Licenses when the conditions for such availability set forth in the Eclipse
 *   Public License v. 2.0 are satisfied: GNU General Public License, version 2
 *   with the GNU Classpath Exception which is available at
 *   https://www.gnu.org/software/classpath/license.html.
 *  
 *   SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ******************************************************************************/
package org.eclipse.glsp.server.websocket;

import java.util.Collection;

import org.eclipse.lsp4j.jsonrpc.Launcher.Builder;
import org.eclipse.lsp4j.websocket.WebSocketEndpoint;

import org.eclipse.glsp.api.json.GsonConfigurator;
import org.eclipse.glsp.api.jsonrpc.GLSPClient;
import org.eclipse.glsp.api.jsonrpc.GLSPClientAware;
import org.eclipse.glsp.api.jsonrpc.GLSPServer;
import com.google.inject.Inject;

public class GLSPServerEndpoint extends WebSocketEndpoint<GLSPClient> {

	@Inject
	private GLSPServer glspServer;

	@Inject
	private GsonConfigurator gsonConfigurator;

	@Override
	protected void configure(Builder<GLSPClient> builder) {
		builder.setLocalService(glspServer);
		builder.setRemoteInterface(GLSPClient.class);
		builder.configureGson(gsonConfigurator::configureGsonBuilder);
	}

	@Override
	protected void connect(Collection<Object> localServices, GLSPClient remoteProxy) {
		localServices.stream().filter(GLSPClientAware.class::isInstance).map(GLSPClientAware.class::cast)
				.forEach(ca -> ca.connect(remoteProxy));
	}

}
