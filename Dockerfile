FROM gradle:8.7.0-jdk17-alpine AS build

WORKDIR /app

COPY build.gradle.kts settings.gradle.kts /app/

COPY src /app/src

RUN gradle assemble --no-daemon


FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]