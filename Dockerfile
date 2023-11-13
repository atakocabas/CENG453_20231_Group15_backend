# Stage 1: Build the application
FROM maven:3.9-amazoncorretto-21-al2023 as builder

# Copy the project files to the container
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

# Set the working directory
WORKDIR /usr/src/app

# Build the application
RUN mvn clean package

# Stage 2: Create the final image with the built JAR file
FROM openjdk:21-jdk-slim

# Create a volume
VOLUME /app

# Copy the JAR file from the builder stage
COPY --from=builder /usr/src/app/target/*.jar app.jar

# Set the entrypoint to run the application
ENTRYPOINT ["java","-jar","/app.jar"]

# Expose the port the application runs on
EXPOSE 8080
