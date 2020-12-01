/********************************************************************************
 * Copyright (c) 2020 EclipseSource and others.
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
package org.eclipse.glsp.server.features.navigation;

import java.util.Optional;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class JsonOpenerOptions {
   private static final Logger LOGGER = Logger.getLogger(JsonOpenerOptions.class);

   private TextSelection selection;

   public JsonOpenerOptions() {
      super();
   }

   public JsonOpenerOptions(final TextSelection selection) {
      super();
      this.selection = selection;
   }

   /**
    * Creates new opener options for the given selection.
    *
    * @param startLine      0-based line number for selection start (inclusive)
    * @param startCharacter 0-based character number for selection start (inclusive)
    * @param endLine        0-based line number for selection end (inclusive)
    * @param endCharacter   0-based character number for selection end (exclusive)
    */
   public JsonOpenerOptions(final int startLine, final int startCharacter, final int endLine, final int endCharacter) {
      this(new TextSelection(new LinePosition(startLine, startCharacter), new LinePosition(endLine, endCharacter)));
   }

   public TextSelection getSelection() { return selection; }

   public void setSelection(final TextSelection selection) { this.selection = selection; }

   @Override
   public String toString() {
      return "JsonOpenerOptions [selection=" + selection + "]";
   }

   public String toJson() {
      return toJson(this);
   }

   public static String toJson(final JsonOpenerOptions options) {
      return new Gson().toJson(options);
   }

   public static Optional<JsonOpenerOptions> fromJson(final String options) {
      try {
         return Optional.ofNullable(new Gson().fromJson(options, JsonOpenerOptions.class));
      } catch (JsonSyntaxException exception) {
         LOGGER.error(exception);
         return Optional.empty();
      }
   }

   public static class TextSelection {
      private LinePosition start;
      private LinePosition end;

      public TextSelection() {
         super();
      }

      public TextSelection(final LinePosition start, final LinePosition end) {
         super();
         this.start = start;
         this.end = end;
      }

      public LinePosition getStart() { return start; }

      public void setStart(final LinePosition start) { this.start = start; }

      public LinePosition getEnd() { return end; }

      public void setEnd(final LinePosition end) { this.end = end; }

      @Override
      public String toString() {
         return "TextSelection [start=" + start + ", end=" + end + "]";
      }
   }

   public static class LinePosition {
      private int line;
      private int character;

      public LinePosition() {
         super();
      }

      public LinePosition(final int line, final int character) {
         super();
         this.line = line;
         this.character = character;
      }

      public int getLine() { return line; }

      public void setLine(final int line) { this.line = line; }

      public int getCharacter() { return character; }

      public void setCharacter(final int character) { this.character = character; }

      @Override
      public String toString() {
         return "LinePosition [line=" + line + ", character=" + character + "]";
      }
   }
}
