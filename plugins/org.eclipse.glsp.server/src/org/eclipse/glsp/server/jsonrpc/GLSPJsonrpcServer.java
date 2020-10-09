/********************************************************************************
 * Copyright (c) 2020 EclipseSource and others.
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
package org.eclipse.glsp.server.jsonrpc;

import java.util.concurrent.CompletableFuture;

import org.eclipse.glsp.server.actions.ActionMessage;
import org.eclipse.glsp.server.protocol.GLSPServer;
import org.eclipse.glsp.server.protocol.InitializeParameters;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;

public interface GLSPJsonrpcServer extends GLSPServer<GLSPJsonrpcClient> {
   @Override
   @JsonRequest
   CompletableFuture<Boolean> initialize(InitializeParameters params);

   @Override
   @JsonNotification
   void process(ActionMessage message);

   @Override
   @JsonRequest
   CompletableFuture<Boolean> shutdown();
}
