# Use Eclipse Temurin JDK 21 as the base image
# This image contains a full JDK, required for compiling Java sources
FROM eclipse-temurin:21-jdk

# Set the working directory inside the container
# All subsequent commands will be executed relative to this path
WORKDIR /app

# Copy only the server source code into the container
# The build context is the 'server' directory, so 'src' is available here
COPY src ./src

# Compile the Java module
# -d out            → output directory for compiled classes
# module-info.java → defines the Java module (JPMS)
# HelloDockerServer → main class of the server application
RUN javac \
    -d out \
    src/hello_docker/server/module-info.java \
    src/hello_docker/server/HelloDockerServer.java

# Run the application as a Java module
# -p out → module path
# -m     → module name and main class
CMD ["java", "-p", "out", "-m", "hello_docker/hello_docker.server.HelloDockerServer"]

