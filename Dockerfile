FROM openjdk:21-jdk-slim
LABEL authors="eddie"

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} cropper.jar

ENTRYPOINT ["java", "-jar", "cropper.jar"]
