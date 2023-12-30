FROM openjdk:latest
ARG JAR_FILE=target/*.jar
ADD target/springboot-images-new.jar springboot-images-new.jar
EXPOSE 8080
CMD ["java", "-jar", "/springboot-images-new.jar"]
