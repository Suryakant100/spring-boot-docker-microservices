# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/api-gateway-0.0.1-SNAPSHOT.jar /app/api-gateway-0.0.1-SNAPSHOT.jar

EXPOSE 9090

# Specify the default command to run when the container starts
CMD ["java", "-jar", "api-gateway-0.0.1-SNAPSHOT.jar"]