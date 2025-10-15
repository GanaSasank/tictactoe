# Use official OpenJDK 17 image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make mvnw executable
RUN chmod +x mvnw

# Download dependencies (offline mode)
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Build the Spring Boot jar
RUN ./mvnw package -DskipTests

# Expose port 8080
EXPOSE 8080

# Command to run the app
CMD ["java", "-jar", "target/tictactoe-0.0.1-SNAPSHOT.jar"]
