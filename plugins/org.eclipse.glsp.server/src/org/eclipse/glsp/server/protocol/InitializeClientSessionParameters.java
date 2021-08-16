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
package org.eclipse.glsp.server.protocol;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

   /**
    * Additional custom arguments.
    */
   private Map<String, String> args;

   public InitializeClientSessionParameters() {
      this.args = new HashMap<>();
   }

   public String getClientSessionId() { return clientSessionId; }

   public void setClientSessionId(final String clientSessionId) { this.clientSessionId = clientSessionId; }

   public String getDiagramType() { return diagramType; }

   public void setDiagramType(final String diagramType) { this.diagramType = diagramType; }

   public Map<String, String> getArgs() { return args; }

   public void setArgs(final Map<String, String> args) { this.args = args; }

   @Override
   public String toString() {
      String argsString = args.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue())
         .collect(Collectors.joining(";"));
      return "InitializeClientSessionParameters [clientSessionId=" + clientSessionId + ", diagramType=" + diagramType
         + ", args={ " + argsString + " } ]";
   }

}
