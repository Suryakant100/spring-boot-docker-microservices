# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/configServer-0.0.1-SNAPSHOT.jar /app/configServer-0.0.1-SNAPSHOT.jar

EXPOSE 8084

# Specify the default command to run when the container starts
CMD ["java", "-jar", "configServer-0.0.1-SNAPSHOT.jar"]