FROM openjdk:17-alpine
ARG JAR_FILE=/target/*.jar
WORKDIR /app
COPY ${JAR_FILE} app.jar
COPY application.properties .
EXPOSE 8080
ENTRYPOINT ["java", "-Djava.security.egd=file:dev/./urandom","-jar", "app.jar"] 
