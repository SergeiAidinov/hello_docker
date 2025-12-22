# Use Eclipse Temurin JDK 21 as the base image
# A full JDK is required to compile Java source files inside the container
FROM eclipse-temurin:21-jdk

# Set the working directory inside the container
# All build and runtime commands will be executed relative to this directory
WORKDIR /app

# Copy the client source code into the container
# The build context is the 'client' directory, so 'src' is available here
COPY src ./src

# Compile the Java client module
# -d out             → output directory for compiled classes
# module-info.java  → defines the Java module (JPMS)
# HelloDockerClient → main class of the client application
RUN javac \
    -d out \
    src/hello_docker/client/module-info.java \
    src/hello_docker/client/HelloDockerClient.java

# Run the application as a Java module
# -p out → module path
# -m     → module name and main class
CMD ["java", "-p", "out", "-m", "hello_docker/hello_docker.client.HelloDockerClient"]
