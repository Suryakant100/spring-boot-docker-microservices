# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/companyMS-0.0.1-SNAPSHOT.jar /app/companyMS-0.0.1-SNAPSHOT.jar

EXPOSE 9091

# Specify the default command to run when the container starts
CMD ["java", "-jar", "companyMS-0.0.1-SNAPSHOT.jar"]