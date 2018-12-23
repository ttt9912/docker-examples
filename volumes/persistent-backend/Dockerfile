FROM openjdk:8-jdk-alpine
MAINTAINER ttt
VOLUME /tmp
COPY target/persistent-backend-0.0.1-SNAPSHOT.jar /home/persistent-backend.jar
CMD ["java","-jar","/home/persistent-backend.jar"]