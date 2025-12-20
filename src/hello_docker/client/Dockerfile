FROM eclipse-temurin:21-jdk
WORKDIR /app

# Копируем структуру пакета в контейнер
COPY src/hello_docker ./hello_docker

# Компилируем Java-класс с пакетом
RUN javac hello_docker/client/HelloDockerClient.java

# Запуск с полным именем пакета
CMD ["java", "hello_docker.client.HelloDockerClient"]
