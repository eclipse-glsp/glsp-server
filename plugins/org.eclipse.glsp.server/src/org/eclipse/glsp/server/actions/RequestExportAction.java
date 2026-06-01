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
 * Generic, format-agnostic export request. Sent by the server to the client for server-orchestrated
 * export flows (e.g. requesting a PNG snapshot of the active diagram). The expected response is an
 * {@link ExportResultAction} carrying the rendered bytes.
 *
 * <p>
 * The {@code format} identifies the export strategy ({@code "svg"} and {@code "png"} are shipped;
 * adopters may register additional formats). {@code formatOptions} is opaque to the protocol and is
 * validated by the {@code DiagramExporter} strategy for the given format.
 * </p>
 */
public class RequestExportAction extends RequestAction<ExportResultAction> {

   public static final String KIND = "requestExport";

   private String format;

   /** Format-specific options interpreted by the export strategy for the given {@link #format}. */
   private Object formatOptions;

   public RequestExportAction() {
      super(KIND);
   }

   public RequestExportAction(final String format) {
      super(KIND);
      this.format = format;
   }

   public RequestExportAction(final String format, final Object formatOptions) {
      super(KIND);
      this.format = format;
      this.formatOptions = formatOptions;
   }

   public String getFormat() { return format; }

   public void setFormat(final String format) { this.format = format; }

   public Optional<Object> getFormatOptions() { return Optional.ofNullable(formatOptions); }

   public void setFormatOptions(final Object formatOptions) { this.formatOptions = formatOptions; }

}
