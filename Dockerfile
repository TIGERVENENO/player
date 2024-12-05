## Use an official Java runtime as a parent image
#FROM eclipse-temurin:21-jre
#
## Set the working directory
#WORKDIR /app
#
## Copy the application JAR file to the container
#COPY target/*.jar app.jar
#
## Expose the application's port
#EXPOSE 8080
#
## Run the application
#CMD ["java", "-jar", "app.jar"]
