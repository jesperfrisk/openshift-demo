FROM maven:3.9-eclipse-temurin-17

WORKDIR /app

COPY pom.xml .
RUN mvn -B -q -DskipTests dependency:go-offline

COPY src ./src

ENV HOME=/tmp
ENV MAVEN_OPTS="-Dmaven.repo.local=/tmp/.m2/repository"

CMD ["mvn", "-B", "test"]