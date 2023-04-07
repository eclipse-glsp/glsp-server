/********************************************************************************
 * Copyright (c) 2023 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.glsp.server.emf;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.glsp.server.model.GModelState;

public interface EMFModelState extends GModelState {
   void setEditingDomain(final EditingDomain editingDomain);

   EditingDomain getEditingDomain();

   ResourceSet getResourceSet();

   @Override
   EMFModelIndex getIndex();
}
