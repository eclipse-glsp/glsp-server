/********************************************************************************
 * Copyright (c) 2019-2023 EclipseSource and others.
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
package org.eclipse.glsp.server.features.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.glsp.graph.GModelElement;

/**
 * Validates a list of {@link GModelElement}s based on a set of validation rules and returns corresponding issue
 * {@link Marker}s.
 * An issue marker is a serializable description of the validation violation that can be visualised by the GLSP client.
 *
 * There are two default reasons for a validation:
 * <ol>
 * <li><i>live</i> validation is executed on open and after each operation. These validation rules should be rather
 * fast. Overwrite {@link #doLiveValidation(GModelElement)} to implement the live validation rules.</li>
 * <li><i>batch</i> validation is executed on demand by the client. These validation rules can be more expensive.
 * Overwrite {@link #doBatchValidation(GModelElement)} to implement the batch validation rules.</li>
 * </ol>
 */
public interface ModelValidator {

   /**
    * Validates the given list of {@link GModelElement}s and returns a list of {@link Marker}s.
    *
    * @param elements The list of {@link GModelElement} to validate.
    * @param reason   The reason for a validation request, such as "batch" or "live" validation.
    * @return A list of {@link Marker}s for the validated {@link GModelElement}s.
    */
   default List<Marker> validate(final List<GModelElement> elements, final String reason) {
      List<Marker> markers = new ArrayList<>();

      for (GModelElement element : elements) {
         if (MarkersReason.LIVE.equals(reason)) {
            markers.addAll(doLiveValidation(element));
         } else if (MarkersReason.BATCH.equals(reason)) {
            markers.addAll(doBatchValidation(element));
         } else {
            markers.addAll(doValidationForCustomReason(element, reason));
         }
         if (!element.getChildren().isEmpty()) {
            markers.addAll(validate(element.getChildren(), reason));
         }
      }

      return markers;
   }

   /**
    * Runs a <code>batch</code> validation with the given list of {@link GModelElement}s and returns a list of
    * {@link Marker}s.
    *
    * @param elements The list of {@link GModelElement} to validate.
    * @return A list of {@link Marker}s for the validated {@link GModelElement}s.
    */
   default List<Marker> validate(final GModelElement... elements) {
      return validate(List.of(elements), MarkersReason.BATCH);
   }

   /**
    * Perform the live validation rules for the given <code>element</code>.
    *
    * <p>
    * This will be invoked on start and after each operation for all elements.
    * Thus, the validation should be rather inexpensive.
    * There is no need to traverse through the children in this method as {@link #validate(List, String)} will invoke
    * this method for all children anyway.
    * </p>
    *
    * @param element The element to validate.
    * @return A list of {@link Marker}s for the validated {@link GModelElement}.
    */
   default List<Marker> doLiveValidation(final GModelElement element) {
      return Collections.emptyList();
   }

   /**
    * Perform the batch validation rules for the given <code>element</code>.
    *
    * <p>
    * This will be invoked on demand by the client.
    * Thus, the validation can include more expensive validation rules.
    * There is no need to traverse through the children in this method as {@link #validate(List, String)} will invoke
    * this method for all children anyway.
    * </p>
    *
    * @param element The element to validate.
    * @return A list of {@link Marker}s for the validated {@link GModelElement}.
    */
   default List<Marker> doBatchValidation(final GModelElement element) {
      return Collections.emptyList();
   }

   /**
    * Perform a validation for a custom <code>reason</code> with the given <code>element</code>.
    *
    * <p>
    * GLSP editors may add custom reasons for triggering a validation, other than <code>live</code> and
    * <code>batch</code>.
    * Validation requests that are not live or batch validations will be handled by this method.
    * There is no need to traverse through the children in this method as {@link #validate(List, String)} will invoke
    * this method for all children anyway.
    * </p>
    *
    * @param element The element to validate.
    * @return A list of {@link Marker}s for the validated {@link GModelElement}.
    */
   default List<Marker> doValidationForCustomReason(final GModelElement element, final String reason) {
      return Collections.emptyList();
   }

}
