# Сборка с улучшенным кэшированием
FROM gradle:9.2.1-jdk25-alpine AS build

WORKDIR /app

ENV GRADLE_USER_HOME /app/.gradle

COPY gradlew .
COPY gradle/wrapper/gradle-wrapper.properties gradle/wrapper/
COPY gradle/wrapper/gradle-wrapper.jar gradle/wrapper/

COPY build.gradle settings.gradle ./

RUN ./gradlew dependencies --no-daemon || return 0

COPY src ./src

RUN ./gradlew clean bootJar --no-daemon

FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

# otel java agent
RUN mkdir -p /otel
COPY opentelemetry-javaagent.jar /otel/opentelemetry-javaagent.jar

EXPOSE 8081

RUN addgroup -S app && adduser -S app -G app
USER app:app

ENTRYPOINT ["java","-javaagent:/otel/opentelemetry-javaagent.jar","-jar","/app/app.jar"]