FROM maven:3.9-amazoncorretto-17 AS builder
COPY . .
RUN mvn clean package

FROM amazoncorretto:17.0.9-alpine3.18
COPY --from=builder target/it-events-app-*.jar /it-events-app.jar
ENTRYPOINT ["java", "-jar", "it-events-app.jar"]
