FROM gradle:8.1-jdk17 AS build
WORKDIR /app
COPY . .

RUN chmod +x gradlew
RUN ./gradlew build --no-daemon

FROM eclipse-temurin:17-jdk AS runtime
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]

