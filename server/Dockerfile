# Используем JDK 21
FROM eclipse-temurin:21-jdk

# Рабочая директория
WORKDIR /app

# Создаём папку lib и скачиваем драйвер PostgreSQL
RUN mkdir -p lib && \
    curl -L https://jdbc.postgresql.org/download/postgresql-42.6.0.jar -o lib/postgresql-42.6.0.jar && \
    ls -l lib/

# CMD будет компилировать код и запускать сервер при старте контейнера
CMD javac -cp lib/postgresql-42.6.0.jar -d out src/hello_docker/server/*.java && \
    java -cp out:lib/postgresql-42.6.0.jar hello_docker.server.HelloDockerServer
