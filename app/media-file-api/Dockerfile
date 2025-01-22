FROM openjdk:17-slim AS base

FROM base AS builder
WORKDIR /app

COPY . /app
RUN /app/gradlew :app:media-file-api:clean :app:media-file-api:build --no-daemon

FROM base
WORKDIR /app
COPY --from=builder /app/app/media-file-api/build/libs/media-file-api-0.0.1.jar /app/app.jar
EXPOSE 8081

ENTRYPOINT ["sh", "-c", "java -jar app.jar"]

