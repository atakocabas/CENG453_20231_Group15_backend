FROM openjdk:21-jre-slim
VOLUME /app
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080