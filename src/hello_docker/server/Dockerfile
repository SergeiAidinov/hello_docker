FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY src/hello_docker ./hello_docker
RUN javac hello_docker/server/HelloDockerServer.java
CMD ["java", "hello_docker.server.HelloDockerServer"]
