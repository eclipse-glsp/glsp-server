/********************************************************************************
 * Copyright (c) 2021-2023 EclipseSource and others.
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
package org.eclipse.glsp.server.protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * POJO providing all arguments required for a
 * {@link GLSPServer#initializeClientSession(InitializeClientSessionParameters)}
 * request.
 */
public class InitializeClientSessionParameters {
   /**
    * Unique identifier for the new client session.
    */
   private String clientSessionId;

   /**
    * Unique identifier of the diagram type for which the session should be configured.
    */
   private String diagramType;

   /*
    * The set of action kinds that can be handled by the client.
    * Used by the server to know which dispatched actions should be forwarded to the client.
    */
   private List<String> clientActionKinds;

   /**
    * Additional custom arguments.
    */
   private Map<String, String> args;

   public InitializeClientSessionParameters() {
      this.args = new HashMap<>();
      this.clientActionKinds = new ArrayList<>();
   }

   public InitializeClientSessionParameters(final String clientSessionId, final String diagramType,
      final List<String> clientActionKinds) {
      this();
      this.clientSessionId = clientSessionId;
      this.diagramType = diagramType;
      this.clientActionKinds = clientActionKinds;
   }

   public InitializeClientSessionParameters(final String clientSessionId, final String diagramType,
      final List<String> clientActionKinds,
      final Map<String, String> args) {
      this(clientSessionId, diagramType, clientActionKinds);
      this.args = args;
   }

   public String getClientSessionId() { return clientSessionId; }

   public void setClientSessionId(final String clientSessionId) { this.clientSessionId = clientSessionId; }

   public String getDiagramType() { return diagramType; }

   public void setDiagramType(final String diagramType) { this.diagramType = diagramType; }

   public Map<String, String> getArgs() { return args; }

   public void setArgs(final Map<String, String> args) { this.args = args; }

   public List<String> getClientActionKinds() { return clientActionKinds; }

   public void setClientActionKinds(final List<String> clientActionKinds) {
      this.clientActionKinds = clientActionKinds;
   }

   @Override
   public String toString() {
      return "InitializeClientSessionParameters [clientSessionId=" + clientSessionId + ", diagramType=" + diagramType
         + ", clientActionKinds=" + clientActionKinds + ", args=" + args + "]";
   }

}
