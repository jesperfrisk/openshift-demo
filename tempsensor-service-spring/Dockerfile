FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /workspace
LABEL authors="Samed Salman"

COPY . .

RUN mvn -DskipTests package

FROM maven:3.9-eclipse-temurin-17
WORKDIR /app
COPY --from=build /workspace/target/ /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/tempsensor-service-spring-0.0.1-SNAPSHOT.jar"]