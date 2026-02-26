FROM maven:3.9-eclipse-temurin-17

WORKDIR /app
COPY . /app

ENV HOME=/tmp
ENV MAVEN_OPTS="-Dmaven.repo.local=/tmp/.m2/repository"

CMD ["bash", "-lc", "cp -R /app /tmp/app && cd /tmp/app && mvn -B test"]