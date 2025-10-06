# Step 1: Use an OpenJDK image
FROM openjdk:17-jdk-slim

# Step 2: Set working directory inside the container
WORKDIR /app

# Step 3: Copy the built JAR from target (Render will build it for us)
COPY target/*.jar app.jar

# Step 4: Expose the default Spring Boot port
EXPOSE 8080

# Step 5: Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
