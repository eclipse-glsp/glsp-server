/*******************************************************************************
 * Copyright (c) 2019-2021 EclipseSource and others.
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
package org.eclipse.glsp.server.protocol;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * POJO providing all arguments required for a {@link GLSPServer#initialize(InitializeParameters)}
 * request.
 */
public class InitializeParameters {
   /**
    * Unique identifier for the current client application.
    */
   private String applicationId;

   /**
    * GLSP protocol version that this client is implementing.
    */
   private String protocolVersion;

   /**
    * Additional custom arguments e.g. application specific parameters.
    */
   private Map<String, String> args;

   public InitializeParameters() {
      args = new HashMap<>();
   }

   public Map<String, String> getArgs() { return args; }

   public void setArgs(final Map<String, String> args) { this.args = args; }

   public String getApplicationId() { return applicationId; }

   public void setApplicationId(final String applicationId) { this.applicationId = applicationId; }

   public String getProtocolVersion() { return protocolVersion; }

   public void setProtocolVersion(final String protocolVersion) { this.protocolVersion = protocolVersion; }

   @Override
   public String toString() {
      String argsString = args.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue())
         .collect(Collectors.joining(","));
      return "InitializeParameters [applicationId=" + applicationId + ", protocolVersion=" + protocolVersion + ", args="
         + "{ " + argsString + " } ]";
   }

}
