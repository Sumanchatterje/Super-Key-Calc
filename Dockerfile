# Use an official OpenJDK runtime as a base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/Super_Keys-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port (Render automatically assigns one)
EXPOSE 8080

# Set the entry point command to run the application
CMD ["java", "-jar", "app.jar"]
