/********************************************************************************
 * Copyright (c) 2022-2023 EclipseSource and others.
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
package org.eclipse.glsp.server.emf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.glsp.graph.GModelIndex;
import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.server.model.DefaultGModelState;
import org.eclipse.glsp.server.session.ClientSession;
import org.eclipse.glsp.server.session.ClientSessionListener;
import org.eclipse.glsp.server.session.ClientSessionManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Model state that holds the status of an arbitrary EMF model in an EMF {@link ResourceSet}.
 *
 * This model state is intended to be used if your source model is an EMF model.
 * Therefore, this model state includes an {@link EMFModelIndex}, is able to execute commands via
 * its {@link CommandStack} using and {@link EditingDomain} and is registered as {@link ClientSessionListener} to be
 * able to reset the EMF resources on diagram close.
 *
 * @see EMFDiagramModule
 */
@Singleton
public class EMFModelStateImpl extends DefaultGModelState implements EMFModelState, ClientSessionListener {

   private static Logger LOGGER = LogManager.getLogger(EMFModelStateImpl.class.getSimpleName());

   @Inject
   protected ClientSessionManager clientSessionManager;

   @Inject
   protected EMFIdGenerator idGenerator;

   protected EditingDomain editingDomain;

   @Override
   @Inject
   public void init() {
      this.clientSessionManager.addListener(this, this.clientId);
   }

   @Override
   public void setEditingDomain(final EditingDomain editingDomain) {
      this.editingDomain = editingDomain;
      setCommandStack(this.editingDomain.getCommandStack());
   }

   @Override
   public EditingDomain getEditingDomain() { return editingDomain; }

   @Override
   public ResourceSet getResourceSet() { return editingDomain == null ? null : editingDomain.getResourceSet(); }

   @Override
   protected GModelIndex getOrUpdateIndex(final GModelRoot newRoot) {
      return EMFModelIndex.getOrCreate(getRoot(), idGenerator);
   }

   @Override
   public EMFModelIndex getIndex() { return (EMFModelIndex) super.getIndex(); }

   @Override
   public void sessionDisposed(final ClientSession clientSession) {
      this.index.clear();
      closeResourceSet();
   }

   @SuppressWarnings("checkstyle:IllegalCatch")
   protected void closeResourceSet() {
      if (getResourceSet() == null) {
         return;
      }
      boolean result = false;
      EList<Resource> resourceList = getResourceSet().getResources();
      for (Resource resource : resourceList) {
         if (resource.getURI() != null) {
            try {
               resource.unload();
               result = true;
            } catch (RuntimeException e) {
               result = false;
               LOGGER.error("Could not unload resource: " + resource.getURI(), e);
               break;
            }
         }
      }
      if (result) {
         commandStack.flush();
         saveIsDone();
      }
   }

}
