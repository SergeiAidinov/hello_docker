Docker Learning Project

Branches:

initial
Minimal working project. Prints 'Hello from Docker!' to the console.
Build: docker build -t hello-java .
Run: docker run --rm hello-java

cycle
Slightly more advanced project. Continuously prints 'Hello from Docker!' to the console.
Build: docker build -t hello-java .
Run: docker run hello-java

port
Demonstrates port forwarding.
Build: docker build -t hello-java .
Run: docker run --rm -p 8080:8080 hello-java
Access the port: http://localhost:8080/
