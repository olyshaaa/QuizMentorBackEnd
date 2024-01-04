FROM openjdk:17-jre-slim
WORKDIR /app
RUN mkdir /app
COPY target/backendQuizMentor-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
