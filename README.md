# Eclipse GLSP Server
Contains the code for the Java-based framework to create [GLSP](https://github.com/eclipse-glsp/glsp) server components.

## Structure
- `org.eclipse.glsp.api`: Java bindings for the GLSP API
- `org.eclipse.glsp.graph`: EMF-based implementation of graphical model that's used for client-server communication
- `org.eclipse.glsp.layout`: Server-based layout using the [Eclipse Layout Kernel](https://www.eclipse.org/elk/) framework (adapted from [Eclipse Sprotty Server](https://www.github.com/eclipse/sprotty-server))
- `org.eclipse.glsp.server`: Generic base implemenation for standalone GLSP servers (based on JSON-RPC)
- `org.eclipse.glsp.server.websocket`: Extension of the base server implementation for communication over websockets

