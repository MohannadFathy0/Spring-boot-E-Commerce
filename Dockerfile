# Use an official OpenJDK image as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project to the working directory
COPY . .

# Build the project inside the container (for Maven)
RUN ./mvnw clean package

# Alternatively, for Gradle:
# RUN ./gradlew build

# Make sure to copy the jar file correctly
# Check the correct path to your jar file after the build
# For Maven, the jar file is located in /app/target/
# Adjust this line if necessary
COPY target/e-commerce-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 to the world outside this container
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
