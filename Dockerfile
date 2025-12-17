FROM eclipse-temurin:21-jdk

WORKDIR /app

# Копируем папку с пакетами целиком
COPY src ./src

# Компилируем (javac сам создаст .class в нужной структуре)
RUN javac src/hello_docker/HelloDocker.java

# Пробрасываем порт 8080
EXPOSE 8080

# Запускаем с полным именем класса
CMD ["java", "-cp", "src", "hello_docker.HelloDocker"]