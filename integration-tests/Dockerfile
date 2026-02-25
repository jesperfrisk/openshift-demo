FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /workspace

COPY . .

RUN mvn -B -DskipTests=false test-compile

FROM eclipse-temurin:17-jre
WORKDIR /work
COPY --from=build /workspace/ /work/

CMD ["bash", "-lc", "mvn -B test"]
