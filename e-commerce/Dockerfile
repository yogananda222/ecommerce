# Use a base image with OpenJDK
FROM openjdk:17-jdk-slim as builder

# Set the working directory
WORKDIR /app

# Copy the project files to the container
COPY . .

# Build the Spring Boot application
RUN ./mvnw clean install -DskipTests

# Run the jar file when the container starts
ENTRYPOINT ["java", "-jar", "target/e-commerce-0.0.1-SNAPSHOT.jar"]
