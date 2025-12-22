Docker Learning Project

Branches:

[initial](https://github.com/SergeiAidinov/hello_docker/tree/initial)<br>
Minimal working project. Prints 'Hello from Docker!' to the console.<br>
Build: docker build -t hello-java .<br>
Run: docker run --rm hello-java<br>

[cycle](https://github.com/SergeiAidinov/hello_docker/tree/cycle)<br>
Slightly more advanced project. Continuously prints 'Hello from Docker!' to the console.<br>
Build: docker build -t hello-java .<br>
Run: docker run hello-java<br>

[port](https://github.com/SergeiAidinov/hello_docker/tree/port)<br>
Demonstrates port forwarding.<br>
Build: docker build -t hello-java .<br>
Run: docker run --rm -p 8080:8080 hello-java<br>
Access the port: http://localhost:8080/<br>

[bridge](https://github.com/SergeiAidinov/hello_docker/tree/bridge)<br>
Multi-container Java project with server and client.<br>
Build: docker compose up --build<br>
Run: docker compose up<br>
Stop: docker compose down<br>
Access the port: http://localhost:8080/<br>

[client_server](https://github.com/SergeiAidinov/hello_docker/tree/initial)<br>
Multi-container Java project with a server and a client.<br> The server exposes an HTTP endpoint, and the client communicates with it over a Docker Compose network.<br>
Build & Run: docker compose up --build<br>
Run (without rebuild): docker compose up<br>
Stop: docker compose down<br>
Access the server: http://localhost:8080/
