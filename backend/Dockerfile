FROM openjdk:11 as base
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline

COPY src ./src

FROM base as build
RUN ./mvnw install -DskipTests

FROM adoptopenjdk/openjdk11:jre-11.0.11_9-alpine as production
WORKDIR /app
EXPOSE 8080
COPY --from=build /app/target/*.jar ./
CMD ["java", "-jar", "/app/Ecc.jar"]
