FROM openjdk:latest
ARG JAR_FILE=target/*.jar
COPY target/springboot-images-new.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app.jar"]
