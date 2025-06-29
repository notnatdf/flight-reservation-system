FROM gradle:8.7.0-jdk21 AS build
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
RUN ./gradlew dependencies
COPY src/main/java/com/notnatdf/flightreservationsystem .
RUN ./gradlew clean build -x test

FROM openjdk:21-jre-slim
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]