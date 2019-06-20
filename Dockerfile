FROM openjdk:12 as runtime
EXPOSE 8080

COPY /target/test-project-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]