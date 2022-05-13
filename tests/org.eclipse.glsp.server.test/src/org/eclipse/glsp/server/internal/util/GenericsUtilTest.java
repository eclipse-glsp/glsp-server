/********************************************************************************
 * Copyright (c) 2022 EclipseSource and others.
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
package org.eclipse.glsp.server.internal.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.RequestAction;
import org.eclipse.glsp.server.actions.ResponseAction;
import org.eclipse.glsp.server.features.core.model.RequestBoundsAction;
import org.eclipse.glsp.server.features.core.model.RequestModelAction;
import org.eclipse.glsp.server.features.core.model.SetBoundsAction;
import org.eclipse.glsp.server.features.core.model.SetModelAction;
import org.eclipse.glsp.server.features.directediting.ApplyLabelEditOperation;
import org.eclipse.glsp.server.gmodel.AbstractGModelCreateEdgeOperationHandler;
import org.eclipse.glsp.server.gmodel.GModelApplyLabelEditOperationHandler;
import org.eclipse.glsp.server.gmodel.GModelReconnectEdgeOperationHandler;
import org.eclipse.glsp.server.operations.CreateEdgeOperation;
import org.eclipse.glsp.server.operations.Operation;
import org.eclipse.glsp.server.operations.ReconnectEdgeOperation;
import org.eclipse.glsp.server.types.GLSPServerException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class GenericsUtilTest {

   public static class MyResponseAction extends ResponseAction {
      public MyResponseAction() {
         super("custom");
      }

      @Override
      public int hashCode() {
         return Objects.hash(getKind());
      }

      @Override
      public boolean equals(final Object obj) {
         if (this == obj) {
            return true;
         }
         if (!(obj instanceof MyResponseAction)) {
            return false;
         }
         Action other = (MyResponseAction) obj;
         return Objects.equals(getKind(), other.getKind());
      }
   }

   public static class MyResponseActionExt extends MyResponseAction {}

   public abstract static class IntermediateRequestAction<T extends MyResponseAction> extends RequestAction<T> {
      public IntermediateRequestAction(final String kind) {
         super(kind);
      }
   }

   public static class MyRequestAction extends IntermediateRequestAction<MyResponseAction> {
      public MyRequestAction() {
         super("myrequest");
      }
   }

   public static class MySubRequestAction extends MyRequestAction {}

   public static class MyRequestActionExt extends IntermediateRequestAction<MyResponseActionExt> {
      public MyRequestActionExt() {
         super("myrequestext");
      }
   }

   private static Stream<Arguments> matchingRequestResponse() {
      return Stream.of(
         arguments(MyRequestAction.class, MyResponseAction.class),
         arguments(MySubRequestAction.class, MyResponseAction.class),
         arguments(MyRequestActionExt.class, MyResponseActionExt.class),
         arguments(RequestModelAction.class, SetModelAction.class),
         arguments(RequestBoundsAction.class, SetBoundsAction.class));
   }

   @ParameterizedTest
   @MethodSource
   void matchingRequestResponse(final Class<?> requestClass, final Class<?> expectedResponseClass)
      throws IOException, InterruptedException {
      assertEquals(expectedResponseClass, GenericsUtil.getActualTypeArgument(requestClass, ResponseAction.class));
   }

   private static Stream<Arguments> matchingOperationHandler() {
      return Stream.of(
         arguments(GModelApplyLabelEditOperationHandler.class, ApplyLabelEditOperation.class),
         arguments(AbstractGModelCreateEdgeOperationHandler.class, CreateEdgeOperation.class),
         arguments(GModelReconnectEdgeOperationHandler.class, ReconnectEdgeOperation.class));
   }

   @ParameterizedTest
   @MethodSource
   void matchingOperationHandler(final Class<?> handlerClass, final Class<?> expectedOperationClass)
      throws IOException, InterruptedException {
      assertEquals(expectedOperationClass, GenericsUtil.getActualTypeArgument(handlerClass, Operation.class));
   }

   @ParameterizedTest
   @MethodSource
   void erroneousMatching(final Class<?> handlerClass, final Class<?> baseType)
      throws IOException, InterruptedException {
      assertThrows(GLSPServerException.class, () -> GenericsUtil.getActualTypeArgument(handlerClass, baseType));
   }

   private static Stream<Arguments> erroneousMatching() {
      return Stream.of(
         arguments(RequestBoundsAction.class, Operation.class),
         arguments(GModelApplyLabelEditOperationHandler.class, ResponseAction.class),
         arguments(RequestModelAction.class, MyResponseActionExt.class));
   }

   @ParameterizedTest
   @MethodSource
   void matchFinding(final Class<?> clazz, final Class<?> baseType, final Optional<Class<?>> expectedResult)
      throws IOException, InterruptedException {
      assertEquals(expectedResult, GenericsUtil.findActualTypeArgument(clazz, baseType));
   }

   private static Stream<Arguments> matchFinding() {
      return Stream.of(
         arguments(MyRequestAction.class, ResponseAction.class, Optional.of(MyResponseAction.class)),
         arguments(MySubRequestAction.class, ResponseAction.class, Optional.of(MyResponseAction.class)),
         arguments(MyRequestActionExt.class, ResponseAction.class, Optional.of(MyResponseActionExt.class)),
         arguments(RequestModelAction.class, ResponseAction.class, Optional.of(SetModelAction.class)),
         arguments(RequestModelAction.class, MyResponseActionExt.class, Optional.empty()),
         arguments(GModelApplyLabelEditOperationHandler.class, Operation.class,
            Optional.of(ApplyLabelEditOperation.class)),
         arguments(AbstractGModelCreateEdgeOperationHandler.class, Operation.class,
            Optional.of(CreateEdgeOperation.class)),
         arguments(AbstractGModelCreateEdgeOperationHandler.class, ResponseAction.class, Optional.empty()),
         arguments(null, null, Optional.empty()),
         arguments(MyRequestAction.class, null, Optional.empty()),
         arguments(null, ResponseAction.class, Optional.empty()));
   }

   @ParameterizedTest
   @MethodSource
   void asActualTypeArgument(final Class<?> clazz, final Class<?> baseType, final Object typeObject,
      final Object expectedResult)
      throws IOException, InterruptedException {
      assertEquals(expectedResult, GenericsUtil.asActualTypeArgument(clazz, baseType, typeObject));
   }

   private static Stream<Arguments> asActualTypeArgument() {
      return Stream.of(
         arguments(MyRequestAction.class, ResponseAction.class, new MyResponseAction(),
            Optional.of(new MyResponseAction())),
         arguments(MyRequestAction.class, MyResponseAction.class, new MyResponseAction(),
            Optional.of(new MyResponseAction())),
         arguments(MyRequestAction.class, ResponseAction.class, new MyResponseActionExt(),
            Optional.of(new MyResponseActionExt())),
         arguments(MyRequestAction.class, MyResponseAction.class, new MyResponseActionExt(),
            Optional.of(new MyResponseActionExt())),
         arguments(MyRequestAction.class, MyResponseActionExt.class, new MyResponseActionExt(),
            Optional.empty()),
         arguments(MyRequestActionExt.class, ResponseAction.class, new MyResponseActionExt(),
            Optional.of(new MyResponseActionExt())),
         arguments(MyRequestActionExt.class, MyResponseAction.class, new MyResponseActionExt(),
            Optional.of(new MyResponseActionExt())),
         arguments(MyRequestActionExt.class, MyResponseActionExt.class, new MyResponseActionExt(),
            Optional.of(new MyResponseActionExt())),
         arguments(MyRequestActionExt.class, Operation.class, new MyResponseActionExt(),
            Optional.empty()),
         arguments(MyRequestActionExt.class, Object.class, new MyResponseActionExt(),
            Optional.of(new MyResponseActionExt())));
   }
}
