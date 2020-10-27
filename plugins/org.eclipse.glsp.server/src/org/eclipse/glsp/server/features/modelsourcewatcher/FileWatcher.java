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
package org.eclipse.glsp.server.features.modelsourcewatcher;

import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.protocol.ClientSessionListener;
import org.eclipse.glsp.server.protocol.ClientSessionManager;
import org.eclipse.glsp.server.protocol.GLSPClient;
import org.eclipse.glsp.server.utils.ClientOptions;

import com.google.inject.Inject;

public class FileWatcher implements ClientSessionListener, ModelSourceWatcher {

   private static final String FILE = "file://";

   @Inject
   private ActionDispatcher actionDispatcher;

   private final Map<String, List<FileWatchWorker>> workers = new HashMap<>();

   @Inject
   public FileWatcher(final ClientSessionManager sessionManager) {
      sessionManager.addListener(this);
   }

   public FileWatcher(final ClientSessionManager sessionManager, final ActionDispatcher actionDispatcher) {
      this(sessionManager);
      this.actionDispatcher = actionDispatcher;
   }

   @Override
   public void sessionClosed(final String clientId, final GLSPClient client) {
      disposeAllWorkers(clientId);
   }

   @Override
   public void startWatching(final GModelState modelState) {
      createWorkers(modelState).forEach(FileWatchWorker::start);
   }

   @Override
   public void stopWatching(final GModelState modelState) {
      disposeAllWorkers(modelState.getClientId());
   }

   @Override
   public void pauseWatching(final GModelState modelState) {
      getAllWorkers(modelState.getClientId()).forEach(FileWatchWorker::pauseNotifications);
   }

   @Override
   public void continueWatching(final GModelState modelState) {
      getAllWorkers(modelState.getClientId()).forEach(FileWatchWorker::continueNotifications);
   }

   protected List<Path> getPaths(final GModelState modelState) {
      final Optional<String> uriString = ClientOptions.getValue(modelState.getClientOptions(),
         ClientOptions.SOURCE_URI);
      URI sourceUri = URI.createFileURI(uriString.map(uri -> uri.replace(FILE, "")).orElseThrow());
      sourceUri = URI.createURI(URI.decode(sourceUri.toString()));
      final List<Path> paths = new ArrayList<>();
      paths.add(new File(sourceUri.toFileString()).toPath());
      return paths;
   }

   private List<FileWatchWorker> createWorkers(final GModelState modelState) {
      stopAllWorkers(modelState.getClientId());
      final List<FileWatchWorker> fileWorkers = createFileWatchWorkers(modelState);
      workers.put(modelState.getClientId(), fileWorkers);
      return fileWorkers;
   }

   private void disposeAllWorkers(final String clientId) {
      stopAllWorkers(clientId);
      workers.remove(clientId);
   }

   private void stopAllWorkers(final String clientId) {
      getAllWorkers(clientId).forEach(FileWatchWorker::stopWorking);
   }

   protected Stream<FileWatchWorker> getAllWorkers(final String clientId) {
      return Optional.ofNullable(workers.get(clientId)).stream().flatMap(Collection::stream);
   }

   private List<FileWatchWorker> createFileWatchWorkers(final GModelState modelState) {
      return getPaths(modelState).stream().map(path -> new FileWatchWorker(modelState.getClientId(), path))
         .collect(Collectors.toList());
   }

   protected void notifyClient(final String clientId, final Path filePath) {
      actionDispatcher.dispatch(clientId, new ModelSourceChangedAction(filePath.toAbsolutePath().toString()));
   }

   class FileWatchWorker extends Thread {

      private boolean stopped;
      private boolean paused;

      private final String clientId;
      private final Path filePath;
      private WatchKey key;

      FileWatchWorker(final String clientId, final Path filePath) {
         super();
         this.clientId = clientId;
         this.filePath = filePath;
         this.setName("File watcher: file " + filePath + " [" + clientId + "]");
      }

      @Override
      public void run() {
         try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            final Path directory = filePath.getParent();
            key = directory.register(watchService, ENTRY_MODIFY, ENTRY_DELETE);
            while (!stopped) {
               final WatchKey newKey = watchService.take();
               if (this.key != newKey) {
                  continue;
               }
               pollEventsAndNotifyClient(directory);
               if (!key.reset()) {
                  break;
               }
            }
         } catch (IOException | ClosedWatchServiceException | InterruptedException e) {
            Thread.currentThread().interrupt();
         }
      }

      private void pollEventsAndNotifyClient(final Path directory)
         throws IOException {
         for (final WatchEvent<?> event : key.pollEvents()) {
            if (!paused && !stopped && Files.isSameFile(directory.resolve((Path) event.context()), filePath)) {
               notifyClient(clientId, filePath);
            }
         }
      }

      public void stopWorking() {
         stopped = true;
         if (key != null) {
            key.reset();
         }
         interrupt();
      }

      public void pauseNotifications() {
         paused = true;
      }

      public void continueNotifications() {
         paused = false;
      }

   }

}
