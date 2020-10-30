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

import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.disposable.IDisposable;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.protocol.ClientSessionListener;
import org.eclipse.glsp.server.protocol.ClientSessionManager;
import org.eclipse.glsp.server.protocol.GLSPClient;
import org.eclipse.glsp.server.utils.ClientOptions;
import org.eclipse.glsp.server.utils.Debouncer;

import com.google.inject.Inject;

public class FileWatcher implements ClientSessionListener, ModelSourceWatcher {

   protected Debouncer<ClientNotification> clientNotificationDebouncer;

   @Inject
   protected ActionDispatcher actionDispatcher;

   protected final ClientSessionManager sessionManager;

   protected int debounceDelay = 500;

   protected final Map<String, List<FileWatchWorker>> workers = new HashMap<>();

   @Inject
   public FileWatcher(final ClientSessionManager sessionManager) {
      this.sessionManager = sessionManager;
      this.sessionManager.addListener(this);
   }

   public FileWatcher(final ClientSessionManager sessionManager, final ActionDispatcher actionDispatcher) {
      this(sessionManager);
      this.actionDispatcher = actionDispatcher;
   }

   public int getDebounceDelay() { return debounceDelay; }

   public void setDebounceDelay(final int debounceDelay) { this.debounceDelay = debounceDelay; }

   @Override
   public void clientDisconnected(final GLSPClient client) {
      sessionManager.removeListener(this);
   }

   @Override
   public void sessionClosed(final String clientId, final GLSPClient client) {
      stop(clientId);
   }

   @Override
   public void startWatching(final GModelState modelState) {
      start(modelState);
   }

   @Override
   public void stopWatching(final GModelState modelState) {
      stop(modelState.getClientId());
   }

   @Override
   public void pauseWatching(final GModelState modelState) {
      getAllWorkers(modelState.getClientId()).forEach(FileWatchWorker::pauseNotifications);
   }

   @Override
   public void continueWatching(final GModelState modelState) {
      getAllWorkers(modelState.getClientId()).forEach(FileWatchWorker::continueNotifications);
   }

   protected void start(final GModelState modelState) {
      IDisposable.disposeIfExists(clientNotificationDebouncer);
      clientNotificationDebouncer = new Debouncer<>(this::notifyClient, getDebounceDelay(), TimeUnit.MILLISECONDS);
      createWorkers(modelState).forEach(FileWatchWorker::start);
   }

   protected void stop(final String clientId) {
      disposeAllWorkers(clientId);
      IDisposable.disposeIfExists(clientNotificationDebouncer);
   }

   protected List<Path> getPaths(final GModelState modelState) {
      return ClientOptions.getSourceUriAsFile(modelState.getClientOptions()).stream()
         .map(file -> file.toPath()).collect(Collectors.toList());
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

   protected void scheduleClientNotification(final String clientId, final Path filePath) {
      clientNotificationDebouncer.accept(new ClientNotification(clientId, filePath.getFileName().toString()));
   }

   protected void notifyClient(final ClientNotification clientNotification) {
      actionDispatcher.dispatch(clientNotification.clientId,
         new ModelSourceChangedAction(clientNotification.modelSourceName));
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
               scheduleClientNotification(clientId, filePath);
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

   protected class ClientNotification {

      private final String clientId;
      private final String modelSourceName;

      ClientNotification(final String clientId, final String modelSourceName) {
         super();
         this.clientId = clientId;
         this.modelSourceName = modelSourceName;
      }

      @Override
      public int hashCode() {
         final int prime = 31;
         int result = 1;
         result = prime * result + getEnclosingInstance().hashCode();
         result = prime * result + Objects.hash(clientId, modelSourceName);
         return result;
      }

      @Override
      public boolean equals(final Object obj) {
         if (this == obj) {
            return true;
         }
         if (!(obj instanceof ClientNotification)) {
            return false;
         }
         ClientNotification other = (ClientNotification) obj;
         if (!getEnclosingInstance().equals(other.getEnclosingInstance())) {
            return false;
         }
         return Objects.equals(clientId, other.clientId) && Objects.equals(modelSourceName, other.modelSourceName);
      }

      private FileWatcher getEnclosingInstance() { return FileWatcher.this; }

   }

}
