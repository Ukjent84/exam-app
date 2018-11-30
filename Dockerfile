FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/MovieRest-exec.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
