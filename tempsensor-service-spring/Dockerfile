FROM dhi.io/maven:3-jdk25-debian13-dev AS build
WORKDIR /workspace
LABEL authors="Samed Salman"

COPY . .

RUN mvn -DskipTests package

FROM dhi.io/maven:3-jdk25-debian13-dev
WORKDIR /app
COPY --from=build /workspace/target/ /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/tempsensor-service-spring-0.0.1-SNAPSHOT.jar"]