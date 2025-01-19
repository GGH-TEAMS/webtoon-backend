FROM openjdk:17-slim AS base

FROM base AS builder
WORKDIR /app

COPY . /app
RUN /app/gradlew :app:webtoon-api:clean :app:webtoon-api:build --no-daemon

FROM base
WORKDIR /app
COPY --from=builder /app/app/webtoon-api/build/libs/webtoon-api-0.0.1.jar /app/app.jar
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -jar app.jar"]

