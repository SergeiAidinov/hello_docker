# Используем JDK 21
FROM eclipse-temurin:21-jdk

# Рабочая директория
WORKDIR /app

# Копируем исходники сервера
COPY src ./src

# Создаём папку lib и скачиваем драйвер PostgreSQL
RUN mkdir -p lib && \
    curl -L https://jdbc.postgresql.org/download/postgresql-42.6.0.jar -o lib/postgresql-42.6.0.jar && \
    ls -l lib/

# Компиляция всех файлов сервера без модульности
RUN javac -cp lib/postgresql-42.6.0.jar -d out src/hello_docker/server/*.java

# Запуск сервера с драйвером в classpath
CMD ["java", "-cp", "out:lib/postgresql-42.6.0.jar", "hello_docker.server.HelloDockerServer"]
