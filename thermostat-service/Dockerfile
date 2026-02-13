# ---------- build stage ----------
FROM registry.access.redhat.com/ubi9/openjdk-17:1.23 AS build
USER root
WORKDIR /project

# Copy source
COPY . .

# If you have a Maven wrapper, prefer it; otherwise you need Maven available.
# Quarkus projects often use ./mvnw
RUN ./mvnw -DskipTests package

# ---------- runtime stage ----------
FROM registry.access.redhat.com/ubi9/openjdk-17:1.23

ENV LANGUAGE='en_US:en'

COPY --from=build --chown=185 /project/target/quarkus-app/lib/ /deployments/lib/
COPY --from=build --chown=185 /project/target/quarkus-app/*.jar /deployments/
COPY --from=build --chown=185 /project/target/quarkus-app/app/ /deployments/app/
COPY --from=build --chown=185 /project/target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
USER 185
ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"

ENTRYPOINT [ "/opt/jboss/container/java/run/run-java.sh" ]
