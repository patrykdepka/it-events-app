FROM maven:3.9-amazoncorretto-17 AS builder
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B
COPY ./src ./src
RUN mvn clean package

FROM amazoncorretto:17.0.9-alpine3.18
COPY --from=builder target/it-events-app-*.jar /it-events-app.jar
ENTRYPOINT ["java", "-jar", "it-events-app.jar"]
