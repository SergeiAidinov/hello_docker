FROM eclipse-temurin:21-jdk

WORKDIR /app

# копируем только серверный код
COPY src ./src

# компиляция
RUN javac \
    -d out \
    src/hello_docker/server/module-info.java \
    src/hello_docker/server/HelloDockerServer.java

# запуск
CMD ["java", "-p", "out", "-m", "hello_docker/hello_docker.server.HelloDockerServer"]
