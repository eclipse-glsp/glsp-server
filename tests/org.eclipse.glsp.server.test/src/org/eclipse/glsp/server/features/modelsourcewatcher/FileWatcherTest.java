/********************************************************************************
 * Copyright (c) 2020-2021 EclipseSource and others.
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.disposable.Disposable;
import org.eclipse.glsp.server.model.DefaultGModelState;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.session.ClientSession;
import org.eclipse.glsp.server.session.ClientSessionListener;
import org.eclipse.glsp.server.session.ClientSessionManager;
import org.eclipse.glsp.server.utils.ClientOptionsUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWatcherTest {

   private static final String DIR = "fileWatcherTests";

   private RecordingActionDispatcher actionDispatcher;
   private ClientSessionManager sessionManager;

   @BeforeEach
   public void before()
      throws IOException {
      actionDispatcher = new RecordingActionDispatcher();
      sessionManager = new MockClientSessionManager();
      Files.createDirectory(Path.of(DIR));
   }

   @AfterEach
   public void removeDirectory()
      throws IOException {
      Files.walk(Path.of(DIR))
         .sorted(Comparator.reverseOrder())
         .map(Path::toFile)
         .forEach(File::delete);
   }

   @Test
   void changingWatchedFileNotifiesClient()
      throws IOException, InterruptedException {
      final String clientId = "1";
      final File file = createFile("test.txt");
      final GModelState modelState = modelState(clientId, fileUri(file));

      final FileWatcher fileWatcher = new FileWatcher(sessionManager, actionDispatcher);
      fileWatcher.setDebounceDelay(0);
      fileWatcher.startWatching(modelState);
      sleep();
      changeFile(file);
      sleep();
      fileWatcher.stopWatching(modelState);

      assertNotifications(clientId, 1);
   }

   @Test
   void deletingWatchedFileNotifiesClient()
      throws IOException, InterruptedException {
      final String clientId = "1";
      final File file = createFile("test.txt");
      final GModelState modelState = modelState(clientId, fileUri(file));

      final FileWatcher fileWatcher = new FileWatcher(sessionManager, actionDispatcher);
      fileWatcher.setDebounceDelay(0);
      fileWatcher.startWatching(modelState);
      sleep();
      deleteFile(file);
      sleep();
      fileWatcher.stopWatching(modelState);

      assertNotifications(clientId, 1);
   }

   @Test
   void changingWatchedFileWhilePausedDoesntNotifyClient()
      throws IOException, InterruptedException {
      final String clientId = "1";
      final File file = createFile("test.txt");
      final GModelState modelState = modelState(clientId, fileUri(file));

      final FileWatcher fileWatcher = new FileWatcher(sessionManager, actionDispatcher);
      fileWatcher.setDebounceDelay(0);
      fileWatcher.startWatching(modelState);
      sleep();
      fileWatcher.pauseWatching(modelState);
      sleep();
      changeFile(file);
      sleep();
      fileWatcher.stopWatching(modelState);

      assertNoNotification(clientId);
   }

   @Test
   void changingWatchedFileAfterPauseAndContinueNotifiesClient()
      throws IOException, InterruptedException {
      final String clientId = "1";
      final File file = createFile("test.txt");
      final GModelState modelState = modelState(clientId, fileUri(file));

      final FileWatcher fileWatcher = new FileWatcher(sessionManager, actionDispatcher);
      fileWatcher.setDebounceDelay(0);
      fileWatcher.startWatching(modelState);
      sleep();
      fileWatcher.pauseWatching(modelState);
      sleep();
      fileWatcher.continueWatching(modelState);
      sleep();
      changeFile(file);
      sleep();
      fileWatcher.stopWatching(modelState);

      assertNotifications(clientId, 1);
   }

   @Test
   void changingWatchedFileNotifiesCorrectClient()
      throws IOException, InterruptedException {
      final String clientId1 = "1";
      final String clientId2 = "2";
      final File file1 = createFile("test1.txt");
      final File file2 = createFile("test2.txt");
      final GModelState modelState1 = modelState(clientId1, fileUri(file1));
      final GModelState modelState2 = modelState(clientId2, fileUri(file2));

      final FileWatcher fileWatcher = new FileWatcher(sessionManager, actionDispatcher);
      fileWatcher.setDebounceDelay(0);
      fileWatcher.startWatching(modelState1);
      fileWatcher.startWatching(modelState2);
      sleep();
      // only watched by clientId1
      changeFile(file1);
      sleep();

      // we only have a notification for clientId1
      assertNotifications(clientId1, 1);
      // and no notification for clientId2
      assertNoNotification(clientId2);

      sleep();
      // only watched by clientId2
      changeFile(file2);
      sleep();

      // now we have a notification for clientId2 too
      assertNotifications(clientId2, 1);
      // and still only one for clientId1
      assertNotifications(clientId1, 1);

      fileWatcher.stopWatching(modelState1);
      fileWatcher.stopWatching(modelState2);
   }

   @Test
   void changingWatchedFileNotifiesCorrectClientAlsoIfOtherClientIsPaused()
      throws IOException, InterruptedException {
      final String clientId1 = "1";
      final String clientId2 = "2";
      final File file1 = createFile("test1.txt");
      final File file2 = createFile("test2.txt");
      final GModelState modelState1 = modelState(clientId1, fileUri(file1));
      final GModelState modelState2 = modelState(clientId2, fileUri(file2));

      final FileWatcher fileWatcher = new FileWatcher(sessionManager, actionDispatcher);
      fileWatcher.setDebounceDelay(0);
      fileWatcher.startWatching(modelState1);
      fileWatcher.startWatching(modelState2);
      sleep();
      fileWatcher.pauseWatching(modelState2);
      sleep();
      changeFile(file1);
      changeFile(file2);
      sleep();

      // we only have a notification for clientId1
      assertNotifications(clientId1, 1);
      // and no notification for clientId2
      assertNoNotification(clientId2);

      fileWatcher.stopWatching(modelState1);
      fileWatcher.stopWatching(modelState2);
   }

   private void assertNoNotification(final String clientId) {
      if (actionDispatcher.dispatchedActions.get(clientId) != null) {
         assertEquals(actionDispatcher.dispatchedActions.get(clientId).size(), 0);
      }
   }

   private void assertNotifications(final String clientId, final int size) throws InterruptedException {
      final List<Action> actionsDispatchedToClient1 = actionDispatcher.dispatchedActions.get(clientId);
      assertEquals(actionsDispatchedToClient1.size(), size);
      for (int i = 0; i < size; i++) {
         assertTrue(actionsDispatchedToClient1.get(i) instanceof ModelSourceChangedAction);
      }
   }

   private File createFile(final String fileName)
      throws IOException {
      final File file = Path.of(DIR).resolve(fileName).toFile();
      deleteFile(file);
      file.createNewFile();
      return file;
   }

   private void deleteFile(final File file) {
      if (file.exists()) {
         file.delete();
      }
   }

   private String fileUri(final File file) {
      return "file://" + file.getAbsolutePath();
   }

   private void changeFile(final File file)
      throws IOException {
      Files.write(file.toPath(), "some Content".getBytes(), StandardOpenOption.APPEND);
   }

   private GModelState modelState(final String clientId, final String sourceUri) {
      final DefaultGModelState modelState = new DefaultGModelState();
      modelState.setClientId(clientId);
      Map<String, String> options = new HashMap<>();
      options.put(ClientOptionsUtil.SOURCE_URI, sourceUri);
      modelState.setClientOptions(options);
      return modelState;
   }

   private void sleep()
      throws InterruptedException {
      TimeUnit.MILLISECONDS.sleep(300);
   }

   @SuppressWarnings("checkstyle:VisibilityModifier")
   class RecordingActionDispatcher extends Disposable implements ActionDispatcher {
      Map<String, List<Action>> dispatchedActions = new HashMap<>();

      @Override
      public CompletableFuture<Void> dispatch(final String clientId, final Action action) {
         if (!dispatchedActions.containsKey(clientId)) {
            dispatchedActions.put(clientId, new ArrayList<Action>());
         }
         dispatchedActions.get(clientId).add(action);
         return CompletableFuture.completedFuture(null);
      }
   }

   class MockClientSessionManager implements ClientSessionManager {

      @Override
      public void dispose() {}

      @Override
      public boolean isDisposed() { return false; }

      @Override
      public Optional<ClientSession> createClientSession(final String clientSessionId, final String diagramType) {
         return Optional.empty();
      }

      @Override
      public Optional<ClientSession> getSession(final String clientSessionId) {
         return Optional.empty();
      }

      @Override
      public List<ClientSession> getSessionsByType(final String diagramType) {
         return Collections.emptyList();
      }

      @Override
      public boolean disposeClientSession(final String clientSessionId) {
         return false;
      }

      @Override
      public boolean addListener(final ClientSessionListener listener, final String... clientSessionIds) {
         return false;
      }

      @Override
      public boolean removeListener(final ClientSessionListener listener) {
         return false;
      }

      @Override
      public void removeListeners(final String... clientSessionIds) {}

   }

}
