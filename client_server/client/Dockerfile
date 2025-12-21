FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY src ./src

RUN javac \
    -d out \
    src/hello_docker/client/module-info.java \
    src/hello_docker/client/HelloDockerClient.java

CMD ["java", "-p", "out", "-m", "hello_docker/hello_docker.client.HelloDockerClient"]
