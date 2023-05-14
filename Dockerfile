FROM openjdk:17-jdk-alpine

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]