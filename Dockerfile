FROM maven:3.9.3-eclipse-temurin-17-focal as build

COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src src

RUN --mount=type=cache,target=/root/.m2,rw mvn compile -DskipTests

FROM eclipse-temurin:17-jre-jammy

WORKDIR root

COPY --from=build target/classes/ch/frankel/blog/loki/Main.class ch/frankel/blog/loki/Main.class

ADD https://repo1.maven.org/maven2/org/slf4j/slf4j-api/2.0.7/slf4j-api-2.0.7.jar lib/
ADD https://repo1.maven.org/maven2/ch/qos/logback/logback-core/1.4.8/logback-core-1.4.8.jar lib/
ADD https://repo1.maven.org/maven2/ch/qos/logback/logback-classic/1.4.8/logback-classic-1.4.8.jar lib/
ADD https://repo1.maven.org/maven2/com/github/loki4j/loki-logback-appender/1.4.0/loki-logback-appender-1.4.0.jar lib/

ENTRYPOINT [ "java", "-cp", ".:lib/*", "ch.frankel.blog.loki.Main"]
