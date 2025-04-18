# Eclipse GLSP Server Changelog

## v2.5.0 - active

### Changes

### Potentially Breaking Changes

## [v2.4.0 - 04/04/2025](https://github.com/eclipse-glsp/glsp-server/releases/tag/v2.4.0)

### Changes

- [operation] Avoid unnecessary model changes after empty operations [#253](https://github.com/eclipse-glsp/glsp-server/pull/253)

## [v2.3.0 - 27/12/2024](https://github.com/eclipse-glsp/glsp-server/releases/tag/v2.3.0)

### Changes

- [API] Fix: do not bind the DefaultActionDispatcher as ActionHandler, so it can be correctly disposed and the thread is closed at the end of a session [#246](https://github.com/eclipse-glsp/glsp-server/pull/246) - Contributed on behalf of Axon Ivy AG
- [API] Align default type configuration with client side [#245](https://github.com/eclipse-glsp/glsp-server/pull/245)
  - Introduce `GForeignObjectElement` and corresponding builder
  - Update default type mapping configuration to match the configuration on client side
- Introduce async live model validation behavior [#247](https://github.com/eclipse-glsp/glsp-server/pull/247)

### Potentially Breaking Changes

- [deps] Remove dependency to Google Guava [#244](https://github.com/eclipse-glsp/glsp-server/pull/244)
  - The core framework no longer depends on any Guava components.
  - Introduced custom `BiIndex` class to replace usage of Guava's `BiMap`
  - Adopters that want that still depend  on Guava code have to ensure that the dependency is available as it is no longer shipped with GLSP
- [deps] Update dependencies to Jakarta namespace and Eclipse 2024-x [#249](https://github.com/eclipse-glsp/glsp-server/pull/249)
  - This includes a switch to Jetty 12.x, and the Jakarta variant of LSP4j Websocket. For adopters that still rely on the javax namespace and [alternative release](https://github.com/eclipse-glsp/glsp-server/releases/tag/v2.3.0.javax) is provided

## [v2.2.1 - 22/07/2024](https://github.com/eclipse-glsp/glsp-server/releases/tag/v2.2.1)

### Changes

- [build] Restrict log4j version to 2.19.0 <= x <= 2.23.1 for maven [#230](https://github.com/eclipse-glsp/glsp-server/pull/230)
- [model] Allow definition of resize handle locations for `GShapeElements` [#231](https://github.com/eclipse-glsp/glsp-server/pull/231)
- [api] Improve extensibility of `DefaultActionDispatcher` [#235](https://github.com/eclipse-glsp/glsp-server/pull/235)- Contributed on behalf of Axon Ivy AG

## [v2.1.0 -  24/01/2024](https://github.com/eclipse-glsp/glsp-server/releases/tag/v2.1.0)

### Changes

- [diagram] Add support for rendering ghost/template elements during node creation [#221](https://github.com/eclipse-glsp/glsp-server/pull/221)

## [v2.0.0 - 16/11/2023](https://github.com/eclipse-glsp/glsp-server/releases/tag/v2.0.0)

### Changes

- [graph] Updated the Graph model and add Layoutable interface [#175](https://github.com/eclipse-glsp/glsp-server/pull/175) - Contributed on behalf of STMicroelectronics
- [layout] Extend `ComputedBoundsAction` to also provide route data for client-side routed edges and store source/target point in the `args` map [#181](https://github.com/eclipse-glsp/glsp-server/pull/181)
- [websocket] Remove listing on `stdin` from `WebsocketServerLauncher` [#189](https://github.com/eclipse-glsp/glsp-server/pull/189)
- [diagram] Fix a bug that prevented stable ids within one session when using the `IdKeeperAdapter` [#192](https://github.com/eclipse-glsp/glsp-server/pull/192) - Contributed on behalf of STMicroelectronics
- [API] Introduce `deselectAll` flag for `SelectAction`s [#204](https://github.com/eclipse-glsp/glsp-server/pull/204)
- [API] Add support for progress reporting [#205](https://github.com/eclipse-glsp/glsp-server/pull/205)
- [diagram] Add support for handling reconnection requests to RequestModelActionHandler [#208](https://github.com/eclipse-glsp/glsp-server/pull/208)
- [diagram] Add support for dynamic edge type hints [#210](https://github.com/eclipse-glsp/glsp-server/pull/210)
  - Provide EdgeCreationChecker API. Adopters can implement this to handle dynamic edge creation validation requests.
- [launch] Add `hostname` arg to `CLIParser` [#214](https://github.com/eclipse-glsp/glsp-server/pull/214)
- [deps] Remove upper bounds version constraints for Guice and Guava [#216](https://github.com/eclipse-glsp/glsp-server/pull/216)

### Breaking Changes

- [websocket] Update to Jetty Websocket 10 [#185](https://github.com/eclipse-glsp/glsp-server/pull/185) [#186](https://github.com/eclipse-glsp/glsp-server/pull/186) - Contributed on behalf of STMicroelectronics
  - This includes breaking changes due to major API changes in Jetty and the following new minimum versions:
    - Jetty 9.x -> Jetty 10.0.13
    - LSP4J -> 0.8.0 -> 0.11.0
    - ELK 0.7.0 -> 0.8.1
- [operation] Rework `OperationHandler` to provide an optional command instead of direct execution to allow more execution control [#187](https://github.com/eclipse-glsp/glsp-server/pull/187)
  - `Abstract<XYZ>` base implementations were deprecated in favor of `GModelOperationHandler` and `EMFOperationHandler` base classes
  - Long-term deprecated and unused `Basic<XYZ>` base classes were removed
- [modelstate] Use interface-injection for all subclasses of GModelState (EMFModelState, EMFNotationModelState) [#199](https://github.com/eclipse-glsp/glsp-server/pull/199)
  - `EMFModelState` and `EMFNotationModelState` are now interfaces instead of classes
  - Add `EMFModelStateImpl` and `EMFNotationModelStateImpl` classes
  - Update related modules to inject these GModelState sub-types as a Singleton
- [validation] Add explicit support and API for live and batch validation [#200](https://github.com/eclipse-glsp/glsp-server/pull/200)
- [server] Change default ports from 5007 (and 8081 for websockets) to 0, which implies autoassignment by the OS [#198](https://github.com/eclipse-glsp/glsp-server/pull/198)
- [API] Remove deprecated `GConstants.STACK` constant [#209](https://github.com/eclipse-glsp/glsp-server/pull/209)
- [API] Revise model loading and client action handling [#211](https://github.com/eclipse-glsp/glsp-server/pull/211) 
  - Refactor `ModelSubmissionHandler` to enable handling of `RequestModelAction` as proper request action
    - Introduce a `submitInitialModel` method that is called by the `RequestModelActionHandler`
  - Remove `configureClientActions` from `DiagramModule` as client actions are now implicitly configured via `InitializeClientSession` request
  - Remove `ClientActionHandler` and replace with `ClientActionForwarder`
  - Rename `ServerStatusAction` -> `StatusAction` and `ServerMessageAction` -> `MessageAction`
- [deps] Set Java compliance level to Java 17 as Java 11 is now EOL [#217](https://github.com/eclipse-glsp/glsp-server/pull/217)

## [v1.0.0 - 30/06/2022](https://github.com/eclipse-glsp/glsp-server/releases/tag/v1.0.0)

### Changes

- [documentation] Improved existing API javadoc and added missing documentation for service interfaces. [#164](https://github.com/eclipse-glsp/glsp-server/pull/146) - Contributed on behalf of STMicroelectronics
- [graph] Fixed a bug that lead to incomplete `GModelIndex` initialization [#143](https://github.com/eclipse-glsp/glsp-server/pull/143)
- [example] Improved styling and cleaned up code of the workflow example [#148](https://github.com/eclipse-glsp/glsp-server/pull/148) [#156](https://github.com/eclipse-glsp/glsp-server/pull/156)
- [gson] Ensure that `DefaultServerGsonConfigurator` properly disposes temporary DI diagram sessions after server initialization.[#151](https://github.com/eclipse-glsp/glsp-server/pull/151)
- [layout] Ensure that `LayoutEngine` is injected as optional field to avoid `NullPointerExceptions`s if no implementation is bound [#153](https://github.com/eclipse-glsp/glsp-server/pull/153/)
- [build] Remove dependency to `org.apache.commons.io` [#157](https://github.com/eclipse-glsp/glsp-server/pull/157)
- [emf] Inception of new `org.eclipse.glsp.server.emf` package + example models
  - Provides reusable base classes for EMF-based source models [#159](https://github.com/eclipse-glsp/glsp-server/pull/159)
- [graph] Introduced `GShapePrerenderedElementBuilder` to enable easy construction of shaped prerendered elements eg. foreign object elements. [#168](https://github.com/eclipse-glsp/glsp-server/pull/168)
- [diagram] Fixed a bug that could occur during saving by ensuring that all pending actions are dispatched before a client session is disposed. [#172](https://github.com/eclipse-glsp/glsp-server/pull/172)

### Breaking Changes

- [protocol] Align server actions with the definitions in @eclipse-glsp-protocol [#142](https://github.com/eclipse-glsp/glsp-server/pull/142)
  - Affects methods and return types of all action classes.
- [model] Source model refactoring [#154](https://github.com/eclipse-glsp/glsp-server/pull/154)
  - `ModelSourceLoader` → `SourceModelStorage`
  - `ModelSourceWatcher` → `SourceModelWatcher`
  - Added method to `SourceModelStorage`
- [model] Refactoring as part of adding new GLSP examples [#159](https://github.com/eclipse-glsp/glsp-server/pull/159), [#161](https://github.com/eclipse-glsp/glsp-server/pull/161)
  - Renamed `setRoot` to `updateRoot` in model state to better reflect dependent updates, remove re-generation of command stack in method but add index re-generation
- [operation] Refactoring as part of cleaning up operation handlers [#164](https://github.com/eclipse-glsp/glsp-server/pull/164)
  - Renamed `CreateEdgeOperationHandler` to `AbstractCreateEdgeOperationHandler`
  - Renamed `CreateNodeOperationHandler` to `AbstractCreateNodeOperationHandler`
- [gmodel] Move base diagram module and operation handlers that operate directly on GModels (as a model source) to the dedicated package `org.eclipse.glsp.server.gmodel` and add prefix `GModel` in the class name [#165](https://github.com/eclipse-glsp/glsp-server/pull/165)
- [logging] Update from log4j 1.x to log4j 2.17.1. [#163](https://github.com/eclipse-glsp/glsp-server/pull/163/)
  - Affects logger creation across all classes
- [protocol] Rename `ModelSourceChangedAction` to `SourceModelChangedAction` including handlers [#171](https://github.com/eclipse-glsp/glsp-server/pull/171)

## [v0.9.0- 09/12/2021](https://github.com/eclipse-glsp/glsp/releases/tag/0.9.0)

### Changes

- [websocket] Fixed issue that was caused by reusing a shared injector for each client connection [#91](https://github.com/eclipse-glsp/glsp-server/pull/91)
- [navigation] Added a utility class for 'JsonOpenerOptions' [#92](https://github.com/eclipse-glsp/glsp-server/pull/92)
- [diagram] Merged the `ServerLayoutConfiguration` API into the `DiagramConfiguration` API. [#95](https://github.com/eclipse-glsp/glsp-server/pull/95)
- [model] Reworked model update flow. All aspects of the update process are now handled by the `ModelSubmissionHandler` [#122](https://github.com/eclipse-glsp/glsp-server/pull/95)
- [model] Reworked and split `ModelFactory` API into a dedicated component responsible for loading the model source (`ModelSourceLoader`) and a component responsible for transforming the current modelstate into its GModel-representation (`GModelFactory`) [#96](https://github.com/eclipse-glsp/glsp-server/pull/96)
- [protocol] Added implementation for `SetViewportAction` [#99](https://github.com/eclipse-glsp/glsp-server/pull/99)
- [model] Introduced `GArgumentable` interface which is implemented by all `GModelElement`s. This can be used to provide additional information in form of an `args` map without having to extend the `GModel` type [#100](https://github.com/eclipse-glsp/glsp-server/pull/100)
- [model] Extended default type mapping and added builder for `GArgumentable` elements and corresponding utility classes [#105](https://github.com/eclipse-glsp/glsp-server/pull/105)
- [protocol] Added `fileUri` property to `SaveModelAction` and updated `SaveModelActionHandler` accordingly [#103](https://github.com/eclipse-glsp/glsp-server/pull/103/)
- [protocol] Added optional `reason` string property to `SetDirtyStateAction`. This property indicates the reason that caused to dirty state change and enables more fine granular handling of dirty state changes [#101](https://github.com/eclipse-glsp/glsp-server/pull/101)
- [build] Update dependencies of Google Guava (>= 30.1) and Google Guice (>= 5.0.0). [#119](https://github.com/eclipse-glsp/glsp-server/pull/119)
- [protocol] Refactor the base communication protocol to support initializing and disposing a client session. [#123](https://github.com/eclipse-glsp/glsp-server/pull/123)
- [server] The `org.apache.log4j` dependency is not reexported any more when consumed as p2 artifact. [#43](https://github.com/eclipse-glsp/glsp-eclipse-integration/pull/43)
- [di] Complete rework of DI architecture. Move away from one global injector per application. Instead use one global server injector and dedicated child injectors for each diagram session. [#127](https://github.com/eclipse-glsp/glsp-server/pull/127)
- [launch] Improve customizability of `DefaultGLSPServerLauncher`. [#128](https://github.com/eclipse-glsp/glsp-server/pull/128)
- [model] Add properties map in model state to store arbitrary properties that are persistent between handler calls. [#132](https://github.com/eclipse-glsp/glsp-server/pull/132)
- [server] Refactor json-rpc protocol and `DefaultGLSPServerImplementation`. [#133](https://github.com/eclipse-glsp/glsp-server/pull/133)
- [action] Rework `ActionHandler` & `OperationHandler` API. [#135](https://github.com/eclipse-glsp/glsp-server/pull/135)
- [action] Introduce `dispatchAfterNextUpdate` functionality for the `ActionDispatcher`. This enables queuing of actions until the next model update has been processed. [#141](https://github.com/eclipse-glsp/glsp-server/pull/141/)
- [protocol] Align server-side action definitions with [protocol definition](https://github.com/eclipse-glsp/glsp/blob/master/PROTOCOL.md). [#142](https://github.com/eclipse-glsp/glsp-server/pull/142)

### Breaking Changes

- [diagram] Merged the `ServerLayoutConfiguration` API into the `DiagramConfiguration` API. The standalone `ServerLayoutConfiguration` is now deprecated and no longer supported [#95](https://github.com/eclipse-glsp/glsp-server/pull/95)
- [model] Renamed `ModelFactory` to `ModelSourceLoader` and adapted interface method. This also affects implementing classes like the `JsonFileModelFactory` [#96](https://github.com/eclipse-glsp/glsp-server/pull/96)
- [model] Reworked `ModelSubmissionHandler` API. This includes changes of method names and parameters [#101](https://github.com/eclipse-glsp/glsp-server/pull/96) [#197](https://github.com/eclipse-glsp/glsp-server/pull/101)
- [build] Update dependencies of Google Guava (>= 30.1) and Google Guice (>= 5.0.0). [#119](https://github.com/eclipse-glsp/glsp-server/pull/119)
- [build] The `org.apache.log4j` dependency is not reexported any more when consumed as p2 artifact. You need to import `org.apache.log4j` in your own plugins if you use the log4j API there. [#43](https://github.com/eclipse-glsp/glsp-eclipse-integration/pull/43)
- [protocol] Refactor the base communication protocol to support initializing and disposing a client session. Remove now obsolete `InitializeClientSessionAction` and `DisposeClientSessionAction`. [#123](https://github.com/eclipse-glsp/glsp-server/pull/123)
- [di] Complete rework of DI architecture. Your main module now has to extend `DiagramModule` instead of `GLSPModule`. Includes restructurings,cleanups and refactorings that affect most of the API components. [#127](https://github.com/eclipse-glsp/glsp-server/pull/127)
- [server] Refactor json-rpc protocol and `DefaultGLSPServerImplementation`. This includes changes of method names and parameters [#133](https://github.com/eclipse-glsp/glsp-server/pull/133)

## [v0.8.0 - 20/10/2020](https://github.com/eclipse-glsp/glsp/releases/tag/0.8.0)

This is the first release of Eclipse GLSP since it is hosted at the Eclipse Foundation. The 0.8.0 release includes new protocol message types and respective framework support for several new features, such as copy-paste, diagram navigation, etc. It also contains several clean-ups of the protocol and refactorings to simplify and streamline the API. The Eclipse Theia integration of GLSP features many improvements, such as problem marker integration, native context menu items and keybindings. Finally, several bug fixes and minor are part of this release as well.
