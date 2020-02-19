# Eclipse GLSP Server [![build-status](https://img.shields.io/jenkins/build?jobUrl=https%3A%2F%2Fci.eclipse.org%2Fglsp%2Fjob%2Feclipse-glsp%2Fjob%2Fglsp-server%2Fjob%2Fmaster%2F)](https://ci.eclipse.org/glsp/job/eclipse-glsp/job/glsp-server/job/master/)

Contains the code for the Java-based framework to create [GLSP](https://github.com/eclipse-glsp/glsp) server components.

## Structure
- `org.eclipse.glsp.api`: Java bindings for the GLSP API
- `org.eclipse.glsp.graph`: EMF-based implementation of graphical model that's used for client-server communication
- `org.eclipse.glsp.layout`: Server-based layout using the [Eclipse Layout Kernel](https://www.eclipse.org/elk/) framework (adapted from [Eclipse Sprotty Server](https://www.github.com/eclipse/sprotty-server))
- `org.eclipse.glsp.server`: Generic base implemenation for standalone GLSP servers (based on JSON-RPC)
- `org.eclipse.glsp.server.websocket`: Extension of the base server implementation for communication over websockets

## Building

The GLSP server bundles are built with `mvn clean install -Pm2` (for maven) or `mvn clean install -Pp2` (for p2). The nightly builds are available as maven repository or p2 update site.

### Maven Repositories [![build-status-server](https://img.shields.io/jenkins/build?jobUrl=https://ci.eclipse.org/glsp/job/deploy-m2-glsp-server/&label=publish)](https://ci.eclipse.org/glsp/job/deploy-m2-glsp-server/)

- <i>Snapshots: </i> https://oss.sonatype.org/content/repositories/snapshots/org/eclipse/glsp/
- <i>Releases/Release Candiates: </i> https://oss.sonatype.org/content/groups/public/org/eclipse/glsp/

### P2 Update Sites   [![build-status-server](https://img.shields.io/jenkins/build?jobUrl=https://ci.eclipse.org/glsp/job/deploy-p2-glsp-server/&label=publish)](https://ci.eclipse.org/glsp/job/deploy-p2-glsp-server/)
- <i>Snapshots: </i> https://download.eclipse.org/glsp/server/p2/nightly/

All changes on the master branch are deployed automatically to the corresponding snapshot repositories.

## See also
For more information, please visit the [Eclipse GLSP Umbrella repository](https://github.com/eclipse-glsp/glsp) and the [Eclipse GLSP Website](https://www.eclipse.org/glsp/). If you have questions, contact us on our [spectrum chat](https://spectrum.chat/glsp/) and have a look at our [communication and support options](https://www.eclipse.org/glsp/contact/).

![alt](https://www.eclipse.org/glsp/images/diagramanimated.gif)
