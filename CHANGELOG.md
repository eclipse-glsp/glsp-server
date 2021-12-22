# Eclipse GLSP Server Changelog
## v0.10.0 - Upcoming

### Changes

- [documentation] Improved existing API javadoc and added missing documentation for service interfaces. [#487](https://github.com/eclipse-glsp/glsp-server/pull/146) - Contributed on behalf of STMicroelectronics
- 

### Breaking Changes



## [v0.9.0- 09/12/2021](https://github.com/eclipse-glsp/glsp/releases/tag/0.9.0)

### Changes

- [websocket] Fixed issue that was caused by reusing a shared injector for each client connection [#149](https://github.com/eclipse-glsp/glsp-server/pull/91)
- [navigation] Added a utility class for 'JsonOpenerOptions' [#153](https://github.com/eclipse-glsp/glsp-server/pulls?q=92+93)
- [diagram] Merged the `ServerLayoutConfiguration` API into the `DiagramConfiguration` API. [#123](https://github.com/eclipse-glsp/glsp-server/pull/95)
- [model] Reworked model update flow. All aspects of the update process are now handled by the `ModelSubmissionHandler` [#122](https://github.com/eclipse-glsp/glsp-server/pull/95)
- [model] Reworked and split `ModelFactory` API into a dedicated component responsible for loading the model source (`ModelSourceLoader`) and a component responsible for transforming the current modelstate into its GModel-representation (`GModelFactory`) [#119](https://github.com/eclipse-glsp/glsp-server/pull/96)
- [protocol] Added implementation for `SetViewportAction` [#179](https://github.com/eclipse-glsp/glsp-server/pull/99)
- [model] Introduced `GArgumentable` interface which is implemented by all `GModelElement`s. This can be used to provide additional information in form of an `args` map without having to extend the `GModel` type [#194](https://github.com/eclipse-glsp/glsp-server/pull/100)
- [model] Extended default type mapping and added builder for `GArgumentable` elements and corresponding utility classes [#180](https://github.com/eclipse-glsp/glsp-server/pull/105)
- [protocol] Added `fileUri` property to `SaveModelAction` and updated `SaveModelActionHandler` accordingly [#208](https://github.com/eclipse-glsp/glsp-server/pull/103/)
- [protocol] Added optional `reason` string property to `SetDirtyStateAction`. This property indicates the reason that caused to dirty state change and enables more fine granular handling of dirty state changes [#197](https://github.com/eclipse-glsp/glsp-server/pull/101)
- [build] Update dependencies of Google Guava (>= 30.1) and Google Guice (>= 5.0.0). [#260](https://github.com/eclipse-glsp/glsp-server/pull/119)
- [protocol] Refactor the base communication protocol to support initializing and disposing a client session. [#315](https://github.com/eclipse-glsp/glsp-server/pull/123)
- [server] The `org.apache.log4j` dependency is not reexported any more when consumed as p2 artifact. [#430](https://github.com/eclipse-glsp/glsp-eclipse-integration/pull/43)
- [di] Complete rework of DI architecture. Move away from one global injector per application. Instead use one global server injector and dedicated child injectors for each diagram session. [#150](https://github.com/eclipse-glsp/glsp-server/pull/127)
- [launch] Improve customizability of `DefaultGLSPServerLauncher`. [#385](https://github.com/eclipse-glsp/glsp-server/pull/128)
- [model] Add properties map in model state to store arbitrary properties that are persistent between handler calls. [#401](https://github.com/eclipse-glsp/glsp-server/pull/132)
- [server] Refactor json-rpc protocol and `DefaultGLSPServerImplementation`. [#421](https://github.com/eclipse-glsp/glsp-server/pull/133)
- [action] Rework `ActionHandler` & `OperationHandler` API. [#425](https://github.com/eclipse-glsp/glsp-server/pull/135)
- [action] Introduce `dispatchAfterNextUpdate` functionality for the `ActionDispatcher`. This enables queuing of actions until the next model updates has been processed. [#448](https://github.com/eclipse-glsp/glsp-server/pull/141/)
- [protocol] Align server-side action definitions with [protocol definition](https://github.com/eclipse-glsp/glsp/blob/master/PROTOCOL.md). [#432](https://github.com/eclipse-glsp/glsp-server/pull/142)

### Breaking Changes

- [diagram] Merged the `ServerLayoutConfiguration` API into the `DiagramConfiguration` API. The standalone `ServerLayoutConfiguration` is now deprecated and no longer supported [#123](https://github.com/eclipse-glsp/glsp-server/pull/95)
- [model] Renamed `ModelFactory` to `ModelSourceLoader` and adapted interface method. This also affects implementing classes like the `JsonFileModelFactory` [#119](https://github.com/eclipse-glsp/glsp-server/pull/96)
- [model] Reworked `ModelSubmissionHandler` API. This includes changes of method names and parameters [#119](https://github.com/eclipse-glsp/glsp-server/pull/96) [#197](https://github.com/eclipse-glsp/glsp-server/pull/101)
- [build] Update dependencies of Google Guava (>= 30.1) and Google Guice (>= 5.0.0). [#260](https://github.com/eclipse-glsp/glsp-server/pull/119)
- [build] The `org.apache.log4j` dependency is not reexported any more when consumed as p2 artifact. You need to import `org.apache.log4j` in your own plugins if you use the log4j API there. [#430](https://github.com/eclipse-glsp/glsp-eclipse-integration/pull/43)
- [protocol] Refactor the base communication protocol to support initializing and disposing a client session. Remove now obsolete `InitializeClientSessionAction` and `DisposeClientSessionAction`. [#315](https://github.com/eclipse-glsp/glsp-server/pull/123)
- [di] Complete rework of DI architecture. Your main module now has to extend `DiagramModule` instead of `GLSPModule`. Includes restructurings,cleanups and refactorings that  affect most of the API components. [#150](https://github.com/eclipse-glsp/glsp-server/pull/127)
- [server] Refactor json-rpc protocol and `DefaultGLSPServerImplementation`. This includes changes of method names and parameters [#421](https://github.com/eclipse-glsp/glsp-server/pull/133)

## [v0.8.0 - 20/10/2020](https://github.com/eclipse-glsp/glsp/releases/tag/0.8.0)

This is the first release of Eclipse GLSP since it is hosted at the Eclipse Foundation. The 0.8.0 release includes new protocol message types and respective framework support for several new features, such as copy-paste, diagram navigation, etc. It also contains several clean-ups of the protocol and refactorings to simplify and streamline the API. The Eclipse Theia integration of GLSP features many improvements, such as problem marker integration, native context menu items and keybindings. Finally, several bug fixes and minor are part of this release as well.
