/********************************************************************************
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
 ********************************************************************************/
package org.eclipse.glsp.graph;

import org.eclipse.emf.common.notify.Notification;

/**
 * A listener to observe changes of the {@link GModelRoot}.
 * It is typically associated with a {@link GModelChangeNotifier}.
 */
public interface GModelListener {

   /**
    * Triggered if a change to the {@link GModelRoot} has occurred.
    *
    * @param notification A description of the change to the GModelRoot.
    */
   void notifyChanged(Notification notification);

}
