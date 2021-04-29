FROM openjdk:11
ARG JAR_FILE=build/libs/mgp-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]