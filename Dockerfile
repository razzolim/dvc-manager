FROM openjdk:17-jdk-slim-buster
WORKDIR /app
ARG JAR_FILE=target/dvc.manager-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]