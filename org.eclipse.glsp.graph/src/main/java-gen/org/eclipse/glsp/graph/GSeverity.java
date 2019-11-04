/**
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
 * ********************************************************************************
 */
package org.eclipse.glsp.graph;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>GSeverity</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.glsp.graph.GraphPackage#getGSeverity()
 * @model
 * @generated
 */
public enum GSeverity implements Enumerator {
   /**
    * The '<em><b>Error</b></em>' literal object.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #ERROR_VALUE
    * @generated
    * @ordered
    */
   ERROR(0, "error", "error"),

   /**
    * The '<em><b>Warning</b></em>' literal object.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #WARNING_VALUE
    * @generated
    * @ordered
    */
   WARNING(1, "warning", "warning"),

   /**
    * The '<em><b>Info</b></em>' literal object.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #INFO_VALUE
    * @generated
    * @ordered
    */
   INFO(2, "info", "info");

   /**
    * The '<em><b>Error</b></em>' literal value.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #ERROR
    * @model name="error"
    * @generated
    * @ordered
    */
   public static final int ERROR_VALUE = 0;

   /**
    * The '<em><b>Warning</b></em>' literal value.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #WARNING
    * @model name="warning"
    * @generated
    * @ordered
    */
   public static final int WARNING_VALUE = 1;

   /**
    * The '<em><b>Info</b></em>' literal value.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #INFO
    * @model name="info"
    * @generated
    * @ordered
    */
   public static final int INFO_VALUE = 2;

   /**
    * An array of all the '<em><b>GSeverity</b></em>' enumerators.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private static final GSeverity[] VALUES_ARRAY = new GSeverity[] {
      ERROR,
      WARNING,
      INFO,
   };

   /**
    * A public read-only list of all the '<em><b>GSeverity</b></em>' enumerators.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public static final List<GSeverity> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

   /**
    * Returns the '<em><b>GSeverity</b></em>' literal with the specified literal value.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param literal the literal.
    * @return the matching enumerator or <code>null</code>.
    * @generated
    */
   public static GSeverity get(String literal) {
      for (int i = 0; i < VALUES_ARRAY.length; ++i) {
         GSeverity result = VALUES_ARRAY[i];
         if (result.toString().equals(literal)) {
            return result;
         }
      }
      return null;
   }

   /**
    * Returns the '<em><b>GSeverity</b></em>' literal with the specified name.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param name the name.
    * @return the matching enumerator or <code>null</code>.
    * @generated
    */
   public static GSeverity getByName(String name) {
      for (int i = 0; i < VALUES_ARRAY.length; ++i) {
         GSeverity result = VALUES_ARRAY[i];
         if (result.getName().equals(name)) {
            return result;
         }
      }
      return null;
   }

   /**
    * Returns the '<em><b>GSeverity</b></em>' literal with the specified integer value.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param value the integer value.
    * @return the matching enumerator or <code>null</code>.
    * @generated
    */
   public static GSeverity get(int value) {
      switch (value) {
         case ERROR_VALUE:
            return ERROR;
         case WARNING_VALUE:
            return WARNING;
         case INFO_VALUE:
            return INFO;
      }
      return null;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private final int value;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private final String name;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private final String literal;

   /**
    * Only this class can construct instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   private GSeverity(int value, String name, String literal) {
      this.value = value;
      this.name = name;
      this.literal = literal;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public int getValue() { return value; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public String getName() { return name; }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public String getLiteral() { return literal; }

   /**
    * Returns the literal value of the enumerator, which is its string representation.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public String toString() {
      return literal;
   }

} //GSeverity
