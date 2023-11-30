FROM openjdk:17-alpine

COPY target/user-service-1.0.jar application.jar

CMD java -jar /application.jar