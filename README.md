This is suppossed to be used as a dependency for real projects. 


The server based component takes a simple list of protocol-backends (consisting of the protocol itself and a few helper-methods for serializing; use upickle if you want to implement them quickly) and returns a akka-http route (combined from all protocols).


The client based component consists of helpers and a Frontend-Protocol implementation. This implementation includes UI framework building.
