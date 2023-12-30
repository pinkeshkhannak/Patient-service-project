FROM openjdk:latest
WORKDIR /app
COPY target/springboot-images-new.jar /app/springboot-images-new.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/springboot-images-new.jar"]
