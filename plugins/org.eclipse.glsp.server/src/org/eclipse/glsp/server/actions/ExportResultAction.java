/********************************************************************************
 * Copyright (c) 2026 EclipseSource and others.
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
package org.eclipse.glsp.server.actions;

import java.util.Optional;

/**
 * Response to a {@link RequestExportAction} carrying the rendered diagram. Text-encoded
 * payloads (e.g. SVG markup) ride in {@code data} directly; binary payloads (e.g. PNG) are
 * base64-encoded with {@code encoding = "base64"} so the action stays JSON-safe.
 */
public class ExportResultAction extends ResponseAction {

   public static final String KIND = "exportResult";

   /** Echoes the requested format (e.g. {@code "svg"} or {@code "png"}). */
   private String format;

   /** MIME type of the exported payload (e.g. {@code "image/svg+xml"} or {@code "image/png"}). */
   private String mimeType;

   /** Encoding of the bytes carried in {@link #data} (e.g. {@code "text"} or {@code "base64"}). */
   private String encoding;

   /** SVG markup ({@code encoding = "text"}) or base64-encoded bytes ({@code encoding = "base64"}). */
   private String data;

   /** Echoes the request's {@code formatOptions} so receivers can correlate result fields back to the request. */
   private Object formatOptions;

   public ExportResultAction() {
      super(KIND);
   }

   public ExportResultAction(final String format, final String data, final String mimeType, final String encoding) {
      super(KIND);
      this.format = format;
      this.data = data;
      this.mimeType = mimeType;
      this.encoding = encoding;
   }

   public String getFormat() { return format; }

   public void setFormat(final String format) { this.format = format; }

   public String getMimeType() { return mimeType; }

   public void setMimeType(final String mimeType) { this.mimeType = mimeType; }

   public String getEncoding() { return encoding; }

   public void setEncoding(final String encoding) { this.encoding = encoding; }

   public String getData() { return data; }

   public void setData(final String data) { this.data = data; }

   public Optional<Object> getFormatOptions() { return Optional.ofNullable(formatOptions); }

   public void setFormatOptions(final Object formatOptions) { this.formatOptions = formatOptions; }

}
