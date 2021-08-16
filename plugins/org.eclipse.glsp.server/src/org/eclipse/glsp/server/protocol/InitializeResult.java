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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class InitializeResult {

   private String protocolVersion;
   private Map<String, Collection<String>> serverActions;

   public InitializeResult(final String protocolVersion) {
      super();
      this.protocolVersion = protocolVersion;
      this.serverActions = new HashMap<>();
   }

   public InitializeResult(final String protocolVersion, final Map<String, Collection<String>> handledActions) {
      this(protocolVersion);
      this.serverActions = handledActions;
   }

   public void addServerActions(final String diagramType, final Collection<String> actionKinds) {
      Collection<String> value = serverActions.computeIfAbsent(diagramType, key -> new HashSet<>());
      value.addAll(actionKinds);
   }

   public String getProtocolVersion() { return protocolVersion; }

   public void setProtocolVersion(final String protocolVersion) { this.protocolVersion = protocolVersion; }

   public Map<String, Collection<String>> getServerActions() { return serverActions; }

   public void setServerActions(final Map<String, Collection<String>> serverActions) {
      this.serverActions = serverActions;
   }

   @Override
   public String toString() {
      return "InitializeResult [protocolVersion=" + protocolVersion + ", serverActions=" + serverActions + "]";
   }

}
