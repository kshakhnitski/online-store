FROM openjdk:17-jdk-slim AS build

WORKDIR /build
COPY . .

RUN ./gradlew --no-daemon build

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /build/build/libs/*.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]