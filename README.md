# Eclipse GLSP Server [![build-status](https://img.shields.io/jenkins/build?jobUrl=https%3A%2F%2Fci.eclipse.org%2Fglsp%2Fjob%2Feclipse-glsp%2Fjob%2Fglsp-server%2Fjob%2Fmaster%2F)](https://ci.eclipse.org/glsp/job/eclipse-glsp/job/glsp-server/job/master/)

Contains the code for the Java-based framework to create [GLSP](https://github.com/eclipse-glsp/glsp) server components.

## Building

The GLSP server bundles are built with Java 11 or higher and maven.
Execute `mvn clean verify -Pm2` (for maven) or `mvn clean verify -Pp2` (for p2).
The nightly builds are available as maven repository or p2 update site.

### Maven Repositories [![build-status-server](https://img.shields.io/jenkins/build?jobUrl=https://ci.eclipse.org/glsp/job/deploy-m2-glsp-server/&label=publish)](https://ci.eclipse.org/glsp/job/deploy-m2-glsp-server/)

- *Snapshots:* <https://oss.sonatype.org/content/repositories/snapshots/org/eclipse/glsp/>
- *Releases/Release Candiates:* <https://oss.sonatype.org/content/groups/public/org/eclipse/glsp/> (also mirrored to the [maven central repository](https://search.maven.org/search?q=org.eclipse.glsp))

### P2 Update Sites   [![build-status-server](https://img.shields.io/jenkins/build?jobUrl=https://ci.eclipse.org/glsp/job/deploy-p2-glsp-server/&label=publish)](https://ci.eclipse.org/glsp/job/deploy-p2-glsp-server/)

- *Snapshots:* <https://download.eclipse.org/glsp/server/p2/nightly/>
- *Release Candidates:* <https://download.eclipse.org/glsp/server/p2/staging/>
- *Releases:* <https://download.eclipse.org/glsp/server/p2/releases/>

All changes on the master branch are deployed automatically to the corresponding snapshot repositories.

## Structure of this repository

- `org.eclipse.glsp.graph`: EMF-based implementation of graphical model that's used for client-server communication
- `org.eclipse.glsp.layout`: Server-based layout using the [Eclipse Layout Kernel](https://www.eclipse.org/elk/) framework (adapted from [Eclipse Sprotty Server](https://www.github.com/eclipse/sprotty-server))
- `org.eclipse.glsp.server`: Generic base implementation for standalone GLSP servers (based on JSON-RPC)
- `org.eclipse.glsp.server.emf`: Reusable implementations if an [EMF](https://www.eclipse.org/modeling/emf/)-based source model is used
- `org.eclipse.glsp.server.websocket`: Extension of the base server implementation for communication over websockets

- `org.eclipse.glsp.example.workflow`: GLSP server for the Workflow Diagram example

## Workflow Diagram Example

The workflow diagram is a consistent example provided by all GLSP components.
The example implements a simple flow chart diagram editor with different types of nodes and edges (see screenshot below).
The example can be used to try out different GLSP features, as well as several available integrations with IDE platforms (Theia, VSCode, Eclipse, Standalone).
As the example is fully open source, you can also use it as a blueprint for a custom implementation of a GLSP diagram editor.
See [our project website](https://www.eclipse.org/glsp/documentation/#workflowoverview) for an overview of the workflow example and all components implementing it.

https://user-images.githubusercontent.com/588090/154459938-849ca684-11b3-472c-8a59-98ea6cb0b4c1.mp4

### How to start the Workflow Diagram example?

To see the diagram in action, you need to choose and launch one diagram client, see [here for an overview of available clients](https://www.eclipse.org/glsp/examples/#workflowoverview).

-   [`glsp-theia-integration`](https://github.com/eclipse-glsp/glsp-theia-integration): Diagrams clients integrated into [Theia](https://github.com/theia-ide/theia).
-   [`glsp-vscode-integration`](https://github.com/eclipse-glsp/glsp-vscode-integration): Diagram clients integrated into [VSCode](https://github.com/microsoft/vscode).
-   [`glsp-eclipse-integration`](https://github.com/eclipse-glsp/glsp-eclipse-integration): Diagram clients integrated into Eclipse IDE.

Please look at the workflow example guides in the repository linked above to get more information on building and running the respecitive GLSP clients.

### Building the Workflow Diagram example server

In the root of this repository, run

```bash
mvn clean verify -Pm2 -Pfatjar
```

### Execute from IDE

To run the Workflow Diagram example server within an IDE, run the main method of
[`WorkflowServerLauncher.java`](./examples/org.eclipse.glsp.example.workflow/src/org/eclipse/glsp/example/workflow/launch/WorkflowServerLauncher.java) as a Java Application, located in the module `glsp-server/examples/org.eclipse.glsp.example.workflow.launch`.

### Execute Standalone JAR

In the folder `examples/org.eclipse.glsp.example.workflow/target`, you should have a jar file `org.eclipse.glsp.example.workflow-X.X.X-SNAPSHOT-glsp.jar` whereas `X.X.X` is the current version.

To run the Workflow Diagram example server standalone JAR, run this command in your terminal:

```console
    cd examples/org.eclipse.glsp.example.workflow/target
    java -jar org.eclipse.glsp.example.workflow-X.X.X-SNAPSHOT-glsp.jar
```

#### Usage

```console
    usage: java -jar org.eclipse.glsp.example.workflow-X.X.X-glsp.jar [-c <arg>] [-d <arg>]
        [-f <arg>] [-h] [-j <arg>] [-l <arg>] [-p <arg>] [-w]

    options:
    -c,--consoleLog <arg>      Enable/Disable console logging. [default='true']
    -d,--logDir <arg>          Set the directory for log files (File logging has to be
                                enabled)
    -f,--fileLog <arg>         Enable/Disable file logging. [default='false']
    -h,--help                  Display usage information about GLSPServerLauncher
    -j,--jettyLogLevel <arg>   Set the log level for the Jetty websocket server.
                                [default='INFO']
    -l,--logLevel <arg>        Set the log level. [default='INFO']
    -p,--port <arg>            Set server port. [default='5007']
    -w,--websocket             Use websocket launcher instead of default launcher.
```

Once the server is running, choose a diagram client integration (such as Eclipse Theia, VSCode, Eclipse, or Standalone) below.

### Where to find the sources?

In addition to this repository, the related source code can be found here:

- <https://github.com/eclipse-glsp/glsp-client>
- <https://github.com/eclipse-glsp/glsp-theia-integration>
- <https://github.com/eclipse-glsp/glsp-eclipse-integration>
- <https://github.com/eclipse-glsp/glsp-vscode-integration>

## See also

For more information, please visit the [Eclipse GLSP Umbrella repository](https://github.com/eclipse-glsp/glsp) and the [Eclipse GLSP Website](https://www.eclipse.org/glsp/).
If you have questions, please raise them in the [discussions](https://github.com/eclipse-glsp/glsp/discussions) and have a look at our [communication and support options](https://www.eclipse.org/glsp/contact/).
