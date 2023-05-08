/********************************************************************************
 * Copyright (c) 2023 EclipseSource and others.
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
package org.eclipse.glsp.server.emf.notation;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.server.emf.EMFModelState;
import org.eclipse.glsp.server.emf.model.notation.Diagram;

public interface EMFNotationModelState extends EMFModelState {

   <T extends Diagram> Optional<T> getNotationModel(Class<T> clazz);

   Diagram getNotationModel();

   void setNotationModel(Diagram notationModel);

   <T extends EObject> Optional<T> getSemanticModel(Class<T> clazz);

   EObject getSemanticModel();

   void setSemanticModel(EObject semanticModel);

   @Override
   EMFNotationModelIndex getIndex();

}
