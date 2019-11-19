/*******************************************************************************
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
 ******************************************************************************/
package org.eclipse.glsp.api.action.kind;

import java.util.Objects;

import org.eclipse.glsp.api.action.Action;

public class ResponseAction extends Action {
   private String responseId;

   public ResponseAction(final String kind) {
      super(kind);
   }

   public String getResponseId() { return responseId; }

   public void setResponseId(final String responseId) { this.responseId = responseId; }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + Objects.hash(responseId);
      return result;
   }

   @Override
   public boolean equals(final Object obj) {
      if (this == obj) {
         return true;
      }
      if (!super.equals(obj)) {
         return false;
      }
      if (!(obj instanceof ResponseAction)) {
         return false;
      }
      ResponseAction other = (ResponseAction) obj;
      return Objects.equals(responseId, other.responseId);
   }

   /**
    * Transfers the {@link ResponseAction#responseId id} from request to response if applicable.
    *
    * @param request  potential {@link RequestAction}
    * @param response potential {@link ResponseAction}
    * @return given response action with id set if applicable
    */
   public static Action respond(final Action request, final Action response) {
      if (request instanceof RequestAction<?> && response instanceof ResponseAction) {
         ((ResponseAction) response).setResponseId(((RequestAction<?>) request).getRequestId());
      }
      return response;
   }
}
