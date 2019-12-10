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

import java.util.List;
import java.util.ServiceLoader;

import javax.websocket.Extension;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

import org.eclipse.jetty.websocket.jsr356.server.ContainerDefaultConfigurator;

import com.google.inject.Injector;

public class GLSPConfigurator extends Configurator {
	private Configurator containerConfigurator;
	private Injector injector;

	public GLSPConfigurator(Injector injector) {
		this.injector = injector;
	}

	Configurator getContainerConfigurator() {
		if (containerConfigurator == null) {
			containerConfigurator = ServiceLoader.load(javax.websocket.server.ServerEndpointConfig.Configurator.class)
					.findFirst().orElse(new ContainerDefaultConfigurator());
		}
		return containerConfigurator;
	}

	@Override
	public String getNegotiatedSubprotocol(List<String> supported, List<String> requested) {
		return this.getContainerConfigurator().getNegotiatedSubprotocol(supported, requested);
	}

	@Override
	public List<Extension> getNegotiatedExtensions(List<Extension> installed, List<Extension> requested) {
		return this.getContainerConfigurator().getNegotiatedExtensions(installed, requested);
	}

	@Override
	public boolean checkOrigin(String originHeaderValue) {
		return this.getContainerConfigurator().checkOrigin(originHeaderValue);
	}

	@Override
	public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
		return injector.getInstance(endpointClass);
	}

	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
		/* do nothing */
	}
}
