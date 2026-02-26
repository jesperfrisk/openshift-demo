FROM maven:3.9-eclipse-temurin-20
WORKDIR /app
COPY . /app
RUN chgrp -R 0 /app && chmod -R g=u /app
ENV HOME=/tmp
ENV MAVEN_OPTS="-Dmaven.repo.local=/tmp/.m2/repository"
CMD ["mvn", "-B", "test"]