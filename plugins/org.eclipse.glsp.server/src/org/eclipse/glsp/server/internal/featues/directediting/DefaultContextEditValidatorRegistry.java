/********************************************************************************
 * Copyright (c) 2020-2021 EclipseSource and others.
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
package org.eclipse.glsp.server.internal.featues.directediting;

import java.util.Optional;
import java.util.Set;

import org.eclipse.glsp.server.features.directediting.ContextEditValidator;
import org.eclipse.glsp.server.features.directediting.ContextEditValidatorRegistry;
import org.eclipse.glsp.server.features.directediting.LabelEditValidator;
import org.eclipse.glsp.server.internal.labeledit.ValidateLabelEditAdapter;
import org.eclipse.glsp.server.internal.registry.MapRegistry;
import org.eclipse.glsp.server.model.GModelState;

import com.google.inject.Inject;

public class DefaultContextEditValidatorRegistry extends MapRegistry<String, ContextEditValidator>
   implements ContextEditValidatorRegistry {
   @Inject
   public DefaultContextEditValidatorRegistry(final Set<ContextEditValidator> contextEditValidators,
      final Optional<LabelEditValidator> labelEditValidator, final GModelState modelState) {
      contextEditValidators.forEach(provider -> register(provider.getContextId(), provider));
      if (labelEditValidator.isPresent()) {
         register(LabelEditValidator.CONTEXT_ID, new ValidateLabelEditAdapter(modelState, labelEditValidator.get()));
      }
   }
}
